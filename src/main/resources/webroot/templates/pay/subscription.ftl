<!DOCTYPE html>
<html lang="en" ng-app="userApp">
<head>
    <meta charset="utf-8" />
    <title>Stripe Subscription</title>
    <meta name="description" content="Advanced Membership" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <script src='/app/javascript/bootstrap/bootstrap.js'></script>
    <link rel="stylesheet" type="text/css" href="/app/style/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/app/style/global.css" />
<#--    <link href="https://fonts.googleapis.com/css?family=Raleway|Roboto" rel="stylesheet">-->
    <!-- angular -->
    <#include "../common/javascript_block.ftl">
    <script src="/app/pay/callSubscribe.js"></script>
</head>
<body>
        <div class="sr-root">
            <div class="sr-main" style="display: flex;">
                <header class="sr-header">
                    <div class="margin-btm-16vh sr-header__logo"></div>
                </header>
                    <section class="container">
                        <div class="margin-btm-16vh text-center">
                            <br>
                            <h1 class="center-text">Advanced</h1>
                            <br>
                            <h4 class="center-text">Earn pay and build your knowledge credibility score&trade;. </h4>
                            <br>
                            <p class="center"><b>Let's face it. Being a college student can be challenging but getting
                                    a good job can be harder.</b></p>
                            <p class="center">At KnocScore, we believe there is a lot more that can be done by technology.
                                In fact, we are surprised that we are the first to bring the potential of earning
                                pay<sup>note 1</sup> and the power of earning stats for actual learning. KnocScore was built out of
                                frustration with other systems that give little or no credit and are less focused on
                                learning and more focused on their profits. Our platform is not only about learning
                                and studying, it pays students tutors and educators 100%<sup>note 2</sup> of what they earn, then gives
                                them the ability to show off who they are with their Stats<sup>note 1</sup>. Stats are not grades. Stats
                                show effort and value in a specific area of study. KnocScore just released a new tool
                                that provides concrete evidence of value and effort in specific domain. It’s a tool you
                                can use to show off your capability.</p>
                            <br>
                            <h1 class="center-text">Price: $8.00/month</h1>
                            <br>
                            <h3 class="center-text"> After signing up, you may
                                enroll to receive your pay through Stripe.
                                Stripe is our secure payment-system partner.</h3>
                            <br>
                        </div>
                        <div class="text-center">
                            <div id="btn_div" ng-controller="AppSubscribeController">
                                <button id="subscribe_btn" style="background-color: #FF5400; border-color: #E64C00;"> Start Earning </button>
                            </div>
                            <br>
                            <a href="/earning-money-as-a-college-student">Learn how earning on KnocScore works.</a>
                            <br>
                            <a href="/pay-faq">Pay System FAQ page</a>
                            <br><br>
                            <p><b>Note 1:</b> KnocScore and the technology to provide these capabilities is Patent Pending</p>
                            <p><b>Note 2:</b> Users earn 100% of the price they set minus credit card fees and a $0.32 Stripe fee.</p>
                            <p>DISCLAIMER: This price applies to student, educator, and tutor accounts with less than 4GB of data.
                               For businesses institutions and developers, please email flash@knocscore.com to discuss how to
                                create an advanced user experience on our platform.</p>
                        </div>
                    </section>
            </div>
        </div>
</body>
</html>