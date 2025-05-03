
// Converts any image file to a .webp image

export default class ConvertToWebp {

    #file = null;
    #quality    = null;
    #maxWidth   = 896;

    constructor(file, quality = 1.0, maxWidth) {
        this.#file = file;
        this.#quality = quality;
        this.#maxWidth = maxWidth;
    }

    async convert() {
        // Return a Promise to handle the asynchronous process
        return new Promise((resolve, reject) => {
            // Validate the input to ensure it's a File object
            if (!(this.#file instanceof File)) {
                return reject(new Error("Invalid file input. Must be a File object."));
            }
    
            const reader = new FileReader(); // Create a FileReader instance

            // Read the uploaded file as a Data URL
            reader.onload = function(event) {

                const img = new Image(); // Create an Image instance
                img.onload = function() {
                    // Create a canvas element to process the image
                    const canvas = document.createElement("canvas");
                    const maxDimension = this.#maxWidth | 896; // Example max size
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
                        this.#quality
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
