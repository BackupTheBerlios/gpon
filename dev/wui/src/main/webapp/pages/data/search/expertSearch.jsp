<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>    
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic-el" prefix="logic-el"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="maintopic" content="data">
<meta name="subtopic" content="expertsearch">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<html-el:form action="/data/search/expert-search.do" method="GET">
<html-el:textarea property="queryText" cols="120" rows="20">Bitte Query eingeben!</html-el:textarea>
<html-el:submit>
<bean-el:message key="form.buttons.query"/>
</html-el:submit>
</html-el:form>
</body>
</html>