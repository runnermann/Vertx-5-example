<#list athlete_captions as athl_caption>
    <#assign i = athl_caption?index>
    <#-- Important to limit the photos to under 4
     else must deal with remainders -->
    <#assign wd = 12 / athlete_captions?size>
    <div class="col-md-${wd} text-center">
        <img src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${hash}-athlete-img-${i}.webp"
             class="rounded img-80 img-grey"
             id="athlete-img-${i}"
             height="128px"
             alt="${name}: ${athl_caption}_${i}"
        >
        <br>
        <p>${athl_caption}</p>
    </div>
</#list>