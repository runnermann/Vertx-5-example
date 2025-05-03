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

// confirm amount button
let purchButton = document.querySelector("#purchase-btn");
let submitButton = document.querySelector("#submit-btn");
let payForm = document.querySelector("#payment-form");
let items = document.querySelector("#item-summary");
let formName = document.querySelector("#name");
let title = document.querySelector("#close-title");
submitButton.setAttribute("disabled", "disabled");
payForm.setAttribute("hidden", "hidden");

purchButton.addEventListener(
      "click",
      e => {

          purchButton.textContent = "Opening...";
          items.setAttribute("hidden", "hidden");
          purchButton.setAttribute("hidden", "hidden");
          submitButton.removeAttribute("disabled");
          payForm.removeAttribute("hidden");
            // resource
          fetch("/101/AA2959", {
              method: "POST",
              headers: {
                  "Content-Type": "application/json"
              },
              //@TODO change deck_id to real value from user
              body: JSON.stringify({deck_id: 75})
          })
              .then(response => response.json())
              .then(function (response) {
                  if(undefined !== response.data) {
                      setupElements(response);
                  }
              })
              .then(function(stripe, card, clientSecret, free) {
                  if(!free) {
                      document.querySelector("button").disabled = false;

                      // Handle form submission.
                      let form = document.getElementById("payment-form");
                      form.addEventListener("submit", function (event) {
                          event.preventDefault();
                          // Initiate payment when the submit button is clicked
                          pay(stripe, card, clientSecret);
                      });
                  } else {
                      freeXferComplete(response);
                  }
              });
      },
        false
);


// Set up Stripe.js and Elements to use in checkout form
// data is from env
let setupElements = function(response) {
            let free = response.data.free;
            if (free) {
                freeXferComplete(response);
            } else {
                stripe = Stripe(response.data.publishableKey);
                let clientSecret = response.data.clientSecret;
                let elements = stripe.elements({
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

                let card = elements.create("card", {style: style});
                card.mount("#card-element");
                // remove hidden attribute to show form
                document.querySelector("#enabled-accounts-section").classList.remove("hidden");


                // handle validation errors locally so the user isn't surprised with annoying
                // message later.
                card.on('change', function (event) {
                    let displayError = document.getElementById('card-errors');
                    if (event.error) {
                        displayError.textContent = event.error.message;
                    } else {
                        displayError.textContent = '';
                    }
                });

                // Handle form submission.
                document.getElementById("payment-form")
                    .addEventListener("submit", function (event) {
                        event.preventDefault();
                        // Initiate payment when the submit button is clicked
                        //pay(stripe, card, paymentIntentClientSecret);
                        stripe.confirmCardPayment(clientSecret, {
                            payment_method: {
                                card: card,
                                billing_details: {
                                    //@TODO replace hard coded name with user real name
                                    name: formName,
                                }
                            }
                        }).then(function (result) {
                            if (result.error) {
                                console.log(result.error.message);
                                purchButton.removeAttribute("hidden");
                                purchButton.textContent = "Payment failed!  Try again";
                                submitButton.setAttribute("disabled", "disabled");
                            } else {
                                // The payment has been processed!
                                orderComplete(clientSecret);
                            }
                        });
                    });
            }
    };

freeXferComplete = function (data) {
    payForm.setAttribute("hidden", "hidden");
    title.textContent = "Free Purchase has Completed";
    document.querySelector("pre").textContent = "The deck has been transferred " +
        "\nand is now available for you" +
        "\nto download on the Deck Selection Page." +
        "\n\nIf the deck is not present, \nrestart the app.";

    document.querySelector(".sr-result").classList.remove("hidden");
    setTimeout(function() {
        document.querySelector(".sr-result").classList.add("expand");
    }, 200);

    changeLoadingState(false);
}

  /*
  * Calls stripe.confirmCardPayment which creates a pop-up modal to
  * prompt the user to enter extra authentication details without leaving your page
  */
    let pay = function(stripe, card, clientSecret) {
        changeLoadingState(true);
        // Initiate the payment.
        // If authentication is required, confirmCardPayment will automatically display a modal
        stripe
            .confirmCardPayment(clientSecret, {
                payment_method: {
                    card: card
                }
            })
            .then(function(result) {
                if (result.error) {
                    // Show error to your customer
                    showError(result.error.message);
                } else {
                    // The payment has been processed!
                    orderComplete(clientSecret);
                }
            });
    };

    var orderComplete = function (clientSecret) {
        // Just for the purpose of the sample, show the PaymentIntent response object
        stripe.retrievePaymentIntent(clientSecret).then(function(result) {

            payForm.setAttribute("hidden", "hidden");
            submitButton.textContent = "Payment completed";
            title.textContent = "Confirmation";
            document.querySelector("pre").textContent = "Thank you for your purchase.\n" +
                "Your study materials are available\non the first page.";


            document.querySelector(".sr-result").classList.remove("hidden");
            setTimeout(function() {
                document.querySelector(".sr-result").classList.add("expand");
            }, 200);

            changeLoadingState(false);
        });
    }

    let showError = function(errorMsgText) {
        changeLoadingState(false);
        var errorMsg = document.querySelector(".sr-field-error");
        errorMsg.textContent = errorMsgText;
        setTimeout(function() {
            errorMsg.textContent = "";
        }, 4000);
    };

    // Show a spinner on payment submission
    var changeLoadingState = function(isLoading) {
        if (isLoading) {
            purchButton.disabled = true;
            document.querySelector("#spinner").classList.remove("hidden");
            document.querySelector("#button-text").classList.add("hidden");
        } else {
            purchButton.disabled = false;
            document.querySelector("#spinner").classList.add("hidden");
            document.querySelector("#button-text").classList.remove("hidden");
        }
    };




