<div class="col-md-4 col-4 text-center border-rt-1">
    <#if reqbyind??>
        <p class="act-like-l1">${percent}%</p>
    </#if>
</div>
<div class="col-md-8 col-8 pe-3">
    <#if reqbyind??>
        <p class="create">Overall KnocScore is ${reqbyind} than
            <span class="act-like-h4">${percent}%</span> of the ${industry} industry.</p>
        <p class="create">Users with equivalent metrics work at <span
                    class="act-like-h2">${equivelantworkatentity}</span>. </p>
        <a href="/">Supporting details</a>
    </#if>
</div>