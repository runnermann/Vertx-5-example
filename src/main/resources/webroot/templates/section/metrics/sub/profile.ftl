<#--  The background photo is set in css <style> in the head -->
<#--  We dynamically set the css background image per user.  -->
<div class="img-bkgnd-graph"
     id="background-img">
    <#-- empty -- displays background img-->
</div>
<div class="row">
    <div class="col-md-5">
        <#-- We set the user's image over the background image. -->
        <img src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${hash}-profile-img.webp"
             class="s-img rounded-circle border border-5 img-fluid mb-5"
             id="profile-img"
             width="40%">
        <br>
    </div>
    <div class="col-md-7">

        <h1 class="act-like-h4 mb-2" id="user_name_nx">${name}</h1>
        <p class="eyebrow">Member since: <b>${join_date}</b></p>

    </div>
</div>