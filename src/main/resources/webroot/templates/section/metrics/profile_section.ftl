<section>
    <div >
        <#-- Profile Data -->
        <div class="container">
            <div class="row">
                <#-- left side on laptop -->
                <div class="col-md-4 border-md-rt-1">
                    <#if name??>
                        <#include "sub/profile.ftl">
                    </#if>
                </div>
                <#-- right side on laptop -->
                <div class="col-md-8">
                    <div class="row mt-5 mb-5">
                        <#include "sub/industry.ftl">
                    </div>
                    <hr>
                    <#-- employer info -->
                    <div class="row">
                        <div class="col-md-12 mt-4">
                            <h2 class="eyebrow">Employer: <b>${employer}</b></h2>
                            <h3 class="eyebrow">Position: <b>${position}</b></h3>
                        </div>
                    </div>
                    <#-- Education data -->
                    <div class="row">
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
                            <h3 class="eyebrow">Various institutions including Stanford, Rice University, and UofI at
                                Urbana Champaign</h3>
                            <h3 class="eyebrow">Major: <b>Software Engineering</b></h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <#-- military service -->
                    <#if status??>
                        <hr>
                        <h2 class="eyebrow mt-5">Military Service</h2>
                        <div class="code-h6 mt-4">
                            <p>${name} ${status} the ${country} ${branch} as a ${rank}. <br> ${gender} served
                                from ${start} to ${end}.</p>
                        </div>

                    <#-- Awards Badges Certificates -->
                    <#-- img 1 -->
                        <#if mil_captions??>
                        <#-- Service Military photos, Awards Badges Certificates -->
                            <#include "sub/mil_svc_photos.ftl">
                        </#if>
                    </#if>
                </div>
                <div class="col-md-8" ng-controller="StatsController">
                    <#if bio??>
                        <br>
                        <br>
                        <h2 class="eyebrow mt-4">Bio</h2>

                        <#-- bio / description text -->
                        <div class="code-h6 mt-4">
                            <p>${bio}</p>
                        </div>
                        <br>

                    </#if>
                    <#if athlete_descript??>
                        <br>
                        <h2 class="eyebrow">Athletic Accomplishments</h2>
                        <div class="code-h6 mt-4">
                            <p>${athlete_descript}</p>
                        </div>
                        <br>


                        <#----------------------------------------------------------------->
                        <#-- Get athlete details table, hash = user_hash , subj = athlete-->
                        <#----------------------------------------------------------------->
                        <button class="nav-link nav-btn"
                                ng-click="getAthletics($event, '${hash}', 'athlete')">supporting details
                        </button>

                        <br>
                        <br>
                        <br>
                        <#-- Insert sports details table here -->
                        <div class="justify-content-center" id="athletic-details"></div>


                        <#if athlete_captions??>
                            <div class="row">
                                <#include "sub/athlete_photos.ftl">
                            </div>
                        </#if>
                    </#if>
                </div>
                <hr class="mt-4">
            </div>

            <div class="row">
                <#-- TIMELINE set in col-md-6 -->
                <#include "timeline_section.ftl">

                <div class="col-md-6">

                </div>
                <hr class="mt-4 mb-5">
            </div>

            <div class="row mt-2">
                <#-- TEXT Description-->
                <div class="col-md-10 text-center border-rt-1">

                </div>
                <#-- image evidence -->
                <div class="col-md-2 text-center border-rt-1">

                </div>
            </div>
        </div>
    </div>
</section>