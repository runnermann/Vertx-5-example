<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
    <link rel="canonical" href="https://knocscore.com/pay-faq">
    <meta name="robots" content="noindex, nofollow">
    <#include "../common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="Pay FAQ's">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://knocscore.com/pay-faq">
    <meta property="og:title" content="Pay FAQ's">
    <#include "../common/styles_block_infopg.ftl">
</head>
<body>
    <#include "../common/header_block.ftl">
    <!-- FAQs -->
    <br class="clearfloat">
    <br>
    <br>

    <div class="sr-root">
        <section>
            <div class="container" >
                <div class="row">
                    <div class="col-md-12 bg-light">
                        <br>
                        <br>
                        <br>
                        <h1 class="text-center">Questions you may have about your Stripe Connected Account</h1>
                        <br>
                        <br>
                        <br>
                        <h3>Are there any fee's when I receive a payout?</h3>
                        <p> The fees are minimal. To see the fees that are charged on a payout, see the FAQ page.
                            <a href="/faq">FAQ's</a></p>
                        <br>
                        <h3>How do I receive a payout?</h3>
                        <p>When your learning materials sell, the payment is transferred into
                            your Stripe Connected Account and placed on hold until the scheduled pay date. At that time,
                            the funding is transferred to the account you provided to Stripe during the on-boarding process.</p>
                        <br>
                        <h3>What is a Stripe payout?</h3>
                        <p>Stripe payouts are how money from your customers are deposited
                            into your bank account. Depending on your business location and payout schedule, funds from your
                            transactions will be paid out to your bank account on a rolling basis. If you reside in the
                            United States and have a US Bank Account, then a payout is made every Friday. You will receive all
                            payments from buyers that were from 7 to 14 days prior.</p>
                        <p><t>Example: If the 1st falls on a Thursday and the 8th is the following Friday. And between the
                                1st and the 7th at 11:59 pm your classmates and school mates purchase
                                10 decks; You will receive a payment for the 10 decks on the following Friday the 15th.</t></p>
                        <p><b>Delayed 1st payment:</b> When you first start processing payments from your customers with Stripe,
                            you won’t receive your first payout until 7–14 days after receiving your first successful payment.
                            The first payout usually takes a little longer in order to establish the Stripe account.</p>
                        <p>For more information on Stripe Payouts see what Stripe has to say at:
                            <a href="https://support.stripe.com/topics/payouts"> Questions about Stripe payouts</a></p>

                        <br>
                        <h3>I just received an email from Stripe. Who is Stripe? Is this email real?</h3>
                        <p>We work with Stripe to enable payments between you and others. Updating information about your
                            account is important and required. These updates help protect against fraud and keep your
                            account current with global
                            <a href="https://support.stripe.com/questions/know-your-customer-obligations" rel="noopener noreferrer"> Know Your Customer (KYC) regulations</a>.</p>
                        <br>
                        <h3>What is onboarding? Do I need to onboard with Stripe?</h3>
                        <p>Onboarding is necessary in order for you to receive payments on our system. Our platform provides
                            revenue which may be taxable. Stripe is a secure third party payment company we are currently
                            partnered with that meets the standards for security, privacy, and reliability. It is not possible
                        to transfer funds to your bank account if you have not provided Stripe your information.</p>
                        <br>
                        <h3>During onboarding, why am I being asked about what do I do for KnocScore?</h3>
                        <p>In certain countries, Stripe is required to collect, verify, and maintain information about its users.
                            These requirements come from government regulators and help promote transparency
                            and prevent financial crimes. It is up to you to chose the correct answer for your
                        situation, but "selling learning materials" would apply to most situations. </p>
                        <br>
                        <h3>Stripe is asking me to update my information.
                                What happens if I do not update my information by the deadline?</h3>
                        <p>If you cannot update your account information by the deadline, payouts to your bank account
                            and your ability to accept payments will be paused. We want to make sure that does not happen,
                            so please let us know how we can help.</p>
                        <br>
                        <h3>How do I update my information?</h3>
                        <p>Follow the link from Stripe’s email. The form will prompt you with instructions on how to update
                            your information. If you need help, you can reach out directly to
                            <a href="mailto:support@stripe.com">support@stripe.com</a>or via Stripe chat and phone support.</p>
                        <br>
                        <br>
                        <p> For information about your Stripe account see the Stripe FAQ pages at
                            <a href="https://support.stripe.com/questions/common-questions-about-stripe-outreach-for-account-information">
                                Stripe common questions about account information.
                            </a></p>
                        <br>
                        <br>

                    </div>
                </div>
            </div>

        </section>
    </div>
<!-- footer -->
<#include "../new_footer.ftl">
<#include "../common/closeout_scripts.ftl">
</body>
</html>