// OnBoarding
let onboardButton = document.querySelector("#onboard-btn");
document
    .getElementById("onboard-btn");

let lx1 = "not set";
function callMe(x1) {
    lx1 = x1;
    localStorage.setItem("x1", lx1)
}

onboardButton.addEventListener(
    "click",
    e => {
        onboardButton.setAttribute("disabled", "disabled");
        onboardButton.textContent = "Opening...";

        if (localStorage.getItem('email') !== null) {

                let payload = {
                    "y2": localStorage.getItem('email')
                };
                fetch("/P01/F06H21", {
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
                            onboardButton.textContent = "cannot process without a subscription";
                        }
                    });
        } else if (localStorage.getItem('x1') !== null) {
            let payload = {
                "x1": localStorage.getItem('x1')
            };
            fetch("/P01/F06H21", {
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
                        onboardButton.textContent = "cannot process without a subscription";
                    }
                });
        }
    },
    false
);

// confirm amount button
// let payForm = document.querySelector("#payment-form");
// submitButton.setAttribute("disabled", "disabled");
// payForm.setAttribute("hidden", "hidden");




