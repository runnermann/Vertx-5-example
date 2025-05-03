<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
    <link rel="canonical" href="https://knocscore.com/jobs_all">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="Jobs/Internships details">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://knocscore.com/jobs_all">
    <meta property="og:title" content="Jobs/Internships details">

    <#include "common/styles_block.ftl">
    <!-- non-index pages -->
</head>
<body>
<#include "common/header-block-noprogbar.ftl">
<!-- Team Page -->
<br class="clearfloat">

<div class="container" >
    <div class="row" id="team">
        <div class="col-md-12">
            <br>
            <br>
            <br>
            <h2>Jobs</h2>
            <br>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 bg-black">
            <#list jobsData as job>
                <div ng-controller="UserController">
                    <div class="card-deck" >
                        <div class="card card-custom bg-dark border-dark">
                            <div class="card-body" style="overflow-y: auto">
                                <h4 class="card-title">Position: ${job.job_position}</h4>
                                <hr class="mt-1">
                                <div class="card-text">
                                    <p><b>Employment Type: </b> ${job.employment_type} </p>
                                    <p><b>Duration: </b>        ${job.duration} </p>
                                    <p><b>Location: </b>        ${job.job_location} </p>
                                    <p><b>Description: </b>     ${job.description} </p>
                                    <p><b>Requirements: </b>    ${job.required_knowledge} </p>
                                    <p><b>Major: </b>           ${job.major} </p>
                                    <p><b>Remote: </b>          ${job.remote} </p>
                                    <p><b>Pay: </b>             ${job.pay} </p>
                                    <p><b>Incentives: </b>      ${job.incentives}</p>
                                    <p><b>Required Documents: </b> ${job.required_docs} </p>
                                    <p><b>Work Authorization: </b> ${job.work_auth} </p>
                                    <p><b>International: </b>   ${job.international} </p>
                                    <p><b>To apply:</b> Use your university email and send your resume and required documents to:
                                        <a id="my_email" href="mailto:opportunities@knocscore.com"><i class="fas fa-paper-plane"></i> opportunities@knocscore.com</a></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 bg-black">
            <br>
            <br>
            <br>
            <br>
            <h2>EQUAL OPPORTUNITY</h2>
            <br>
            <p>At KnocScore, we embrace difference. We don't just accept it - we thrive on it for the benefit of our
                employees, our products and our community. KnocScore
                Inc. is an equal opportunity employer. Employment at KnocScore is based solely on a person's
                merit and qualifications directly related to professional competence. KnocScore does not discriminate
                against any employee or applicant because of race, creed, color, religion, gender, sexual
                orientation, gender identity/expression, national origin, disability, age, genetic information,
                veteran status, marital status, pregnancy or related condition (including breastfeeding), or any
                other basis protected by law.

                It is KnocScore's policy to comply with all applicable national, state and local laws pertaining
                to nondiscrimination and equal opportunity. The Company's EEO policy, as well as its affirmative
                action obligations, includes the full & complete support of the Company, including its
                Chief Executive Officer. Because it's just the right thing to do. We hope you think so too.</p>
        </div>
    </div>
</div>
<!-- footer -->
<#include "new_footer.ftl">

<#include "common/closeout_scripts.ftl">
</body>
</html>