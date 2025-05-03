<!DOCTYPE html>
<html lang="en-us">
<head>
    <title>Profile Builder</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="canonical" href="https://knocscore.com/members">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="About">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://knocscore.com/members">
    <meta property="og:title" content="Members">
    <!-- styles -->
    <#include "common/styles_block_infopg.ftl">
    <link href="/app/style/editor-style.css" rel="stylesheet" type="text/css">
    <!-- javascript -->
    <link href="/app/quill/dist/quill.snow.css" rel="stylesheet" type="text/css">
<#--    <link href="/app/style/animate-style.css" rel="stylesheet" type="text/css">-->
    <link href="/app/style/typewriter_form.css" rel="stylesheet" type="text/css">
    <link href="/app/style/about-style-special.css" rel="stylesheet" type="text/css">
    <link href="/app/style/file_uploader.css" rel="stylesheet" type="text/css">
    <link href="/app/style/button_ripple.css" rel="stylesheet" type="text/css">
    <!-- Background image behind profile image -->
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
<#include "section/non-index-nav.ftl">
<#include "modals/main_images_modal.ftl">
<#include "modals/typewriter_modal.ftl">


<br class="clearfloat">
<div class="hidden" id="hash">${hash}</div>
<section>
    <div class="container">
        <div class="row">
            <div class="col-md-12 mt-5 mb-4 bg-dark text-white">
                <h1>Profile Builder</h1>
            </div>
        </div>
    </div>
</section>
<section>
    <div class="mb-5">
        <div class="container">
            <div class="row bg-editor">
                <!-- Profile Photo and background photo uploader -->
                <div class="col-md-4 border-md-rt-1 bg-editor-hv">
                    <div class="align-right">
                        <button type="button"
                                class="btn-svg "
                                id="edit_main_imgs"
                                data-bs-toggle="modal"
                                data-bs-target="#main_images_modal"
                                data-for-type="individual">
                            <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                        </button>
                    </div>
                    <#include "./section/metrics/sub/profile.ftl">
                </div>
                <div class="col-md-8">
                    <#-- Industry data uploader -->
                    <div class="row">
                        <div class="col-md-12 bg-editor-hv">
                            <div class="align-right">
                                <button type="button"
                                        class="btn-svg "
                                        id="edit_industry"
                                        data-bs-toggle="modal"
                                        data-bs-target="#typewriterModal"
                                        data-for-type="individual">
                                    <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                        <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                                </button>
                            </div>
                            <!-- END UPLOADER -->
                            <div class="row mt-5 mb-5">
                                <#include "./section/metrics/sub/industry.ftl">
                            </div>
                        </div>
                    </div>

                    <hr>
                    <!-- employer info -->
                    <div class="row bg-editor-hv">
                        <div class="col-md-8 mt-4">
                            <h2 class="eyebrow">Employer: <b>${employer}</b></h2>
                            <h3 class="eyebrow">Position: <b>${position}</b></h3>
                        </div>
                        <div class="col-md-4">
                            <div class="align-right">
                                <button type="button" class="btn-svg"
                                        id="edit_employer"
                                        data-bs-toggle="modal"
                                        data-bs-target="#typewriterModal"
                                        data-for-type="individual">
                                    <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                        <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- Education data -->
                    <div class="row bg-editor-hv">
                        <div class="col-md-4">
                            <br>
                            <h2 class="eyebrow">Education</h2>
                            <h3 class="eyebrow">University: <b>${institution}</b></h3>
                            <h3 class="eyebrow">Major: <b>${major}</b></h3>
                            <#if minor??>
                                <h3 class="eyebrow">Minor: <b>${minor}</b></h3>
                            </#if>
                        </div>
                        <div class="col-md-4">
                            <br>
                            <br>
                            <h3 class="eyebrow">${other_ed}</h3>
                            <h3 class="eyebrow">Major: <b>${other_major}</b></h3>
                        </div>
                        <div class="col-md-4">
                            <div class="align-right">
                                <button type="button" class="btn-svg"
                                        id="edit_education"
                                        data-bs-toggle="modal"
                                        data-bs-target="#typewriterModal"
                                        data-for-type="individual">
                                    <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                        <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row bg-editor">
                <div class="col-md-4">
                    <hr>
                    <!-- military service -->
<#--                    <#if status??>-->
                    <div class="bg-editor-hv">
                        <h2 class="eyebrow mt-5">Military or Civil Service</h2>
                        <div class="align-right">
                            <button type="button"
                                    class="btn-svg "
                                    id="edit_mil_svc"
                                    data-bs-toggle="modal"
                                    data-bs-target="#typewriterModal"
                                    data-for-type="individual">
                                <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                    <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                            </button>
                        </div>
                        <div class="code-h6 mt-4">
                            <p>${name} ${status} from the ${country} ${branch} as a ${rank}. <br> ${gender} served
                                from ${start} to ${end}.</p>
                        </div>
                    </div>

                    <!-- Service Military photos, Awards Badges Certificates -->

                    <div class="bg-editor-hv">
                        <div class="align-right">
                            <button type="button"
                                    class="btn-svg"
                                    id="edit_milsvc_imgs"
                                    data-bs-toggle="modal"
                                    data-bs-target="#main_images_modal"
                                    data-for-type="individual">
                                <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                    <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                            </button>
                        </div>
                        <!-- img 1 -->
                        <#if mil_captions??>
                            <#include "./section/metrics/sub/mil_svc_photos.ftl">
                        </#if>
                    </div>
