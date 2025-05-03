/*
 *  Copyright (c) 2020 - 2021 FlashMonkey Inc. and/or its affiliates.
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

        //let close_intro = document.querySelector("#intro-close-btn");
        let pg_next_btn = document.querySelector("#page-next-btn");
        let presubscribe_next_btn = document.querySelector("#presubscribe-next-btn");
        let close_email = document.querySelector("#close_email");
        let close_signUp = document.querySelector("#close_signUp");

        // $scope.newUser = function () {
        //     //$scope.rep_hash = "";
        // };

        /* loads on page load? */
        $scope.load = function () {
            // set into storage
            let tk = document.getElementById("tk").innerText;
            localStorage.setItem("token", tk);
        }


         // The user enters their email in the form
         // on submit, we send the email with sendTwo.
         // If user is found then go to purchase. If not
         // go to sign up.
         $scope.sendtwo = function (isValid) {
            if ($scope.email === "") {
                $scope.error("Please check your email.");
            } else if (!isValid) {
                $scope.error("Please check the form for an incorrect entry.")
            } else {
                localStorage.setItem("email", $scope.email);
                // send to vertx and confirm user does not exist.
                let payload = {
                    "email": $scope.email,
                    "token": localStorage.getItem("token")
                };
                fetch("/Q52/FFD001", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(payload)
                })
                    .then(response => response.json())
                    .then(function (response) {
                        let found = response.user.found;
                        if (found === 0) {
                            $scope.showusercomplete();
                        } else if (found === 1) {
                            $scope.showpresubscribe();
                        } else {
                            $scope.showsignup();
                        }
                    });
            }
        }

        $scope.reqSubscribe = function () {
            let payload = {
                "x1": localStorage.getItem("email")
            }
            fetch("/P01/HF80XZ", {
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
        }

        // used by saveNewUser
        $scope.save = function (isValid) {
            if($scope.userName === "") {
                $scope.error("Please check your name.");
            }
            /*else if($scope.email === "") {
                $scope.error("Please check your email.");
            }*/
            else if ($scope.password !== $scope.verifyPassword) {
                $scope.error("Passwords do not match.");
            } else if (!isValid) {
                $scope.error("Please check the form for an incorrect entry.")
            } else {
                let payload = {
                    "userName": $scope.userName,
                    "password": $scope.password,
                    "email": localStorage.getItem('email'),
                    "token": localStorage.getItem("token")
                };
                $http.post("/get-smart/CU1209", payload).then(function (response) {
                    //$scope.reload();
                    //localStorage.removeItem('byr_hash');
                    localStorage.setItem('byr_hash', response.data.byr_hash);
                    $scope.showpresubscribe();

                }, function (err) {
                    $scope.error(err.data.error);
                });
                $scope.password = "";
                $scope.verifyPassword = "";
                //}
            }
        };

        $scope.showsignup = function () {
            let modalSi = document.getElementById("email_modal");
            modalSi.style.display = "none";
            let modalSu = document.getElementById("signUp_modal");
            modalSu.style.display = "block";
        }
        $scope.showpresubscribe = function () {
            let modalSi = document.getElementById("signUp_modal");
            modalSi.style.display = "none";
            let modalSu = document.getElementById("presubscribe_modal");
            modalSu.style.display = "block";
        }
        $scope.showfollowthru = function () {
            let modalCl = document.getElementById("subscribe_modal");
            modalCl.style.display = "none";
            let modalOp = document.getElementById("followthru_modal");
            modalOp.style.display = "block";
        }
        $scope.showusercomplete = function () {
            let modalSi = document.getElementById("email_modal");
            modalSi.style.display = "none";
            let modalM = document.getElementById("already_subscribed");
            modalM.style.display = "block";
        }

        $scope.load();

        $scope.showError = function(errorMsgText) {
            $scope.changeLoadingState(false);
            let errorMsg = document.querySelector(".sr-field-error");
            errorMsg.textContent = errorMsgText;
            setTimeout(function() {
                errorMsg.textContent = "";
            }, 4000);
        };

        // Show a spinner
        $scope.changeLoadingState = function(isLoading) {
            if (isLoading) {

                document.querySelector("#spinner").classList.remove("hidden");
                document.querySelector("#button-text").classList.add("hidden");
            } else {

                document.querySelector("#spinner").classList.add("hidden");
                document.querySelector("#button-text").classList.remove("hidden");
            }
        };

        // *** SUCCESS FAILURE MESSAGES/ACTIONS ***
        // Tell user they were successfully added.
        $scope.success = function (message) {
            $scope.alertMessage = message;
            let alert = document.getElementById("alertMessage");
            alert.classList.add("alert-success");
            alert.classList.remove("invisible");
            $timeout(function () {
                alert.classList.add("invisible");
                alert.classList.remove("alert-success");
            }, 10000);

            let response = document.getElementById("alertMessage");
            response.classList.add("alert-success");
        };

        // Tell the user that their email already exists and provide the
        // log-in link and password reset link.
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

        close_signUp.addEventListener('click', () => {
            let intro = document.getElementById("signUp_modal");
            intro.style.display = "none";
            pg_next_btn.removeAttribute("disabled");
        });
        close_email.addEventListener('click', () => {
            let intro = document.getElementById("email_modal");
            intro.style.display = "none";
            //let pg_next_btn = document.querySelector("#page-next-btn");
            pg_next_btn.removeAttribute("disabled");
        });
        // close_intro.addEventListener('click', () => {
        //     pg_next_btn.removeAttribute("disabled");
        //     let intro = document.getElementById("intro_modal");
        //     intro.style.display = "none";
        // });
        pg_next_btn.addEventListener('click', () => {
            let email = document.getElementById("email_modal");
            pg_next_btn.setAttribute("disabled", "disabled");
            email.style.display = "block";
        });
        presubscribe_next_btn.addEventListener('click', () => {
            let modalSu = document.getElementById("presubscribe_modal");
            modalSu.style.display = "none";
            let email = document.getElementById("subscribe_modal");
            email.style.display = "block";

            let payload = {
                "email": localStorage.getItem('email'),
                "token": localStorage.getItem("token")
            };
            $http.post("/P01/HF50XZ", payload).then(function () {
                // do nothing
            }, function (err) {
                $scope.error(err.data.error);
            });
        });



        app.directive('nxEqualEx', function() {
            return {
                require: 'ngModel',
                link: function (scope, elem, attrs, model) {
                    if (!attrs.nxEqualEx) {
                        return;
                    }
                    scope.$watch(attrs.nxEqualEx, function (value) {
                        // Only compare values if the second ctrl has a value.
                        if (model.$viewValue !== undefined && model.$viewValue !== '') {
                            model.$setValidity('nxEqualEx', value === model.$viewValue);
                        }
                    });
                    model.$parsers.push(function (value) {
                        // Mute the nxEqual error if the second ctrl is empty.
                        if (value === undefined || value === '') {
                            model.$setValidity('nxEqualEx', true);
                            return value;
                        }
                        var isValid = value === scope.$eval(attrs.nxEqualEx);
                        model.$setValidity('nxEqualEx', isValid);
                        return isValid ? value : undefined;
                    });
                }
            };
        });
}]);