<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head <#list deckData1 as deck>>
    <meta charset="UTF-8">
    <link rel="canonical" href="${deck.endpoint}">
    <title>Learning and memorization materials: ${deck.deck_name}| KnocScore</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Learning and memorization materials on ${deck.subj}: ${deck.section} for ${deck.institute} ${deck.course_code}
          taught by ${deck.prof}"/>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="icon" src="/app/images/LOGO/KnocScore/white/logo_horizontal_464x88.png">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url"  content="https://knocscore.com/">

    <#include "../common/styles_block_infopg.ftl">

    <link rel="stylesheet" type="text/css" href="/app/style/normalize.css" />
    <link rel="stylesheet" type="text/css" href="/app/style/global.css" />

    <!-- angular -->
    <script src="/app/javascript/angular/angularjs_1.7.5_angular.js"></script>
    <script src="/app/javascript/angular/angularjs_1.6.9_angular-animate.js"></script>
    <script src="/app/javascript/angular/angularjs_1.6.9_angular-messages.js"></script>
    <script src="/app/javascript/lodash/lodash_4.17.4.js"></script>
<#--    <script src="https://js.stripe.com/v3/"></script>-->
    <script src="/app/javascript/qrcode1.js"></script>

</head </#list>>
<body>
    <div>
        <#-- Angular assists with adaptable color navbar when scrolling -->
        <header class="navbar bg-dark">
            <nav  class="container-xxl flex-wrap flex-md-nowrap">
                <div class="container-fluid">
                    <a class="navbar-brand" href="https://knocscore.com"><img src="/app/images/LOGO/topscholars/white/logo_horizontal_464x88.png" class="header-logo"></a>

                </div>
            </nav>
        </header>

        <br class="clearfloat">

        <!-- Create Account,  Sign-Up Modal -->
        <div id="signUp_modal" class="modal bg-white" ng-controller="UserController" >
            <div class="modal_content">
                <span class="close-dk" id="close_signUp"> &times; </span>
                <div role="alert" id="alertMessage">
<#--                    {{alertMessage}}-->
                </div>
                <form name="newUser" novalidate>
                    <p class="text-center">Hey, I didn't find that email. You can click the 'x' to go back,
                    or create a new user. </p>

                    <div class="form-group">
                        <label>Email:</label>
                        <h3 class="text-center" id="email"></h3>
                        <br>
                        <small id="emailHelp" class="form-text text-muted">A link to download the app
                            will be sent to the above address.</small>
                    </div>
                    <div class="form-group">
                        <label for="password">New User Password</label>
                        <input class="form-control" type="password" name="passwordField" ng-model="password"
                               required maxlength="40" minlength="8"
                               ng-pattern="/^(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*#.?&])[A-Za-z\d$@$!%*#.?&]{8,20}$/"
                               ng-model-options="{ allowInvalid: false,
                                                    debounce: 0 }"/>
                        <small id="pwHelp" class="form-text text-muted">Passwords require:
                            </br>1) Between eight and 30 characters long </br>2) at least one upper case letter </br>3) at least one lower case letter
                            </br>4) at least one number </br>5) not more than 2 repeating characters </br>6) and at least one of the following special characters @ $ ! % # ? & .
                        </small>
                    </div>
                    <button type="button" class="mdl" ng-click="save(newUser.$valid)">
                        SUBMIT
                    </button>
                </form>
            </div>
        </div>

        <#-- Request email modal -->
        <div id="signIn_modal" class="modal bg-white" ng-controller="UserController">
            <div>
                <span class="close-dk" id="close_signIn">&times;</span>
            </div>
            <div class="modal_content">

                <form name="emailqr" novalidate>
                    <input type="hidden" name="return_url" value="/">

                    <div class="form-group">
                        <h4 class="text-center">Please provide the email you use with
                            us.</h4>
                        <!--<label for="inputemail"> Email </label> -->
                        <div class="center-center">
                            <input class="form-control" type="email" name="email" id="inputemail" aria-describedby="emailHelp"
                                   placeholder="email" ng-model="email" required autocomplete="on">
                            <small id="emailHelp" class="form-text text-muted">New here? No worries! Just enter your
                            email. We'll take care of the rest.</small>
                        </div>
                    </div>
                    <div class="bottom-center">
                        <button type="submit" class="mdl" ng-click="sendone(emailqr.$valid)"> NEXT </button>
                    </div>
                    <div class="invisible alert" role="alert" id="alertMessage">
