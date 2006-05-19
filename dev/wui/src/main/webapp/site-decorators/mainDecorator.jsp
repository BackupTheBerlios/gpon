<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib prefix="decorator" uri="/sitemesh-decorator" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean-el" %>
<html>
	<head>
		<title><decorator:title default="{ Unknown Page - shouldn't see this, since pages should define title }" /></title>
		<link 	href="<%= request.getContextPath() %>/site-decorators/main.css" 
				rel="stylesheet" type="text/css">
		
		<link 	rel="stylesheet" type="text/css" media="screen" 
        		href="<%= request.getContextPath() %>/css/tabs.css" />    
        		

    <script type="text/javascript" src="<%= request.getContextPath() %>/scripts/tabs.js"></script>
		<%--pulls the header from the page we are decorating and inserts it here --%>
        <decorator:head />
	</head>
    
	<body>
	    <div id="gponContainer">
      <div id="gponHeaderContainer">
        <div id="gponHeader">
	  <div id="gponHeaderLogoLeft">
            <img src="<%= request.getContextPath() %>/img/hdr_left.png" alt="General Purpose Object Network">
          </div>
          <div id="gponHeaderLogoRight">
            <img src="<%= request.getContextPath() %>/img/hdr_right.png" alt="gpon">
          </div>
        </div>
        <div id="gponTopNav">
          <div id="gponTopNavLeft">
            <img src="<%= request.getContextPath() %>/img/nav_lft.png" alt="" height="23" width="7">
          </div>
          <div id="gponTopNavRight">
            <img src="<%= request.getContextPath() %>/img/nav_rgt.png" alt="" height="23" width="7">
          </div>
          <ul id="gponTopNavList">
  <li>
    <a href="<%= request.getContextPath() %>/data/search/intro.do" title="Data">Data</a>
  </li>
  <li>
    <a href="<%= request.getContextPath() %>/model/intro.do" title="Model">Model</a>
  </li>
  <li>
    <a href="<%= request.getContextPath() %>/index.jsp" title="Info">Info</a>
  </li>
</ul>

        </div>
      </div>
    <div id="content">
     <%--pulls the body from the page we are decorating and inserts it here --%>
     <decorator:body />
    </div> 
    </div>
	</body>
</html>
