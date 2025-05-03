<!DOCTYPE html>
<html lang="en-us" ng-app="userApp">
<head>
    <meta charset="UTF-8">
    <link rel="canonical" href="https://knocscore.com/privacy">
    <#include "common/icon_block.ftl">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="title" content="Privacy">
    <#-- Open Graph / Facebook -->
    <meta property="og:type" content="website">
    <meta property="og:url" content="https://knocscore.com/privacy">
    <meta property="og:title" content="Privacy">
    <!-- styles -->
    <#include "common/styles_block.ftl">
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
<#include "common/header_block.ftl">

<div class="sr-root">
    <div class="container" >

        <div class="row">

            <h1>How to succeed in College</h1>

            <!-- Left -->
            <div class="col-md-12">
                <br>
                <br>
                <h2>Use effective study strategies</h2>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">
                        <h3>The problem with the most common study habit</h3>
                        <p>Many of us students do not learn good study habits before we get to the college level. Generally
                            however, we follow a similar pattern that is something like this.
                            We go to class. Take some notes. Review them once and set them aside. Then
                            just before exam time, we cram all of our notes in hopes we will remember. Sound familiar?
                            The problem with this strategy is when we are reading and remembering our notes,
                            the type of memory recall that is built relies on hints. When we take an exam and
                            do not see an associated hint, this leads to a lack of recall. Many students refer to this
                            as "drawing a blank".</p>
                    </li>
                    <li class="list-group-item">
                        <h3>An effective study strategy</h3>
                        <p>Time is a limited resource in college, just as it is in life.
                            If we are taking the effort to attend college, adding on a large chunk of debt, and spending 4 or more years of our lives
                            to learn, then using efficient and effective study methods should be a priority. As mentioned above, most of us
                            do not study effectively. This results in degree and career changes when critical
                            courses are failed. A better way to study is to build free recall. Most students feel
                            more confident about their knowledge when they build free recall memory and this
                            reduces anxiety before an exam. Here's a study strategy that employs the findings from
                            research conducted at a top 5 university on the best methods for studying.</p>

                        <table class="table-borderless">
                            <tr>
                                <th scope="row"></th>
                                <td ><h3>1. Review information by testing yourself</h3>
                                    <p>If you read the statement in the beginning. Testing is not re-read your notes.
                                        Instead use smart technologies similar to Flash Cards to
                                        create concise easily digestible
                                        chunks of knowledge. Flash Card technologies are more flexible than physical
                                        cards and offer added capabilities such as storing video, audio, images
                                        and annotations and multiple question or card types.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td ><h3>2. Write your own questions</h3>
                                    <p>Try to avoid hints that give away the answer. If you write
                                        them as you would expect a Professor to write them, you may avoid surprises at
                                        test time. In addition, ask the question in an unexpected
                                        way from the way you learned it. </p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td ><p>If you cannot write your own effective study questions, obtain them from
                                        someone else. It is wise to get study materials from the same classes
                                        and the same professor so that terminology and order remains the same.</p></td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td><h3>3. Repeat questions when you've missed and use time spaced repetition</h3>
                                    <p>Study the information until you've memorized it. Wait a few days, then study it again.
                                        If you cannot memorize it completely, or there is a section that isn't sticking,
                                        wait a few hours or repeat the questions in the same study session.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>Study the materials again after a few days. Then study them at least a third time
                                    before the final cram session.</td>
                            </tr>
                        </table>
                    </li>
                    <li class="list-group-item">
                        <h2>Effectively using TopScholars</h2>
                        <table class="table-borderless">
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>1. Use TopScholars to create digital notes and flashcards.</h3>
                                    <p>Write them while in lectures. You can
                                        quickly include images of the white board, snapshots from the screen, sound, and
                                        drawings. It is engineered to be as fast as a lecture. It's also far faster than
                                        handwriting or recreating a white board drawing.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>2. Try to create notes as flashcards question and answers or other question types.</h3>
                                    <p>Although this takes some skill and can slow down the note-taking process at first,
                                        it becomes more efficient when taking several classes back to back.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>3. Be concise</h3>
                                    <p>Keep a flash card to a concise bit of information similar to a flashcard in the physical
                                        world. Although there is no limit to the size of the text in a card. It
                                        is more effective to be to the point. </p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>4. Initial review</h3>
                                    <p>Use the deck while completing your assignments, use the Q and A mode and the key word search.
                                        you can use review what you created in class, make clarifications, and edit
                                        it so that it is accurate and well explained.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>5. Time spaced review</h3>
                                    <p>As soon as possible, or within a few days review the deck in test mode. Then wait
                                        2 to 3 more days and review again. If you study the deck and reach at least
                                        85%-90%, strive for 100%, success each time, this will make review at cram
                                        time much easier and reduce test anxiety. </p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>6.</h3>
                                    <p>When you miss a question, TopScholars will insert the question a few more times into
                                        the deck. Answer the questions correctly the next time. If you answer it incorrectly
                                        TopScholars's algorithm may prioritize that card so that it shows up at the front of
                                        the deck during the next study session.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>7.</h3>
                                    <p>If you've learned a card and have answered it several times. TopScholars may hide the card.
                                        Before it does it will also begin to prioritize the card so that it shows up later in the deck.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>8.</h3>
                                    <p>You can return the deck to its original order in the Create and Edit mode.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>9.</h3>
                                    <p>TopScholars remembers your score. This is kept private unless you decide to share
                                        it.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>10.</h3>
                                    <p>You may add new cards to the deck. They can be inserted anywhere in the original order.
                                        TopScholars will remember that you have not reviewed the card and prioritize it so it
                                        shows up early in the deck. If you want the card to show up in its original order, reset
                                        the deck order.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>11.</h3>
                                    <p>It is effective to study a deck until you get all the questions correct. But at least study it
                                        until 85%. Try to keep information, questions and answers, concise. It is also
                                        a good strategy to keep decks limited in length less than 50 but 15 to 30 cards is good.
                                        Keep them specific to an easily recognizable or a commonly known topic.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>12.</h3>
                                    <p>It is also important that a card contains text. Although it is possible for a card to be
                                        just an image or video. Images and videos are not great for searches. If a card is used
                                        for review, provide a brief description using keywords about the subject of the video. Do
                                        not include hash marks. Just write the text in sentence form. This makes it easier to
                                        search for a video or image that contains important information about a subject.</p>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"></th>
                                <td>
                                    <h3>13.</h3>
                                    <p>There are alternative
                                        reasons to study on TopScholars. Not only do you learn efficiently, but as a creator, you
                                        can sell your decks to fellow classmates who may be struggling in your class. Importantly,
                                        knowledge has value, this is we pay tuition to education institutions. It is also
                                        important to point out another side effect of using TopScholars to study. That is
                                        that when your decks sell and are reviewed by your peers. This is concrete validation
                                        that can be combined with analytics about your understanding of the information.
                                        These are important metrics that can be used in the future.</p>
                                </td>
                            </tr>
                        </table>
                    </li>

                </ul>
                <h3>Chartered for research</h3>
                <p>From the beginning document of the formation of TopScholars
                    TopScholars was formed in part to research how to improve learning.</p>

                <h3>Modularity</h3>
                <p>New types can be created by developers and made available for creators to use either
                    for studying or learning new concepts. </p>

                <h3>The Ecosystems</h3>
                <p>The ecosystem is multiple sub-ecosystems</p>
                <ul>
                    <title>Sub-Ecosystems</title>
                    <li class="list-group-item">Student Ecosystem</li>
                    <li class="list-group-item">Educator Ecosystem</li>
                    <li class="list-group-item">Type Developers</li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!-- footer -->
<#include "new_footer.ftl">
<#include "common/closeout_scripts.ftl">
</body>
</html>