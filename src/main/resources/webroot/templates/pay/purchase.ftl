<!DOCTYPE html>
<html lang="en-us" ng-app="payApp">
<head>
    <meta charset="UTF-8">
    <title>Stripe payment</title>
    <meta name="description" content="Purchase a study deck resource" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" type="text/css" href="/app/style/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/app/style/global.css" />
<#--    <script src="https://js.stripe.com/v3/"></script>-->
    <script src="/app/pay/payout.js" defer></script>

</head>
<body>
<main id="main" class="loading">
    <div class="sr-root">
        <header class="sr-header">
            <div class="sr-header__logo"></div>
        </header>
    </div>
    <section>
        <!-- Section to display when no connected accounts have been created -->
        <div id="no-accounts-section" class="hidden">
            <div>You need to  <a href="https://stripe.com/docs/connect/collect-then-transfer-guide#create-account">create an account</a> before you can process a payment.</div>
        </div>
        <!-- Section to display when connected accounts have been created, but none have charges enabled -->
        <div id="disabled-accounts-section" class="hidden">
            <div>None of your recently created accounts have charges enabled. <span class="express hidden">Log in to an Express account's dashboard to complete the onboarding process.</span><span class="custom hidden">Manage your Custom accounts and complete the onboarding process <a href="https://dashboard.stripe.com/test/connect/accounts/overview">in the dashboard.</a></span><span class="standard hidden">View your Standard accounts <a href="https://dashboard.stripe.com/test/connect/accounts/overview">in your platform's dashboard</a>, and use their credentials to log in to Stripe and complete the onboarding process.</span></div>
            <form id="disabled-accounts-form" class="hidden">
                <div class="sr-form-row">
                    <label for="disabled-accounts-select">Disabled account</label>
                    <!-- Options are added to this select in JS -->
                    <select id="disabled-accounts-select" class="sr-select"></select>
                </div>
                <div class="sr-form-row">
                    <button type="submit" class='full-width'>View Express dashboard</button>
                </div>
            </form>
        </div>
        <div id="item-summary" >
            <table>
                <#list purchaseData as data>
                    <tr class="active">
                        <td class="text-right">Deck Name:</td>
                        <td>${data.deck_name}</td>
                    </tr>
                    <tr class="active">
                        <td class="text-right">Price:</td>
                        <td>${data.deck_price}</td>
                    </tr>
                    <tr class="active">
                        <td class="text-right">non-preem fee:</td>
                        <td>${data.fee} </td>
                    </tr>
                </#list>
            </table>
        </div>
            <div>
            <button id="purchase-btn">next</button>
        </div>
    </section>

    <!-- Payment Form -->
    <div id="enabled-accounts-section" >
        <div id="checkout">
            <form action="/101/AA2959" method="post" id="payment-form">

                <section>
                    <h2>Billing Information</h2>
                    <fieldset class="with-state">
                        <label>
                            <span>email</span>
                            <input type="text" id="email" placeholder="Email address" required/>
                        </label>
                        <label>
                            <span>Name</span>
                            <input name="name" id="name" class="field" placeholder="Name" required>
                        </label>
                        <div id="card-element" class="sr-input sr-card-element field">
                            <!-- A Stripe Element will be inserted here. -->
                        </div>
                    </fieldset>
                    <!-- display form errors -->
                    <div id="card-errors" role="alert"></div>
                    <div class="spinner hidden" id="spinner"></div>
                    <button id="submit-btn">Make payment</button>

                </section>
        </form>
            <div class="sr-result hidden">
                <div>
                    <h2 id="close-title">PURCHASE COMPLETED</h2>
                </div>
                <pre>
                    <code></code>
                </pre>
            </div>
        </div>
    </div>


</main>
</body>
</html>