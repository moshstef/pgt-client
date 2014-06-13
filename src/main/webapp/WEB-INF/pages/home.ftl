<#assign tiles=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]>
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]>

<@tiles.insertTemplate template="/WEB-INF/layouts/mainLayout.ftl">
    <@tiles.putAttribute name="main">

    This is the home page


    </@tiles.putAttribute>
</@tiles.insertTemplate>







