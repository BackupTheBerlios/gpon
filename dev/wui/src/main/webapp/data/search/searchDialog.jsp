<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>    
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic-el" prefix="logic-el"%>    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<html-el:form action="/data/search/pre-searchDialog.do">
   <c:set var="itemTypes" value="${GPON_MODEL_VIEW.allItemTypes}" scope="page"/>
    <html-el:select property="itemTypeId">
     <html-el:options collection="itemTypes" property="id" labelProperty="description"/>
    </html-el:select>  
   <html-el:submit  value="ausw&auml;hlen"/> 
</html-el:form>   

<c:if test="${!empty ItemSearchForm}">

  <jsp:useBean id="Operators" class="de.berlios.gpon.common.util.search.ComparisonOperators"/>
    <html-el:form action="/data/search/search.do">
      <table>
        <!-- Properties -->
        <c:forEach var="pDecl" items="${ItemSearchForm.itemType.inheritedItemPropertyDecls}">
          <tr>
            <td>
              <c:out value="${pDecl.description}"/>
              <html-el:hidden property="criterion(${pDecl.name}).name" value="${pDecl.name}"/>
            </td>
            <td>
              <c:set var="ops" value="${Operators.operators}"/>
              <html-el:select property="criterion(${pDecl.name}).op">
                 <logic-el:iterate id="op" collection="${ops}" indexId="i">
                  <html-el:option  value="${op.id}">
                    <bean-el:message key="${op.key}"/>
                  </html-el:option>
                 </logic-el:iterate>
                 
              </html-el:select> 
               <html-el:text property="criterion(${pDecl.name}).value"/>
            </td>
          </tr>
        </c:forEach>
        <tr>
          <td>
           <html-el:select property="resultsPerPage">
             <c:forEach var="rpp" begin="20" end="100" step="20">
              <html-el:option  value="${rpp}"><c:out value="${rpp}"/>
              </html-el:option>
             </c:forEach> 
           </html-el:select>
          </td>
          <td>
            <html-el:submit value="Suchen"/>
          </td>
        </tr>
      </table>
    </html-el:form>
</c:if>


</body>
</html>