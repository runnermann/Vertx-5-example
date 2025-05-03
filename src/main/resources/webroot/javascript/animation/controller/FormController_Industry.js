// ——————————————————————————————————————————————————
// Uses the word by word typewriter with letter by letter erase.
// Types out phrases that are contained in an array.
// 1. At the end of an array, it shows a form. The user response
// is captured and sent back to the controller when the user
// presses enter.
// ——————————————————————————————————————————————————

import Form_Typewriter from "../form_Typewriter.js";

class FormController_Industry {

    #hasButtonEnding = false;
    #writerForm1 = null;
    #buttonOne = null;
    //#buttonTwo = null;
    #idx = 0;

    #questionPrompts = null;
    #typeInp = null;
    #responses = [""];

    constructor() {
        this.#questionPrompts = [
            ["What industry do you work in, or are seeking work in?"],
            ["What are the three top companies in your industry?","Please use a comma to separate the companies."],
            ["How would your peers rate you in comparison to those in your profession at the top companies?",
                "Please answer rating yourself as a percentile such as top '10'% or top '50'%",
                "Enter the percentile as numbers only."],
            ["Thank you", "This area is complete. Click the 'X' or outside the box to close."]
        ];
        this.#typeInp = ["text","text","text","text"];
        this.#responses = [""];
        this.#writerForm1 = document.getElementById("form-question");
        this.#buttonOne = document.getElementById("edit_industry");
        //this.#buttonTwo = document.getElementById("submit-form");
        // Bind event listeners to the buttons
        this.#buttonOne.onclick = () => this.startForm();
        //this.#buttonTwo.onclick = () => this.startForm();
    }

    startForm() {
        this.#idx = 0;
        this.#writerForm1.innerHTML = "";
        this.#responses = [""];

        // Initialize and start the typewriter
        const typewriterF = new Form_Typewriter(
            this.#hasButtonEnding,
            this.#questionPrompts[this.#idx],
            this.#writerForm1,
            null,
            (response) => this.#getNextQPrompt(response)
        );
        typewriterF.startForm();
    }

    #getNextQPrompt(previousResponse) {
        console.log("Previous responses:", previousResponse);
        this.#responses.push(previousResponse);
        this.#idx++;

        if (this.#idx === 3) {
            this.#sendToBackend();
        }

        const result = [
            this.#questionPrompts[this.#idx],
            this.#isLast(),
            this.#typeInp[this.#idx] || "text"
        ];
        return result || null;
    }

    #isLast() {
        return this.#idx === this.#questionPrompts.length - 1;
    }

    #sendToBackend() {
        const payload = {
            industry: this.#responses[1],
            top_companies: this.#responses[2],
            my_rating: this.#responses[3]
        };
        fetch("/BLDR1001", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        }).then(function (ok) {
            localStorage.setItem('showSignup', 'false');
        }).catch(function (err) {
            console.log("ERROR: ", err);
        });
    }
}

// Initialize the FormController once the DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    new FormController_Industry();
});