<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta name="maintopic" content="model">
<meta name="subtopic" content="items">
</head>
<body>
  <!-- Wertetyp ändern-->
  <html-el:errors />
  
  <html-el:form action="/model/it/delete.do">
  <html-el:hidden name="selectedItemTypeForm" property="id"/>
   <table class="inputForm">
    <thead>
     <th>Feld</th>
     <th>Wert</th>
    </thead>
    <tr class="odd">
      <td>
        <bean-el:message  key="itemtype.name.prompt"/>
      </td>
      <td>
        <html-el:hidden name="selectedItemTypeForm" property="name"/>
        <html-el:text disabled="true" name="selectedItemTypeForm" property="name" titleKey="itemtype.name.prompt"/>
      </td>
    </tr>
    <tr class="even">
      <td>
        <bean-el:message  key="itemtype.description.prompt"/>
      </td>
      <td>
        <html-el:hidden name="selectedItemTypeForm" property="description"/>
        <html-el:text disabled="true" name="selectedItemTypeForm"  property="description" titleKey="itemtype.description.prompt"/>
      </td>
    </tr>
    <tr class="odd">
      <td>
        <bean-el:message  key="itemtype.baseitemtype.prompt"/>
      </td>
      <td>
       <c:set var="itemTypes" value="${GPON_MODEL_VIEW.allItemTypes}" scope="page"/>
       <html-el:select disabled="true" name="selectedItemTypeForm"  property="baseItemTypeId">
        <html-el:options  collection="itemTypes" property="id" labelProperty="name"/>
       </html-el:select>
       
      </td>
    </tr>
 
 
    <tr>
      <td colspan="2" align="center">
        <html-el:submit>
        <bean-el:message key="form.buttons.delete"/>
        </html-el:submit>
        <html-el:button  property="cancel" onclick="history.back()">
        <bean-el:message key="form.buttons.cancel"/>
        </html-el:button>
      </td>
    </tr>
   </table>
  </html-el:form>
  </body>
</html>