<#assign tiles=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<@tiles.insertTemplate template="/WEB-INF/layouts/mainLayout.ftl">
    <@tiles.putAttribute name="main">

    <div class="clearboth">
        <!-- left content box -->
        <div class="hmleftbox floatleft">
            <table  border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td width="87"><img src="${rc.contextPath}/img/hmpic_01.png" width="87" height="390"/></td>
                    <td width="134"><img src="${rc.contextPath}/img/hmpic_02.jpg" width="134" height="390"/></td>
                    <td width="189"><img src="${rc.contextPath}/img/hmpic_03.jpg" width="189" height="390"/></td>
                    <td width="126"><img src="${rc.contextPath}/img/hmpic_04.jpg" width="126" height="390"/></td>
                    <td width="88"><img src="${rc.contextPath}/img/hmpic_05.png" width="88" height="390"/></td>
                </tr>
                <tr>
                    <td><img src="${rc.contextPath}/img/hmpic_06.png" width="87" height="137"/></td>
                    <td><img src="${rc.contextPath}/img/hmpic_07.png" width="134" height="137"/></td>
                    <td></td>
                    <td><img src="${rc.contextPath}/img/hmpic_08.png" width="126" height="137"/></td>
                    <td><img src="${rc.contextPath}/img/hmpic_09.png" width="88" height="137"/></td>
                </tr>
            </table>
        </div>
        <!-- right content box -->
        <div class="hmrightbox floatright">
            <div class="hmtxt_welcome"><img src="${rc.contextPath}/img/hmtxt_welcome.png" alt="Welcome to Spend &amp; Collect" width="392" height="87" border="0" title="Welcome to Spend &amp; Collect"/></div>
            <div class="hmconsole uncollapse">
                <div class="hmconsolegrp">
                    <form id="redeemForm" action="${rc.contextPath}/web/resumeConversation" autocomplete="off" >
                        <table width="180" border="0" cellpadding="6" cellspacing="0">
                            <tr>
                                <td width="168" height="45" align="left" valign="top"><input name="msisdn" type="text" autocomplete="off"  class="hmconsolefld text_16" placeholder="MSISDN"/></td>
                            </tr>
                            <tr>
                                <td height="45" align="left" valign="top"><input name="code" type="text" autocomplete="off"  class="hmconsolefld text_16" placeholder="Coupon / Code"/></td>
                            </tr>
                            <tr>
                                <td align="right" valign="top"><a href="javascript:;" onclick="document.getElementById('redeemForm').submit();" onmouseover="MM_swapImage('hmbtgo','','${rc.contextPath}/img/but_go02.png',1)" onmouseout="MM_swapImgRestore()"><img src="${rc.contextPath}/img/but_go01.png" alt="go" name="hmbtgo" width="85" height="48" border="0" id="hmbtgo" title="go"/></a></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="clearboth"><img src="${rc.contextPath}/img/hmbar_horz.png" width="957" height="3"/></div>



    <div class="hmbotbuttons">
        <div class="hmbtleft floatleft">
            <!-- QR code div -->
            <div class="qrcode floatleft"><img src="${rc.contextPath}/img/qrcode_app.png" alt="Check our mobile app!" width="207" height="192" border="0" title="Check our mobile app!" /></div>
            <!-- end of QR code div -->
            <img src="${rc.contextPath}/img/hmtxt_knowmore.png" alt="want to know more?" width="200" height="58" border="0" class="hmbtmarglft" title="want to know more?"/><br/>
            <a href="${rc.contextPath}/web/about" target="_top" onmouseover="MM_swapImage('hmclkhere','','${rc.contextPath}/img/but_clkhere02.png',1)" onmouseout="MM_swapImgRestore()">
                <img src="${rc.contextPath}/img/but_clkhere01.png" alt="click here" name="hmclkhere" width="204" height="48" border="0" id="hmclkhere" title="click here"/>
            </a>
        </div>
        <div class="hmbtright floatright">
            <img src="${rc.contextPath}/img/hmtxt_lostcode.png" alt="want just to collect? lost your code?" width="314" height="64" border="0" class="hmbtmargrgt" title="want just to collect? lost your code?"/><br/>
            <div class="floatleft">
                <a href="#inline" class="loginpp" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('hmlogin','','${rc.contextPath}/img/but_login02.png',1)">
                    <img src="${rc.contextPath}/img/but_login01.png" alt="login" name="hmlogin" width="120" height="49" border="0" id="hmlogin" title="login"/>
                </a>
            </div>
            <div class="floatleft text_14 margl_5">don’t have a password?<br/><a href="javascript:;" target="_top">sign up here</a></div>
        </div>
    </div>


    </@tiles.putAttribute>
</@tiles.insertTemplate>







