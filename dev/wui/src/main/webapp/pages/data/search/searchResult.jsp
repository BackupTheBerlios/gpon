<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html-el:html locale="true">
<head>
<meta name="maintopic" content="data">

</head>
<body>

<!--  Fixed Toggle -->
<c:if test="${! empty param.toggleFixed}">
 <c:choose>
  <c:when test="${empty SEARCHRESULT_FIXED_LAYOUT}">
   <c:set var="SEARCHRESULT_FIXED_LAYOUT" value="_FIXED" scope="session"/>
  </c:when>
  <c:otherwise>
   <c:remove var="SEARCHRESULT_FIXED_LAYOUT" scope="session"/> 
  </c:otherwise>
 </c:choose>
</c:if>
             
<c:if test="${not empty ItemSearchForm.itemType}">  

<c:set var="type"            value="${ItemSearchForm.itemType}"/>
<c:set var="typeName"        value="${type.name}"/>
<c:set var="typeDescription" value="${type.description}"/>

<div id="pageTitle">
<bean-el:message key="pagetitles.itemsearchresult"/><c:out value="${typeDescription}"/>
</div>

<div id="itemListContainer<c:out value="${SEARCHRESULT_FIXED_LAYOUT}"/>">
<a href="searchResult.do?toggleFixed=toggle">toggle fixed table layout <c:out value="${SEARCHRESULT_FIXED_LAYOUT}"/></a>
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
                       titleKey="page.labels.result.id" 
                       sortable="true"
                       />
    <display-el:column property="item.itemType.description"
                       titleKey="page.labels.result.type" 
                       sortable="true"
                       />                   
  <!-- Für jedes Property eine Spalte -->             
  <c:forEach var="propDecl" items="${type.inheritedItemPropertyDecls}">
   <c:if test="${!empty ItemSearchForm.display[propDecl.id]}">
   <display-el:column sortable="true" 
                      property="${propDecl.id}" 
                      title="${propDecl.description}"
                      decorator="de.berlios.gpon.wui.displaytag.decorators.ValueColumnDecorator"
                      />
   </c:if>                   
  </c:forEach>
  <c:forEach var="associatedPropertyKey" items="${ItemSearchForm.associatedPropertyKeys}">
   <c:set var="colheader">
   <img src="<%=request.getContextPath()%>/img/path_2.gif" alt="<c:out value="${ItemSearchForm.pathDisplayForAssociatedPropertyKey[associatedPropertyKey]}"/>">
    <c:out value="${ItemSearchForm.associatedPropertyMap[associatedPropertyKey]}"/>
   </c:set>
   <display-el:column sortable="true" property="${associatedPropertyKey}" title="${colheader}" 
   						decorator="de.berlios.gpon.wui.displaytag.decorators.ValueColumnDecorator"/>

  </c:forEach>
  <!-- actions -->
  <display-el:column media="html" titleKey="page.labels.result.actions">
   <a href="pre-edit.do?itemId=<c:out value="${itemList.id}"/>"><bean-el:message key="page.labels.result.actions.change"/></a>
   <a href="pre-delete.do?itemId=<c:out value="${itemList.id}"/>"><bean-el:message key="page.labels.result.actions.delete"/></a>
   <a href="itemDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&itemId=<c:out value="${itemList.id}"/>" onclick="return popup(this);"><bean-el:message key="page.labels.result.actions.details"/></a>
   <a href="<%= request.getContextPath()%>/exploration/viewer.do?decorator=popup&confirm=true&objectId=<c:out value="${itemList.id}"/>" onclick="return popup(this,1200,800);"><bean-el:message key="page.labels.result.actions.explore"/></a>
  </display-el:column>
  <display-el:footer>
   <tr>
      <td class="rightAlignMe" 
          colspan="<c:out value="${ItemSearchForm.displayCount+2}"/>"><bean-el:message key="page.labels.result.create_new_item" arg0="${typeDescription}"/>:</td>
  		<td><html-el:link action="/data/pre-create.do?itemTypeId=${type.id}"><bean-el:message key="page.labels.result.actions.create"/></html-el:link></td>
  	</tr>
  </display-el:footer>
</display-el:table>
</div>
</c:if>
</body>
</html-el:html>
