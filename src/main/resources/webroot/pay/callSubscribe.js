/*
 *  Copyright (c) 2020 - 2022 FlashMonkey Inc.
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

let lx1 = "";
function callMe(x1) {
    lx1 = x1;
    localStorage.setItem("x1", x1);
}

let app = angular.module("userApp", ['ngMessages'])
    .controller("AppSubscribeController", ["$scope", "$http", "$timeout", function ($scope, $http, $timeout) {

        let subscribeButton = document.querySelector("#subscribe_btn");

        subscribeButton.addEventListener("click", () => {

                subscribeButton.setAttribute("disabled", "disabled");
                subscribeButton.textContent = "Opening...";
                // for application
                if(lx1 === "") {
                    lx1 = localStorage.getItem("x1");
                }
                let payload = {
                    "x1": lx1,
                };
                fetch("/P01/HF75XZ", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(payload)
                })
                    .then(response => response.json())
                        .then(data => {
                            if (data.url) {
                                window.location = data.url;
                            } else {
                                onboardButton.removeAttribute("disabled");
                                onboardButton.textContent = "cannot process... error ";
                            }
                    });
        });
}]);

