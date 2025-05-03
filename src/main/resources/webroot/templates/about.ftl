<!DOCTYPE html>
<html lang="en-us">
<head>
    <title>About KnocScore&trade;</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="canonical" href="https://knocscore.com/about">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="About KnocScore&trade;">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://knocscore.com/about">
    <meta property="og:title" content="About KnocScore&trade;">
    <meta property="og:description" content="We're building employment superpowers for everyone.">
    <#-- Twitter -->
    <meta property="twitter:card" content="summary_large_image">
    <meta property="twitter:url" content="https://knocscore.com/about">
    <meta property="twitter:title" content="about KnocScore">
    <meta property="twitter:description" content="We're building employment superpowers for everyone.">
    <meta property="twitter:image" content="https://knocscore.com/app/images/twitter/centurion_knocscore_twitter_card.png">
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
                <p class="act-like-h1">We're building employment superpowers for everyone.</p>
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
                        <p class="act-like-h2 mb-3">Build a trusted knowledge credibility system:
                        </p>
                        <p>Where professionals are equally rated based on their actual competence and work ethic,</p>
                        <p class="act-like-h1 mb-2">and</p>
                        <p>industry benefits from highly skilled people.</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <hr class="top-margin-section">
            <div class="col-md-6">
                <div class="top-margin-section">
                    <div class="about-hero_text">
                        <h1 class="eyebrow mb-4">About KnocScore&trade;</h1>
                        <p class="act-like-h1 mb-4">KnoC&trade;</p>
                        <p class="act-like-h4 mb-2">pronounced 'knock'</p>
                        <p>short for Knowledge Credibility</p>
                    </div>
                </div>
            </div>
            <div class="col-md-5">
                <div class="top-margin-section">
                    <div class="code-h6">
                        <br>
                        <p>A KnocScore is an objective measurement of knowledge, athletics, and skills. In professional meetings, an
                            individual can use a KnocScore as a concrete unbiased credential to validate themselves
                            to others.</p>
                        <p>Just as
                            a credit score reflects an individual's financial reliability when applying for a loan,
                            KnocScore reflects a professional's work ethic, and proven abilities within their field. When seeking
                            employment, a higher KnocScore translates to superior performance capabilities,
                            leading to better job prospects and terms. Professionals with a higher KnocScore are
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
                            <img src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${people.photo_link}"
                                 class="rounded-1"
                                 alt="KnocScore team member"
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
