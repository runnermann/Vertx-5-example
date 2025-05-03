
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

export default class Image_Typewriter {

    #eraseSpeed     = 16; // Erase speed in milliseconds
    #typeSpeed      = 96; // Typing speed in milliseconds
    // set in the constructor
    #phrases        = [""];
    #phraseCount    = 0;
    #typewriterElement = null;
    #callback           = null;
    // The form in the modal
    #typewriterModal = null;
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
    #displayId          = null;

    constructor(phrases, typeWriterElement, callback, displayElement) {
        this.#phrases           = phrases || [""];
        this.#phraseCount       = phrases.length;
        this.#phrasesIndex      = 0;
        this.#typewriterElement = typeWriterElement;
        this.#callback          = callback;
        this.#eraseStartIndex   = this.#phrases[0].length - 1;
        this.#eraseIndex        = this.#eraseStartIndex;
        this.#typewriterElement.innerHTML = "";
        this.#typing            = true;
        this.#typewriterModal   = document.getElementById("imgwriterModalDialog");
        this.#displayId         = displayElement;
        // Initialize the modal close listener
        this.#initModalListener();
    }

    // ----- PUBLIC FUNCTIONS ------- //

    async startImgWriter () {
        await this.#typewriterImage();
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
     * Call this method to track the closure of the modal
     */
    #initModalListener() {
        if (!this.#typewriterModal) return;

        const handleModalHidden = () => this.#resetFields();
        this.#typewriterModal.addEventListener('hidden.bs.modal', handleModalHidden);
    }
    /**
     * Reset fields to null when the modal is closed
     */
    #resetFields() {
        if (this.#displayId) {
            // Clear only listeners and properties associated with this Image_Typewriter instance
            this.#displayId.src = ""; // Example: Reset the image
        }

        this.#phrases = null;
        this.#phraseCount = null;
        this.#typewriterElement = null;
        this.#callback = null;
        this.#typewriterModal = null;
        this.#phrasesIndex = null;
        this.#eraseStartIndex = null;
        this.#eraseIndex = null;
        this.#typing = null;
        this.#isLast = null;
        this.#typeInp = null;
        this.#buildBefore = null;
        this.#buildAfter = null;
        this.#displayId = null;
    }


    /**
     * Builds the question in a conversational style. Based on the qArrays.
     * At the end of the array, An entry form is provided, unless it is the
     * last array. At the end of a string, the cursor will blink, then the
     * erase is called. After the erase, the new string is called. The cursor
     * will hide, and focus will be given to the input field.
     * @returns {Promise<void>}
     */
    async #typewriterImage() {
        const cursor    = document.getElementById("img_cursor");
        const input = document.getElementById("name-entry");
        const div = document.getElementById("formHidden");
        this.#hideButton();
        this.#hideForm(div, input);
        // Iterate through the phrases of this array.
        // We cannot use a forloop for phrases when we are calling the method for every use.
        if (this.#typing && this.#isVisible()) {
            // Remove blinking cursor animation while typing
            if (cursor) {
                cursor.style.visibility = "visible";
                cursor.style.opacity    = 1;
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
            if (cursor) cursor.classList.add("blink");
            if(this.#hasMore()) {
                await this.#sleep(2400);
                // wait until the text has been erased, then
                // start the next loop.
                await this.#erase();
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
                if(!this.#isLast) {
                    setTimeout(() => this.#formHandler(), 264);
                }
            }
        } else {
            console.error("The module is not visible");
        }
    }

    #erase = async _ => {
        try {
            if (!this.#typewriterElement || !this.#phrases || !Array.isArray(this.#phrases)) {
                console.error("Invalid state: Typewriter element or phrases are not initialized properly");
                return;
            }

            // Erase logic
            if (this.#eraseIndex >= 0) {
                const line = this.#typewriterElement.innerHTML;
                this.#typewriterElement.innerHTML = line.substring(0, this.#eraseIndex);
                this.#eraseIndex--;

                // Schedule the next erase iteration
                setTimeout(() => this.#erase(), this.#eraseSpeed);

            } else { // Reset logic when line is fully erased
                this.#eraseIndex = this.#phrases[this.#phrasesIndex]
                    ? this.#phrases[this.#phrasesIndex].length - 1
                    : this.#eraseStartIndex;

                this.#buildBefore = "";
                this.#buildAfter = "";
                this.#phrasesIndex = (this.#phrasesIndex + 1) % this.#phrases.length;
                this.#typing = true;

                // Schedule the next typewriterImage iteration
                setTimeout(() => this.#typewriterImage(), this.#typeSpeed);
            }
        } catch (error) {
            console.error("Error in #erase:", error);
        }
    }

    #hideButton() {
        const divBtm = document.getElementById("divBottom");
        divBtm.style.display = "none";
        divBtm.style.visibility = "hidden";
        divBtm.style.opacity = 0;
    }

    // Make 'send button' visible and wait
    #showButton(file) {
        const divBtm = document.getElementById("divBottom");
        const goBtn = document.getElementById('goBtn');
        divBtm.style.display = "block";
        divBtm.style.visibility = "visible";
        divBtm.style.opacity = 1;

        goBtn.onclick = () => {
            this.#handleNext(file);
        };
    }

