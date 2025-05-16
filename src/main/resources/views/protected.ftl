<html lang="en">
<body>
<p>Well, well, well, ${userInfo.login}!</p>
<p>
    <#if userInfo.email??>
    It looks like your public email address is ${userInfo.email}.
    <#else>
    It looks like you don't have a public email. That's cool.
</#if>
</p>
<p>
    <#if userInfo.private_emails?has_content>
    With your permission, we were also able to dig up your
    private email addresses:
    <#list userInfo.private_emails as email>
    ${email}<#if email?has_next>, </#if>
</#list>
<#else>
Also, you're a bit secretive about your private email
addresses.
</#if>
</p>
</body>
</html>


