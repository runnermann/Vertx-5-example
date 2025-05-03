<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="KnocScore: A versatile and genius multi-media and interactive learning and teaching ecosystem based
on the flashcard concept">
  <meta name="keywords" content="KnocScore, advanced learning ecosystem, flashcards, teaching">
    <link href="https://fonts.googleapis.com/css?family=Raleway|Roboto" rel="stylesheet">
    <meta name="author" content="Lowell Stadelman">
    <link rel="icon" src="/app/images/LOGO/KnocScore/white/logo_horizontal_464x88.png">
  <title>KnocScore</title>
    <link rel="stylesheet" type="text/css" href="/app/style/reset.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="/app/style/style.css">
    <link rel="stylesheet" type="text/css" href="/app/style/smallcards.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css"
          integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
  <!-- fonts -->
  <link rel="stylesheet" href="https://use.typekit.net/tcz7fxq.css">
    <!-- angular -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.5/angular.min.js"></script>
    <script src="https://cdn.jsdelivr.net/lodash/4.17.4/lodash.min.js"></script>
    <!-- tag::load-sockjs-eventbus-scripts[] -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.3.0/sockjs.min.js"
            crossorigin="anonymous"></script>
    <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/vertx/3.5.4/vertx-eventbus.min.js"
            crossorigin="anonymous"></script>-->
</head>
<body>
  <header>
    <div class="header_content">
      <!-- <a href="../img/LOGO/horiz_logo_88x564.png"><img id="home-logo"  alt="KnocScore icon"></a> -->

      <div id="home-logo">
        <a id="home-link" href="/"></a>
      </div>

        <nav class="navigation">
            <a id="link_signOut" href="/logout" aria-pressed="true">Sign out</a>
           <!-- <a class="btn btn-outline-danger" href="/logout" role="button" aria-pressed="true"> Logout </a> -->
        </nav>
    </div>
  </header>



