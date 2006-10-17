<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta name="maintopic" content="data">

</head>
<body>
             
<c:if test="${not empty ItemSearchForm.itemType}">  

<c:set var="type"            value="${ItemSearchForm.itemType}"/>
<c:set var="typeName"        value="${type.name}"/>
<c:set var="typeDescription" value="${type.description}"/>

<div id="pageTitle">
<bean-el:message key="pagetitles.itemsearchresult"/><c:out value="${typeDescription}"/>
</div>

<div id="itemListContainer">
<display-el:table   
  export="true" 
  sort="list" 
  pagesize="${ItemSearchForm.resultsPerPage}" 
  id="itemList" 
  name="${ItemSearchForm.resultList}"
  decorator="de.berlios.gpon.wui.displaytag.decorators.UniversalDecorator"
  requestURI="searchResult.do"
  >
    <display-el:column property="item.id"
                       title="Id" 
                       sortable="true"
                       />
    <display-el:column property="item.itemType.description"
                       title="Typ" 
                       sortable="true"
                       />                   
  <!-- F�r jedes Property eine Spalte -->             
  <c:forEach var="propDecl" items="${type.inheritedItemPropertyDecls}">
   <display-el:column sortable="true" 
                      property="${propDecl.id}" 
                      title="${propDecl.description}"
                      decorator="de.berlios.gpon.wui.displaytag.decorators.ValueColumnDecorator"
                      />
  </c:forEach>
  <!-- actions -->
  <display-el:column media="html" title="Aktionen">
   <a href="pre-edit.do?itemId=<c:out value="${itemList.id}"/>">edit</a>
   <a href="pre-delete.do?itemId=<c:out value="${itemList.id}"/>">delete</a>
   <a href="itemDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&itemId=<c:out value="${itemList.id}"/>" onclick="return popup(this);">detail</a>
   <a href="<%= request.getContextPath()%>/exploration/viewer.do?decorator=popup&confirm=true&objectId=<c:out value="${itemList.id}"/>" onclick="return popup(this,1200,800);">explore</a>
  </display-el:column>
  <display-el:footer>
   <tr>
      <bean-el:size id="propCount" collection="${type.inheritedItemPropertyDecls}" />
      <td class="rightAlignMe" 
          colspan="<c:out value="${propCount+2}"/>">Neues Objekt <c:out value="${typeDescription}"/>:</td>
  		<td><html-el:link action="/data/pre-create.do?itemTypeId=${type.id}">Anlegen</html-el:link></td>
  	</tr>
  </display-el:footer>
</display-el:table>
</div>
</c:if>
</body>
</html>
