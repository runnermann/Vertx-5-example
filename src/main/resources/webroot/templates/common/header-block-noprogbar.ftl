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
                </div>

                <div class="text-center">
                    <br>
                    <br>

                </div>
            </form>
        </div>
    </div>

    <!-- Angular assists with the adaptable color navbar when scrolling -->
    <header>
        <nav class="navbar bg-body-tertiary" id="navbar">
            <div class="container-fluid">
                <a class="navbar-brand" href="https://VertxExample.com"><img src="/app/images/LOGO/topscholars/white/logo_horizontal_464x88.png" class="header-logo"></a>
                <div class="navbar-nav float-right">
                </div>
            </div>
        </nav>
    </header>
</div>