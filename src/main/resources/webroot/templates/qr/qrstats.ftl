<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
    <#if public>
        <link rel="canonical" href="${endpoint}">
        <meta property="og:url" content="${endpoint}">
        <meta property="twitter:url" content="${endpoint}">
    </#if>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <#include "../common/icon_block.ftl">
    <#if name??>
        <title>Knowledge Credibility for ${name} - KnocScore</title>
        <meta name="description" content="${name} is in the top ${percent}% in the ${prime_subj} industry.">
        <#-- Open Graph / Facebook -->
        <meta property="og:type" content="website">
        <meta property="og:title" content="Knowledge Credibility for ${name} - KnocScore">
        <meta property="og:description" content="${name} is in the top ${percent}% in the ${prime_subj} industry.">
        <meta property="og:image" content="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${photo_link}">
        <#-- Twitter -->
        <meta property="twitter:card" content="summary_large_image">
        <meta property="twitter:title" content="Knowledge Credibility for ${name} - KnocScore">
        <meta property="twitter:description" content="${name} is in the top ${percent}% in the ${prime_subj} industry.">
        <meta property="twitter:image" content="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${photo_link}">
    </#if>
    <#if public>
        <#include "../common/google_analytics.ftl">
    <#else >
        <meta name="robots" content="noindex">
    </#if>
    <#include "../common/styles_block_infopg.ftl">
    <link href="/app/style/timeline.css" rel="stylesheet" type="text/css">
    <link href="/app/style/table.css" rel="stylesheet" type="text/css">
    <link href="/app/style/button_drop.css" rel="stylesheet" type="text/css">
    <link href="/app/style/drop_down_div.css" rel="stylesheet" type="text/css">
    <link href="/app/style/check_box.css" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular.min.js"></script>
    <#-- angular animations -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-animate.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-messages.js"></script>
    <script src="https://cdn.jsdelivr.net/lodash/4.17.4/lodash.min.js"></script>
    <script src="/app/javascript/qrStats.js"></script>
    <#-- Chart Related -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <#-- Background image behind profile image -->
    <style>
        p {
            margin-bottom: 28px;
        }
        .img-bkgnd-graph {
            background:  url("https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${hash}-background-img.webp") no-repeat;
            height: 210px;
            background-size: 100%;
            padding: 0;
            margin: -20px 0 0 -50px;
            overflow: hidden;
        }
        @media screen and (max-width: 580px) {
            .img-bkgnd-graph {
                margin: -20px -30px 0 -50px;
                height: 24vh;
            }
        }
    </style>
</head>
<body>
<!-- Google Tag Manager (noscript) -->
<#if public>
    <noscript><iframe src="https://www.googletagmanager.com/ns.html?id=GTM-NHZWJGKR"
                      height="0" width="0" style="display:none;visibility:hidden"></iframe></noscript>
</#if>
<div>
    <!-- Modal Sign up -->
    <#include "../modals/signup_modal.ftl">
    <#-- Thank you MODAL-->
    <#include "../modals/stripe_thankyou_modal.ftl">
    <#-- nav -->
    <#include "../section/non-index-nav.ftl">
    <br class="clearfloat">
</div>
<div>
    <#-- Profile section -->
    <#include "../section/metrics/profile_section.ftl">
    <!-- The metrics section is here. -->
    <#include "../section/metrics/metrics_section.ftl">
    <!-- The explainer section  -->
    <#include "../section/metrics/explanation.ftl">
</div>
<#-- footer -->
<#include "../new_footer.ftl">
<#include "../common/closeout_scripts.ftl">

</body>
</html>