<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
    <head>
        <meta charset="UTF-8">
        <title>KnocScore</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="KnocScore: A versatile and genius multi-media and interactive learning and teaching ecosystem based
    on the flashcard concept">
        <meta name="keywords" content="KnocScore, advanced learning ecosystem, flashcards, teaching">
        <meta name="description" content="Get KnocScore for free" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="apple-touch-icon" sizes="180x180" href="/app/images/favicons/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="16x16" href="/app/images/favicons/favicon-16x16.png">
        <link rel="icon" type="image/png" sizes="32x32" href="/app/images/favicons/favicon-32x32.png">
        <link rel="manifest" href="/app/images/favicons/site.webmanifest">
        <link rel="mask-icon" href="/app/images/favicons/safari-pinned-tab.svg" color="#5bbad5">
        <meta name="msapplication-TileColor" content="#da532c">
        <meta name="theme-color" content="#ffffff">
        <link href="/app/style/bootstrap/bootstrap.css" rel="stylesheet">

        <link rel="stylesheet" type="text/css" href="/app/style/normalize.css" />
        <link rel="stylesheet" type="text/css" href="/app/style/global.css" />
        <link rel="stylesheet" type="text/css" href="/app/style/style.css">

        <#include "../common/javascript_block.ftl">

        <script src="/app/javascript/qrsignup.js"></script>
    </head>
    <body>
        <header>
            <div class="header_content">
                <div id="home-logo">
                    <a id="home-link" href="/"></a>
                </div>
            </div>
        </header>

        <br class="clearfloat">

        <#list qrJoinData as data>
            <#-- modal_1 -->
                <div class="hidden" id="tk">${data.token}</div>
                <#-- modal_2 -->
                <#-- email_request_modal -->
                <div id="email_modal" class="modal" ng-controller="QRSignUpController">
                    <div>
                        <span class="close" id="close_email">&times;</span>
                    </div>
                    <div class="modal_content">
                    <#-- <h3 class="text-center">Let's get started.</h3>-->
                        <div class="sr-root">
                            <div class="sr-header__logo"></div>
                        </div>
                        <form name="emailqr" novalidate>
                            <div class="form-group">
                                <h3 class="text-center">GET OUR APP</h3>
                                <p class="text-center">Get better grades <br>Earn cash and cred <br>Be a creator</p>
                                <div>
                                    <input class="form-control" type="email" name="email" id="inputemail" aria-describedby="emailHelp"
                                           placeholder="email" ng-model="email" required>
                                </div>
                            </div>
                            <div>
                                <button type="button" class="btn-orange" ng-click="sendtwo(emailqr.$valid)"> NEXT </button>
                            </div>
                            <div role="alert" id="alertMessage">
<#--                                {{alertMessage}}-->
                            </div>
                            <div>
                                <p class="text-center">Compatible with Mac and Windows laptops
                                <br> and windows tablets.
                                </p>
                            </div>
                        </form>
                    </div>
                </div>

                <#-- modal_3 -->
                <#-- Create Account,  Sign-Up Modal -->
                <div id="signUp_modal" class="modal" ng-controller="QRSignUpController" >
                    <div class="modal_content">
                        <span class="close" id="close_signUp"> &times; </span>
                        <form name="newUser" novalidate>
                                    <style>
                                        /* input field colors for validation*/
                                        input.ng-valid:focus {
                                            border-left: 6px solid;
                                            border-left-color: WHITE;
                                        }
                                        input.ng-invalid:focus {
                                            border-left: 6px solid;
                                            border-left-color: salmon;

                                        }
                                        input.ng-pristine {
                                            background-color: white;
                                        }
                                    </style>

                                    <h3 class="text-center">AWESOME!</h3>
                                    <p class="text-center">A secure link to download our app has been sent to your inbox. Please
                                    check your spam in case it was filtered.</p>
                                    <p class="text-center">If you already know you want to earn pay on KnocScore, go ahead and
                                        subscribe. Just fill in the info in the following screens. You can also do this later in our software.</p>
                                    <br>
                                    <div class="form-group" >
                                        <label for="userName">First name</label>
                                        <input class="form-control" type="text" id="userName" name="nameField" aria-describedby="nameHelp" placeholder="First name"
                                               ng-model="userName" required maxlength="20" minlength="3"
                                               ng-pattern="/^[a-zA-Z0-9._\/-]+$/"
                                               ng-model-options="{ allowInvalid: false,
                                                            debounce: 0 }"/>
                                    </div>
                                    <div class="form-group">
                                        <label for="password">Password</label>
                                        <input class="form-control" type="password" name="passwordField" placeholder="password"
                                               ng-model="password"
                                               required maxlength="40" minlength="8"
                                               ng-pattern="/^(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*#.?&])[A-Za-z\d$@$!%*#.?&]{8,20}$/"
                                               ng-model-options="{ allowInvalid: false,
                                                            debounce: 0 }"/>
                                        <small id="pwHelp" class="form-text text-muted">Passwords require:
                                            <br>1) Between eight and 30 characters  2) at least one upper case and  3) one lower case letter  4)
                                            at least one number  5) not more than 2 repeating characters  6) and at least one of the following
                                            special characters @ $ ! % # ? & .
                                        </small>
                                    </div>

                                    <div class="form-group">
                                        <label for="verifyPassword">Retype password</label>
                                        <input class="form-control" type="password" name="verifyPassword" placeholder="retype password"
                                               ng-model="verifyPassword"
                                               required nx-equal-ex="password"
                                               ng-model-options="{ allowInvalid: false,
                                                            debounce: 0 }"/>

                                    </div>
                                    <div role="alert" id="alertMessage">
