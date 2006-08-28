<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic-el" prefix="logic-el"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta name="maintopic" content="data">
</head>
  <body>
<c:if test="${!empty ItemForm}">
	<html-el:form action="/data/delete.do">
		<table>
			<!-- Properties -->
			<c:forEach var="pDecl"
				items="${ItemForm.item.itemType.inheritedItemPropertyDecls}">
				<tr>
					<td><c:out value="${pDecl.description}" /></td>
				    <td><html-el:text disabled="true" property="property(${pDecl.name}).value" /></td>
				</tr>
			</c:forEach>
			<tr>
			  <td><html-el:submit value="L&ouml;schen" /></td>
			</tr>
		</table>
	</html-el:form>
</c:if>
  </body>
</html>

