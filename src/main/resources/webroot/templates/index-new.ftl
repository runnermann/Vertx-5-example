<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <title>FlashMonkey | The only learning software that pays and helps you get jobs.</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="canonical" href="https://knocscore.com">
    <link rel="apple-touch-icon" sizes="180x180" href="/app/images/favicons/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/app/images/favicons/favicon-16x16.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/app/images/favicons/favicon-32x32.png">
    <link rel="mask-icon" href="/app/images/favicons/favicon.svg" color="#29ABE2">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="FlashMonkey | The only learning app that pays and helps you get jobs">
    <meta name="description" content="A new platform... Through a single technology, combines learning, monetary value,
    and knowledge credibility. Get higher scores with an actual...">

    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://knocscore.com">
    <meta property="og:title" content="FlashMonkey | The only learning app that pays and helps you get jobs">
    <meta property="og:description" content="A new platform... Through a single technology, combines learning, monetary value,
    and knowledge credibility. Get higher scores with an actual...">
    <meta property="og:image" content="/app/images/index/power_to_the_students.png">
    <#-- twitter -->
    <meta property="twitter:card" content="summary_large_image">
    <meta property="twitter:url" content="https://knocscore.com">
    <meta property="twitter:title" content="FlashMonkey | The only learning app that pays and helps you get jobs">
    <meta property="twitter:description" content="A new platform... Through a single technology, combines learning, monetary value,
    and knowledge credibility. Get higher scores with an actual...">
    <meta property="twitter:image" content="/app/images/index/power_to_the_students.png">

    <link href="/app/style/bootstrap/bootstrap.css" rel="stylesheet">
    <link href="/app/style/idx-style.css" rel="stylesheet" type="text/css">
    <link href="/app/style/svg-overlay.css" rel="stylesheet" type="text/css">
    <link href="/app/style/tog-dropdown.css" rel="stylesheet" type="text/css">
    <#-- custom styles -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css"/>
    <link href="/app/style/swiper-fm.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="/app/style/fontawesome/css/all.css">
    <link rel="stylesheet" href="/app/style/videocontrols.css">
    <#-- javascript -->

    <script src="/app/javascript/index.js"></script>
    <script src="/app/javascript/navbar/nav-index.js"></script>
    <script>
        window.addEventListener("scroll", e => {
            let navbar = document.getElementById("navbar");
            let button = document.getElementById("link_download");
            if ((window).scrollY > 100) {
                navbar.classList.add("navbar-dark");
            } else {
                navbar.classList.remove("navbar-dark");
                button.classList.remove("nav-btn-orange");
            }
            if ((window).scrollY > 1200) {
                button.classList.add("nav-btn-orange");
            }
            if ((window).scrollY > 3000) {
                navbar.style.display = 'none';
            } else {
                navbar.style.display = 'block';
            }
        });
    </script>
    <style>
        /* input field colors for validation*/
        input.ng-valid:focus {
            /*background-color: #cb9fed; *//* FM Purple */
            border-left: 6px solid;
            border-left-color: white;
        }

        input.ng-invalid:focus {
            border-left: 6px solid;
            border-left-color: salmon;
        }

        input.ng-pristine {
            background-color: white;
        }
    </style>
</head>
<body ng-controller="IndexController">

<!-- Modal req email download link -->
<div id="signUp_modal" class="modal">
    <div>
        <span class="close" id="close_signUp"> &times; </span>
    </div>
    <div class="modal_content">
        <div role="alert" id="alertMessage">
<#--            {{alertMessage}}-->
        </div>
        <form name="newUser" novalidate>
            <br>
            <div class="form-group">
                <input class="form-control" type="text" id="userName" name="nameField" aria-describedby="nameHelp"
                       placeholder="First name"
                       ng-model="userName" required maxlength="20" minlength="3" autocomplete="on">
            </div>
            <div class="form-group">
                <input class="form-control" type="email" id="email" name="emailField" aria-describedby="emailHelp"
                       placeholder="email" ng-model="email" ng-disabled="userExists()" required autocomplete="on">
