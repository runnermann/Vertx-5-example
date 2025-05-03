// ——————————————————————————————————————————————————
// Uses the word by word typewriter with letter by letter erase.
// Types out phrases that are contained in an array.
// 1. At the end of an array, it shows a form. The user response
// is captured and sent back to the controller when the user
// presses enter.
// ——————————————————————————————————————————————————

import Form_Typewriter from "./typescript/form_Typewriter";

export default class IndustryFormController {

    private writerForm1: HTMLElement | null = null;
    private buttonOne: HTMLElement | null = null;
    //#buttonTwo: HTMLElement | null = null;
    private idx: number = 0;

    private questionPrompts: string[][] | null = null;
    private typeInp: string[] | null = null;
    private responses: string[] = [""];

    constructor() {
        this.questionPrompts = [
            ["What industry do you work in, or are seeking work in?"],
            ["What are the top companies in your industry?", "Please use a comma to separate the companies"],
            ["What company do you work for?"],
            ["Write a description of what you do.", "It's ok to make this answer long. Be as descriptive as possible.", "Up to 1200 words. What do you do?"],
            ["Thank you", "This area is complete. Click the 'X' or outside the box to close."]
        ];
        this.typeInp = ["text", "email", "password", "text"];
        this.responses = [""];
        this.writerForm1 = document.getElementById("form-question");
        this.buttonOne = document.getElementById("edit_industry");
        //this.#buttonTwo = document.getElementById("submit-form");
        // Bind event listeners to the buttons
        if (this.buttonOne) {
            this.buttonOne.onclick = () => this.startForm();
        }
        //if (this.#buttonTwo) {
        //    this.#buttonTwo.onclick = () => this.startForm();
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
            this.questionPrompts![this.idx],
            this.writerForm1!,
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
            this.questionPrompts![this.idx],
            this.isLast(),
            this.typeInp![this.idx] || "text"
        ];
        return result || null;
    }

    private isLast(): boolean {
        return this.idx === this.questionPrompts!.length - 1;
    }

    private sendToBackend() {
        const payload = {
            industry: this.responses[1],
            top_companies: this.responses[2],
            my_company: this.responses[3],
            job_description: this.responses[4]
        };
        // fetch("/CU1309", {
        //     method: "POST",
        //     headers: {
        //         "Content-Type": "application/json"
        //     },
        //     body: JSON.stringify(payload)
        // }).then(function (ok) {
        //     localStorage.setItem('showSignup', 'false');
        // }).catch(function (err) {
        //     console.log("ERROR: ", err);
        // });
    }
}

// Initialize the FormController once the DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    new IndustryFormController();
});