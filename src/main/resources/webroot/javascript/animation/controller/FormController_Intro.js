// ——————————————————————————————————————————————————
// Uses the word by word typewriter with letter by letter erase.
// Types out phrases that are contained in an array.
// 1. At the end of an array, it shows a form. The user response
// is captured and sent back to the controller when the user
// presses enter.
// ——————————————————————————————————————————————————
import Form_Typewriter from "../form_Typewriter.js";

class FormController_Intro {

    #questionPrompts =null;
    #typeInp        = null;
    #writerForm1     = null;
    #buttonOne = document.getElementById("button_one");
    #idx = 0;

    constructor() {
        this.#questionPrompts = [
            ["Welcome!",
                "Before we can build your KnocScore, we need to set up your free account."]
        ];

        this.#typeInp = ["text"];
        this.#writerForm1 = document.getElementById("form-question");
        this.responses = [""];

        this.#buttonOne.onclick = () => this.startForm();
    }

    startForm() {
        this.#idx = 0;
        this.#writerForm1.innerHTML = "";
        this.responses = [""];
        // Initialize and start the typewriter
        const typewriterF = new Form_Typewriter(
            true,
            this.#questionPrompts[this.#idx],
            this.#writerForm1,
            null,
            (response) => this.#getNextQPrompt(response)
        );
        typewriterF.startForm();
    }


     #getNextQPrompt(previousResponse) {
        //console.log("Previous responses:", previousResponse);
        //this.responses.push(previousResponse);
        this.#idx++;

        const result = [
            this.#questionPrompts[this.#idx],
            this.#isLast(),
            this.#typeInp[this.#idx] || "text"
        ];
        return result || null;
    }


    #isLast() {
        return this.#idx >= this.#questionPrompts.length - 1;
    }
}


// Initialize the FormController once the DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    new FormController_Intro();
});





