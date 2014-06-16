<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>

<!DOCTYPE html>
<!--[if IE 7 ]>		<html lang="en" class="no-js ie7">	<![endif]-->
<!--[if IE 8 ]>		<html lang="en" class="no-js ie8">	<![endif]-->
<!--[if IE 9 ]>		<html lang="en" class="no-js ie9">	<![endif]-->
<!--[if (gt IE 9)|!(IE)]>	<html id="html" lang="en" class="no-js">	<![endif]-->

<head>
    <title>PGT Test</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="SHORTCUT ICON" href="${rc.contextPath}/favicon.png"/>
    <link rel="apple-touch-icon" href="apple-touch-icon.png"/>
    <script type="text/javascript">
        if( window != window.parent ) {
            parent.reload('cgi/login')
        }
    </script>
    <!-- CSS Styles -->
    <link rel="stylesheet" href="<@spring.url value="/styles/style.css"/>"/>
    <link rel="stylesheet" href="<@spring.url value="/styles/colors.css"/>"/>
    <link rel="stylesheet" href="<@spring.url value="/styles/pms.css"/>"/>
    <link rel="stylesheet" href="<@spring.url value="/styles/webfonts/webfonts.css"/>"/>
    <script src="<@spring.url value="/js/libs/modernizr-1.7.min.js"/>" type="text/javascript"></script>
    <script src="<@spring.url value="/js/libs/jquery-1.7.1.min.js"/>"></script>

</head>

<body id="body">
    <form action="${rc.contextPath}/j_spring_security_check" method="POST">

        <div>
            <label for="lg_userid"> Username </label>
            <input id="lg_userid" type="text" name="j_username" autocomplete="off" />
        </div>
        <div class="input_cont">
            <label for="lg_pass"> Password </label>
            <input id="lg_pass" type="password"  name="j_password" autocomplete="off" />
        </div>
        <input  type="submit" title="submit" value="Submit" />

    <#if message?exists><p c><@spring.message code=message.text text=message.text /></p></#if>
    </form>
</div>

<div>© PGT All rights reserved.</div>
<script src="<@spring.url value="/js/jquery/jquery.backstretch.min.js"/>" type="text/javascript"></script>


</body>
</html>