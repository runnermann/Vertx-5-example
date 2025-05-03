/*
 *  Copyright (c) 2020 - 2024 KnoC Inc. .
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

'use strict';

// the modal element
let xModal = new bootstrap.Modal(document.getElementById('formModal'));
//Has user signed up
let res = localStorage.getItem('showSignup');
let showSignup = res === null ? 'true' : res;

const modBtn = document.getElementById('formModalButton');
modBtn.addEventListener('click', (e) => {
    savePrivate();
});
const footerBtn = document.getElementById('footerBtn');
footerBtn.addEventListener('click', (e) => {
    saveLetter();
});

let savePrivate = function (isValid) {
    const modalEmail = document.getElementById('modalEmail').value;
    const name = document.getElementById('name').value;
    let validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if(modalEmail === "") {
        console.log("no email error");
    }else if (!validRegex.test(modalEmail)) {
        console.log("email pattern error:...  " + modalEmail + " " + name);
    } else {
        let payload = {
            "name": name,
            "email": modalEmail
        };
        fetch("/launch/PK9A01", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        }).then(function (ok) {
            modBtn.disabled="true";
            xModal.hide();
            localStorage.setItem('showSignup', 'false');

        }, function (err) {
            console.log(err.data.error)
        });
    }
};

let saveLetter = function (isValid) {
    const emailField = document.getElementById('emailField-ftr').value;
    const name = document.getElementById('name-ftr').value;
    let validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    if(emailField === "") {
        console.log("no email error");
    }else if (!validRegex.test(emailField)) {
        console.log("email pattern error:...  " + emailField + " " + name);
    } else {
        let payload = {
            "name": name,
            "email": emailField
        };
        fetch("/launch/PK9A01", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        }).then(function (ok) {
            // formModal.style.display = "none";
            footerBtn.disabled="true";
            localStorage.setItem('showSignup', 'false');

        }, function (err) {
            console.log(err.data.error)
        });
    }
};








