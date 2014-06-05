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
        <!--
        function MM_swapImgRestore() { //v3.0
            var i, x, a = document.MM_sr;
            for (i = 0; a && i < a.length && (x = a[i]) && x.oSrc; i++) x.src = x.oSrc;
        }
        function MM_preloadImages() { //v3.0
            var d = document;
            if (d.images) {
                if (!d.MM_p) d.MM_p = new Array();
                var i, j = d.MM_p.length, a = MM_preloadImages.arguments;
                for (i = 0; i < a.length; i++)
                    if (a[i].indexOf("#") != 0) {
                        d.MM_p[j] = new Image;
                        d.MM_p[j++].src = a[i];
                    }
            }
        }

        function MM_findObj(n, d) { //v4.01
            var p, i, x;
            if (!d) d = document;
            if ((p = n.indexOf("?")) > 0 && parent.frames.length) {
                d = parent.frames[n.substring(p + 1)].document;
                n = n.substring(0, p);
            }
            if (!(x = d[n]) && d.all) x = d.all[n];
            for (i = 0; !x && i < d.forms.length; i++) x = d.forms[i][n];
            for (i = 0; !x && d.layers && i < d.layers.length; i++) x = MM_findObj(n, d.layers[i].document);
            if (!x && d.getElementById) x = d.getElementById(n);
            return x;
        }
        function MM_swapImage() { //v3.0
            var i, j = 0, x, a = MM_swapImage.arguments;
            document.MM_sr = new Array;
            for (i = 0; i < (a.length - 2); i += 3)
                if ((x = MM_findObj(a[i])) != null) {
                    document.MM_sr[j++] = x;
                    if (!x.oSrc) x.oSrc = x.src;
                    x.src = a[i + 2];
                }
        }
        //-->
    </script>
    <!-- login popup initialization -->
    <script type="text/javascript">
        $(document).ready(function () {
            $(".loginpp").fancybox({
                maxWidth:450,
                maxHeight:350,
                fitToView:false,
                width:334,
                height:309,
                autoSize:false,
                closeClick:false,
                openEffect:'none',
                closeEffect:'none'
            });

            $(".prize_popup_btn").fancybox({
                maxWidth:640,
                maxHeight:420,
                fitToView:false,
                width:640,
                height:420,
                autoSize:false,
                closeClick:false,
                openEffect:'none',
                closeEffect:'none'
            });
        });
    </script>
</head>

<body onload="MM_preloadImages('${rc.contextPath}/img/but_clkhere02.png','${rc.contextPath}/img/but_login02.png','${rc.contextPath}/img/but_go02.png')">
<div class="wrapper">
    <!-- navigation bar -->
    <div class="navbar clearboth text_16">
        <a href="${rc.contextPath}/web/home" target="_top">home</a>
        <span class="navdiv">|</span>
        <a href="${rc.contextPath}/web/about" target="_top">about</a>
        <#if LOGGED_IN_USER?exists>
            <span class="navdiv">|</span>
            <a href="${rc.contextPath}/web/viewRewardsToConsumePoints">my points</a>
            <span class="navdiv">|</span>
            <a href="${rc.contextPath}/web/myinfo">my history</a>
            <span class="navdiv">|</span>
            <a href="${rc.contextPath}/web/logout">log out</a>
        <#else>
            <span class="navdiv">|</span>
            <a href="#inline" class="loginpp">log in</a>
        </#if>
    </div>

    <!-- messages -->
    <div class="nav_alert txtalign_lft text_14 textdrkred">${message!''}</div>

    <!-- container -->
<@tiles.insertAttribute name="main" ignore=true/>

    <!-- footer -->
    <div class="footertxt text_12">© 2012 mGage Loyal-Me<span class="navdiv">|</span><a href="${rc.contextPath}/web/terms" target="_top">Terms and conditions</a></div>
</div>
<!-- login popup -->
<div id="inline" style="display:none">
    <form id="loginForm" method="post" action="${rc.contextPath}/web/login" autocomplete="off" >
        <div class="hmconsolegrp">
            <table width="180" border="0" cellpadding="6" cellspacing="0">
                <tr>
                    <td width="168" height="45" align="left" valign="top"><input name="msisdn" type="text" autocomplete="off" class="hmconsolefld text_16" placeholder="username"/></td>
                </tr>
                <tr>
                    <td height="45" align="left" valign="top"><input name="password" type="password" autocomplete="off" class="hmconsolefld text_16" placeholder="password"/></td>
                </tr>
                <tr>
                    <td align="right" valign="top"><a href="javascript:;" onclick="document.getElementById('loginForm').submit();" target="_top" onmouseover="MM_swapImage('pplogin','','${rc.contextPath}/img/but_login02.png',1)" onmouseout="MM_swapImgRestore()"><img src="${rc.contextPath}/img/but_login01.png" alt="login" name="pplogin" width="120" height="49" border="0" id="pplogin" title="login"/></a></td>
                </tr>
            </table>
        </div>
    </form>
</div>
</body>
</html>