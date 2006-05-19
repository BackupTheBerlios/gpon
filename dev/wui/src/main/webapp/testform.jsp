<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<html-el:form action="test/form.do">
  <c:set var="pName" value="id"/>
  id: <html-el:text property="prop(${pName}).value"/><br>
  <c:set var="pName" value="human.identity.name.sur"/>
  name: <html-el:text property="prop(${pName}).value"/><br>
  <c:set var="pName" value="human.identity.name.first"/>
  firstname: <html-el:text property="prop(${pName}).value"/><br>
  <html-el:submit value="ok"/>
</html-el:form>

</body>
</html>