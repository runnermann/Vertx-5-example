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
// Word by word typewriter with letter by letter erase.
// ——————————————————————————————————————————————————

import Image_Typewriter from "../img_Typewriter.js";

export default class ImagesController_MilService {
    #idx = 0;
    #hash   = null;
    #imgFor;
    #imgFile;
    #sizeAry;
    #altDescript;

    constructor() {
        this.questionPrompts = [
            ["Please add 2 military or civil service images.",
                "The top uses 50% of the box width and is expected to be a vertical orientation.",
                "Width should be between 600px and 800px"],
            ["The bottom uses 100% of the box width and is expected to be horizontal orientation",
            "Width should be between 900px to 1200px","click submit or close and reopen the box to set new images."],
            ["Thank you", "All photos are complete. Click the 'X' or outside the box to close."]
        ];

        this.typeInp = ["image", "image"];
        this.imgWriter = document.getElementById("img-question");
        this.#hash     = document.getElementById("hash");
        this.#idx = 0;
        this.#imgFor = "";
        this.#sizeAry = [50,100];
        this.#altDescript = "";
    }

    startMilSvcImgTypewriter() {
        this.imgWriter.innerHTML = "";
        this.responses = [""];

        // Initialize and start the typewriter
        const typewriterI = new Image_Typewriter(
            this.questionPrompts[this.#idx],
            this.imgWriter,
            (file) => this.#getNextQPrompt(file),
            this.#getImgFor()
        );
        typewriterI.startImgWriter();
    }

    /**
     * After an action such as a drag and drop,
     * process the users responses.
     * @param file
     * @returns {Promise<(string[]|boolean)[]|null>}
     */
    async #getNextQPrompt(file) {
        if (!(file instanceof File)) {
            alert("Invalid file. Please select an image file.");
            return null;
        }

        let imgFor = this.#getImgFor();
        this.#sendToBackend(imgFor, file);
        // Changing the index also changes imgFor.
        this.#idx++;
        // Set the response to the typewriter
        const res = [
            this.questionPrompts[this.#idx],
            this.#isLast(),
            this.#getImgFor()  // The id of the html element for the image display
        ];
        // Send the next phrase and data to the typewriter.
        return res || null;
    }

    #getImgFor() {
        // Same as the img element id.
        return "svc-img-" + this.#idx;
    }


    #isLast() {
        return this.#idx >= this.questionPrompts.length -1;
    }

    /**
     * Send the image and data to the backend.
     * @returns {Promise<void>}
     */
    async #sendToBackend(imgFor, file) {

        // 1, convert the image to webp
        const img = await this.#convertToWebp(file, 1.0)
            .catch((error) => {
                console.error("WebP conversion error:", error);
                alert("Failed to process the image. Please try with a different file.");
                throw error;
            });

        // 2. Get the presigned URL
        const payload = {
            "hash":     this.#hash.innerHTML,
            "img-for":  imgFor, // contains profile, background, athlete, mil photo etc...
            "size":     40,     // not used for now
            "descript": this.#altDescript
        };
        fetch("/33D334/media-left-sidebar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)

        })
            .then((response) => {
                if ( ! response.ok ) throw new Error("Failed to get presigned URL");
                return response.json();
            })
            .then((data) => {

            const s3PresignedUrl = data[0]; // Extract the URL from the response
                if (!s3PresignedUrl) {
                    throw new Error("Missing presigned URL in server response");
                }
                console.log("Received S3 Presigned PUT URL:", s3PresignedUrl);

                // Upload the file to S3 using the presigned URL
                return fetch(s3PresignedUrl, {
                    method: "PUT",
                    mode: "cors",
                    headers: {
                        "Content-Type": img.type // Set the correct MIME type for the file
                    },
                    body: img // Send the actual file as the request body
                })
                    .then((uploadResponse) => {
                        if (!uploadResponse.ok) {
                            throw new Error(`S3 upload failed with status: ${uploadResponse.status}`);
                        }
                        return uploadResponse;
                    });

            })
            .then((uploadResponse) => {
                if (!uploadResponse.ok) {
                    throw new Error("Failed to upload file to S3");
                }
                console.log("File uploaded to S3 successfully!");
                alert("File uploaded successfully!");
            })
            .catch((err) => {
                if (err instanceof TypeError) {
                    console.log("Network error or CORS issue:", err);
                    alert("Network error. Please check your internet connection.");
                } else {
                    console.log("Error during the upload process:", err);
                    alert("Failed to upload the file. Please try again.");
                }
            });
    }


    #convertToWebp(file, quality = 1.0) {
        // Return a Promise to handle the asynchronous process
        return new Promise((resolve, reject) => {
            // Validate the input to ensure it's a File object
            if (!(file instanceof File)) {
                return reject(new Error("Invalid file input. Must be a File object."));
            }

            const reader = new FileReader(); // Create a FileReader instance

            // Read the uploaded file as a Data URL
            reader.onload = function(event) {

                const img = new Image(); // Create an Image instance
                img.onload = function() {
                    // Create a canvas element to process the image
                    const canvas = document.createElement("canvas");
                    const maxDimension = 896; // Example max size
                    const scale = Math.min(maxDimension / img.width, maxDimension / img.height, 1);
                    canvas.width = img.width * scale;
                    canvas.height = img.height * scale;

                    const ctx = canvas.getContext("2d"); // Get the canvas' context
                    ctx.drawImage(img, 0, 0, canvas.width, canvas.height); // Draw the image onto the canvas
                    // Convert the canvas content to a WebP Blob
                    canvas.toBlob(
                        function(blob) {
                            // Resolve the Blob to fulfill the Promise
                            if (blob) {
                                resolve(blob);
                            } else {
                                reject(new Error("Failed to create WebP blob."));
                            }
                        },
                        "image/webp",
                        quality
                    );
                };

                img.onerror = function() {
                    reject(new Error("Failed to load the image from the file."));
                };

                // Set the image source to trigger the loading process
                img.src = event.target.result; // Data URL from FileReader
            };

            // Add error handling for file reading
            reader.onerror = function() {
                reject(new Error("Error reading the file with FileReader."));
            };

            // Start reading the file as a Data URL
            reader.readAsDataURL(file);
        });
    }
}