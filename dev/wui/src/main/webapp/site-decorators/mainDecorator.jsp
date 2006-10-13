<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="decorator" uri="/sitemesh-decorator" %>
<%@ taglib prefix="page" uri="/sitemesh-page" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean-el" %>
<html>
	<head>
		<title><decorator:title default="{ Unknown Page - shouldn't see this, since pages should define title }" /></title>
		<link 	href="<%= request.getContextPath() %>/site-decorators/main.css" 
				rel="stylesheet" type="text/css">
        <decorator:head />
        <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/util.js"></script>
	</head>
	<body>
	  <div id="title">
  </div>
  <!--  read out META tags and display current navigation menu -->
  <c:set var="maintopic" scope="request">
  <decorator:getProperty property="meta.maintopic"/>
  </c:set>
  <c:set var="subtopic" scope="request">
  <decorator:getProperty property="meta.subtopic"/>
  </c:set>
  <jsp:include page="navigation.jsp"/>
  <div id="main">
	 <decorator:body />
  </div>
 </body>
</html>
