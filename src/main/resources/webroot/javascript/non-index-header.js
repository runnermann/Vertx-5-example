/*
 *  Copyright (c) 2020 - 2023 FlashMonkey Inc. .
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
    .controller("NonIndexHeaderController", ["$scope", "$http", "$timeout", function ($scope, $http, $timeout) {

        const EMPTY_STRING = "";
        let user_email = "";
        let totalHeight = document.body.scrollHeight-window.innerHeight;
        let progress = document.getElementById("progressbar");
        let progWrap = document.getElementById("progress-wrapper")
        let timestamp = Date.now();
        let num = 0;
        let isLoaded = false;

        window.addEventListener("scroll", e=> {
            let navbar = document.getElementById("navbar");
            if ((window).scrollY > 60) {
                navbar.style.display = 'none';
                progress.style.display = 'block';
                progWrap.style.display = 'block';
            } else {
                navbar.style.display = 'block';
                progress.style.display = 'none';
                progWrap.style.display = 'none';
            }

            progress.style.width = ((window).scrollY/totalHeight)*100+"%";
        });

        $scope.newUser = function () {
            $scope.userName = EMPTY_STRING;
            $scope.email = EMPTY_STRING;
            user_email = EMPTY_STRING;
//            $scope.use('load');
        };

        $scope.newUserOne = function () {
            $scope.userName = EMPTY_STRING;
            $scope.email = EMPTY_STRING;
            user_email = EMPTY_STRING;
        };

        $scope.updateRendering = function (html) {
            document.getElementById("rendering").innerHTML = html;
        };


        $scope.save = function (isValid) {
            let saveBtn = document.getElementById("send-btn");
            if($scope.email === "") {
                $scope.error("Please check your email.");
            }
            else if (!isValid) {
                $scope.error("Please check the form for an incorrect entry.")
            } else {
                saveBtn.setAttribute("hidden", "hidden");
                let payload = {
                    "userName": $scope.userName,
                    "email": $scope.email
                };
                $http.post("/get-smart/AF1001", payload).then(function (ok) {
                    $scope.showcompleted();
                    // $scope.success("Success! We've sent an email with a link to your application. " +
                    //     "\nPlease check your inbox, or junk folder " +
                    //     "\nfor a message from Flash.");
                }, function (err) {
                    $scope.error(err.data.error);
                    document.body.style = "overflow-y:visible; position: static; margin-right: 0;";
                });
            }
        };

        // From mobile
        // The user enters their email in the form.
        // on submit, we send the email.
        $scope.sendMail = function (isValid) {
            let saveBtn = document.getElementById("send-btn");

            if ($scope.email === "") {
                $scope.error("Please check your email.");
            } else if (!isValid) {
                $scope.error("Please check the form for an incorrect entry.")
            } else {
                saveBtn.setAttribute("hidden", "hidden");
                // localStorage.setItem("email", $scope.email);
                // send to vertx and confirm user does not exist.
                let payload = {
                    "userName": $scope.userName,
                    "email": $scope.email,
                };
                $http.post("/get-smart/AF1001", payload).then(function (ok) {
                    $scope.showcompleted();
                }, function (err) {
                    $scope.error(err.data.error);
                });
            }
        };

        // Get the application link, win or apl
        $scope.download = function () {
            let saveBtn = document.getElementById("download-btn");
            saveBtn.setAttribute("hidden", "hidden");
            let payload = {
                "os_type": $scope.getOS()
            };

            fetch("/get-smart/AF1010", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            })
                .then(response => response.json())
                .then(function (response) {
                    window.open(response[0].link, '_parent');
                });
        };

        $scope.showcompleted = function () {
            let modalSi = document.getElementById("signUp_modal");
            if(modalSi !== null) {
                modalSi.style.display = "none";
            }
            let modalSu = document.getElementById("thankyou_modal");
            modalSu.style.display = "block";
            // let s = document.getElementById("valid");
            // s.textContent = "Powered by: " + msg;
            let t = document.getElementById("from");
            t.textContent = "To: " + $scope.email;
            // alert.classList.remove("invisible");
            $timeout(function () {
                modalSu.style.display = "none";
            }, 10000);
        };

        // Tell user they were successfully added.
        $scope.success = function (message) {
            $scope.alertMessage = message;
            let alert = document.getElementById("alertMessage");
            alert.classList.add("alert-success");
            alert.classList.remove("invisible");
            $timeout(function () {
                alert.classList.add("invisible");
                alert.classList.remove("alert-success");
                $scope.closeModal();
            }, 10000);

            let response = document.getElementById("responseAction")
            response.classList.add("alert-success");
        };

        $scope.closeModal = function () {
            let modal = document.getElementById("signUp_modal");
            modal.style.display = "none";
        };


        $scope.error = function (message) {
            $scope.alertMessage = message;
            let alert = document.getElementById("alertMessage");
            alert.classList.add("alert-danger");
            alert.classList.remove("invisible");
            $timeout(function () {
                alert.classList.add("invisible");
                alert.classList.remove("alert-danger");
            }, 10000);

            let response = document.getElementById("alertMessage");
            response.classList.add("alert-danger");
        };

        $scope.getOS = function () {
            let OSName = "Unknown OS";

            if (navigator.userAgent.indexOf("Mac") != -1) OSName = 0;
            else if (navigator.userAgent.indexOf("Win") != -1) OSName = 1;
            else if (navigator.userAgent.indexOf("Linux") != -1) OSName = 2;
            else if (navigator.userAgent.indexOf("Android") != -1) OSName = 3;
            else if (navigator.userAgent.indexOf("iPhone") != -1) OSName = 4;

            return OSName;
        };

        $scope.newUser();
        window.onload = function() {
            let navbar = document.getElementById("navbar");
            navbar.classList.add("navbar-dark");
        };

    }]);