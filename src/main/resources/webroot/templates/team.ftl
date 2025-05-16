<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
    <link rel="canonical" href="https://MyVIDI.com/team">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="Team page">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://MyVIDI.com">
    <meta property="og:title" content="MyVIDI">
    <#include "common/styles_block.ftl">
</head>
<body>
    <#-- header and modals -->
    <#include "common/header-block-noprogbar.ftl">
        <!-- Team Page -->
    <br class="clearfloat">

    <div class="container" >
        <div class="row" id="team">
            <div class="col-md-12">
                <br>
                <br>
                <br>
                <br>
                <h2>Meet the Team</h2>
                <br>
            </div>
        </div>
        <div class="row">
            <#list peopleData as people>
                <div class="col-md-6 mb-3">
                    <div ng-controller="UserController">
                        <div class="card card-custom bg-dark border-dark">
                            <div class="card-body" style="overflow-y: auto">
                                <div class="center-text mb-5">
                                    <img src="/images/${people.photo_link}" class="rounded-1" alt="MyVIDI team member" width="214" height="214">
                                </div>
                                <h4 class="card-title">${people.name}</h4>
                                <hr class="mt-1">
                                <P class="card-text">
                                    ${people.job_position}
                                    <br>${people.descript}
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <!-- footer -->
    <#include "new_footer.ftl">
    <#include "common/closeout_scripts.ftl">
</body>
</html>