<#--                <small id="emailHelp" class="form-text text-muted">An email with a secure link to download FlashMonkey-->
<#--                    for-->
<#--                    Mac and Windows Laptops, or Windows Tablets-->
<#--                    will be sent to this address. Your information is secret. We respect your privacy and do not share-->
<#--                    your information except as necessary.</small>-->
            </div>
            <div class="text-center">
                <button type="button" class="btn-index centered" id="signup-modal-btn" ng-click="save(newUser.$valid)">
                    SEND EMAIL
                </button>
            </div>
            <div class="text-center">
                <br>
                <br>
<#--                <p class="small">To learn more about compatibility see our <a href="/faq">FAQ</a></p>-->
            </div>
        </form>
    </div>
</div>
<!-- Download Modal -->
<div id="download_modal" class="modal">
    <div>
        <span class="close" id="close_download"> &times; </span>
    </div>
    <div class="modal_content">
        <div class="form-group">
            <p class="text-center">
                Your mission, should you accept, is to get smart, and make money. Start by downloading the app below.
                Then, create your study materials as best as you can. Learn from them and share your QR-Code with
                your fellow college students.
            </p>
            <p class="text-center">If you do this, you will succeed.</p>
        </div>
        <div class="margin-btm-16vh">
            <div class="text-center">
                <button type="button" class="btn-index" id="download-btn" ng-click="download()">
                    DOWNLOAD
                </button>
            </div>
        </div>
    </div>
</div>
<#-- display the new user thank you message -->
<div id="thankyou_modal" class="modal">
    <div class="modal_content">
        <div class="text-center">
            <br>
            <p> Thank you for requesting a secure link to FlashMonkey. <br>
                To get started building your KnocScore and earning cash, Download
                the app to your laptop. Be sure to check your junk mail folder.</p>
            <div>
                <#--                    <label id="valid" class="center-text"></label>-->
                <label id="from" class="center-text"></label>
            </div>
        </div>
    </div>
</div>
<!-- Angular assists with adaptable color navbar when scrolling -->
<header>
    <nav class="navbar bg-body-tertiary" id="navbar">
        <div class="container-fluid">
            <a class="navbar-brand" href="https://knocscore.com"><img
                        src="/app/images/LOGO/topscholars/white/logo_horizontal_464x88.png" class="header-logo"></a>
            <div class="navbar-nav float-right">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link nav-btn float-end" id="link_download">Download</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</header>

<div class="jumbotron jumbotron-fluid">
    <section>
        <div class="section-box-hdr">
            <div class="row">
                <div class="col-md-6">
                    <div class="top-margin-tagline">
                        <h1 class="hero-text">
                            Get <span class="text-orange">smart</span>
                            <br>
                            Make <span class="text-orange">money</span>
                        </h1>
                        <h2 class="hero-white sm-right text-justify">The only learning app that pays and helps you
                            get jobs 🎉</h2>
                        <div class="d-none d-sm-block sticker-box">
                            <img src="/app/images/index/power_to_the_students.png" loading="lazy" class="img-fluid"
                                 height="224px" width="224px">
                        </div>
                        <div class="d-block d-sm-none sticker-box">
                            <img src="/app/images/index/power_to_the_students.png" loading="lazy" class="img-fluid"
                                 height="128px" width="128px">
                        </div>
                    </div>
                    <div class="col-md-6">
                        <br>
                    </div>
                </div>
                <div class="row">
                    <!-- show button for download if using laptop -->
                    <div class="col-md-6 d-none d-sm-block">
                        <p>&nbsp;&nbsp;</p>
                    </div>
                    <div class="col-md-6 d-none d-sm-block top-margin-2nd">
                        <h3 class="hero-blue text-center">Why start now?</h3>
                        <p class="hero-white text-center margin-btm-2">Why wait? When novel technology platforms like
                            Spotify and even Google Search are launched, early adopters gain primacy. FlashMonkey's
                            algorithms
                            have similar properties resulting in possible life-long advantages. By data's nature, the
                            greater its usage and the longer its usage, the greater the advantage.</p>
                        <div>
                            <div class="text-center">
                                <button type="button" class="btn-index nav-btn" id="button_download">Get FlashMonkey
                                </button>
                            </div>
                        </div>
                        <div class="d-none d-lg-block">
                            <p class="hero-white text-center margin-btm-2"><small>Our app is scanned frequently to
                                    ensure
                                    it is safe using NIST's high standards. &nbsp;Apple will show a warning with a
                                    message that they
                                    have scanned our app, and it's free of malicious code.
                                    You will see a warning from Windows while we are submitting to their app
                                    store.</small></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<!-- end jumbotron -->
