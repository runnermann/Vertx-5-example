<!DOCTYPE html>
<html lang="en-us">
<head>
    <title>Our Backers: MyVICI</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="canonical" href="https://MyVICI.com/our-backers">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="About">
    <#-- Open Graph / Facebook -->
    <#include "common/google_analytics.ftl">
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://MyVICI.com/our-backers">
    <meta property="og:title" content="Our Backers">
    <#include "common/google_analytics.ftl">
    <!-- styles -->
    <#include "common/styles_block_infopg.ftl">
    <link href="/app/style/animate-style.css" rel="stylesheet" type="text/css">
    <link href="/app/style/about-style-special.css" rel="stylesheet" type="text/css">
    <!-- javascript -->
    <#-- custom javascript -->
    <script src="/app/javascript/non-index.js"></script>
</head>
<body>
<#include "common/header_block.ftl">

<br class="clearfloat">
<section>

        <div class="container">
            <div class="row">
                <div class="col-md-6 top-margin-top mb-4">
                    <#--                <h3 class="eyebrow mb-4">Introduction</h3>-->
                    <p class="act-like-h1">Our MyVICI Superheroes</p>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <#--                <img src="/app/images/index/hero_v2.webp"-->
                    <#--                     sizes="(max-width: 479px) 85vw, (max-width: 1919px) 90vw, 1280px"-->
                    <#--                     srcset="/app/images/index/hero_v2.webp 1400"-->
                    <#--                     loading="eager"-->
                    <#--                     alt="Group Webflow team photo"-->
                    <#--                     class="about-hero_img">-->
                </div>
            </div>
            <div class="row">
                <hr class="top-margin-section">
                <div class="col-md-6">
                    <div class="top-margin-section">
                        <div class="about-hero_text">
                            <h2 class="act-like-h2 mb-3">Our Corporate Backers</h2>
                            <p class="act-like-h4 mb-3">The companies that helped us succeed.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-1">
                    <br>
                </div>
                <div class="col-md-5">
                    <div class="top-margin-2nd">
                        <div class="code-h6">
                            <br>
                            <p>FlashMonkey Inc.</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <hr class="top-margin-section">
                <div class="col-md-6">
                    <div class="top-margin-section">
                        <div class="about-hero_text">
                            <h2 class="act-like-h2 mb-4">Our Public Backers</h2>
                            <p class="act-like-h4">The people who've backed us with their donations.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-1">
                    <br>
                </div>
                <div class="col-md-5">
                    <div class="top-margin-2nd">
                        <div class="code-h6">
                            <#list backerData as backer>
                                <#if backer.don_name??>
                                    <p>${backer.don_name}</p>
                                </#if>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <hr class="top-margin-section">
                <div class="col-md-6">
                    <div class="top-margin-section">
                        <div class="about-hero_text">
                            <h2 class="act-like-h2 mb-4">Our Public Supporters</h2>
                            <p class="act-like-h4">The people who've shown they want us to succeed.</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-1">
                    <br>
                </div>
                <div class="col-md-5">
                    <div class="top-margin-2nd">
                        <div class="code-h6">
                            <#list backerData as backer>
                                <#if backer.ind_name??>
                                    <p>${backer.ind_name}</p>
                                </#if>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
            <div class="top-margin-section">
                <br>
            </div>
        </div>

</section>
<#-- footer -->
<#include "new_footer.ftl">
<#include "common/closeout_scripts.ftl">
</body>
</html>
