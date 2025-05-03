/*
*  This typewritter() types forwards and backwards.
*
* @Copyright Lowell Stadelman, KnoC Inc. 2020-2024.
* License is 'Do what the 'F' you want. Please give
* credit to the original author.
*
*  This typewriter effect types
*  word by word, then letter by letter when removing a word.
*  The original version did not do well when the typwriter(),
*  letter by letter, added a new line. It caused an unpleasant
*  jump as a word reached the end of the line. If the word overran
*  the length, it would be added to a new line causing an
*  the jump.
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

const delaymultiplier= 52;
const eraseSpeed = 16; // Erase speed in milliseconds




class Typewriter {
    #typeSpeed = 96; // Typing speed in milliseconds
    #phrases = '';
    #typewriterElement = document.querySelector(".animate-typewriter");
    #invisibleEl = document.querySelector(".animate-invisible-text");

    #phrasesIndex = 0;
    #eraseStartIndex= 0;
    #eraseIndex = 0;
    #delay     = 0;
    #startDelay = 0;
    #typing    = true;

    #buildWordsBefore = "";
    #buildWordsAfter = "";

    #originalLine = "";


    constructor(phrases, typeWriterElement, invisibleTextElement) {
        this.#phrases = phrases;
        this.#typewriterElement = typeWriterElement;
        this.#invisibleEl = invisibleTextElement;
        this.#eraseStartIndex = this.#phrases[0].length - 1;
        this.#delay      = this.#phrases[this.#phrasesIndex].length * delaymultiplier; // = ~2200 milliseconds for 43 characters and spaces
        this.#eraseIndex = this.#eraseStartIndex;
        this.#startDelay = this.#delay;
    }

    startTop() {
        this.#typeWriter();
    }

    #sleep = ms => {
        return new Promise(resolve => setTimeout(resolve, ms));
    }

    #getWord = (idx, words) => {
        return this.#sleep(this.#typeSpeed).then(v => words[idx]);
    }

    setTypeSpeed(ms) {
        this.#typeSpeed = ms;
    }

    // #getChar = (idx, word) => {
    //     return this.#sleep(typeSpeed).then(v => word[idx]);
    // }

    #typeWriter = async _ => {
        if(this.#typing) {
            const phrase = this.#phrases[this.#phrasesIndex];
            const words = phrase.split(" ");

            for(let wordsIdx = 0; wordsIdx < words.length; wordsIdx++) {
                const word = await this.#getWord(wordsIdx, words) + " ";
                this.#buildWordsAfter += word;
                // Uncomment for letter by letter
                //for(let charIdx = 0; charIdx < word.length; charIdx++) {
                //const char = await getChar(charIdx, word);
                if(! this.#isOverrun(this.#buildWordsAfter)) {
                    this.#typewriterElement.innerHTML += word;
                } else {
                    // Add a new line
                    this.#typewriterElement.innerHTML += "<br>" + word;
                    // for isOverrun
                    this.#buildWordsBefore = this.#buildWordsAfter;
                }
            }

            this.#typing = false;
            await this.#typeWriter();
        } else {
            setTimeout(this.#eraseWriter, this.#delay);
        }
    }


    #typeOnce = async _ => {
        let idx = 0;

        while(idx < this.#phrases.length) {
            let phrase = this.#phrases[idx];
            let words = phrase.split(" ");
            for(let wordsIdx = 0; wordsIdx < words.length; wordsIdx++) {
                const word = await this.#getWord(wordsIdx, words) + " ";
                this.#buildWordsAfter += word;
                // Uncomment for letter by letter
                //for(let charIdx = 0; charIdx < word.length; charIdx++) {
                //const char = await getChar(charIdx, word);
                //if(! this.#isOverrun(this.#buildWordsAfter)) {
                this.#typewriterElement.innerHTML += word;
                // } else {
                //     // Add a new line
                //     this.#typewriterElement.innerHTML += "<br>" + word;
                //     // for isOverrun
                //     this.#buildWordsBefore = this.#buildWordsAfter;
    //     }
            }
            await this.#sleep(1100);
            this.#typewriterElement.innerHTML += "<br><br>";
            idx++;
            phrase = this.#phrases[idx];
        }

        this.#typing = false;
        //await this.#typeWriter();

    }

    #isOverrun(futureLine) {
        // start ht
        this.#invisibleEl.innerHTML = this.#buildWordsBefore ? this.#buildWordsBefore : "D";
        let htOne = this.#invisibleEl.offsetHeight;
        this.#invisibleEl.innerHTML = futureLine;
        let bool = htOne !== this.#invisibleEl.offsetHeight;

        return bool;
    }

    #eraseWriter = async _ => {
        this.#originalLine = this.#typewriterElement.innerHTML;
        await this.#erase();
    }

    #erase = async _ => {
        if (this.#eraseIndex >= 0) {
            const line = this.#originalLine;
            let subline = line.substring(0, this.#eraseIndex);
            if(subline.endsWith(">")) {
                this.#eraseIndex = subline.lastIndexOf(" <");
                this.#typewriterElement.innerHTML = subline.substring(0, this.#eraseIndex)
            } else {
                this.#typewriterElement.innerHTML = subline;
            }
            this.#eraseIndex--;
            setTimeout(this.#erase, eraseSpeed);
        } else { // line complete, reset
            this.#typing = true;
            this.#phrasesIndex++;
            this.#eraseIndex = this.#phrases[this.#phrasesIndex] ? this.#phrases[this.#phrasesIndex].length - 1 : this.#eraseStartIndex;
            this.#delay = this.#phrases[this.#phrasesIndex] ? this.#phrases[this.#phrasesIndex].length * delaymultiplier : this.#startDelay;
            this.#buildWordsBefore = "";
            this.#buildWordsAfter = "";
            // When restarting the phrases.
            if (this.#phrasesIndex > this.#phrases.length - 1) {
                this.#phrasesIndex = 0;
                setTimeout(this.#typeWriter, this.#typeSpeed);
            } else {
                setTimeout(this.#typeWriter, this.#typeSpeed);
            }
        }
    }
}