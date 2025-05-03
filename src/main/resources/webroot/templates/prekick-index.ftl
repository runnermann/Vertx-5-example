<!doctype html>
<html lang="en-us">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <link rel="canonical" href="https://knocscore.com/launch">
  <link rel="apple-touch-icon" sizes="180x180" href="/app/images/favicons/apple-touch-icon.png">
  <link rel="icon" type="image/png" sizes="16x16" href="/app/images/favicons/favicon-16x16.png">
  <link rel="icon" type="image/png" sizes="32x32" href="/app/images/favicons/favicon-32x32.png">
  <link rel="mask-icon" href="/app/images/favicons/favicon.svg" color="#29ABE2">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="title" content="We're raising on Indigogo">
  <meta name="description" content="Sign up to be a KnocScore Champion.
  Help us build a revolutionary learning and jobs platform for the future.">

  <#-- Open Graph / Facebook -->
  <meta property="og:type" content="website">
  <meta property="og:url" content="https://knocscore.com/launch">
  <meta property="og:title" content="We're raising on Indigogo">
  <meta property="og:description" content="Sign up to be a TopScholars Champion.
  Help us build a revolutionary learning and jobs platform for the future.">
<#--  <meta property="og:image" content="/app/images/index/power_to_the_students.png">-->
  <#-- twitter -->
  <meta property="twitter:card" content="summary_large_image">
  <meta property="twitter:url" content="https://knocscore.com/launch">
  <meta property="twitter:title" content="We're raising on Indigogo">
  <meta property="twitter:description" content="Sign up to be a TopScholars Champion.
  Help us build a revolutionary learning and jobs platform for the future.">
<#--  <meta property="twitter:image" content="/app/images/index/power_to_the_students.png">-->

  <link href="/app/style/bootstrap/bootstrap.css" rel="stylesheet" type="text/css">
  <link href="/app/style/fontawesome/css/all.css" rel="stylesheet" type="text/css">
  <link href="/app/style/prekick.css" rel="stylesheet" type="text/css">
  <#-- custom javascript -->
  <script src='/app/javascript/prekick.js' type='text/javascript'></script>
  <!-- Facebook Pixel Code -->
  <script>
    !function(f,b,e,v,n,t,s)
    {if(f.fbq)return;n=f.fbq=function(){n.callMethod?
            n.callMethod.apply(n,arguments):n.queue.push(arguments)};
      if(!f._fbq)f._fbq=n;n.push=n;n.loaded=!0;n.version='2.0';
      n.queue=[];t=b.createElement(e);t.async=!0;
      t.src=v;s=b.getElementsByTagName(e)[0];
      s.parentNode.insertBefore(t,s)}(window, document,'script',
            'https://connect.facebook.net/en_US/fbevents.js');

    fbq('init', '{EAAFVXmWCYhABO2fQQez2VyZBQZCg8ePeZBfYoouZBkNxSZCwxZBCB3xWwNYFQhoZBeinbedQrIfLbfmXY2UXk2YGhaOaF6fSmx2ZCSOkCB3I7joDGZCLxP0ia0ea3rdfKf8nnxFuyRhBi4ERZBxY9Vq7CypOLYzIlKQeXpuNpU7UhXTYBYsja8xTcEOaupNuDaoqA2sgZDZD}');
    fbq('track', 'PageView');
  </script>
  <noscript>
    <img height="1" width="1" style="display:none"
         src="https://www.facebook.com/tr?id={EAAFVXmWCYhABO2fQQez2VyZBQZCg8ePeZBfYoouZBkNxSZCwxZBCB3xWwNYFQhoZBeinbedQrIfLbfmXY2UXk2YGhaOaF6fSmx2ZCSOkCB3I7joDGZCLxP0ia0ea3rdfKf8nnxFuyRhBi4ERZBxY9Vq7CypOLYzIlKQeXpuNpU7UhXTYBYsja8xTcEOaupNuDaoqA2sgZDZD}&ev=PageView&noscript=1"/>
  </noscript>
  <!-- End Facebook Pixel Code -->