<#--                                        {{alertMessage}}-->
                                    </div>
                                    <div>
                                        <button type="submit" class="btn-orange" ng-click="save(newUser.$valid)">NEXT</button>
                                    </div>
                        </form>
                    </div>
                </div>

                <#-- modal_4 -->
                <#-- display the pre-subscribe message -->
                <div id="presubscribe_modal" class="modal" ng-controller="QRSignUpController">
                    <div class="modal_content">
                        <div class="text-center">
                            <p>${data.subscribe_text}</p>
                            <button id="presubscribe-next-btn" class="btn-orange"> NEXT </button>
                        </div>
                    </div>
                </div>

                <#-- modal_5 -->
                <#-- subscribe -->
                <div id="subscribe_modal" class="modal" ng-controller="QRSignUpController">
                    <div class="modal_content">
                        <#--<div class="sr-main" >-->
                            <div class="sr-header">
                                <div class="sr-header__logo"></div>
                            </div>
                            <div class="sr-container">
                                <div class="text-center">
                                    <h3>KnocScore Advanced</h3>
                                    <br>
                                    <h4 class="text-center">Join, save, and make money
                                        with KnocScore. </h4>
                                    <br>
                                    <br>
                                    <h4 class="text-center">KnocScore believes in improving student's ability to learn
                                        and keep up with their bills while doing so.
                                        Let's face it. Being a college student isn't easy. Having extra income
                                        is always a good thing.</h4>
                                    <br>
                                    <button id="basic-btn" class="btn-orange" ng-click="reqSubscribe()">$7 a month </button>
                                    <br>
                                        <p>To celebrate our launch, we are lowering 'KnocScore Advanced'
                                            to $8/mo as long as you remain enrolled.
                                            As a subscriber you can earn money either as a creator,
                                            or distributor. If you're buying decks, your also save.</p>
                                        <p></p>
                                        <p>There are many more features that will be added in the near future including
                                        more ways to earn pay. </p>
                                        <br>
                                        <h4> Sign-up to earn cash and get the discount, then
                                            enroll to receive your cash earnings through Stripe,
                                            our secure payment system partner.</h4>
                                    <br>
                                </div>
                                    <div class="text-center">
                                    <br>
                                    <a class="text-center" href="/earning-money-as-a-college-student">Learn how to earn on KnocScore</a>
                                    <br>
                                    <a class="text-center" href="/pay-faq">Stripe, our payment system FAQ's</a>
                                    <br><br>
                                    <p>DISCLAIMER: This lifetime discount applies to college students and educators with less than 6GB of data.
                                        May not apply to businesses and institutions.</p>
                                </div>
                            </div>
                    </div>
                </div>

                <#-- modal_6 -->
                <div id="followthru_modal" class="modal" ng-controller="QRSignUpController">
                    <div class="modal_content">
                        <div class="center-text">
                            <p>WE WILL SHOW THE FOLLOW THRU PAGE HERE</p>
                            <p class="center-text">Thank you. Spread the word by sharing the QR code for ${data.avatar_name}</p>
                        </div>
                    </div>
                </div>

                <#-- modal_7 -->
                <div id="error_modal" class="modal" ng-controller="QRSignUpController">
                    <div class="modal_content">
                        <h3>There was an error processing your information. :/</h3>
                    </div>
                </div>

                <#-- modal_8 -->
        <div id="already_subscribed" class="modal" ng-controller="QRSignUpController">
            <div class="modal_content">
                <div class="sr-header">
                    <div class="sr-header__logo"></div>
                </div>
                <br/>
                <h3 class="text-center">Congratulations. <br/> You have already completed this step. </h3>
                <br/>
                <p class="text-center">If you would like to learn more about how to make money on
                KnocScore, follow the links below. </p>
                <br/>
                <a class="text-center" href="https://www.knocscore.com/earning-money-as-a-college-student">Learn about earning pay on KnocScore</a>
                <br/>
                <a class="text-center" href="https://www.knocscore.com/pay-faq">Stripe, our payment system FAQ's</a>
            </div>
        </div>
    </#list>
        <#-- Main section -->
        <#include "verbosedescript.ftl">
        <section>
            <div class="container" >
                <div class="row">
                    <div class="col-md-3 text-center"></div>
                    <div class="col-md-6 text-center">
                        <button id="page-next-btn" class="btn-orange"> Get KnocScore </button>
                        <div class="text-center">
                            <p class="small">KnocScore is a powerful native software application that is compatible with Apple and Windows laptops and most non-apple tablets.
                            Powered by Java.</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="../js/popper.min.js"></script>
        <#include "../common/closeout_scripts.ftl">
    </body>
</html>