<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign tiles=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Loyal - Me</title>
    <link rel="SHORTCUT ICON" href="${rc.contextPath}/favicon.ico"/>
    <link href="${rc.contextPath}/styles/styles.css" rel="stylesheet" type="text/css"/>
    <link href="${rc.contextPath}/styles/jquery.fancybox.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${rc.contextPath}/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/jquery.fancybox.pack.js"></script>
    <script type="text/javascript" src="${rc.contextPath}/js/jquery.corner.js"></script>
    <script type="text/javascript">

    </script>
</head>

<body>

<@tiles.insertAttribute name="main"  />


</body>
</html>