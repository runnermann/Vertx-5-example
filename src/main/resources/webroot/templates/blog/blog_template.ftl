<!DOCTYPE html>
<html lang="en-us">
<head>
    <#list blogData as meta>
            <meta charset="UTF-8">
            <link rel="canonical" href="https://VertxExample.com/articles/${meta.id}/${meta.page_endpoint}">
            <#include "../common/icon_block.ftl">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>${meta.title}</title>
            <meta name="description" content="${meta.meta_descript}">
            <#-- Open Graph / Facebook -->
            <meta property="og:type" content="website">
            <meta property="og:url" content="https://VertxExample.com/articles/${meta.page_endpoint}">
            <meta property="og:title" content="${meta.title}">
            <meta property="og:description" content="${meta.meta_descript}">
            <meta property="og:image" content="${meta.image_twitter_link}">
            <#-- Twitter -->
            <meta property="twitter:card" content="summary_large_image">
            <meta property="twitter:url" content="https://VertxExample.com/articles/${meta.page_endpoint}">
            <meta property="twitter:title" content="${meta.title}">
            <meta property="twitter:description" content="${meta.meta_descript}">
            <meta property="twitter:image" content="${meta.image_twitter_link}">
<#--            <#include "../common/google_analytics.ftl">-->
            <#include "../common/styles_block_infopg.ftl">
            <link href="/app/style/blog.css" rel="stylesheet" type="text/css">
    </#list>
</head>
<body>
<#include "../common/header_block.ftl">
<br>
<br>
<br>
<!-- Post Content-->
<div>
    <section class="two">
        <div class="container">
            <div class="row">
                <div class="col-lg-2 col-sm-0">&nbsp;</div>
                <div class="col-md-8">
                    <#list blogData as rdata>
                        <br>
                        <br>
                        <br>
                        <h1 class="center-text"><b>${rdata.title}</b></h1>
                        <br>
                        <#if rdata.image_link != ''>
                            <picture>
                                <img class="text-center blog-img" src="${rdata.image_link}" width="100%">
                            </picture>
                        </#if>
                        <br>
                        <br>
                        <span class="meta"><b>VertxExample Articles</b></span>
                        <br>
                        <span><b>Read Time:</b> ${rdata.read_time} minutes</span>
                        <br>
                    </#list>
                </div>
            </div>
        </div>
    </section>

    <section class="two">
        <div class="container">
            <br>
            <br>
            <br>
            <hr class="mt-1 mb-1"/>
            <br>
            <#-- INTRODUCTION -->
            <#if rowData[0].intro = true >
                <div class="row">
                    <div class="col-lg-2 col-sm-0">&nbsp;&nbsp;</div>
                    <div class="col-lg-8" col-sm-12>
                        <h2>Introduction</h2>
                        <#list rowData as intro_row>
                            <#if intro_row.intro>
                                <p>
                                    ${intro_row.para}
                                </p>
                            </#if>
                        </#list>
                    </div>
                </div>
                <br>
                <br>
                <hr class="mt-1 mb-1"/>
                <br>
                <br>
            </#if>
            <#-- BODY -->
            <div class="row">
                <div class="col-lg-2 col-sm-0">&nbsp;&nbsp;</div>
                <div class="col-lg-8" col-sm-12>
                    <#list rowData as section>
                        <#if section.intro = false>
                                <#if section.subtitle = true>
                                    <${section.h2_h3}>${section.h2_subtitle}</${section.h2_h3}>
                                </#if>

                                <#if section.num_list_start = true>
                                    <ol start="${section.num_start_no}">
                                </#if>

                                <#if section.num_list_item = true>
                                    <li>${section.para}</li>
                                <#else>
                                    <p class="mt-5">${section.para}</p>
                                </#if>

                                <#if section.num_list_end = true>
                                    </ol>
                                </#if>
                        </#if>
                    </#list>
                </div> <#-- end right section -->
            </div> <#-- end rows -->
            <br>
            <br>
            <hr class="mt-1 mb-1"/>
            <br>
            <br>
        </div> <#-- end container !! Looks like an error bc of end of ol must be called. Not sure if we can just correct this. ???-->
    </section>
    <section>
        <div class="container" >
            <div class="row fm-container__paper">
                <div class="col-lg-1 col-sm-0">&nbsp;</div>
                <div class="col-lg-10">
                    <br>
                    <br>
                    <#list blogData as chng>
                        <p><b>Published: ${chng.create_date}</b></p>
                        <p><b>Author: ${chng.author}</b></p>
                        <#if chng.change_date??>
                            <h4>Change history</h4>
                                <ol class="list-unstyled">
                                    <li><b>Edited:</b> ${chng.change_date} - ${chng.change_descript}</li>
                                </ol>
                        </#if>
                    </#list>
                </div>
            </div>
            <br>
            <br>
            <hr class="mt-1 mb-1"/>
            <br>
            <br>
        </div>
    </section>
    <section>
        <div class="container">
            <#include "../common/signup_btm.ftl">
            <div class="mgn-btm-100"></div>
    </section>
</div>
<!-- footer -->
<#include "../new_footer.ftl">
<#include "../common/closeout_scripts.ftl">
</body>
</html>