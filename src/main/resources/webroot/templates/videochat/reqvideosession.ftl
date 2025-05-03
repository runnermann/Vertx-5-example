<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
<#--    <link rel="canonical" href="${endpoint}">-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="apple-touch-icon" sizes="180x180" href="/app/images/favicons/apple-touch-icon.png">
    <link rel="icon" type="image/png" sizes="16x16" href="/app/images/favicons/favicon-16x16.png">
    <link rel="icon" type="image/png" sizes="32x32" href="/app/images/favicons/favicon-32x32.png">
    <link rel="mask-icon" href="/app/images/favicons/safari-pinned-tab.svg" color="#5bbad5">

    <title>Launch Meeting - KnocScore</title>
    <meta name="robots" content="noindex,nofollow">
<#--    <meta name="description" content="${description}">-->

    <link href="/app/style/index-style.css" rel="stylesheet">
    <#include "../common/styles_block_infopg.ftl">
    <script>

    </script>
</head>
<body>
<div class="sr-root">
    <#include "../common/header_nodownload.ftl">
    <br class="clearfloat">
    <br>
    <br>
    <section>
        <div class="outer">
            <div class="inner">
<#--                <h3>Click Launch Meeting below to meet with ${host}</h3>-->
                <h6>By joining a metting,you agree to our Terms of Service and Privacy Statement</h6>
<#--                <button class="mdl" onclick="${link}">Launch Meeting</button>-->
                <br>
                <br>


            </div>
        </div>

    </section>

</div>
<!-- END NO FOOTER -->
<#include "../common/minimal_footer.ftl">
<#include "../common/closeout_scripts.ftl">
</body>
</html>