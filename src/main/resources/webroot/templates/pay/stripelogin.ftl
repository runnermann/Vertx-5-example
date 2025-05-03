<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>FlashMonkey-Stripe login</title>
    <meta name="description" content="Advanced Membership" />

    <!--<link rel="icon" href="favicon.ico" type="image/x-icon" />-->
    <link rel="stylesheet" type="text/css" href="/app/style/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/app/style/global.css" />
<#--    <script src="https://js.stripe.com/v3/"></script>-->
    <script src="/app/pay/payout.js" defer></script>
</head>
<body>
    <div class="sr-root">

        <div class="sr-root">
            <div class="sr-main">
                <header class="sr-header">
                    <div class="sr-header__logo"></div>
                </header>
                <div class="alert-warning"><h2>Oops something happened. Try again.</h2></div>

                <div class="sr-payment-summary payment-view">
                    <h1 class="order-amount">Setup payouts to receive payments</h1>
                </div>

                <button id="submit">Setup payouts on Stripe</button>
            </div>
        </div>
    </div>


</body>
</html>