<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
    <link rel="canonical" href="https://VertxExample.com/jobs_all">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="Jobs and Internships">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://VertxExample.com/jobs_all">
    <meta property="og:title" content="Jobs and Internships">
    <#include "common/styles_block.ftl">
</head>
<body>
    <#include "common/header-block-noprogbar.ftl">
    <!-- Team Page -->
    <br class="clearfloat">

    <div class="container" >
        <div class="row">
            <div class="col-md-12 bg-dark border-dark">
                <br>
                <br>
                <br>
                <br>
                <h1>Careers</h1>
                <hr>
                <br>
                <h2>About Us:</h2>
                <p>VertxExample is a disruptive, bootstrapped, early-stage tech start-up based in the SF Bay Area.
                    You will be among the first to join the company. Some examples of successful bootstrapped companies
                    are GoPro, Microsoft, Dell Computers, Oracle and GitHub to name a few.</p>
                <p>We are building a novel interactive learning and teaching platform for higher education -- an actual
                    learning tool we believe will help simplify life for both students and Faculties. We are creating
                    an advanced, engaging, and intuitive multimedia study system that organizes content, categorizes
                    classes, and prioritizes studies. The integrated AI-powered platform identifies your learning style
                    and uses psychological learning strategies, efficient for retaining knowledge long term. Our platform
                    is based on a flashcard technology and can be used for studying any subject in principle.</p>
                <p>There is a lot that will happen over the next few months. We are planning to grow, and if history
                    tells us anything, then it supports that our concept will grow rapidly. There are many important tasks
                    that must occur and that you will be a part of. In the idea of the Civitas of a Greek polis, your work
                    counts. And more than anywhere else, you will be relied on to complete your tasks, and you will have
                    the opportunity to impact the future of what the company becomes. Saying that, early-stage start-ups
                    are lean and count on creativity, agility, and team players. It is not a place where you will find
                    mentors, instead you are bringing your ability to create something meaningful. Like in an Ancient
                    Greek Polis, the team will count on you to be there for them, and it will be there to help you when you
                    need it. Someday, you will be able to tell others that you were here, and you will be able to point to
                    something that others use and say "I helped to create that".</p>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 bg-dark border-dark mb-3">
                <br>
                <br>
                <h2>How to apply:</h2>
                <p>Send your CV and the required documents to:
                    <a id="my_email" href="mailto:opportunities@VertxExample.com"><i class="fas fa-paper-plane"></i>Contact</a>opportunities@VertxExample.com</p>
                <br>
                <br>
                <h2>Open positions:</h2>
            </div>
        </div>

        <div ng-controller="UserController">
            <#list jobsData as job>
                <div class="row mb-3">
                    <div class="col-md-12 bg-black">
                        <div class="card-deck" >
                            <div class="card card-custom bg-dark border-dark">
                                <div class="card-body" style="overflow-y: auto">
    <#--                                <hr class="mt-1">-->
                                    <h4 class="card-title">${job.job_position}</h4>
                                    <div class="card-text">
                                        <p>Employment Type: ${job.employment_type}</p>
                                        <p>Career ID:       ${job.job_id}</p>
                                        <p>Duration:        ${job.duration}</p>
                                        <p>Location:        ${job.job_location}</p>
                                        <p>Remote:          ${job.remote}</p>
                                        <p>Find out more about this<a href="/job_details/${job.job_id}" > position.</a></p>
                                    </div>
                                </div>
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