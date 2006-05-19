<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>

<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <body>
  <!-- neuer Objekttyp -->
  <html-el:errors />
  <html-el:form action="model/it/new.do">
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
        <html-el:text property="name" titleKey="itemtype.name.prompt"/>
      </td>
    </tr>
    <tr class="even">
      <td>
        <bean-el:message  key="itemtype.description.prompt"/>
      </td>
      <td>
        <html-el:text property="description" titleKey="itemtype.description.prompt"/>
      </td>
    </tr>
    <tr class="odd">
      <td>
        <bean-el:message  key="itemtype.baseitemtype.prompt"/>
      </td>
      <td>
       <c:set var="itemTypes" value="${GPON_MODEL_VIEW.allItemTypes}" scope="page"/>
       <html-el:select value="" name="ItemTypeForm" property="baseItemTypeId">
        <html-el:option value="">-</html-el:option>
        <html-el:options collection="itemTypes" property="id" labelProperty="name"/>
       </html-el:select>
       
      </td>
    </tr>
 
 
    <tr>
      <td colspan="2" align="center">
        <html-el:submit  value="Anlegen"/>
      </td>
    </tr>
   </table>
  </html-el:form>
  </body>
</html>