<section class="d-block d-sm-none">
    <div class="color-rgt section-two">
        <div class="section-box-hdr">
            <div class="row">
                <!--show form if using phone-->
                <div class=" col-xs-12 top-margin-2nd">
                    <h2 class="text-blue">Why start now?</h2>
                    <p class=" text-white">Why wait? When novel technology platforms like
                        Spotify and even Google Search are launched, early adopters gain primacy. FlashMonkey's
                        algorithms
                        have similar properties resulting in possible life-long advantages. By data's nature, the
                        greater its usage and the longer its usage, the greater the advantage.</p>
                    <div ng-controller="IndexController">
                        <form name="newUserOne" novalidate>
                            <div class="form-group">
                                <div>
                                    <div class="form-group">
                                        <input class="form-control" type="text" id="userNameOne" name="nameFieldOne"
                                               aria-describedby="nameHelp" placeholder="First name"
                                               ng-model="userName" required maxlength="20" minlength="3"
                                               autocomplete="on">
                                    </div>
                                    <div class="form-group">
                                        <input class="form-control" type="email" id="emailOne" name="emailFieldOne"
                                               aria-describedby="emailHelp"
                                               placeholder="email" ng-model="email" required autocomplete="on">
                                    </div>
                                </div>
                            </div>
                            <div class="text-center">
                                <button type="button" class="btn-index centered" id="send-btn"
                                        ng-click="sendMail(newUserOne.$valid)"> NEXT
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
                <!-- end form for phones-->
            </div>
        </div>
    </div>
