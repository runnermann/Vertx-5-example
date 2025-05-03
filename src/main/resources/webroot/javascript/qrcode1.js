/*
 *  Copyright (c) 2017 Red Hat, Inc. and/or its affiliates.
 *  Copyright (c) 2017 INSA Lyon, CITI Laboratory.
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
    .controller("UserController", ["$scope", "$http", "$timeout", function ($scope, $http, $timeout) {

        const EMPTY_STRING = "";
        let payBtn = document.querySelector("#pay-btn");
        let payForm = document.querySelector("#payment-form");
        let continueBtn = document.getElementById("continue-btn");
        let userName = document.querySelector("#name");
        let title = document.querySelector("#page-title");
        let user_email = "";
        let close_pay = document.getElementById("close_pay_modal");
        close_pay.addEventListener('click', () => {
            let modal = document.getElementById("pay_modal");
            modal.style.display = "none";
            document.body.style = "overflow-y:visible; position: static; margin-right: 0;";
        });

        // NEW

        // window.addEventListener("scroll", e=> {
        //     let navbar = document.getElementById("navbar");
        //     if ((window).scrollY > 60) {
        //         navbar.style.display = 'none';
        //     } else {
        //         navbar.style.display = 'block';
        //     }
        // });

        // *** CRUD OPERATIONS ****

        $scope.newUser = function () {
            $scope.deckId = undefined;
            $scope.email = EMPTY_STRING;
            user_email = EMPTY_STRING;
        };

        /* loads on page load? */
        $scope.load = function () {
            let deck_id = document.getElementById("deck_id").innerText;
            let deck_name = document.getElementById("deck_name").innerText;
            let tk = document.getElementById("tk").innerText;
            localStorage.setItem("deck_id", deck_id);
            localStorage.setItem("deck_name", deck_name);
            localStorage.setItem("token", tk);
        };

        $scope.updateRendering = function (html) {
            document.getElementById("rendering").innerHTML = html;
        };

        // used by saveNewUser
        $scope.save = function (isValid) {
            if($scope.userName === "") {
                $scope.error("Please check your name.");
            } else if (!isValid) {
                $scope.error("Please check the form for an incorrect entry.")
            } else {
                    let payload = {
                        "userName": " ",
                        "email": localStorage.getItem('byr_email'),
                        "password": $scope.password,
                        "token": localStorage.getItem('token')
                    };
                    $http.post("/get-smart/CU1209", payload).then(function (response) {

                        localStorage.setItem('byr_hash', response.data.byr_hash);
                        $scope.showpurchase();

                    }, function (err) {
                        $scope.error(err.data.error);
                    });
                    $scope.password = "";
            }
        };

        // When the user enters their email, We call sendOne.
        // If user is found then go to purchase. If not
        // go to sign up.
        $scope.sendone = function (isValid) {
            if ($scope.email === "") {
                window.alert("Email is invalid")
            } else if (!isValid) {
                $scope.error("Please check the form for an incorrect entry.")
            } else {
                let payload = {
                    "deck_id": localStorage.getItem("deck_id"),
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
                        localStorage.setItem('byr_email', $scope.email);
                        localStorage.setItem('byr_hash', response.user.hash);
                        if (found === 2) {
                            $scope.showsignup();
                        } else {
                            $scope.showpurchase();
                        }
                    });
            }
        }

        // shows the purchase modal after showPurchase is called.
        $scope.showpurchasemodal = function () {
            let modalSi = document.getElementById("signIn_modal");
            modalSi.style.display = "none";
            let modalPay = document.getElementById("pay_modal");
            modalPay.style.display = "block";
        }

        //Shows the credit card form
        $scope.showpurchform = function () {
            let displayP = document.getElementById("repeat_purchased");
            displayP.style.display = "none";
            let showform = document.getElementById("show_form");
            showform.style.display = "block";
        }

        // purchase process
        $scope.showpurchase = function () {
            let payload = {
                "byr_email": localStorage.getItem("byr_email"),
                "byr_hash": localStorage.getItem("byr_hash"),
                "deck_name": localStorage.getItem("deck_name"),
                "deck_id": localStorage.getItem("deck_id")
            };
            // request payment intent
            fetch("/QR/AA2959", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            })
                .then(response => response.json())
                .then(function (response) {
                    $scope.showpurchasemodal();
                    if(undefined !== response.data) {
                        let free = response.data.free;
                        let hasPurchased = response.data.repeatpurchase;
                        if(free) {
                            if(hasPurchased) {
                                document.getElementById("repeat_purchased").style.display = "block";
                                document.getElementById("show_form").style.display = "none";
                                continueBtn.style.display = "none";
                            } else {
                                $scope.freeXferComplete();
                            }
                        } else if(hasPurchased) {
                            //$scope.showhasalreadypurchased();
                            document.getElementById("repeat_purchased").style.display = "block";
                            document.getElementById("show_form").style.display = "none";
                            continueBtn.addEventListener('click', () => {
                                $scope.showpurchform();
                                $scope.setupElements(response);
                            })
                        } else {
                            $scope.showpurchform();
                            $scope.setupElements(response);
                        }
                    }
            });
        }

        // $scope.freeOrPurchase = function (free, response) {
        //     if(free) {
        //         $scope.freeXferComplete(response);
        //     } else {
        //         $scope.showpurchform();
        //         $scope.setupElements(response);
        //     }
        // }

        $scope.showsignup = function () {
            let modalSi = document.getElementById("signIn_modal");
            modalSi.style.display = "none";
            let modalSu = document.getElementById("signUp_modal");
            modalSu.style.display = "block";
            let nm = localStorage.getItem('byr_email');
            //document.getElementById("email").placeholder = nm;
            document.getElementById("email").innerHTML = nm;
        }

        // $scope.reload();
        $scope.newUser();
        $scope.load();

        // confirm amount button

        // data is from env
        $scope.setupElements = function(response) {
                    $scope.stripe = Stripe(response.data.publishableKey);
                    let clientSecret = response.data.clientSecret;
                    let elements = $scope.stripe.elements({
                        locale: 'auto'
                    });
                    let style = {
                        base: {
                            padding: "20px 20px",
                            background: "transparent",
                            fontWeight: "400",
                            color: "#31325f",
                            fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
                            fontSmoothing: "antialiased",
                            fontSize: "14px",
                            "::placeholder": {
                                color: "#aab7c4"
                            }
                        },
                        invalid: {
                            color: "#fa755a",
                            iconColor: "#fa755a"
                        }
                    };

                    let card = elements.create("card", { style: style });
                    card.mount("#card-element");
                    // handle validation errors locally so the user isn't surprised with annoying
                    // message later.
                    card.on('change', function(event) {
                        let displayError = document.getElementById('card-errors');
                        if (event.error) {
                            displayError.textContent = event.error.message;
                        } else {
                            displayError.textContent = '';
                        }
                    });

                    // Handle form submission.
                    payForm.addEventListener("submit", function(event) {
                            event.preventDefault();
                            // Initiate payment when the submit button is clicked
                            // If authentication is required, confirmCardPayment will automatically display a modal
                            payBtn.setAttribute("disabled", "disabled");
                            $scope.stripe.confirmCardPayment(clientSecret, {
                                payment_method: {
                                    card: card,
                                    billing_details: {
                                        name: userName
                                    }
                                }
                            }).then(function (result) {
                                if (result.error) {
                                    console.log(result.error.message);
                                    payBtn.textContent = "Payment failed!";
                                    showError(result.error.message);
                                } else {
                                    // The payment has been processed!
                                    $scope.orderComplete(clientSecret);
                                }
                            });
                    });
        };

        $scope.orderComplete = function (clientSecret) {
                    // Just for the purpose of the sample, show the PaymentIntent response object
                    $scope.stripe.retrievePaymentIntent(clientSecret).then(function(result) {

                        let json = result.paymentIntent;

                        payForm.setAttribute("hidden", "hidden");
                        payBtn.textContent = "Payment completed";
                        title.textContent = "Confirmation";
                        document.querySelector("pre").textContent = "Thank you for your purchase.\n" +
                            "A receipt has been sent \nto the address you provided." +
                            "\n\nYou should see your new " +
                            "\nstudy materials in the app.";

                        document.querySelector(".sr-result").classList.remove("hidden");
                        setTimeout(function() {
                            document.querySelector(".sr-result").classList.add("expand");
                        }, 200);

                        $scope.changeLoadingState(false);
            });
        }

        $scope.freeXferComplete = function () {

            let displayP = document.getElementById("repeat_purchased");
            displayP.style.display = "none";
            //document.getElementById("show_form").style.display = "none";
            payForm.setAttribute("hidden", "hidden");
            continueBtn.setAttribute("hidden", "hidden");
            //payBtn.setAttribute("hidden", "hidden");;

            title.textContent = "Confirmation";
            document.querySelector(".sr-result").classList.remove("hidden");
            document.querySelector("pre").textContent = "Free purchase.\n" +
                "The deck has been transferred \nYou should see your new\n" +
                "study materials in the app.";

        }

        $scope.showError = function(errorMsgText) {
                    $scope.changeLoadingState(false);
                    let errorMsg = document.querySelector(".sr-field-error");
                    errorMsg.textContent = errorMsgText;
                    setTimeout(function() {
                        errorMsg.textContent = "";
                    }, 4000);
        };

        // Show a spinner on payment submission
        $scope.changeLoadingState = function(isLoading) {
                    // if (isLoading) {
                    //
                    //     document.querySelector("#spinner").classList.remove("hidden");
                    //     document.querySelector("#button-text").classList.add("hidden");
                    // } else {
                    //
                    //     document.querySelector("#spinner").classList.add("hidden");
                    //     document.querySelector("#button-text").classList.remove("hidden");
                    // }
        };

        // *** SUCCESS FAILURE MESSAGES/ACTIONS ***
        // Tell user they were successfully added.
        let alert = document.getElementById("alertMessage");
        alert.classList.add("alert-success");

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

        // NEW

        // window.addEventListener("scroll", e=> {
        //     let navbar = document.getElementById("navbar");
        //     if ((window).scrollY > 60) {
        //         navbar.style.display = 'none';
        //     } else {
        //         navbar.style.display = 'block';
        //     }
        // });


    }]);

