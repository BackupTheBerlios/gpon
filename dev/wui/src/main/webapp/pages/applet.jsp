<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<!--  http://localhost:9080/wui/acb -->

<applet code="de/berlios/gpon/applets/GponGraphViewerApplet.class" 
        codebase="http://localhost:9080/wui/acb" 
                width="1000" height="700">
          <param name="textField" value="label"/>
          <param name="graphURL" value="http://localhost:9080/wui/exploration/environment.do?objectId=8&radius=20"/>
          <param name="detailURL" value="bla"/>
          <param name="configDetails" value="node.details=off node.typecolor=on"/>
          <param name="detailTarget" value="_blank"/>If you can read this text, the applet is not working. Perhaps you don't have the Java 1.4 (or later) web plug-in installed? 
</applet>

</body>
</html>