</section>
<!-- Arguments -->
<section>
    <!-- bkgnd color -->
    <div class="color-lft">
        <!-- size -->
        <div class="section-box">
            <div class="row">
                <div class="col-md-2"><br></div>
                <div class="col-md-8">
                    <div class="top-margin-section">
                        <div class="hb-text-wrap">
                            <h2 class="content text-blue">A new learning technology platform</h2>
                            <br>
                            <div class="hero-feat-instance">
                                <div class="hf-tick w-embed">
                                    <svg width="21" height="14" viewBox="0 0 21 14" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path d="M2 5.15683L8.56818 11.4531L19 1.45312" stroke="white"
                                              stroke-width="3"></path>
                                    </svg>
                                </div>
                                <div>
                                    <p class="index">Through a single technology - combines learning, monetary value,
                                        and knowledge credibility.</p>
                                </div>
                            </div>
                            <div class="hero-feat-instance">
                                <div class="hf-tick w-embed">
                                    <svg width="21" height="14" viewBox="0 0 21 14" fill="none"
                                         xmlns="http://www.w3.org/2000/svg">
                                        <path d="M2 5.15683L8.56818 11.4531L19 1.45312" stroke="white"
                                              stroke-width="3"></path>
                                    </svg>
                                </div>
                                <div>
                                    <p class="index">Get higher scores with an actual learning system that builds
                                        recall.</p>
                                </div>
                            </div>
                            <div class="hero-feat-table">
                                <div class="hero-feat-instance">
                                    <div class="hf-tick w-embed">
                                        <svg width="21" height="14" viewBox="0 0 21 14" fill="none"
                                             xmlns="http://www.w3.org/2000/svg">
                                            <path d="M2 5.15683L8.56818 11.4531L19 1.45312" stroke="white"
                                                  stroke-width="3"></path>
                                        </svg>
                                    </div>
                                    <div>
                                        <p class="index">Earn cash. Get 100% of the price you set minus credit card
                                            fees.</p>
                                    </div>
                                </div>
                                <div class="hero-feat-instance">
                                    <div class="hf-tick w-embed">
                                        <svg width="21" height="14" viewBox="0 0 21 14" fill="none"
                                             xmlns="http://www.w3.org/2000/svg">
                                            <path d="M2 5.15683L8.56818 11.4531L19 1.45312" stroke="white"
                                                  stroke-width="3"></path>
                                        </svg>
                                    </div>
                                    <div>
                                        <p class="index">A novel earning system. Two ways to earn. As an original
                                            creator; earn passive revenue that grows over time.
                                            Or as a consumer-editor; refine existing study materials and share earnings
                                            when you sell them to your classmates.
                                            Patent pending.</p>
                                    </div>
                                </div>
                                <div class="hero-feat-instance last">
                                    <div class="hf-tick w-embed">
                                        <svg width="21" height="14" viewBox="0 0 21 14" fill="none"
                                             xmlns="http://www.w3.org/2000/svg">
                                            <path d="M2 5.15683L8.56818 11.4531L19 1.45312" stroke="white"
                                                  stroke-width="3"></path>
                                        </svg>
                                    </div>
                                    <div>
                                        <p class="index">The KnoC&trade; Score is new but it's hot. Employers are
                                            already asking us for it.
                                            Start building it to get higher paying jobs.</p>
                                    </div>
                                </div>
                            </div>
                            <#--                            <div class="hb-logo-wrap">-->
                            <#--                                <div class="hbl-text">TRUSTED BY STUDENTS AT</div>-->
                            <#--                                <div class="hbl-grid">-->
                            <#--                                    <!--                                    <img src="https://florida.svg" loading="lazy" alt="University of Florida">&ndash;&gt;-->
                            <#--                                    <!--                                    <img src="https://utexas.svg" loading="lazy" id="w-node-_3384dd66-8814-f577-54e3-a74de90a561b-91d8b9ae" alt="Texas University">&ndash;&gt;-->
                            <#--                                    <!--                                    <img src="https://asu.svg" loading="lazy" id="w-node-_3384dd66-8814-f577-54e3-a74de90a561c-91d8b9ae" alt="Arizona State University">&ndash;&gt;-->
                            <#--                                    <!--                                    <img src="https://ucf.svg" loading="lazy" id="w-node-_3384dd66-8814-f577-54e3-a74de90a561d-91d8b9ae" alt="University of Central Florida">&ndash;&gt;-->
                            <#--                                </div>-->
                            <#--                            </div>-->
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <br>
                </div>
            </div> <!-- end row -->
        </div>
    </div>
</section>
<!-- Isaac Fast video -->
<section>
    <!-- bkgnd color -->
    <div class="color-rgt">
        <!-- size -->
        <div class="video2-section-box">
            <div class="row">
                <div class="col-md-2"><br></div>
                <div class="col-md-8">
                    <div class="d-flex align-items-center justify-content-center">
                        <video width="351" controls
                               poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/ifYouAreWatchingThis.jpg">
                            <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/IfYouAreWatchingThis.mp4"
                                    type="video/mp4" loading="lazy">
                            Your browser does not support the video tag.
                        </video>
                    </div>
                </div>
                <div class="col-md-2"><br>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- SLIDER Referals-->