</head>
<body>
  <#-- MAIN -->
  <div class="jumbotron jumbotron-fluid">
    <header>
      <nav class="navbar bg-body-tertiary" id="navbar">
        <div class="container-fluid">
          <a class="navbar-brand" href="https://knocscore.com">
            <img src="/app/images/LOGO/topscholars/white/logo_h_white_398x86.png" class="header-logo">
          </a>
          <div class="navbar-nav float-right">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <li class="nav-item">
                <!--              <a class="nav-link nav-btn float-end" id="link_download">Download</a>-->
              </li>
            </ul>
          </div>
        </div>
      </nav>
    </header>
    <div class="row">
      <div class="col-md-6 col-s-0"> <br>  </div>
      <div class="col-md-6 col-s-12">
        <div class="top-margin-36 blue-round-rect">
              <form name="newUser" novalidate>
            <div class="row mb-4">
              <div class="col-md-8">
                <h7>Our patents pending technology revolutionizes
                  higher learning and employment.</h7>
              </div>
              <div class="col-md-4">
                <button class="btn-email" id="link_download" ng-click="vidclick()">
                  WATCH
                </button>
              </div>
            </div>
                <div class="row">
                  <div class="col-md-12">
                    <h6>Join the TopScholars Champions.</h6>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-8">
                    <div class="form-group mb-3">
                  <!--                    <label for="email" class="form-label">Email</label>-->
                      <input class="form-control email-ts" type="email" id="email-id" name="emailField" aria-describedby="emailHelp"
                             ng-model="email" placeholder="email" required autocomplete="on">
                    </div>
                  </div>
              <div class="col-md-4 mb-2">
                <div class="form-group">
                      <button type="submit" class="btn-email" id="send-btn" ng-click="save(newUser.$valid)">
                        JOIN
                      </button>
                      <script type="text/javascript">
                        $('#send-btn').click(function() {
                          fbq('trackCustom', 'emailGiven', {promotion: 'platform_champion'});
                        });
                      </script>
                    </div>
                  </div>
                </div>
              </form>
        </div>
      </div>
    </div>
  </div>
  <#-- MODAL VIDEO -->
  <div id="signUp_modal" class="modal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <#-- TITLE -->
          <h4> </h4>
          <h5 class="text-center">
            It's time for a change
          </h5>

          <span class="close" id="close_signUp"> &times; </span>
        </div>
        <#-- BODY -->
        <div class="modal-body">
          <form name="newUser" novalidate>
            <div class="row">
              <div class="col-md-12">
                <video class="modal-video" controls
                    <source src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/TopScholars_FinalCutv2.mp4"
                            type="video/mp4" loading="lazy">
                    Your browser does not support the video tag.
                </video>
              </div>
            </div>
            <div class="row">
              <div class="col-md-7">
                <br>
                <div class="form-group mb-3">
                  <#--                    <label for="email" class="form-label">Email</label>-->
                  <input class="form-control email-ts" type="email" id="email-id" name="emailField" aria-describedby="emailHelp"
                         ng-model="email" placeholder="email" required autocomplete="on">
                </div>
              </div>
              <div class="col-md-5">
                <div class="form-group mb-3">
                  <br>
                  <button type="submit" class="btn-email" id="send-btn" ng-click="save(newUser.$valid)">
                    JOIN
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
  <#-- display thank you message -->
  <div id="thankyou_modal" class="modal"tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-body">
          <div class="text-center">
            <br>
            <p> Good job joining the TopScholars Champions for our Indigogo project. <br>
            Champions are the only ones eligible to get the coveted TopScholars Champion Merch and the best deals.
            We will launch in January. We will keep you posted for launch day.</p>
            <div>
              <#--                    <label id="valid" class="center-text"></label>-->
              <label id="from" class="center-text"></label>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <#-- footer -->
  <#include "new_footer.ftl">
  <script src='/app/javascript/viewport.js' type='text/javascript'></script>
  <script src='/app/javascript/prelaunch_modal.js' type='text/javascript'></script>
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <#--<script src='/app/javascript/jquery/jquery-3.6.1.min.js'></script>-->
  <!-- Include all compiled plugins (below), or include individual files as needed -->
  <#--<script src="/app/javascript/bootstrap/Popper.js"></script>-->
  <script src="/app/javascript/bootstrap/five-three-two/bootstrap.bundle.js"></script>

</body>
</html>

