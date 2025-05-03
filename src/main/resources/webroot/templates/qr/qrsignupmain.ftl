<!DOCTYPE html>
<#--This is the simple version of the QR Sign up. For the original see multi path
    - This version only asks for the email, adds user, and sends an email with a
    - secure link to the app.
    - Uses simple_qrsignup.js
    -->
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
        <!-- angular -->
        <#include "../common/javascript_block.ftl">
<#--        <script src="https://js.stripe.com/v3/"></script>-->
        <script src="/app/javascript/simple_qrsignup.js"></script>
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
                <div class="hidden" id="tk">${data.token}</div>
                <#-- modal_1 -->
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
                                <p class="text-center">Get better grades <br>Earn cash <br> and gain Knowledge Credibility&trade; </p>
                                <div>
                                    <input class="form-control" type="email" name="email" id="inputemail" aria-describedby="emailHelp"
                                           placeholder="email" ng-model="email" required>
                                </div>
                            </div>
                            <div>
                                <button type="button" class="btn-orange" ng-click="sendform(emailqr.$valid)"> NEXT </button>
                            </div>
                            <div role="alert" id="alertMessage">
                            <#--{{alertMessage}}-->
                            </div>
                            <div>
                                <p class="text-center">Compatible with Mac and Windows laptops
                                <br> and windows tablets.
                                </p>
                            </div>
                        </form>
                    </div>
                </div>
        <#-- modal_2 -->
        <#-- display the new user thank you message -->
                <div id="thankyou_modal" class="modal" ng-controller="QRSignUpController">
                    <div class="modal_content">
                        <div class="sr-header">
                            <div class="sr-header__logo"></div>
                        </div>
                        <div class="text-center">
                            <br>
                            <p>${data.subscribe_text}</p>
                            <div id="alertMessage">
                                <label id="valid" class="center-text"></label>
                                <label id="from" class="center-text"></label>
                            </div>
                        </div>
                    </div>
                </div>
        <#-- modal_3 -->
                <div id="already_exists" class="modal" ng-controller="QRSignUpController">
                    <div class="modal_content">
                        <div class="sr-header">
                            <div class="sr-header__logo"></div>
                        </div>
                        <div class="text-center">
                            <br>
                            <p>${data.follow_text1}</p>
                        </div>
                    </div>
                </div>
    </#list>
        <#-- Main section -->
        <section>
            <div class="container" >
                <div class="row">
                    <div class="col-md-3 text-center"></div>
                    <div class="col-md-6 text-center">
                        <button id="req-app-btn" class="btn-orange"> Get KnocScore </button>
                        <div class="text-center">
                            <p class="small">KnocScore is a powerful native software application that is compatible with Apple and Windows laptops and most non-apple tablets.
                            Powered by Java.</p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <#include "verbosedescript.ftl">


        <#include "../common/closeout_scripts.ftl">
    </body>
</html>