<section>
    <!-- bkgnd color -->
    <div class="color-lft">
        <!-- size -->
        <div class="horizmedia-section-box">
            <div class="row">
                <div class="col-md-2">
                </div>
                <div class="col-md-8 top-margin-people">
                    <h2 class="content text-blue">Join hundreds of FlashMonkey users</h2>
                    <div>
                        <!--Slider main container-->
                        <div class="swiper mySwiper">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/ut_attention.jpg">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/ut_attention.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                                <div class="swiper-slide swiper-text-block">
                                    <div class="swiper-quote-top w-embed">
                                        <svg width="37" height="27" viewBox="0 0 37 27" fill="none"
                                             xmlns="http://www.w3.org/2000/svg">
                                            <path d="M16.4732 0.0175781C6.49137 0.0175781 0.927734 4.84485 0.927734 17.4449V26.4449H15.4914V13.5176H9.10955V13.1903C9.10955 8.85395 11.4005 6.89031 16.4732 6.89031V0.0175781ZM36.4368 0.0175781C26.455 0.0175781 20.8914 4.84485 20.8914 17.4449V26.4449H35.455V13.5176H29.0732V13.1903C29.0732 8.85395 31.3641 6.89031 36.4368 6.89031V0.0175781Z"
                                                  fill="white" fill-opacity="0.3"></path>
                                        </svg>
                                    </div>
                                    <div class="swiper-text">
                                        It's a no-brainer! Why study with anything else.
                                    </div>
                                    <div class="swiper-quote-bottom-1 w-embed">
                                        <svg width="37" height="27" viewBox="0 0 37 27" fill="none"
                                             xmlns="http://www.w3.org/2000/svg">
                                            <path d="M0.927734 26.5913C10.9096 26.5913 16.4732 21.7641 16.4732 9.16406V0.164062H1.90955V13.0913H8.29137V13.4186C8.29137 17.755 6.00046 19.7186 0.927734 19.7186V26.5913ZM20.8914 26.5913C30.8732 26.5913 36.4368 21.7641 36.4368 9.16406V0.164062H21.8732V13.0913H28.255V13.4186C28.255 17.755 25.9641 19.7186 20.8914 19.7186V26.5913Z"
                                                  fill="white" fill-opacity="0.3"></path>
                                        </svg>
                                    </div>

                                    <div class="swiper-name">Thomas, 20 <br></div>
                                    <div class="swiper-pos">Austin Community College, TX <br></div>
                                </div>
                                <div class="swiper-slide">
                                    <img src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/century_tower.jpg"
                                         loading="lazy" alt="" class="carousel-img">
                                </div>
                                <div class="swiper-slide">
                                    <img src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/4_guys.jpg"
                                         loading="lazy" alt="" class="carousel-img">
                                </div>
                                <div class="swiper-slide swiper-text-block">
                                    <div class="swiper-quote-top w-embed">
                                        <svg width="37" height="27" viewBox="0 0 37 27" fill="none"
                                             xmlns="http://www.w3.org/2000/svg">
                                            <path d="M16.4732 0.0175781C6.49137 0.0175781 0.927734 4.84485 0.927734 17.4449V26.4449H15.4914V13.5176H9.10955V13.1903C9.10955 8.85395 11.4005 6.89031 16.4732 6.89031V0.0175781ZM36.4368 0.0175781C26.455 0.0175781 20.8914 4.84485 20.8914 17.4449V26.4449H35.455V13.5176H29.0732V13.1903C29.0732 8.85395 31.3641 6.89031 36.4368 6.89031V0.0175781Z"
                                                  fill="white" fill-opacity="0.3"></path>
                                        </svg>
                                    </div>
                                    <div class="swiper-text">
                                        FlashMonkey is a great resource for creating digital notecards. Organizing
                                        class notes is intuitive and simple.
                                        <br>
                                        <br>
                                        *Award for First to Sell Learning Materials on FlashMonkey.
                                    </div>
                                    <div class="swiper-quote-bottom-2 w-embed">
                                        <svg width="37" height="27" viewBox="0 0 37 27" fill="none"
                                             xmlns="http://www.w3.org/2000/svg">
                                            <path d="M0.927734 26.5913C10.9096 26.5913 16.4732 21.7641 16.4732 9.16406V0.164062H1.90955V13.0913H8.29137V13.4186C8.29137 17.755 6.00046 19.7186 0.927734 19.7186V26.5913ZM20.8914 26.5913C30.8732 26.5913 36.4368 21.7641 36.4368 9.16406V0.164062H21.8732V13.0913H28.255V13.4186C28.255 17.755 25.9641 19.7186 20.8914 19.7186V26.5913Z"
                                                  fill="white" fill-opacity="0.3"></path>
                                        </svg>
                                    </div>
                                    <div class="swiper-name">Lark, 23 <br></div>
                                    <div class="swiper-pos">Graduate, UI Urbana-Champaign <br></div>
                                </div>
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/ut_attention.jpg">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/ut_attention.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                            </div>
                            <div class="swiper-button-next ref-next"></div>
                            <div class="swiper-button-prev ref-prev"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- DEMO SLIDER -->
