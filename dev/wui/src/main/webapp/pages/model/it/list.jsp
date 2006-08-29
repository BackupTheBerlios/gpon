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
               decorator="de.berlios.gpon.wui.displaytag.decorators.UniversalDecorator">
 <display-el:column  sortable="true" property="id" titleKey="itemtype.id.prompt" />
 <display-el:column sortable="true" property="name" titleKey="itemtype.name.prompt"/>
 <display-el:column sortable="true" property="description" titleKey="itemtype.description.prompt"/>
 <display-el:column sortable="true" property="baseType.name" titleKey="itemtype.baseitemtype.prompt"/>
 <display-el:column property="prepareEditAndDeleteTypeLink" title="Bearbeiten"/>
 <display-el:column media="html" property="viewTypeDetailLink" title="Detail"/>
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