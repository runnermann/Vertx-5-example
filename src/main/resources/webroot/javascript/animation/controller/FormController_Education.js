// ——————————————————————————————————————————————————
// Uses the word by word typewriter with letter by letter erase.
// Types out phrases that are contained in an array.
// 1. At the end of an array, it shows a form. The user response
// is captured and sent back to the controller when the user
// presses enter.
// ——————————————————————————————————————————————————

import Form_Typewriter from "../form_Typewriter.js";

class FormController_Education {
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
          ["Have you attended a college or university?","What was the institutions name?"],
          ["Please give us the website for your institution."],
          ["What is the highest degree of your education?"],
          ["Did you graduate?"],
          ["When did you start? Please give the date."],
          ["Please give the finish date."],
          ["What was your major?"],
          ["Please give a specific website for your program/major."],
          ["If you had a minor, what was it?"],
          ["If you had a specialization, what was it?"],
          ["On average, how many hours a week did you study? This includes class and lab time."],
          ["Thank you", "This area is complete. Click the 'X' or outside the box to close."]
        ];
        //--- Additional information to explain the context of the question. This is in the (i) information area. ---//
        this.#tipInfo = [["Please use the official name of the college, university, or other institute that you took courses with." +
        "This can include military courses as well."],
        ["Be as specific as possible. If there is a website for your particular program, provide a public link to that."],
        ["This could include a bachelor's degree, a certificate, a PhD, or other forms of education. You'll have the " +
        "opportunity to provide additional educational details later, including uploading confirmation documents and " +
        "images as needed."],
        ["Leave this blank if you didn't have a major or a focus of your education. Or you can state 'General'."],
        ["Graduating is a significant signal. It's the knowledge that counts."],
        [""]];
        //--- If there is anything other than simple text inputs, fill in the array. ---//
        this.#typeInp = ["text"];
        this.#responses = [""];
        this.#writerForm1 = document.getElementById("form-question");
        this.#buttonOne = document.getElementById("edit_education");
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
            institution: this.#responses[i++],
            website: this.#responses[i++],
            cert_name: this.#responses[i++],
            isGrad: this.#responses[i++],
            start_date: this.#responses[i++],
            end_date: this.#responses[i++],
            // program or major
            major: this.#responses[i++],
            program_website: this.#responses[i++],
            minor: this.#responses[i++],
            specialization: this.#responses[i++],
            hours_week: this.#responses[i++]
        };
        fetch("/BLDR3003", {
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
    new FormController_Education();
});