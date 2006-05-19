<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="/sitemesh-decorator" prefix="decorator"%>
<html>
	<head>
		<title><decorator:title default="{ Unknown Page - shouldn't see this, since pages should define title }" /></title>
		<link href="<%= request.getContextPath() %>/site-decorators/main.css" rel="stylesheet" type="text/css">
		<%--pulls the header from the page we are decorating and inserts it here --%>
        <decorator:head />
	</head>
    
	<body>
        <div id="popupDiv">
         <div id="popupTitle">
          <decorator:title default="Unknown Title - shouldn't see this" />
         </div>
         <div id="popupBody">   
            <%--inserts the body of whatever we are decorating here --%>
            <decorator:body />
         </div>   
        </div>
    </body>
</html>
