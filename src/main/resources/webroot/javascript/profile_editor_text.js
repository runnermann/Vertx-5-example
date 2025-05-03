// Function to initialize Quill editors

// Class Definition
class ProfileEditor {

    #hash = "";

    constructor() {
        this.editors = {};
        this.activeEditor = null;
        const temp = document.getElementById("hash");
        this.#hash = temp.innerHTML;

        this.initializeEditors();
        this.hideAllEditors(); // Ensure editors are hidden on page load
        this.handleEditButtonClicks();
    }

    // Initializes Quill editors for all elements with the `editor` class
    initializeEditors() {
        const toolbarOptions = [
            ['bold', 'italic', 'underline'],        // Toggled buttons
            ['blockquote'],
            [{ 'header': 1 }, { 'header': 2 }],    // Custom button values
            [{ 'list': 'ordered' }, { 'list': 'bullet' }],
            [{ 'script': 'sub' }, { 'script': 'super' }],    // Superscript/subscript
            [{ 'indent': '-1' }, { 'indent': '+1' }],        // Outdent/indent
            [{ 'direction': 'rtl' }],                        // Text direction
            [{ 'header': [1, 2, 3, 4, 5, 6, false] }],
            [{ 'align': [] }],
            ['clean']                                        // Remove formatting button
        ];

        document.querySelectorAll('.editor').forEach((editorElement) => {
            const editorId = editorElement.getAttribute('id');
            this.editors[editorId] = new Quill(`#${editorId}`, {
                modules: {
                    toolbar: toolbarOptions,
                },
                placeholder: 'Tell us something about yourself that makes us want to hire you.',
                theme: 'snow'
            });
        });
    }

    // Ensures all editors are hidden on page load
    hideAllEditors() {
        document.querySelectorAll('[id^="editor-container"]').forEach((editorContainer) => {
            editorContainer.style.display = 'none';
        });
    }

    // Handles Edit/Save button clicks
    handleEditButtonClicks() {
        const editButtons = document.querySelectorAll('.edit-btn');

        editButtons.forEach((button) => {
            button.addEventListener('click', () => {
                const newParagraphId = button.getAttribute('data-target');
                const newEditorId = button.getAttribute('data-editor');
                const newParagraph = document.getElementById(newParagraphId);
                const newEditorContainer = document.getElementById(`editor-container-${newEditorId.split('-')[1]}`);
                const quillInstance = this.editors[newEditorId];

                // Close the currently active editor if a different one is open
                if (this.activeEditor && this.activeEditor !== newEditorId) {
                    this.deselectOldEditor();
                }

                // Toggle between Edit and Save modes
                if (button.textContent === 'Edit' || this.activeEditor == null) { // undefined or null
                    this.enableEditing(newParagraph, newEditorContainer, quillInstance, button, newEditorId);
                } else {
                    this.saveAndCloseEditing(newParagraph, newEditorContainer, quillInstance, button);
                }
            });
        });
    }

    // Enables editing mode for the target editor

    enableEditing(paragraph, editorContainer, quillInstance, button, editorId) {
        paragraph.style.setProperty("display", "none", "important");
        editorContainer.style.display = 'block';
        quillInstance.root.innerHTML = paragraph.innerHTML; // Load content into Quill editor
        button.innerHTML = `<svg  class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path d="M433.9 129.9l-83.9-83.9A48 48 0 0 0 316.1 32H48C21.5 32 0 53.5 0 80v352c0 26.5 21.5 48 48 48h352c26.5 0 48-21.5 48-48V163.9a48 48 0 0 0 -14.1-33.9zM272 80v80H144V80h128zm122 352H54a6 6 0 0 1 -6-6V86a6 6 0 0 1 6-6h42v104c0 13.3 10.7 24 24 24h176c13.3 0 24-10.7 24-24V83.9l78.2 78.2a6 6 0 0 1 1.8 4.2V426a6 6 0 0 1 -6 6zM224 232c-48.5 0-88 39.5-88 88s39.5 88 88 88 88-39.5 88-88-39.5-88-88-88zm0 128c-22.1 0-40-17.9-40-40s17.9-40 40-40 40 17.9 40 40-17.9 40-40 40z"/></svg>`;
        this.activeEditor = editorId;
    }

    // Saves content and exits editing mode

    saveAndCloseEditing(paragraph, editorContainer, quillInstance, button) {
        paragraph.innerHTML = quillInstance.root.innerHTML; // content
        paragraph.style.display = 'block';
        editorContainer.style.display = 'none';
        button.innerHTML = `<svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                    <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>`;

        const target = button.getAttribute('data-target');
        this.saveData(target, quillInstance);

        this.activeEditor = null;
    }

    // Closes the currently active editor

    deselectOldEditor() {
        const oldButton = document.querySelector(`.edit-btn[data-editor="${this.activeEditor}"]`);
        const oldParagraphId = oldButton.getAttribute('data-target');
        const oldParagraph = document.getElementById(oldParagraphId);
        const oldEditorContainer = document.getElementById(`editor-container-${this.activeEditor.split('-')[1]}`);
        const oldQuill = this.editors[this.activeEditor];
        const target = oldButton.getAttribute('data-target');
        this.saveData(target, oldQuill);

        oldParagraph.innerHTML = oldQuill.root.innerHTML; // Save content
        oldParagraph.style.display = 'block';
        oldEditorContainer.style.display = 'none';
        oldButton.innerHTML = `<svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512">
        <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/>
        </svg>`;
        this.activeEditor = null;
    }

    saveData(target, oldQuill) {
        const data = oldQuill.root.innerHTML;

        if(target && target === "bio-1") {
            this.saveBio(data);
        }
        else if(target && target === "bio-2") {
            this.saveAthlete(data);
        }
    }

    saveAthlete(data) {
        let payload = {
            "hash": this.#hash,
            "data": data
        };
        fetch("/P44/ATTATE001", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.text())
            .then(result => {
                if(!result) {
                    alert("Something went wrong. Nothing was posted.");
                }
            })

    }


    saveBio(data) {
        let payload = {
            "hash": this.#hash,
            "data": data
        };
        fetch("/P44/BSTATE011", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        })
            .then(response => response.text())
            .then(result => {
                if(!result) {
                    alert("Something went wrong. Nothing was posted.");
                }
            })

    }
}

// Instantiate and initialize the ProfileEditor class when the DOM is loaded
document.addEventListener('DOMContentLoaded', () => {
    new ProfileEditor();
});





