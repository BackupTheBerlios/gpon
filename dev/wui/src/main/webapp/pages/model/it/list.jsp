<%@ taglib uri="/sitemesh-page" prefix="page"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta name="maintopic" content="model">
<meta name="subtopic" content="items">
</head>
<body>
   
<display-el:table sort="list" pagesize="10" id="itemTypeList" 
               name="${GPON_MODEL_VIEW.allItemTypes}"
               requestURI="list.do"
               decorator="de.berlios.gpon.wui.displaytag.decorators.UniversalDecorator">
 <display-el:column  sortable="true" property="id" titleKey="itemtype.id.prompt" />
 <display-el:column sortable="true" property="name" titleKey="itemtype.name.prompt"/>
 <display-el:column sortable="true" property="description" titleKey="itemtype.description.prompt"/>
 <display-el:column sortable="true" property="baseType.name" titleKey="itemtype.baseitemtype.prompt"/>
 <!-- actions -->
 <display-el:column media="html" title="Aktionen">
   <a href="pre-edit.do?objectId=<c:out value="${itemTypeList.id}"/>">edit</a>
   <a href="pre-delete.do?objectId=<c:out value="${itemTypeList.id}"/>">delete</a>
   <a href="itemTypeDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&objectId=<c:out value="${itemTypeList.id}"/>" onclick="return popup(this);">detail</a>
 </display-el:column>
 <display-el:footer>
  	<tr>
      <td colspan="3"></td>
  		<td>Neuer Objekttyp:</td>
  		<td><a href="pre-new.do">Anlegen</a></td>
  	</tr>
  </display-el:footer>
</display-el:table>
</body>
</html>