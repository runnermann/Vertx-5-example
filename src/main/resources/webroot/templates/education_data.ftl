<!DOCTYPE html>
<html lang="en-us">
<head>
  <meta charset="UTF-8">
  <link rel="canonical" href="https://knocscore.com/education-data">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <#include "common/icon_block.ftl">
  <meta name="title" content="About">
  <#include "common/google_analytics.ftl">
  <#-- Open Graph / Facebook -->
  <meta property="og:type" content="website">
  <meta property="og:url" content="https://knocscore.com/education-data">
  <meta property="og:title" content="About">
  <!-- styles -->
  <#--  <#include "common/styles_block.ftl">-->
  <#include "common/styles_block_infopg.ftl">
  <link href="/app/style/blog.css" rel="stylesheet" type="text/css">
  <!-- javascript -->
  <link href="/app/style/animate-style.css" rel="stylesheet" type="text/css">
  <#--    <link href="/app/style/about-style-special.css" rel="stylesheet" type="text/css">-->
  <!-- javascript -->
  <#-- custom javascript -->
  <script src="/app/javascript/non-index.js"></script>
</head>
<body>
<section>
  <!-- Modal Sign up -->
  <#include "modals/signup_modal.ftl">
  <#-- Thank you MODAL-->
  <#include "modals/stripe_thankyou_modal.ftl">
  <#-- nav -->
  <#include "section/non-index-nav.ftl">
</section>
<br class="clearfloat">
  <br>
  <br>
  <div class="sr-root">
    <section class="two">
      <div class="container" >
        <div class="row">
          <div class="col-lg-1 col-sm-0">&nbsp;</div>
          <div class="col-md-10">
            <br>
            <br>
            <br>
            <h1 class="center-text">Education research data and helpful links</h1>
            <br>
          </div>
        </div>
      </div>
    </section>
    <section class="two">
      <div class="container">
        <br>
        <br>
        <br>
        <hr class="mt-1 mb-1"/>
        <br>
        <div class="row">
          <div class="col-lg-1 col-sm-0">&nbsp;&nbsp;</div>
          <div class="col-lg-10" col-sm-12>
            <h2>Research Data</h2>
            <p>
              A location for links to the data we use in our research. Use this data in your own research. Please send
              any links that would be helpful for education or economic statistics to <a href="mailto: isaac.one@knocscore.com">isaac.one@knocscore.com</a>
              You may submit links to your research here.
            </p>
          </div>
        </div>
        <br>
        <br>
        <hr class="mt-1 mb-2"/>
        <br>
        <br>
        <div class="row mb-5">
          <div class="col-lg-1 col-sm-0">
            <br>
          </div>
          <div class="col-lg-5" col-sm-6 mb-2>
            <h2 class="fm-align-left"><b>First Source Information</b></h2>
            <br>
            <br>
            <ul class="nav flex-column">
              <li class="nav-item mb-2"><a href="https://nces.ed.gov/programs/digest/" class="nav-link-alt p-0 ">National Center for Education: Programs Digest</a></li>
              <li class="nav-item mb-2"><a href="https://nces.ed.gov/programs/digest/" class="nav-link-alt p-0 ">National Center for Education: Programs Digest</a></li>
              <li class="nav-item mb-2"><a href="https://www.pewresearch.org/short-reads/2022/04/12/10-facts-about-todays-college-graduates/" class="nav-link-alt p-0 ">PEW Research: Facts about today's college graduates</a></li>
              <li class="nav-item mb-2"><a href="https://www.newyorkfed.org/research/college-labor-market#--:explore:underemployment" class="nav-link-alt p-0 ">NY Federal Reserve Bank on Graduate Underemployment</a></li>
              <li class="nav-item mb-2"><a href="https://www.lisep.org/tru" class="nav-link-alt p-0">Unemployment for Graduates is worse than you think. LISEP</a></li>
              <li class="nav-item mb-2"><a href="https://nche.ed.gov/topics/" class="nav-link-alt p-0">National Center for Homeless Education: Topics</a></li>
              <li class="nav-item mb-2"><a href="https://en.wikipedia.org/wiki/DIKW_pyramid" class="nav-link-alt p-0">What is knowledge? A DIKW perspective. Wikipedia</a></li>
              <li class="nav-item mb-2"><a href="https://en.wikipedia.org/wiki/Ontology_(information_science)" class="nav-link-alt p-0">Ontology, Defining the division of knowledge. Wikipedia</a></li>
              <!-- Commented out... This article was rewritten and modified from its original form for the purpose of the editors bias... In the new version, the author of the edits displays a classical
              -- problem with the purpose of influencing the readers views. The new version lacks broad cognitive awareness. Due to this, it limits the readers perception, making it wrong.
              -- The majority of the new article adds little value. However some of the original article remains untouched and articulates some problems with knowledge that are worthy of being placed here. It remains
              -- commented out due to the bias/error of the editor.
              <li class="nav-item mb-2"><a href="https://plato.stanford.edu/entries/epistemology/" class="nav-link-alt p-0">Epistemology: Stanford Encyclopedia of Philosophy, Particularly Knowing How and What (JTB) Justification Truth and Belief</a></li>
              -->
            </ul>
          </div>
          <div class="col-lg-5" col-sm-6 mb-2>
            <h2 class="fm-align-left"><b>Articles, Podcasts, and Books</b></h2>
            <br>
            <br>
            <ul class="nav flex-column">
              <li class="nav-item mb-2"><a href="https://www.amazon.com/Abundant-University-Remaking-Education-Digital-ebook/dp/B0BQLKK2LH" class="nav-link-alt p-0">'The Abundant University' by Michael D. Smith, www.amazon.com</a></li>
              <li class="nav-item mb-2"><a href="https://www.youtube.com/watch?v=40XcXV9G4wA" class="nav-link-alt p-0">"Higher Education is broken, can it be fixed", podcast on YouTube, by Steve Levitt with Michael D. Smith</a></li>
            </ul>
          </div>
          <br>
          <br>
        </div>
      </div>
    </section>
  </div>
  <!-- footer -->
  <#include "new_footer.ftl">
  <#include "common/closeout_scripts.ftl">

</body>
</html>