<section>
    <!-- bkgnd color -->
    <div class="color-rgt">
        <!-- size -->
        <div class="video-section-box">
            <div class="row">
                <div class="col-md-2 top-margin-section"></div>
                <div class="col-md-8 top-margin-section">
                    <h2 class="content text-blue">FlashMonkey Demo's</h2>
                    <#-- LAPTOP Larger videos --->
                    <div class="d-none d-sm-block">
                        <!--Slider main container-->
                        <div class="swiper swiperTwo">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/knoc_score.png">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/knoc_score_demo.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/earn.png">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/earn_demo.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/create.png">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/create_demo.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/test.png">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/test_demo.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                            </div>
                            <div class="carousel-navigation">
                                <div class="carousel-nav-inner">
                                    <a href="#"
                                       class="carousel-btn swiper-button-next vdo-next w-inline-block swiper-button-disabled"
                                       tabindex="-1" role="button"></a>
                                    <a href="#" class="carousel-btn swiper-button-prev vdo-prev w-inline-block"
                                       tabindex="0" role="button" aria-label="Next slide"
                                       aria-controls="swiper-wrapper-5352c5c496fceca4" aria-disabled="false"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- PHONE -->
                    <div class="d-block d-sm-none">
                        <!--Slider main container-->
                        <div class="swiper swiperTwo">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/knoc_score.png">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/knoc_score_demo_mobile.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/earn.png">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/earn_demo_mobile.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/create.png">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/create_demo_mobile.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                                <div class="swiper-slide">
                                    <video width="96%" controls
                                           poster="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/test.png">
                                        <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/test_demo_mobile.mp4"
                                                type="video/mp4" loading="lazy">
                                        Your browser does not support the video tag.
                                    </video>
                                </div>
                            </div>
                            <div class="carousel-navigation">
                                <div class="carousel-nav-inner">
                                    <a href="#"
                                       class="carousel-btn swiper-button-next vdo-next w-inline-block swiper-button-disabled"
                                       tabindex="-1" role="button"></a>
                                    <a href="#" class="carousel-btn swiper-button-prev vdo-prev w-inline-block"
                                       tabindex="0" role="button"></a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <br>
                    <br>
                    <br>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- Frequently Asked Questions -->
