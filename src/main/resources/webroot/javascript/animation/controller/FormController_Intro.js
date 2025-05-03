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
        this.idx = 0;
        this.#writerForm1.innerHTML = "";
        this.responses = [""];
        // Initialize and start the typewriter
        const typewriterF = new Form_Typewriter(
            true,
            this.#questionPrompts[this.idx],
            this.#writerForm1,
            this.callProfileBuilder,
            (response) => this.#getNextQPrompt(response)
        );
        typewriterF.startForm();
    }


    #getNextQPrompt(previousResponse) {
        console.log("Previous responses:", previousResponse);
        this.responses.push(previousResponse);
        this.idx++;

        const result = [
            this.#questionPrompts[this.idx],
            this.#isLast(),
            this.#typeInp[this.idx] || "text"
        ];
        return result || null;
    }


    #isLast() {
        return this.idx === this.#questionPrompts.length - 1;
    }


    // #sendToBackend() {
    //     const name = this.responses[1];
    //     const email = this.responses[2];
    //     const x4 = this.responses[3];
    //     const names = name.split(" ");
    //     const first = names[0];
    //     const last = names[1];
    //
    //     const validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    //
    //     if (email === "") {
    //         console.log("no email error");
    //     } else if (!validRegex.test(email)) {
    //         console.log("email pattern error:...  " + email + " " + name);
    //     } else {
    //         const payload = {
    //             x1: first,
    //             x2: last,
    //             x3: email,
    //             x4: x4
    //         };
    //         fetch("/CU1309", {
    //             method: "POST",
    //             headers: {
    //                 "Content-Type": "application/json"
    //             },
    //             body: JSON.stringify(payload)
    //         }).then(function (ok) {
    //             localStorage.setItem('showSignup', 'false');
    //         }).catch(function (err) {
    //             console.log("ERROR: ", err);
    //         });
    //     }
    // }

    // /**
    //  * Send the users data to the builderPage
    //  * and display the builderPage.
    //  */
    callProfileBuilder() {
        // let payload = {
        //     "name": "wolf runnermann",
        //     "email": "fast@flashmonkey.xyz"
        // };
        fetch("/protected", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            },
            //body: JSON.stringify(payload)
        })
            //.then()
            .catch(error => {
                // Handle network or server errors
                console.error("Error during profile builder process:", error);
                alert("Failed to process the request. Please try again."); // Inform the user
            });
    }
}


// Initialize the FormController once the DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    new FormController_Intro();
});





