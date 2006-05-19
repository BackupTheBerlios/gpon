<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<c:if test="${! empty GPON_MODEL_VIEW}">
 model view set.
</c:if>

<c:set var="allTypes" value="${GPON_MODEL_VIEW.allItemTypes}"/>

<display-el:table name="${allTypes}">
	<display-el:column property="id" />
	<display-el:column property="name" />
	<display-el:column property="description" />
</display-el:table>
Test
</body>
</html>
