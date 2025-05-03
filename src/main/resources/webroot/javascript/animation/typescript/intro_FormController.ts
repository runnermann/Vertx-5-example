// ——————————————————————————————————————————————————
// Uses the word by word typewriter with letter by letter erase.
// Types out phrases that are contained in an array.
// 1. At the end of an array, it shows a form. The user response
// is captured and sent back to the controller when the user
// presses enter.
// ——————————————————————————————————————————————————

import Form_Typewriter from "./typescript/form_Typewriter";

export default class FormController {
    private questionPrompts: string[][];
    private typeInp: string[];
    private writerForm1: HTMLElement | null;
    private buttonOne: HTMLElement | null;
    private responses: string[];
    private idx: number = 0;

    constructor() {
        this.questionPrompts = [
            ["Welcome!",
                "Before we can build your KnocScore, we need to set up your free account.",
                "Please enter your first and last name."],
            ["Thank you.", "Next, please provide your email."],
            ["Thank you.", "Please provide a strong password."],
            ["Very good!", "We've sent an email confirming your account.",
                "Now we are ready to build your knocScore."]
        ];

        this.typeInp = ["text", "email", "password", "text"];
        this.writerForm1 = document.getElementById("form-question");
        this.buttonOne = document.getElementById("button_one");
        //this.buttonTwo = document.getElementById("submit-form");
        this.responses = [""];

        // Bind event listeners to the buttons
        if (this.buttonOne) {
            this.buttonOne.onclick = () => this.startForm();
        }
        //if (this.buttonTwo) {
        //    this.buttonTwo.onclick = () => this.startForm();
        //}
    }

    startForm() {
        this.idx = 0;
        if (this.writerForm1) {
            this.writerForm1.innerHTML = "";
        }
        this.responses = [""];

        // Initialize and start the typewriter
        const typewriterF = new Form_Typewriter(
            true,
            this.questionPrompts[this.idx],
            this.writerForm1,
            (response: string) => this.getNextQPrompt(response)
        );
        typewriterF.startForm();
    }

    private getNextQPrompt(previousResponse: string): [string[], boolean, string] | null {
        console.log("Previous responses:", previousResponse);
        this.responses.push(previousResponse);
        this.idx++;

        if (this.idx === 3) {
            this.sendToBackend();
        }

        const result: [string[], boolean, string] = [
            this.questionPrompts[this.idx],
            this.isLast(),
            this.typeInp[this.idx] || "text"
        ];
        return result || null;
    }

    private isLast(): boolean {
        return this.idx === this.questionPrompts.length - 1;
    }

    private sendToBackend(): void {
        const name = this.responses[1];
        const email = this.responses[2];
        const x4 = this.responses[3];
        const names = name.split(" ");
        const first = names[0];
        const last = names[1];

        const validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

        if (email === "") {
            console.log("no email error");
        } else if (!validRegex.test(email)) {
            console.log("email pattern error:...  " + email + " " + name);
        } else {
            const payload = {
                x1: first,
                x2: last,
                x3: email,
                x4: x4
            };
            fetch("/CU1309", {
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
}

// Initialize the FormController once the DOM is fully loaded
window.onload = function () {
    new FormController();
};