<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<c:set var="urlPrefix" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}"/>
<!--  http://localhost:9080/wui/acb -->
<div style="margin-left:auto;margin-right:auto;">
<applet code="de/berlios/gpon/applets/GponGraphViewerApplet.class" 
        codebase="<c:out value="${urlPrefix}"/>/acb" 
                width="1000" height="700">
          <param name="textField" value="label"/>
          <param name="graphURL" value="<c:out value="${urlPrefix}"/>/exploration/environment.do?objectId=<c:out value="${param.objectId}"/>&radius=1"/>
          <param name="graphExplorationURL" value="<c:out value="${urlPrefix}"/>/exploration/environment.do?radius=1&objectId="/>
          <param name="detailURL" value="<c:out value="${urlPrefix}"/>/data/search/itemDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&itemId="/>
          <param name="configDetails" value="node.details=off node.typecolor=on"/>
          <param name="detailTarget" value="_blank"/>If you can read this text, the applet is not working. Perhaps you don't have the Java 1.4 (or later) web plug-in installed? 
</applet>
</div>

</body>
</html>