<section>
    <div class="color-lft">
        <div class="section-box">

            <div class="row">
                <div class="col-md-12 top-margin-FAQ">
                    <div class="faq-section wf-section">
                        <div class="main-container">
                            <div class="faq-block">
                                <h2 class="content text-blue">Frequently asked questions</h2>
                                <div class="faq-block-container">
                                    <div data-hover="false" data-delay="0"
                                         data-w-id="22d3e2fa-5a46-6d14-68f7-6f17742216b6" class="faq-dd w-dropdown">
                                        <div class="faq-dd-toggle w-dropdown-toggle">
                                            <h2 class="index">What is a KnoC&trade; Score?</h2>
                                            <div class="faq-dd-toggle-icon">
                                                <div class="ff-icon-bar _1"></div>
                                                <div class="ff-icon-bar _2"></div>
                                            </div>
                                        </div>
                                        <nav class="faq-dd-list w-dropdown-list">
                                            <div class="faq-dd-rt w-richtext">
                                                <p>
                                                    A KnoC, or Knowledge Credibility Score provides the viewer an
                                                    unbiased means to verify your
                                                    work ethic and your knowledge in relevant domains. An Employer's
                                                    most difficult duty in hiring new
                                                    employees is understanding a candidates potential to contribute to
                                                    work. The KnocScore gives
                                                    insight into a students academics.
                                                    Learn more about the <a href="/knowledge-credibility-score">
                                                        knowledge credibility score&trade;.</a></p>
                                                </p>
                                            </div>
                                        </nav>
                                    </div>
                                    <div data-hover="false" data-delay="0"
                                         data-w-id="22d3e2fa-5a46-6d14-68f7-6f17742216b6" class="faq-dd w-dropdown">
                                        <div class="faq-dd-toggle w-dropdown-toggle">
                                            <h2 class="index">How do I create a KnocScore?</h2>
                                            <div class="faq-dd-toggle-icon">
                                                <div class="ff-icon-bar _1"></div>
                                                <div class="ff-icon-bar _2"></div>
                                            </div>
                                        </div>
                                        <nav class="faq-dd-list w-dropdown-list">
                                            <div class="faq-dd-rt w-richtext">
                                                <p>There is nothing special that needs to be done. It is easy to create,
                                                    edit, study, and earn.
                                                    The platform keeps track and automatically update your score.
                                                    Learn more about <a href="/earning-your-knowledge-credibility">
                                                        Building your KnoC&trade; Score.</a>
                                                </p>
                                            </div>
                                        </nav>
                                    </div>
                                    <div data-hover="false" data-delay="0"
                                         data-w-id="22d3e2fa-5a46-6d14-68f7-6f17742216b6" class="faq-dd w-dropdown">
                                        <div class="faq-dd-toggle w-dropdown-toggle">
                                            <h2 class="index">How can I view or share my KnocScore?</h2>
                                            <div class="faq-dd-toggle-icon">
                                                <div class="ff-icon-bar _1"></div>
                                                <div class="ff-icon-bar _2"></div>
                                            </div>
                                        </div>
                                        <nav class="faq-dd-list w-dropdown-list">
                                            <div class="faq-dd-rt w-richtext">
                                                <p>
                                                    View your KnocScore in the app.
                                                    You can easily share it publicly or privately with job recruiters.
                                                </p>
                                            </div>
                                        </nav>
                                    </div>
                                    <div data-hover="false" data-delay="0"
                                         data-w-id="22d3e2fa-5a46-6d14-68f7-6f17742216b6" class="faq-dd w-dropdown">
                                        <div class="faq-dd-toggle w-dropdown-toggle">
                                            <h2 class="index">Does FlashMonkey cost anything?</h2>
                                            <div class="faq-dd-toggle-icon">
                                                <div class="ff-icon-bar _1"></div>
                                                <div class="ff-icon-bar _2"></div>
                                            </div>
                                        </div>
                                        <nav class="faq-dd-list w-dropdown-list">
                                            <div class="faq-dd-rt w-richtext">
                                                <p>FlashMonkey is free to study and create learning materials. If you
                                                    purchase learning materials, the original creator sets their own
                                                    price.
                                                    If you earn from FlashMonkey there is a minimal license fee. </p>
                                            </div>
                                        </nav>
                                    </div>
                                    <div data-hover="false" data-delay="0"
                                         data-w-id="22d3e2fa-5a46-6d14-68f7-6f17742216b6" class="faq-dd w-dropdown">
                                        <div class="faq-dd-toggle w-dropdown-toggle">
                                            <h2 class="index">Who do I contact for support or additional questions?</h2>
                                            <div class="faq-dd-toggle-icon">
                                                <div class="ff-icon-bar _1"></div>
                                                <div class="ff-icon-bar _2"></div>
                                            </div>
                                        </div>
                                        <nav class="faq-dd-list w-dropdown-list">
                                            <div class="faq-dd-rt w-richtext">
                                                <p>
                                                    We are excited to answer any questions you might have. You can reach
                                                    us by sending an email to <a href="mailto:support@knocscore.com">support@knocscore.com</a>
                                                </p>
                                            </div>
                                        </nav>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</section>

