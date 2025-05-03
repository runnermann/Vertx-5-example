// ——————————————————————————————————————————————————
// Uses the word by word typewriter with letter by letter erase.
// Types out phrases that are contained in an array.
// 1. At the end of an array, it shows a form. The user response
// is captured and sent back to the controller when the user
// presses enter.
// ——————————————————————————————————————————————————

import Form_Typewriter from "../form_Typewriter.js";

class FormController_MilService {

    #hasButtonEnding = false;
    #writerForm1 = null;
    #buttonOne = null;
    #idx = 0;
    #hash = null;
    #questionPrompts = null;
    #tipInfo = null;
    #typeInp = null;
    #responses = [""];

    constructor() {
        this.#questionPrompts = [
            ["Have you served in the military or civil service?","What was the service or branch?"],
            ["What country did you serve?"],
            ["What was or is your rank?"],
            ["What is your status?","Active, Retired, Honorably Discharged, etc..."],
            ["Are you male or female?"],
            ["What year did you start?"],
            ["What year did you end?"],
            ["Thank you","This area is complete. Click the 'X' or outside the box to close."]
        ];

        //--- Additional information to explain the context of the question. This is in the (i) information area. ---//
        this.#tipInfo = [];

        //--- If there is anything other than simple text inputs, fill in the array. ---//
        this.#typeInp = ["text"];
        this.#responses = [""];
        this.#writerForm1 = document.getElementById("form-question");
        this.#buttonOne = document.getElementById("edit_mil_svc");
        this.#hash     = document.getElementById("hash");

        //--- Bind event listeners to the buttons ---//
        this.#buttonOne.onclick = () => this.startForm();
    }

    startForm() {
        this.#idx = 0;
        this.#writerForm1.innerHTML = "";
        this.#responses = [""];

        //--- Initialize and start the typewriter ---//
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

        if (this.#idx === this.#questionPrompts.length -1) {
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
        let i = 1;
        const payload = {
            user_hash: this.#hash.innerHTML,
            branch_svc: this.#responses[i++],
            nation: this.#responses[i++],
            rank: this.#responses[i++],
            status: this.#responses[i++],
            sex: this.#responses[i++],
            date_start: this.#responses[i++],
            date_end: this.#responses[i++],
            //descript: this.#responses[i++]
        };
        fetch("/BLDR4004", {
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
    new FormController_MilService();
});