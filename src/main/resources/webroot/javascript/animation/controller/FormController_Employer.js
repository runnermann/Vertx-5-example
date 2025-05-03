// ——————————————————————————————————————————————————
// Uses the word by word typewriter with letter by letter erase.
// Types out phrases that are contained in an array.
// 1. At the end of an array, it shows a form. The user response
// is captured and sent back to the controller when the user
// presses enter.
// ——————————————————————————————————————————————————

import Form_Typewriter from "../form_Typewriter.js";

class FormController_Employer {

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
            ["What company do you work for?"],
            ["What is their website?"],
            ["When did you start working for them?"],
            ["What is your job title?"],
            ["Are you employed or a contractor?"],
            ["Why would we hire you?","Tell us what you do. 1200 word well written summary."],
            ["Earnings: What's your monthly total pay?"],
            ["Thank you","This area is complete. Click the 'X' or outside the box to close."]
        ];
        //--- Add extra information to explain the context of the question ---//
        this.#tipInfo = [["Name the company that you are performing work for. " +
        "If your subcontracted to another company, give the company that aligns with your goals. You'll have a chance " +
        "later to indicate if your a contractor or employee."]];
        this.#typeInp = ["text","text","text","text","text","textarea","text"];
        this.#responses = [""];
        this.#writerForm1 = document.getElementById("form-question");
        this.#buttonOne = document.getElementById("edit_employer");
        this.#hash     = document.getElementById("hash");

        // Bind event listeners to the buttons
        this.#buttonOne.onclick = () => this.startForm();

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

        if (this.#idx === 7) {
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
            user_hash: this.#hash.innerHTML,
            entity_name: this.#responses[1],
            website: this.#responses[2],
            start_date: this.#responses[3],
            job_title: this.#responses[4],
            relation: this.#responses[5],
            job_descript: this.#responses[6],
            value: this.#responses[7]

        };
        fetch("/BLDR2002", {
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
    new FormController_Employer();
});
