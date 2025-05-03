/*
*  This typewritter() types forwards and backwards.
*
* @Copyright Lowell Stadelman, KnoC Inc. 2020-2024.
* License is 'Do what the 'F' you want. Please give
* credit to the original author.
*
*  This typewriter effect types out the words very quickly
* and erases them even faster. It pauses between the phrases.
*
* At the end of a phrase, it shows an input. The input is returned
* in a callback that is set in the constructor. If it is the
* last phrase, it shows a button that will send it to the next
* page.
*
*
*/

export default class Form_Typewriter {

    private eraseSpeed: number = 16; // Erase speed in milliseconds
    private typeSpeed: number = 96; // Typing speed in milliseconds
    // set in the constructor
    private phrases: string[] = [""];
    private phraseCount: number = 0;
    private typewriterElement: HTMLElement | null = null;
    private callback: ((response: any) => void) | null = null;
    // Store the users response
    private response: string | null = null;
    // The form in the modal
    private typewriterModal: HTMLElement | null = document.getElementById("typewriterModalDialog");
    // Operation of the typewriter and erase
    private phrasesIndex: number = 0;
    private eraseStartIndex: number = 0;
    private eraseIndex: number = 0;
    private typing: boolean = true;
    // the last one in the series.
    private isLast: boolean = false;
    private typeInp: string = "text";
    private responses: string[] = [""];

    constructor(hasButtonEnding: boolean, phrases: string[], typeWriterElement: HTMLElement, callback: (response: any) => void) {
        //this.hasButton = hasButtonEnding || true;
        this.phrases = phrases || [""];
        this.phraseCount = phrases.length;
        this.phrasesIndex = 0;
        this.typewriterElement = typeWriterElement;
        this.callback = callback;
        this.eraseStartIndex = this.phrases[0].length - 1;
        this.eraseIndex = this.eraseStartIndex;
        this.typewriterElement.innerHTML = "";
        this.typing = true;
        this.responses = [""];
    }

    async startForm(): Promise<void> {
        await this.typewriterForm();
    }

    private sleep = (ms: number): Promise<void> => {
        return new Promise((resolve) => setTimeout(resolve, ms));
    };

    private getWord = (idx: number, words: string[]): Promise<string> => {
        return this.sleep(this.typeSpeed).then(() => words[idx]);
    };

    private getChar = (idx: number, word: string): Promise<string> => {
        return this.sleep(32).then(() => word[idx]);
    };

    private hasMore(): boolean {
        return this.phraseCount - 1 !== this.phrasesIndex;
    }

    /**
     * Builds the question in a conversational style. Based on the qArrays.
     * At the end of the array, An entry form is provided, unless it is the
     * last array. At the end of a string, the cursor will blink, then the
     * erase is called. After the erase, the new string is called. The cursor
     * will hide, and focus will be given to the input field.
     * @returns {Promise<void>}
     */
    private async typewriterForm(): Promise<void> {
        const div = document.getElementById("formHidden") as HTMLElement;
        const input = document.getElementById("name-entry") as HTMLInputElement;
        const cursor = document.getElementById("tp_cursor") as HTMLElement;
        this.hideButton();
        this.hideForm(div, input);
        if (this.typing && this.isVisible()) {
            if (cursor) {
                cursor.style.visibility = "visible";
                cursor.style.opacity = "1";
                cursor.classList.remove("blink");
            }
            const phrase = this.phrases[this.phrasesIndex];
            const words = phrase.split(" ");
            for (let wordsIdx = 0; wordsIdx < words.length; wordsIdx++) {
                const word = (await this.getWord(wordsIdx, words)) + " ";
                for (let charIdx = 0; charIdx < word.length; charIdx++) {
                    const char = await this.getChar(charIdx, word);
                    this.typewriterElement!.innerHTML += char;
                }
            }

            this.typing = false;
            if (cursor) {
                cursor.classList.add("blink");
            }
            if (this.hasMore()) {
                this.showButton();
            } else {
                if (cursor) {
                    cursor.style.visibility = "hidden";
                    cursor.style.opacity = "0";
                }
                setTimeout(() => this.formHandler(), 264);
            }
        } else {
            console.error("The module is not visible");
        }
    }

