<div ng-controller="NonIndexHeaderController">
    <div id="signUp_modal" class="modal">
        <div>
            <span class="close" id="close_signUp"> &times; </span>
        </div>
        <div class="modal_content">
            <div role="alert" id="alertMessage">
<#--                {{alertMessage}}-->
            </div>
            <form name="newUser" novalidate>
                <br>
                <div class="form-group" >
                    <input class="form-control" type="text" id="userName" name="nameField" aria-describedby="nameHelp" placeholder="First name"
                           ng-model="userName" required maxlength="20" minlength="3" autocomplete="on">
                </div>
                <div class="form-group">
                    <input class="form-control" type="email" id="email" name="emailField" aria-describedby="emailHelp"
                           placeholder="email" ng-model="email" ng-disabled="userExists()" required autocomplete="on">
<#--                    <small id="emailHelp" class="form-text text-muted">An email with a secure link to download KnocScore for-->
<#--                        Mac and Windows Laptops, or Windows Tablets-->
<#--                        will be sent to this address. Your information is secret. We respect your privacy and do not share-->
<#--                        your information except as necessary.</small>-->
                </div>
<#--                <div class="text-center">-->
<#--                    <button type="button" class="btn-index centered" id="send-btn" ng-click="save(newUser.$valid)" >SEND EMAIL</button>-->
<#--                </div>-->
                <div class="text-center">
                    <br>
                    <br>
<#--                    <p class="small">To learn more about compatibility see our <a href="/faq">FAQ</a></p>-->
                </div>
            </form>
        </div>
    </div>
    <!-- Download Modal -->
<#--    <div id="download_modal" class="modal" >-->
<#--        <div>-->
<#--            <span class="close" id="close_download"> &times; </span>-->
<#--        </div>-->
<#--        <div class="modal_content">-->
<#--            <div class="form-group">-->
<#--                <p class="text-center">-->
<#--                    Your mission, should you accept, is to get smart, and make money. Start by downloading the app below.-->
<#--                    Then, create your study materials as best as you can. Learn from them and share your QR-Code with-->
<#--                    your fellow college students.-->
<#--                </p>-->
<#--                <p class="text-center">If you do this, you will succeed.</p>-->
<#--            </div>-->
<#--            <div class="margin-btm-16vh">-->
<#--                <div class="text-center">-->
<#--                    <button type="button" class="btn-index" id="download-btn"  ng-click="download()">-->
<#--                        DOWNLOAD-->
<#--                    </button>-->
<#--                </div>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
    <#-- display a thank you message -->
    <div id="thankyou_modal" class="modal">
        <div class="modal_content">
            <div class="text-center">
                <br>
                <p> Thank you for your interests in KnocScore. <br>
                    We've added you to our email list and will keep<br>
                    you posted when our software is ready for download.</p>
                <div id="alertMessage">
                    <#--                    <label id="valid" class="center-text"></label>-->
                    <label id="from" class="center-text"></label>
                </div>
            </div>
        </div>
    </div>
    <!-- Angular assists with the adaptable color navbar when scrolling -->
    <header>
        <nav class="navbar bg-body-tertiary" id="navbar">
            <div class="container-fluid">
                <a class="navbar-brand" href="https://knocscore.com"><img src="/app/images/LOGO/topscholars/white/logo_horizontal_464x88.png" class="header-logo"></a>
                <div class="navbar-nav float-right">
<#--                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">-->
<#--                        <li class="nav-item">-->
<#--                            <a class="nav-link nav-btn float-end" id="link_download">Download</a>-->
<#--                        </li>-->
<#--                    </ul>-->
                </div>
            </div>
        </nav>
    </header>
</div>