<!-- footer -->
<section>
    <div class="color-lft">
        <div class="container">
            <footer class="py-5">
                <div class="row">
                    <div class="col-6 col-md-2 mb-3">
                        <br>
                        <ul class="nav flex-column">
                            <li class="nav-item mb-2"><a href="/about" class="nav-link p-0 text-muted">About</a></li>
                            <li class="nav-item mb-2"><a href="/team" class="nav-link p-0 text-muted">Team</a></li>
                            <li class="nav-item mb-2"><a href="/jobs_all" class="nav-link p-0 text-muted">Jobs</a></li>
                        </ul>
                    </div>
                    <div class="col-6 col-md-2 mb-3">
                        <br>
                        <ul class="nav flex-column">
                            <li class="nav-item mb-2"><a href="/faq" class="nav-link p-0 text-muted">FAQ's</a></li>
                            <li class="nav-item mb-2"><a href="/pay-faq" class="nav-link p-0 text-muted">Pay-FAQ's</a>
                            </li>
                            <!--                    <li class="nav-item mb-2"><a href="/cancel-policy" class="nav-link p-0 text-muted">Cancel Policy</a></li>-->
                            <li class="nav-item mb-2"><a href="/privacy" class="nav-link p-0 text-muted">Privacy</a>
                            </li>
                        </ul>
                    </div>
                    <div class="col-6 col-md-2 mb-3">
                        <br>
                        <ul class="nav flex-column">
                            <li class="nav-item mb-2"><a href="/earning-money-as-a-college-student"
                                                         class="nav-link p-0 text-muted">Earning Pay</a></li>
                            <li class="nav-item mb-2"><a href="/knowledge-credibility-score"
                                                         class="nav-link p-0 text-muted">Knowledge Credibility Score&trade;</a>
                            </li>
                            <li class="nav-item mb-2"><a href="/earning-your-knowledge-credibility"
                                                         class="nav-link p-0 text-muted">Building your KnoC&trade;
                                    Score</a></li>
                        </ul>
                    </div>

                    <div class="col-md-5 offset-md-1 mb-3">
                        <br>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <br>
                        <br>
                        <p align="right">Built in the USA with recycled electrons. DTG: 2023-03-20 07:44 EDST &nbsp  </p>
                    </div>
                </div>
                <div class="d-flex flex-column flex-sm-row justify-content-between py-4 my-4 border-top">
                    <p>© Patents Pending 2020-2023 FlashMonkey Inc. All rights reserved.</p>
                    <ul class="list-unstyled d-flex">
                        <li class="ms-3"><a class="link-dark" href="https://www.instagram.com/flashmonkeygetsmart/"><i
                                        class="fab fa-instagram"></i></a>
                        <li class="ms-3"><a class="link-dark"
                                            href="https://www.facebook.com/FlashMonkey-399314090825681/"><i
                                        class="fab fa-facebook"></i></a>
                        <li class="ms-3"><a class="link-dark"
                                            href="https://www.linkedin.com/company/flashmonkey-inc/"><i
                                        class="fab fa-linkedin"></i></a>
                        <li class="ms-3"><a class="link-dark"
                                            href="https://www.youtube.com/channel/UCsmpEPsC_AKOpfYILZTzyfw"><i
                                        class="fab fa-youtube"></i></a>
                    </ul>
                </div>
            </footer>
        </div>
    </div>
</section>

<script src='/app/javascript/viewport.js' type='text/javascript'></script>
<script src='/app/javascript/modal.js' type='text/javascript'></script>
<!-- slider.javascript -->
<script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.js"></script>
<script src='/app/javascript/swiper-fm.js'></script>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src='/app/javascript/jquery/jquery-3.6.1.min.js'></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<!--<script src="../javascript/popper.min.javascript"></script>-->
<script src='/app/javascript/bootstrap/bootstrap.js'></script>
<script src='/app/javascript/webflow-script.js'></script>
</body>
</html>