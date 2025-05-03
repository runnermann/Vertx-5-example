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

        let clickedOff = false;
        // the modal element
        let myModal = new bootstrap.Modal(document.getElementById('formModal'));
        //Has user signed up
        let res = localStorage.getItem('showSignup');
        let showSignup = res === null ? 'true' : res;
        document.querySelector('.footerBtn').addEventListener('click', (e) => {
            saveLetter();
        });
        document.querySelector('.modalBtn').addEventListener('click', (e) => {
            savePrivate();
        });

        window.addEventListener("scroll", e => {
            let scroll_top = window.scrollY;

            if(showSignup != "false" && clickedOff == false && scroll_top > 600) {
                    myModal.toggle();
                    clickedOff = true;
            }
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
                    document.querySelector('.modalBtn').disabled="true";
                    myModal.hide();
                    localStorage.setItem('showSignup', 'false');

                }, function (err) {
                    console.log(err.data.error)
                });
            }
        };

        // pay modal
        // let showModal2 = function () {
        //     $('#modalToggle2').modal('show');
        // };

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

            document.querySelector('.footerBtn').disabled="true";
            localStorage.setItem('showSignup', 'false');

        }, function (err) {
            console.log(err.data.error)
        });
    }
};






