<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta name="maintopic" content="data">
<meta name="subtopic" content="expertsearch">
</head>
<body>
             
<c:if test="${not empty ExpertItemSearchForm.itemType}">  

<c:set var="type"            value="${ExpertItemSearchForm.itemType}"/>
<c:set var="typeName"        value="${type.name}"/>
<c:set var="typeDescription" value="${type.description}"/>

<div id="listTitle">
<bean-el:message key="pagetitles.itemsearchresult"/><c:out value="${typeDescription}"/>
</div>

<div id="itemListContainer">
<display-el:table   export="true" sort="list" pagesize="20" id="itemList" 
               name="${ExpertItemSearchForm.resultList}"
               decorator="de.berlios.gpon.wui.displaytag.decorators.UniversalDecorator">
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
  <!-- Bearbeiten -->
  <display-el:column media="html" property="prepareEditAndDeleteLink" title="Bearbeiten"/>
  <display-el:column media="html" property="viewDetailLink" title="Detail"/>
  <display-el:footer>
   <tr>
      <bean-el:size id="propCount" collection="${type.inheritedItemPropertyDecls}" />
      <td class="rightAlignMe" 
          colspan="<c:out value="${propCount+3}"/>">Neues Objekt <c:out value="${typeDescription}"/>:</td>
  		<td><html-el:link action="/data/pre-create.do?itemTypeId=${type.id}">Anlegen</html-el:link></td>
  	</tr>
  </display-el:footer>
</display-el:table>
</div>
</c:if>
</body>
</html>
