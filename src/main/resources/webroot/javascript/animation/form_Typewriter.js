
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

    #sleepTime      = 2400;
    #eraseSpeed     = 16; // Erase speed in milliseconds
    #typeSpeed      = 96; // Typing speed in milliseconds
    // set in the constructor
    #phrases        = [""];
    #phraseCount    = 0;
    #typewriterElement = null;
    #callback          = null;
    // The form in the modal
    #typewriterModal = document.getElementById("typewriterModalDialog");
    // Operation of the typewriter and erase
    #phrasesIndex   = 0;
    #eraseStartIndex= 0;
    #eraseIndex     = 0;
    #typing         = true;
    // the last one in the series.
    #isLast         = false;
    #typeInp          = "text";
    #buildBefore      = "";
    #buildAfter       = "";
    #responses       =[""];
    // Store the users response
    #response          = null;



    constructor(hasButtonEnding, phrases, typeWriterElement, lastButtonCallback, callback) {
        this.hasButton          = hasButtonEnding || true;
        this.#phrases           = phrases || [""];
        this.#phraseCount       = phrases.length;
        this.#phrasesIndex      = 0;
        this.#typewriterElement = typeWriterElement;
        this.#eraseStartIndex   = this.#phrases[0].length - 1;
        this.#eraseIndex        = this.#eraseStartIndex;
        this.#typewriterElement.innerHTML = "";
        this.#typing            = true;
        this.#responses         = [""];
        this.buttonCallbackFn   = lastButtonCallback; // Store the callback
        this.#callback          = callback;
    }

    // ----- PUBLIC FUNCTIONS ------- //

    async startForm () {
        await this.#typewriterForm();
    }

    // ----- PRIVATE FUNCTIONS ------- //

    #sleep = ms => {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    #getWord = (idx, words) => {
        return this.#sleep(this.#typeSpeed).then(v => words[idx]);
    }

    #getChar = (idx, word) => {
        return this.#sleep(32).then(v => word[idx]);
    }

    #hasMore() {
        return this.#phraseCount -1 !== this.#phrasesIndex;
    }

    /**
     * Builds the question in a conversational style. Based on the qArrays.
     * At the end of the array, An entry form is provided, unless it is the
     * last array. At the end of a string, the cursor will blink, then the
     * erase is called. After the erase, the new string is called. The cursor
     * will hide, and focus will be given to the input field.
     * @returns {Promise<void>}
     */
    async #typewriterForm() {
        const div = document.getElementById("formHidden");
        const input = document.getElementById("name-entry");
        const cursor = document.getElementById("tp_cursor");
        const removeForDev = document.getElementById("hash");
        this.#hideButton();
        this.#hideForm(div, input);
        // Iterate through the phrases of this array.
        // We cannot use a forloop for phrases when we are calling the method for every use.
        if (this.#typing && this.#isVisible()) {
            // Remove blinking cursor animation while typing
            if (cursor) {
                cursor.style.visibility = "visible";
                cursor.style.opacity = 1;
                cursor.classList.remove("blink");
            }
            // Split phrases into a single phrase
            // until we get words and characters.
            const phrase = this.#phrases[this.#phrasesIndex];
            const words = phrase.split(" ");
            // iterate through the words of this phrase
            for (let wordsIdx = 0; wordsIdx < words.length; wordsIdx++) {
                const word = await this.#getWord(wordsIdx, words) + " ";
                // Iterate and add each letter
                for (let charIdx = 0; charIdx < word.length; charIdx++) {
                    const char = await this.#getChar(charIdx, word);
                    this.#typewriterElement.innerHTML += char;
                }
            }

            this.#typing = false;
            if (cursor) {
                cursor.classList.add("blink");
            }
            // ----------- REMOVE -----------//
            // Remove this if when deploying.-//
            // Stops the introForm from moving on
            // to next phrases. And shows the final
            // show button.
            if(removeForDev === null) {
                this.#showButton();
            }
            // ----- END REMOVE ------ //

            // !!! Remove the else if. Should be if(... !!! -//
            else if(this.#hasMore()) {
                await this.#sleep(this.#sleepTime);
                // wait until the text has been erased, then
                // start the next loop.
                await this.#erase();
            } 
            else if (this.#isLast) {
                // Show next button.
                if(this.hasButton) {
                        this.#showButton();
                }
            }
            else {
                // When the phrases are completed,
                // 1. show the form.
                // 2. Stop the blinking cursor on the phrase so the blinking cursor on the
                // input is the only one that is blinking.
                if (cursor) {
                    cursor.style.visibility = "hidden";
                    cursor.style.opacity = 0;
                }
                setTimeout(() => this.#formHandler(), 264);
            }
        } else {
            console.error("The module is not visible");
        }

    }

    #erase = async _ => {
        // Erase counts backwards.
        if (this.#eraseIndex >= 0) {
            const line = this.#typewriterElement.innerHTML;
            this.#typewriterElement.innerHTML = line.substring(0, this.#eraseIndex);
            this.#eraseIndex--;
            setTimeout(await this.#erase, this.#eraseSpeed);
        } else { // line complete, reset
            this.#eraseIndex = this.#phrases[this.#phrasesIndex] ? this.#phrases[this.#phrasesIndex].length - 1 : this.#eraseStartIndex;
            this.#buildBefore = "";
            this.#buildAfter = "";
            this.#phrasesIndex++;
            this.#typing = true;
            setTimeout(async () => await this.#typewriterForm(), this.#typeSpeed);
        }
    }

    /**
     * 1. Displays the answer form
     * 2. listens for the response
     * 3. handles the response
     */
    async #formHandler () {
         const div = document.getElementById("formHidden");
         const input = document.getElementById("name-entry");
         if (input) {
                try {
                    this.#showForm(div, input);
                    try {
                        this.#response = await this.#waitForUserResponse();
                        this.#responses.push(this.#response);
                    } catch (err) {
                        console.error("Error on await: ", err)
                    }
                    await this.#handleNext(div, input);

                } catch (error) {
                    console.error("Still not focused! caused an error.", error)
                }
         } else {
            console.error("Element with ID 'formHidden' not found.");
         }
    }

     async #waitForUserResponse () {
        return new Promise((returnIt) => {
            const input = document.getElementById("name-entry");
            input.addEventListener("keydown", async (e) => {
                if (e.key === "Enter" && input.value.trim() !== "") {
                    const response = input.value.trim();

                    returnIt(response);
                }
            });
        });
    }


     async #handleNext (div, input) {
        const next = await this.#callback(this.#response); // Call the callback
         if (Array.isArray(next) && next[0] && next[0].length > 0) {
             setTimeout(() => this.#hideForm(div, input), 264);
            this.#phrases = next[0]; // Update the questions
            this.#isLast = next[1];
            this.#typeInp = next[2];
            this.#phrasesIndex = 0; // Reset the index for the new set
            this.#phraseCount = this.#phrases.length;
            this.#typing = true;
            this.#eraseStartIndex = this.#phrases[0].length - 1;
            this.#eraseIndex = this.#eraseStartIndex;
            this.#typewriterElement.innerHTML = "";
            await this.#typewriterForm(); // Start the new set of questions
        } else {
            this.#typewriterElement.innerText =
                "Thank you! All questions are completed.";
        }
    }

    // Is the modal visibile?
    #isVisible() {
        if (!this.#typewriterModal) return false;
        const rect = this.#typewriterModal.getBoundingClientRect();
        const style = window.getComputedStyle(this.#typewriterModal);

        const inViewport = (
            rect.top >= 0 &&
            rect.left >= 0 &&
            rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) &&
            rect.right <= (window.innerWidth || document.documentElement.clientWidth)
        );

        return true;
    }

    #showForm(div, input) {
        div.style.display = "block";
        div.style.visibility = "visible";
        div.style.opacity = 1;
        // modify input placeholder and type
        input.style.visibility = "visible";
        input.style.opacity = 1;
        input.type = this.#typeInp;
        input.classList.add("show");
        input.focus();
    }

    #hideForm(div, input) {
        div.style.display = "none";
        div.style.visibility = "hidden";
        div.style.opacity = 0;
        input.style.visibility = "hidden";
        input.style.opacity = 0;
        input.value = "";
        input.classList.remove("show");
    }

    #hideButton() {
        const divBtm = document.getElementById("divBottom");

        divBtm.style.display = "none";
        divBtm.style.visibility = "hidden";
        divBtm.style.opacity = 0;
    }

    // Make getBuilder button visible
    #showButton() {
        const divBtm = document.getElementById("divBottom");
        const goBtn = document.getElementById('submit-form');
        divBtm.style.display = "block";
        divBtm.style.visibility = "visible";
        divBtm.style.opacity = 1;

        goBtn.addEventListener('click', (e) => {
            this.buttonCallbackFn();
        });

    }
}