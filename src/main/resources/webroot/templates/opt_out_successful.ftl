<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- styles -->
    <#include "common/styles_block_infopg.ftl">
    <!-- javascript -->
    <#include "common/javascript_block.ftl">
    <script>
        window.onload = function() {
            let navbar = document.getElementById("navbar");
            navbar.classList.add("navbar-dark");
        }
        window.addEventListener("scroll", e=> {
            let navbar = document.getElementById("navbar");
            if ((window).scrollY > 60) {
                navbar.style.display = 'none';
            } else {
                navbar.style.display = 'block';
            }
        });
    </script>
</head>
<#include "common/header-block-noprogbar.ftl">

<br class="clearfloat">

<div class="sr-root">
    <div class="container" >
        <div class="row">
            <div class="col-md-12">
                <div style="margin-top: 100px;">
                    <br>
                    <br>
                    <br>
                    <h1>We have updated your status. You have been disenrolled from future emails. </h1>
                    <br>
                    <br>
                    <p>To learn more about how to earn on TopScholars see: <a href="/earning-money-as-a-college-student" >Earning Pay</a></p>
                    <p>To learn more about how to build your Knowledge Crediblity Score that you can share with employers see: <a href="/earning-your-knowledge-credibility">
                            Building your KnoC&trade; Score.</a></p>
                    <p>To learn about the KnoC&trade; Score see: <a href="/knowledge-credibility-score">
                            knowledge credibility score&trade;.</a></p>
                    <br>
                    <br>
                    <br>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- footer -->
<#include "new_footer.ftl">
<#include "common/closeout_scripts.ftl">
</body>
</html>