<#--                    </#if>-->
                </div>
                <!-- END MIL OR CIVIL SERVICE -->
                <div class="col-md-8" ng-controller="StatsController">

                    <#-- --------------------------------------------------- -->
                    <#--                        BIO                          -->
                    <#-- --------------------------------------------------- -->
                    <br>
                    <br>
                    <div class="row bg-editor-hv">
                        <div class="col-md-12">
                            <h2 class="eyebrow mt-4">Bio</h2>
                            <div class="align-right">
                                <button class="btn-svg edit-btn"
                                        data-target="bio-1"
                                        data-editor="editor-1">
                                    <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                        <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                                </button>
                            </div>
                            <!-- bio / description text and editor-->
                            <div class="code-h6 mt-2">
                                <div class="bio" id="bio-1">${bio}</div>
                            </div>
                            <div id="editor-container-1">
                                <div class="editor" id="editor-1"></div>
                            </div>
                        </div>
                    </div>
                    <br>

                    <#-- --------------------------------------------------- -->
                    <#--                  Athlete Description                -->
                    <#-- --------------------------------------------------- -->

                    <br>
                    <div class="row bg-editor-hv">
                        <div class="col-md-12">
                            <h2 class="eyebrow">Athletic Accomplishments</h2>
                            <div class="align-right">
                                <button class="btn-svg edit-btn"
                                        data-target="bio-2"
                                        data-editor="editor-2">
                                    <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                        <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                                </button>
                            </div>
                            <div class="code-h6 mt-2">
                                <div class="bio" id="bio-2">${athlete_descript}</div>
                            </div>
                            <div id="editor-container-2">
                                <div class="editor" id="editor-2"></div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <#-- --------------------------------------------------- -->
                    <#--             Athlete Detail Links                    -->
                    <#-- --------------------------------------------------- -->
                    <#-- hash = user_hash , subj = athlete-->
<#--                    <button class="nav-link"-->
<#--                            ng-click="getAthletics($event, '${hash}', 'athlete')">-->
<#--                        supporting details-->
<#--                    </button>-->
                    <!-- Insert sports details here -->
                    <div class="justify-content-center" id="athletic-details"></div>
                    <br>
                    <br>
                    <br>
                    <#-- --------------------------------------------------- -->
                    <#--                  Athlete Photos                     -->
                    <#-- --------------------------------------------------- -->
                    <div class="bg-editor-hv">
                        <div class="align-right">
                            <button type="button"
                                    class="btn-svg "
                                    id="edit_athlete_imgs"
                                    data-bs-toggle="modal"
                                    data-bs-target="#main_images_modal"
                                    data-for-type="individual">
                                <svg class="editor-icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><!--!Font Awesome Free 6.7.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license/free Copyright 2025 Fonticons, Inc.-->
                                    <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32l0 144L48 224c-17.7 0-32 14.3-32 32s14.3 32 32 32l144 0 0 144c0 17.7 14.3 32 32 32s32-14.3 32-32l0-144 144 0c17.7 0 32-14.3 32-32s-14.3-32-32-32l-144 0 0-144z"/></svg>
                            </button>
                        </div>
                        <#if athlete_captions??>
                            <div class="row">
                                <#include "./section/metrics/sub/athlete_photos.ftl">
                            </div>
                        </#if>
                    </div>
                </div>
                <hr class="mt-4">
            </div>

<#--            <div class="row bg-editor">-->
<#--                &lt;#&ndash; TIMELINE set in col-md-6 &ndash;&gt;-->
<#--                <#include "timeline_section.ftl">-->

<#--                <div class="col-md-6">-->

<#--                </div>-->
<#--                <hr class="mt-4 mb-5">-->
<#--            </div>-->

<#--            <div class="row bg-editor mt-2">-->
<#--                &lt;#&ndash; TEXT Description&ndash;&gt;--
<#--                <div class="col-md-10 text-center border-rt-1">-->

<#--                </div>-->
<#--                &lt;#&ndash; image evidence &ndash;&gt;-->
<#--                <div class="col-md-2 text-center border-rt-1">-->

<#--                </div>-->
<#--            </div>-->
        </div>
    </div>
</section>
<#-- footer -->
<#include "new_footer.ftl">
<script src="/app/quill/dist/quill.js"></script>
<script src='/app/javascript/profile_editor_text.js'></script>
<script src='/app/javascript/bootstrap/bootstrap.js'></script>
<script src='/app/javascript/jquery/jquery-3.6.1.min.js'></script>

<script src="/app/javascript/animation/controller/FormController_Industry.js" type="module"></script>
<script src="/app/javascript/animation/controller/FormController_Employer.js" type="module"></script>
<script src="/app/javascript/animation/controller/FormController_Education.js" type="module"></script>
<script src="/app/javascript/animation/controller/FormController_MilService.js" type="module"></script>

<script src="/app/javascript/animation/Init_ImgController.js" type="module"></script>
</body>
</html>