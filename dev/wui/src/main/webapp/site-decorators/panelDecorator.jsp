<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="decorator" uri="/sitemesh-decorator" %>
<decorator:head />
<div class="panelDiv">
    <span class="panelTitle"><decorator:title default="Unknown panel" /></span>
    </ br>
    <decorator:getProperty property="page.childrenlist"/>
    <%--inserts the body of whatever we are decorating here --%>
    <decorator:body />     
</div>
