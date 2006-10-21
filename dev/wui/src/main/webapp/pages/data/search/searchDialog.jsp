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
<meta name="subtopic" content="search">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<html-el:form action="/data/search/pre-searchDialog.do">
<table class="searchForm">
  <tr>
  <td class="searchLabel">
  <bean-el:message key="itemtype"/>
  </td>
  <td class="searchOp">
   <c:set var="itemTypes" value="${GPON_MODEL_VIEW.allItemTypes}" scope="page"/>
    <html-el:select property="itemTypeId" onchange="this.form.submit();">
     <html-el:options collection="itemTypes" property="id" labelProperty="description"/>
    </html-el:select>
   </td>
   <td class="searchValue">   
   <html-el:submit  value="ausw&auml;hlen"/> 
   </td>
   </tr>
   </table>
</html-el:form>   

<c:if test="${!empty ItemSearchForm}">

  <jsp:useBean id="Operators" class="de.berlios.gpon.common.util.search.ComparisonOperators"/>
    <html-el:form action="/data/search/search.do">
    <table>
    <tr>
    <td>
      <table class="searchForm">
        <thead>
        <th class="searchLabel">
         Feld
        </th>
        <th class="searchOp">
          Operator
        </th>
        <th class="searchValue">
          Wert
        </th>
        </thead>
        <!-- Properties -->
        <c:forEach var="pDecl" items="${ItemSearchForm.itemType.inheritedItemPropertyDecls}">
          <tr>
           <td class="searchLabel" nowrap="nowrap">
              <c:out value="${pDecl.description}"/>
              <html-el:hidden property="criterion(${pDecl.name}).name" value="${pDecl.name}"/>
             <html-el:checkbox 
              styleClass="custom_view_selection" 
              property="displayProperty(${pDecl.id})" 
              value="${pDecl.id}"/>
            
            </td>
            <td class="searchOp">
              <c:set var="ops" value="${Operators.operators}"/>
              <html-el:select property="criterion(${pDecl.name}).op">
                 <logic-el:iterate id="op" collection="${ops}" indexId="i">
                  <html-el:option  value="${op.id}">
                    <bean-el:message key="${op.key}"/>
                  </html-el:option>
                 </logic-el:iterate>
                 
              </html-el:select>
              </td>
              <td class="searchValue"> 
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
      </td>
    <!-- right side -->
    <td>  
      <c:if test="${ItemSearchForm.pathsToParentsSize > 0}">
    <b>Anzeige von Attributen assoziierter Objekte:</b>
    </c:if>
    <table>
    <c:forEach items="${ItemSearchForm.pathsToParents}" var="path"> 
    <tr class="custom_view_selection">
    <td>
    <c:out value="${path.displayWithoutStart}"/>
    </td>
    <tr>
    <tr>
    <td>
    <c:forEach var="ipd" items="${path.end.inheritedItemPropertyDecls}">
     <html-el:checkbox styleClass="custom_view_selection" property="associatedProperty(${path.digest}|${ipd.id})" value="${ipd.description}" indexed=""/>
     <c:out value="${ipd.description}"/>&nbsp;
    </c:forEach>
    </td>
    </tr>
    </c:forEach>
    </table>
    </td>
    </tr>
    </table>
    </html-el:form>
</c:if>
</body>
</html>