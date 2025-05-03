<br>
<br>
<#list mil_captions as caption>
    <#assign i = caption?index>
    <#if i = 0>
        <#assign size = 40>
    <#else>
        <#assign size = 100>
    </#if>
    <#-- multiple images -->
        <div>
            <img src="https://flashmonkey-avatar.s3.us-west-2.amazonaws.com/${hash}-svc-img-${i}.webp"
                 class="rounded img-fluid img-grey"
                 id="svc-img-${i}"
                 width="${size}%"
                 alt="${caption}">
            <br>
            <p>${caption}</p>
        </div>
        <br>
        <br>
</#list>