<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<%@ taglib uri="/sitemesh-page" prefix="page"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta name="maintopic" content="model">
<meta name="subtopic" content="associations">
</head>
<body>
<%--  Trick to acquire a session from jsp --%>
 
<display-el:table sort="list" pagesize="10" id="associationTypeList" 
               name="${GPON_MODEL_VIEW.allAssociationTypes}"
               decorator="de.berlios.gpon.wui.displaytag.decorators.UniversalDecorator">
 <display-el:column  sortable="true" property="id" titleKey="associationtype.id.prompt" />
 <display-el:column sortable="true" property="name" titleKey="associationtype.name.prompt"/>
 <display-el:column sortable="true" property="description" titleKey="associationtype.description.prompt"/>
 <display-el:column sortable="true" property="itemAType.name" titleKey="associationtype.itematype.prompt"/>
 <display-el:column sortable="true" property="itemARoleName" titleKey="associationtype.itemarolename.prompt"/>
 <display-el:column sortable="true" property="multiplicityLabel" titleKey="associationtype.multiplicity.prompt"/>
 <display-el:column sortable="true" property="itemBType.name" titleKey="associationtype.itembtype.prompt"/>
 <display-el:column sortable="true" property="itemBRoleName" titleKey="associationtype.itembrolename.prompt"/>
  <display-el:column media="html" property="viewAssociationTypeDetailLink" title="Detail"/>
 <display-el:column property="prepareEditAndDeleteTypeLink" title="Bearbeiten"/>
 <display-el:footer>
  	<tr>
      <td colspan="6"></td>
  		<td colspan="2">Neuer Objekttyp:</td>
  		<td><a href="pre-new.do">Anlegen</a></td>
  	</tr>
  </display-el:footer>
</display-el:table>
</body>
</html>