    private erase = async (): Promise<void> => {
        if (this.eraseIndex >= 0) {
            const line = this.typewriterElement!.innerHTML;
            this.typewriterElement!.innerHTML = line.substring(0, this.eraseIndex);
            this.eraseIndex--;
            setTimeout(await this.erase, this.eraseSpeed);
        } else {
            this.eraseIndex = this.phrases[this.phrasesIndex] ? this.phrases[this.phrasesIndex].length - 1 : this.eraseStartIndex;
            this.phrasesIndex++;
            this.typing = true;
            setTimeout(async () => await this.typewriterForm(), this.typeSpeed);
        }
    };

    /**
     * 1. Displays the answer form
     * 2. listens for the response
     * 3. handles the response
     */
    private async formHandler(): Promise<void> {
        const div = document.getElementById("formHidden") as HTMLElement;
        const input = document.getElementById("name-entry") as HTMLInputElement;
        if (input) {
            try {
                this.showForm(div, input);
                try {
                    this.response = await this.waitForUserResponse();
                    this.responses.push(this.response);
                } catch (err) {
                    console.error("Error on await: ", err);
                }
                await this.handleNext(div, input);
            } catch (error) {
                console.error("Still not focused! caused an error.", error);
            }
        } else {
            console.error("Element with ID 'formHidden' not found.");
        }
    }

    async waitForUserResponse(): Promise<string> {
        return new Promise((returnIt) => {
            const input = document.getElementById("name-entry") as HTMLInputElement;
            input.addEventListener("keydown", async (e: KeyboardEvent) => {
                if (e.key === "Enter" && input.value.trim() !== "") {
                    const response = input.value.trim();
                    returnIt(response);
                }
            });
        });
    }

    async handleNext(div: HTMLElement, input: HTMLInputElement): Promise<void> {
        const next = await this.callback!(this.response);
        if (Array.isArray(next) && next[0] && next[0].length > 0) {
            setTimeout(() => this.hideForm(div, input), 264);
            this.phrases = next[0];
            this.isLast = next[1];
            this.typeInp = next[2];
            this.phrasesIndex = 0;
            this.phraseCount = this.phrases.length;
            this.typing = true;
            this.eraseStartIndex = this.phrases[0].length - 1;
            this.eraseIndex = this.eraseStartIndex;
            this.typewriterElement!.innerHTML = "";
            await this.typewriterForm();
        } else {
            this.typewriterElement!.innerText = "Thank you! All questions are completed.";
        }
    }

    private isVisible(): boolean {
        if (!this.typewriterModal) return false;
        return (
            this.typewriterModal.offsetParent !== null && document.contains(this.typewriterModal)
        );
    }

    private showForm(div: HTMLElement, input: HTMLInputElement): void {
        div.style.display = "block";
        div.style.visibility = "visible";
        div.style.opacity = "1";
        input.style.visibility = "visible";
        input.style.opacity = "1";
        input.type = this.typeInp;
        input.classList.add("show");
        input.focus();
    }

    private hideForm(div: HTMLElement, input: HTMLInputElement): void {
        div.style.display = "none";
        div.style.visibility = "hidden";
        div.style.opacity = "0";
        input.style.visibility = "hidden";
        input.style.opacity = "0";
        input.value = "";
        input.classList.remove("show");
    }

    private hideButton(): void {
        const divBtm = document.getElementById("divBottom") as HTMLElement;
        divBtm.style.display = "none";
        divBtm.style.visibility = "hidden";
        divBtm.style.opacity = "0";
    }

    private showButton(): void {
        const divBtm = document.getElementById("divBottom") as HTMLElement;
        const goBtn = document.getElementById("submit-form") as HTMLButtonElement;
        divBtm.style.display = "block";
        divBtm.style.visibility = "visible";
        divBtm.style.opacity = "1";

        goBtn.addEventListener("click", () => {
            this.callProfileBuilder();
        });
    }

    private callProfileBuilder(): void {
        const payload = {
            name: "wolf runnermann",
            email: "fast@flashmonkey.xyz",
        };
        fetch("/BLDR1609", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(payload),
        })
            .then((response) => response.text())
            .then((html) => {
                document.open();
                document.write(html);
                document.close();
            })
            .catch((error) => {
                console.error("Error during profile builder process:", error);
                alert("Failed to process the request. Please try again.");
            });
    }
}