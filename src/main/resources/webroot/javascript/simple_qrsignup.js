/*
 *  Copyright (c) 2020 - 2023 FlashMonkey Inc. and/or its affiliates.
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

let app = angular.module("userApp", ['ngMessages'])
    .controller("QRSignUpController", ["$scope", "$http", "$timeout", function ($scope, $http, $timeout) {
        let req_app_btn = document.querySelector("#req-app-btn");
        let close_email = document.querySelector("#close_email");

         // The user enters their email in the form.
         // on submit, we send the email.
         $scope.sendform = function (isValid) {
            if ($scope.email === "") {
                $scope.error("Please check your email.");
            } else if (!isValid) {
                $scope.error("Please check the form for an incorrect entry.")
            } else {
                // localStorage.setItem("email", $scope.email);
                // send to vertx and confirm user does not exist.
                let payload = {
                    "email": $scope.email,
                };
                fetch("/Q52/FFD020", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(payload)
                })
                    .then(response => response.json())
                    .then(function (response) {
                        $scope.showcompleted();
                    });
            }
        }

        $scope.showcompleted = function () {
            let modalSi = document.getElementById("email_modal");
            modalSi.style.display = "none";
            let modalSu = document.getElementById("thankyou_modal");
            modalSu.style.display = "block";
            let s = document.getElementById("valid");
            s.textContent = "Powered by: " + msg;
            let t = document.getElementById("from");
            t.textContent = "To: " + $scope.email;
        }
        close_email.addEventListener('click', () => {
            let intro = document.getElementById("email_modal");
            intro.style.display = "none";
            req_app_btn.removeAttribute("disabled");
        });

        req_app_btn.addEventListener('click', () => {
            let email = document.getElementById("email_modal");
            req_app_btn.setAttribute("disabled", "disabled");
            email.style.display = "block";
        });
}]);