    /**
     * Displays the answer form
     */
    async #formHandler () {
        const input = document.getElementById("img-input");
        if (input) {
            try {
                await this.#showForm();
            } catch (error) {
                console.error("Still not focused! caused an error.", error)
            }
        } else {
            console.error("Element with ID 'img-uploader' not found.");
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

    async #showForm() {
        await this.#initDragDropUploader();
        const div = document.getElementById("img-uploader");
        const input = document.getElementById("img-input");
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

    #hideForm() {
        const div = document.getElementById("img-uploader");
        const input = document.getElementById("img-input");
        div.style.display = "none";
        div.style.visibility = "hidden";
        div.style.opacity = 0;
        input.style.visibility = "hidden";
        input.style.opacity = 0;
        input.value = "";
        input.classList.remove("show");
    }

    // Image Drag and Drop related
    async #initDragDropUploader() {
        const uploader = document.getElementById("img-uploader");
        const input = document.getElementById("img-input");

        // Handle drag-and-drop
        uploader.addEventListener("dragover", (e) => {
            e.preventDefault();
            e.stopPropagation();
            uploader.classList.add("drag-over");
        });

        uploader.addEventListener("dragleave", (e) => {
            e.preventDefault();
            e.stopPropagation();
            uploader.style.borderColor = "#ccc";
        });

        uploader.addEventListener("drop", async (e) => {
            e.preventDefault();
            e.stopPropagation();
            uploader.classList.remove('drag-over');

            this.files = e.dataTransfer.files;
            if (this.files.length) {
                let file = this.files[0];
                if (!file.type.startsWith("image/")) {
                    alert("Please upload a valid image file.");
                    return;
                }
                this.#showNewImage(file);
                this.#showButton(file);
            }
        });

        input.addEventListener("change", (e) => {
            if (e.target.files[0].length > 0) {
                this.file = e.dataTransfer.files[0];
                if (!this.file.type.startsWith("image/")) {
                    alert("Please upload a valid image file.");
                    return;
                }
                this.#showButton(this.file);
            }
            else {
                console.error("There is more than one file uploaded. num files= " + e.target.files.length )
            };
        });
    }

    #showNewImage(file) {
        this.reader = new FileReader();
        // On successful file read
        this.reader.onload = (e) => {
            this.imageDataUrl = e.target.result; // The base64 image data
            // Check if displayId is set
            if (this.#displayId) {
                this.imageElement = document.getElementById(this.#displayId); // Target the element by its ID

                // Set the src attribute of the image element to display the image
                if(this.imageElement.src) { this.imageElement.src = "" }
                // Set the src attribute of the image element to display the image
                this.imageElement.src = this.imageDataUrl;
                if (! this.imageElement.tagName === 'IMG') {
                    console.error(`Element with ID "${this.#displayId}" is not an <img> element.`);
                }
            } else {
                console.error("Display ID not set. Cannot display the image.");
            }
        };

        this.reader.onerror = () => {
            console.error("Error reading the image file.");
        };

        // Read the file as a Data URL
        this.reader.readAsDataURL(file);
    }


    /**
     * Send the image to the controller. Get the next phrase from the
     * controller and start the typewriter.
     * @param file
     * @returns {Promise<void>} The next phrase.
     */
    async #handleNext (file) {
        // Send the image/file to the controller, return the next phrase, isLast next[1]
        const next = await this.#callback(file); // Call the callback, Returns the next phrase.

        if (Array.isArray(next) && next[0] && next[0].length > 0) {
            this.#phrases = next[0]; // Update the questions
            this.#isLast  = next[1];
            this.#displayId = next[2];
            this.#phrasesIndex = 0; // Reset the index for the new set
            this.#phraseCount  = this.#phrases.length;
            this.#typing = true;
            this.#eraseStartIndex = this.#phrases[0].length - 1;
            this.#eraseIndex = this.#eraseStartIndex;
            this.#typewriterElement.innerHTML = "";
            // Start the new set of questions
            setTimeout(() => {
                try {
                    this.#hideForm();
                } catch (error) {
                    console.error("Error hiding form:", error);
                }
            }, 264);

            this.#typewriterImage();
        }
        else {
            this.#hideButton();
            this.#hideForm();
            this.#typewriterElement.innerText =
                "Ooops! Something went wrong.";
        }
    }
}