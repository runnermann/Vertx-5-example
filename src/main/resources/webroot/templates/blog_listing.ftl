<!DOCTYPE html>
<html lang="en-us">
<head>
    <meta charset="UTF-8">
    <link rel="canonical" href="https://MyVIDI.com/blogs">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>MyVIDI&trade;</title>
    <meta name="description" content="A short description">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://MyVIDI.com/blogs">
    <meta property="og:title" content="Title">
    <meta property="og:description" content="A short description">
    <meta property="og:image" content="https://MyVIDI.com/app/images/blog/hiring_top_talent.png">
    <#-- Twitter -->
    <meta property="twitter:card" content="summary_large_image">
    <meta property="twitter:url" content="https://MyVIDI.com/blogs">
    <meta property="twitter:title" content="Title">
    <meta property="twitter:description" content="A short description">
    <meta property="twitter:image" content="https://MyVIDI.com/app/images/blog/hiring_top_talent.png">

<#--    <#include "common/google_analytics.ftl">-->
    <#include "common/styles_block_infopg.ftl">
    <link href="/app/style/blog.css" rel="stylesheet" type="text/css">
    <!-- non-index pages -->
</head>
<body>
    <#include "common/header_block.ftl">
    <!-- Team Page -->
    <br>
    <br>
    <br>
    <div class="container" >
        <div class="row mt-7">
            <div class="col-md-12">

                <h1>Articles about Knowledge Credibility</h1>
                <p class="act-like-h4">Popular, need to know information on the benefits, whats, and hows of Knowledge Credibility.</p>
                <br>
                <br>
                <hr>
            </div>
        </div>
        <div>
            <#list blogData as blog>
                <br>
                <br>
                <a href="${blog.page_endpoint}">
                    <div class="card mb-3">
                        <div class="row g-0">
                            <div class="col-md-4">
                                <img src="${blog.image_card_link}" class="img-fluid rounded-start" alt="${blog.image_card_alt}">
                            </div>
                            <div class="col-md-8">
                                <div class="card-body">
                                    <h5 class="card-title">${blog.title}</h5>
                                    <p class="card-text">${blog.card_intro}</p>
                                    <p class="card-text"><small class="text-muted">Created: ${blog.create_date}</small></p>
                                    <p class="card-text"><small class="text-muted">By: ${blog.author}</small></p>
                                    <p class="card-text"><small class="text-muted">Read time: ${blog.read_time} minutes</small></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </a>

            </#list>
        </div>
    </div>

    <!-- footer -->
    <#include "new_footer.ftl">
    <#include "common/closeout_scripts.ftl">
</body>
</html>