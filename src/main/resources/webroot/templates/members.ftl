<!DOCTYPE html>
<html lang="en-us">
<head>
    <title>Members: KnocScore</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="canonical" href="https://knocscore.com/members">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="About">
    <#-- Open Graph / Facebook -->
    <#include "common/google_analytics.ftl">
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://knocscore.com/members">
    <meta property="og:title" content="Members">
    <#include "common/google_analytics.ftl">
    <!-- styles -->
    <#include "common/styles_block_infopg.ftl">
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
                <h1>People starting with A - Z Page 1</h1>
                <div class="mt-5 col-md-5">
                    <h2 class="mb-3">Listing</h2>
                    <#list memberData as mm>
                        <#if mm.name??>
                            <p><a href="/knoc-for/${mm.link}-${mm.hash}">${mm.name}</a></p>
                        </#if>
                    </#list>
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