<#--                        {{alertMessage}}-->
                    </div>
                </form>
            </div>
        </div>

        <!-- Payment Form -->
        <div id="pay_modal" class="modal bg-white"  ng-controller="UserController">
            <section class="pad-12">
                <div>
                    <span class="close-dk" id="close_pay_modal">&times;</span>
                </div>
                <div class="sr-root">
                    <div class="sr-header__logo"></div>
                </div>
                <div id="repeat_purchased">
                    <h3 align="center">Hold on!</h3>
                    <h4 align="center">You have already purchased this deck.</h4>
                    <p  align="center">To go back click 'X'</p>
                    <div id="continue-btn">
                        <h4 align="center">If you continue it will overwrite your current deck,
                            and you will lose any edits you have made.</h4>
                        <button class="mdl">
                            <span id="button-text">continue?</span>
                        </button>
                    </div>
                </div>
            </section>
            <div id="show_form" >
                <div id="checkout">
                    <form method="post" id="payment-form">
                        <section class="pad-12">
                            <h3 align="center" id="page-title">Payment Information</h3>
                            <h4 align="center"> Enter credit card details.</h4>
                            <br>
                            <fieldset class="with-state">
                                <label>
                                    <span>name:</span>
                                    <input name="name" id="name" class="field" placeholder="Name" required>
                                </label>
                                <div id="card-element" class="sr-input sr-card-element field">
                                    <!-- A Stripe Element is inserted here. -->
                                </div>
                            </fieldset>
                            <!-- display form errors -->
                            <div id="card-errors" role="alert"></div>
                            <button id="pay-btn" class="mdl">
                                <div class="spinner hidden" id="spinner"></div>
                                <span id="button-text">BUY</span><span id="order-amount"></span>
                            </button>
                        </section>
                    </form>
                </div>
                <div class="sr-result hidden">
                    <section class="pad-12">
                        <p class="text-center">Payment completed<br /></p>
                        <pre>
                            <code></code>
                        </pre>
                    </section>
                </div>
            </div>
        </div>
        <!-- Main section -->
        <section>
            <div class="container">
                <div class="row">
                    <div class="col-md-12" ng-controller="UserController">
                        <div >
                            <!-- LOGO -->
                            <#list deckData1 as deck>
                                <div class="hidden" id="deck_id">${deck.deck_id}</div>
                                <div class="hidden" id="tk">${deck.token}</div>
                                <h1>${deck.subj} Study Materials</h1>
                                <br>
                                <table>
                                    <tr>
                                        <td class="padded" id="deck_name"><h2>${deck.deck_name}</h2></td>
                                    </tr>
                                </table>
                                <table>
                                    <tr>
                                        <td class="bold padded">Rating: </td>
                                        <#if deck.deck_numraters??>
                                            <td>${deck.stars} stars by ${deck.deck_numraters} users</td>
                                        <#else>
                                            <td>Unrated. Be the first to rate this deck</td>
                                        </#if>
                                    </tr>
                                </table>
                                <table>
                                    <tr>
                                        <td class="bold padded">Area of Studies:</td>
                                        <td> ${deck.subj}</td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded" >Section:</td>
                                        <td> ${deck.section}</td>
                                    </tr>
                                </table>
                                <table>
                                    <br>
                                    <#if deck.deck_photo?? >
                                    <tr>
                                        <td><img src="https://flashmonkey-deck-photo.s3.us-west-2.amazonaws.com/${deck.deck_photo}" class="rounded img-fluid" alt="Deck Image" ></td>
                                    </tr>
                                    <#else>
                                        <td></td>
                                    </#if>
                                </table>
                                <table>
                                    <br>
                                    <label class="card">Deck Description </label>
                                    <br>
                                    <tr>
                                        <td class="padded small">${deck.descript}</td>
                                    </tr>
                                </table>
                                <br>
                                <table>
                                    <tr>
                                        <td class="bold padded">Created:</td>
                                        <td> ${deck.create_date}</td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded"> Last Used: </td>
                                        <td id="dateT"> </td>
                                        <script>
                                            function to2Digits(num) {
                                                return num.toString().padStart(2, '0');
                                            }
                                            let dateObj = new Date(${deck.last_date});
                                            let m = dateObj.getMonth();
                                            let y = dateObj.getFullYear();
                                            let d = dateObj.getDate();
                                            let dtg = y + '-' + to2Digits(m + 1) + '-' + to2Digits(d);
                                            document.getElementById("dateT").innerHTML = dtg;
                                        </script>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Size: </td>
                                        <td> ${deck.num_cards} cards</td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Images: </td>
                                        <td> ${deck.num_imgs} </td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Video: </td>
                                        <td> ${deck.video} </td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Audio: </td>
                                        <td> ${deck.audio} </td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Total Sessions:  </td>
                                        <td>${deck.session_count}</td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Number of users:  </td>
                                        <#if deck.numusers?? >
                                            <td>${deck.num_users}</td>
                                        <#else>
                                            <td>1</td>
                                        </#if>
                                    </tr>
                                </table>
                                <label class="card">Course Info</label>
                                <br>
                                <table>
                                    <tr>
                                        <td><h3>${deck.institute}</h3> </td>
                                    </tr>
                                </table>
                                <table>
                                    <tr>
                                        <td class="bold padded">Professor: </td>
                                        <td> ${deck.prof}</td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Course Code: </td>
                                        <td> ${deck.course_code}</td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Building: </td>
                                        <td> ${deck.bldg}</td>
                                    </tr>
                                </table>
                                <table>
                                    <label class="card">From</label>
                                    <tr>
                                        <td rowspan="4">  <img src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${deck.photo_link}" class="rounded img-fluid" alt="Cinque Terre" width="124" height="124"></td>
                                        <!-- distributor -->
                                        <#if deck.dist_public??>
                                            <td class="bold padded">Editor Name:</td>
                                            <td>${deck.dist_name}</td>
                                            <br>
                                        <#else>
                                            <td class="bold padded">Avatar Name:</td>
                                            <td>${deck.dist_avatar}</td>
                                        </#if>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Joined Date:</td>
                                        <td>${deck.join_date}</td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">KnoC&trade; Score:</td>
                                        <td><a href="${deck.dist_plink}">${deck.dist_name}</a></td>
                                    </tr>
                                    <tr>
                                        <td><br><br></td>
                                    </tr>
                                </table>
                                <table>
                                    <tr>
                                        <br>
                                        <label class=="card">About the deck editor</label>
                                        <td class="padded small">${deck.dist_descript}</td>
                                    </tr>
                                </table>
                                <#if deck.creator_public??>
                                    <tr>
                                        <td rowspan="4">  <img src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${deck.creator_photo}" class="rounded img-fluid" alt="Cinque Terre" width="124" height="124"></td>
                                        <!-- distributor -->
                                        <label class=="card">About the deck creator.</label>
                                        <td class="padded small"><a href="${deck.creator_plink}">${deck.creator_name}</a></td>
                                        <br>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Joined Date:</td>
                                        <td>${deck.join_date}</td>
                                    </tr>
                                </#if>
                                <hr>
                                <table>
                                    <tr>
                                        <td class="bold padded">Price: </td>
                                        <td>$ ${deck.price}</td>
                                    </tr>
                                    <tr>
                                        <td class="bold padded">Non-Subscriber: </td>
                                        <td>$ ${deck.fee}</td>
                                    </tr>
                                </table>
                                <table>
                                    <tr>
                                        <td class="small"><i>The non-subscriber fee is waived.</i></td>
                                    </tr>
                                </table>
                                <hr>
                            </#list>
                        </div>
                    </div>
                    <div class="col-md-12 text-center qr-row">
                        <button id="link_signIn">PURCHASE</button>
                    </div>
                </div>  <!-- end row -->
                <div class="row bt-16">
                    <div class="col-md-12 text-center qr-row" >
                        <h3 class="text-center">Join the revolution</h3>
                        <p class="small text-center">TopScholars is a powerful native application that diverges from other learning technologies
                            in more ways than we can list. Of particular interest is its application of the same study methods
                            found to have the highest student success rates in research conducted by a
                            top 5 university. Go Gators!</p>
                        <p class="small text-center">Students can earn revenue by selling their "study materials". Creators keep 100% of all earnings minus minor credit card fees.</p>
                        <p class="small text-center">By studying on TopScholars, students build their Knowledge Credibility Score&trade;. An optionally sharable metric to get an edge up in front of employers.</p>
                        <p class="small text-center">TopScholars is free to download and free to use for creating and studying
                        learning materials. Exceptions apply for business, gov't and larger education entities.
                            For more information visit our home page or get in touch.</p>
                    </div>
                </div> <!-- end row -->
            </div>
        </section>
    </div>
    <#include "../new_footer.ftl">
    <#include "../common/closeout_scripts.ftl">
</body>
</html>