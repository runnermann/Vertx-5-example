<#include "headersecure.ftl">

<br class="clearfloat">
<!-- Main section -->
<section>
    <!--<div class="row"> -->
    <div class="container" >


        <div class="row">
            <!-- left side -->
            <div class="col-md-6" ng-controller="UserController">
                <label> My Decks </label>
                <hr class="mt-1">
                <div class="card-deck" >
                    <#list decksData as deck>
                    <!-- <div ng-repeat="deck in decksData" class="card card-custom bg-white border-white"> -->
                    <!-- Author: Peter Dannis GitHub https://github.com/peterdanis/custom-bootstrap-cards -->

                        <div class="card card-custom bg-white border-white">
                            <div class="card-custom-img" style="background-image: url(http://res.cloudinary.com/d3/image/upload/c_scale,q_auto:good,w_1110/trianglify-v1-cs85g_cc5d2i.jpg);"></div>
                            <div class="card-custom-avatar">
                                <img class="img-fluid" src="images/books/algorithms.jpg" alt="Avatar" />
                            </div>
                            <div class="card-body" style="overflow-y: auto">
                                <!-- deck is associated with ng-repeat that parses each element from decksdata -->
                                <h4 class="card-title">${deck.name}</h4>

                                <p class="card-text">My last score: ${deck.score} <br>
                                    correct/attempted/total cards</p>
                                <label> About </label>
                                <hr class="mt-1">
                                <p class="card-text">Description: ${deck.descript} </p>
                                <P class="card-text">Test types: ${deck.test_types}
                                    <br>Images: ${deck.num_imgs}
                                    <br>Videos: ${deck.num_video}
                                    <br>Audios: ${deck.num_audio}
                                    <br>Stars:  ${deck.deck_numstars} | Raters: ${deck.deck_numraters} | Users: ${deck.num_users}
                                </p>
                            </div>
                            <!-- @TODO implement detailed view of users decks -->
                            <!--<div class="card-footer" style="background: inherit; border-color: inherit;">
                                <a href="#" class="btn btn-primary btn-sm"> ng-click="getUserDecks(110011)">Option</a>
                            </div>-->
                        </div>
                    <!-- END CARDS -->
                    </#list>
                </div>
            </div>

            <!-- right side -->
            <div class="col-md-6 ">
                <form action="/search-decks" name="search" method="POST">
                    <input type="hidden" name="return_url" value="/users/secure">
                    <div class="form-group">
                        <label for="Find a deck">Find a deck</label>
                        <hr class="mt-1">
                        <label for="searchbysubject">Enter the subject</label>
                        <input class="form-control" type="text" name="searh" id="searchinput"
                               aria-describedby="search by subject" placeholder='example: "physics"' required>
                        <small id="emailHelp" class="form-text text-muted">Enter a subject you are studying</small>
                    </div>
                    <div class="form-group">
                        <label for="searchbyprof">Enter the professors name</label>
                        <input class="form-control" type="profname" name="profname" id="profname" ng-model="profname"
                               placeholder="name" required>
                        <small id="emailHelp" class="form-text text-muted">Enter the professors name</small>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="fa fa-pencil" aria-hidden="false"></i>
                        search
                    </button>
                </form>
            </div>

        </div>
    </div>


</section>
<br>
<br>
<br>


<#include "footer.ftl">
