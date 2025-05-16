/*
*  This typewritter() types forwards and backwards.
*
* @Copyright Lowell Stadelman, KnoC Inc. 2020-2024.
* License is 'Do what the 'F' you want. Please give
* credit to the original author.
*
*  This typewriter effect types
*  word by word, then letter by letter when removing a word.
*  The original version did not do well when the typewriter(),
*  letter by letter, added a new line. It caused an unpleasant
*  jump as a word reached the end of the line. If the word overran
*  the length, it would be added to a new line causing a jump.
*
*  1. To fix this, we first test if the height of the container
*  remains the same after a whole word has been added. The test
*  'isOverrun(...)' is in a hidden HTML element below the visible
*  text. If isOverrun( newWord ) === true, we add a <br> tag.
*
*  2. When it is removing a word, letter by letter, the same
*  problem exists except there is the <br> tag. Similarly, we
*  test if there is a <br> tag and remove it.
*/

// ——————————————————————————————————————————————————
// Word by word typewritter with letter by letter erase.
// ——————————————————————————————————————————————————

class AnimateController_Index {

    constructor() {
        const phrases = [
            "Sumo du jacsum loopi duc me forne, wekse yumki  . . .",
            "Nakeed semaad yokuud lokud . . .",
            "Jami kia lomi seeksak. Kebe zeebe zakee . . ."
        ];
        // Typewriter on index page
        const typeWriterElement = document.querySelector(".animate-typewriter");
        const invisibleTextEl = document.querySelector(".animate-invisible-text");
        const typeWriter = new Typewriter(phrases, typeWriterElement, invisibleTextEl);
        // Start typewriter
        typeWriter.startTop();
        // Observe elements when they're in the viewport
        this.observeScrollSections();
    }



    // Observe sections when they are near the viewport.
    observeScrollSections() {
        // Create an IntersectionObserver instance
        const observer = new IntersectionObserver((entries) => {
            entries.forEach((entry) => {
                if (entry.isIntersecting) {
                    // Element is in the viewport, add 'visible' class
                    entry.target.classList.add("visible");
                } else {
                    // Optionally remove the 'visible' class when it's outside the viewport
                    entry.target.classList.remove("visible");
                }
            });
        }, {
            root: null, // Use the viewport as the root
            rootMargin: "0px 0px 600px 0px", // Add a 600px margin for preloading elements
            threshold: 0 // Trigger as soon as the element is even slightly visible
        });

        // Observe all elements with the "scroll-section" class
        document.querySelectorAll(".scroll-section").forEach((section) => {
            observer.observe(section);
        });

        //Observe bottom image with the "scroll-image" class
        document.querySelectorAll(".scroll-image").forEach((section) => {
            observer.observe(section);
        });
    }

}

// Initialize the typewriter once the DOM is fully loaded
document.addEventListener("DOMContentLoaded", () => {
    new AnimateController_Index();
});



