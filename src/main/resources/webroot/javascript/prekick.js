/*
 *  Copyright (c) 2020 - 2024 FlashMonkey Inc. .
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
    .controller("PrekickController", ["$scope", "$http", "$timeout", function ($scope, $http, $timeout) {

        const EMPTY_STRING = "";
        let user_email = "";
        let timestamp = Date.now();
        let num = 0;
        let isLoaded = false;

        window.addEventListener("scroll", e => {
            $scope.use('scrolling');
        });

        $scope.newUser = function () {
            $scope.email = EMPTY_STRING;
            user_email = EMPTY_STRING;
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
                    "email": $scope.email
                };
                $http.post("/launch/PK9A01", payload).then(function (ok) {
                    $scope.showcompleted();
                }, function (err) {
                    $scope.error(err.data.error);
                });
            }
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
                document.body.style = "overflow-y:visible; position: static; margin-right: 0;";
            }, 10000);
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

        $scope.use = function (action) {
            let payload = {
                "event": action,
                "user-agent": navigator.userAgent
            };
            if(num < 1) {
                $http.post("/PA/EFA137", payload);
                num++;
            }
            else if((timestamp - Date.now() > 15000) && (num < 2)) {
                $http.post("/PA/EFA137", payload);
                num++;
            }
            else if((timestamp - Date.now() > 30000) && (num < 3)) {
                $http.post("/PA/EFA137", payload);
                num++;
            }
        };
        $scope.vidclick = function () {
            let payload = {
                "event": "video_clicked",
                "user-agent": navigator.userAgent
            };
            $http.post("/PA/EFA137", payload);
        }

        window.onload = function() {
            if (isLoaded === false) {
                $scope.use('load');
                isLoaded = true;
            }
        };
    }]);