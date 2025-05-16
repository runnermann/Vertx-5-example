<!DOCTYPE html>
<html lang="en-us">
<head>
    <title>About MyVIDI&trade;</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="canonical" href="https://MyVIDI.com/about">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="About MyVIDI&trade;">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://MyVIDI.com/about">
    <meta property="og:title" content="About MyVIDI&trade;">
    <meta property="og:description" content="A short description here.">
    <#-- Twitter -->
    <meta property="twitter:card" content="summary_large_image">
    <meta property="twitter:url" content="https://MyVIDI.com/about">
    <meta property="twitter:title" content="about MyVIDI">
    <meta property="twitter:description" content="A short description here.">
    <meta property="twitter:image" content="https://MyVIDI.com/app/images/twitter/centurion_MyVIDI_twitter_card.png">
    <#include "common/google_analytics.ftl">
    <!-- styles -->
    <#include "common/styles_block_infopg.ftl">
    <#-- custom javascript -->

</head>
<body >
<#include "common/header_block.ftl">
<section>
    <div class="container">
        <div class="row">
            <div class="col-md-6 top-margin-top mb-4">
                <p class="act-like-h1">We're building something awesome for everyone.</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
            </div>
        </div>
        <div class="row">
            <hr class="top-margin-section">
            <div class="col-md-6">
                <div class="top-margin-section">
                    <div class="about-hero_text">
                        <h2 class="eyebrow mb-4">Our Vision</h2>
                        <p class="act-like-h2 mb-3">Build something for everyone:
                        </p>
                        <p>Where people meet to build something great,</p>
                        <p class="act-like-h1 mb-2">and</p>
                        <p>industry benefits from cool stuff.</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <hr class="top-margin-section">
            <div class="col-md-6">
                <div class="top-margin-section">
                    <div class="about-hero_text">
                        <h1 class="eyebrow mb-4">About MyVIDI&trade;</h1>
                        <p class="act-like-h1 mb-4">MyVIDI</p>
                        <p class="act-like-h4 mb-2">pronounced 'MIX'</p>
                        <p>short for MIX</p>
                    </div>
                </div>
            </div>
            <div class="col-md-5">
                <div class="top-margin-section">
                    <div class="code-h6">
                        <br>
                        <p>A MyVIDI is an objective measurement of knowledge, athletics, and skills. In professional meetings, an
                            individual can use a MyVIDI as a concrete unbiased credential to validate themselves
                            to others.</p>
                        <p>Just as
                            a credit score reflects an individual's financial reliability when applying for a loan,
                            MyVIDI reflects a professional's work ethic, and proven abilities within their field. When seeking
                            employment, a higher MyVIDI translates to superior performance capabilities,
                            leading to better job prospects and terms. Professionals with a higher MyVIDI are
                            more competitive and would have greater opportunities for career growth, increased
                            responsibilities, and higher salary offers.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section>
    <div class="container">
        <div class="top-margin-section">
            <div class="row">
                <hr>
                <div class="col-md-6">
                    <div class="top-margin-dblsection">
                        <div class="about-hero_text">
                            <h2 class="eyebrow mb-4">Our Founder</h2>
                            <p class="act-like-h4 mb-6">Lowell (Wolf) Stadelman</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-5">
                    <#list peopleData as people>
                        <div class="center-text mb-5 top-margin-section">
                            <img src="/images/${people.photo_link}"
                                 class="rounded-1"
                                 alt="MyVIDI team member"
                                 width="214"
                                 height="214">
                        </div>
                        <div class="code-h6">
                            <br>
                            ${people.descript}
                        </div>
                    </#list>
                </div>
            </div>
            <div class="mb-5";></div>
        </div>
    </div>
</section>
<#-- footer -->
<#include "new_footer.ftl">
<#include "common/closeout_scripts.ftl">
</body>
</html>
