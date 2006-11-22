<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>

<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<meta name="maintopic" content="model">
<meta name="subtopic" content="associations">
</head>
  <body>

  <!-- neuer Objekttyp -->
  <html-el:errors />
  <html-el:form action="model/at/delete.do">
  <html-el:hidden property="id" name="selectedAssociationTypeForm"/>
   <table class="inputForm">
    <thead>
     <th>Feld</th>
     <th>Wert</th>
    </thead>
    <tr class="odd">
      <td>
        <bean-el:message  key="associationtype.name.prompt"/>
      </td>
      <td>
        <html-el:hidden name="selectedAssociationTypeForm" property="name" titleKey="associationtype.name.prompt"/>
        <html-el:text disabled="true" name="selectedAssociationTypeForm" property="name" titleKey="associationtype.name.prompt"/>
      </td>
    </tr>
    <tr class="even">
      <td>
        <bean-el:message  key="associationtype.description.prompt"/>
      </td>
      <td>
        <html-el:hidden name="selectedAssociationTypeForm" property="description" titleKey="associationtype.description.prompt"/>
        <html-el:text disabled="true" name="selectedAssociationTypeForm" property="description" titleKey="associationtype.description.prompt"/>
      </td>
    </tr>
    <tr>
      <td colspan="3">
        <table>
          <tr>
           <td colspan="2">Objekttyp A</td><td></td><td colspan="2">Objekttyp B</td>
          </tr>
          <tr>
           <td>
            Rolle:
           </td>
           <td>
            <!-- Item A Role -->
            <html-el:hidden name="selectedAssociationTypeForm" property="itemARoleName" titleKey="associationtype.itemarolename.prompt"/> 
            <html-el:text disabled="true" name="selectedAssociationTypeForm" property="itemARoleName" titleKey="associationtype.itemarolename.prompt"/> 
           </td>
           <td rowspan="2" valign="middle">
           <c:set var="multiplicityTypes" value="${GPON_MODEL_VIEW.allMultiplicities}" 
                  scope="page"/>
            <html-el:hidden  name="selectedAssociationTypeForm" property="multiplicity"/>      
            <html-el:select disabled="true" name="selectedAssociationTypeForm" property="multiplicity">
              <html-el:options collection="multiplicityTypes" property="name" labelProperty="label"/>
            </html-el:select>          
           </td>
           <td>
             Rolle:       
           </td>
           <td><!-- Item B Role -->
            <html-el:hidden name="selectedAssociationTypeForm" property="itemBRoleName" titleKey="associationtype.itembrolename.prompt"/>
            <html-el:text disabled="true" name="selectedAssociationTypeForm" property="itemBRoleName" titleKey="associationtype.itembrolename.prompt"/>
           </td>
          </tr>
          <tr>
           <td>
            Objekttyp:
           </td>
           <td>
            <!-- ItemType A --> 
            <c:set var="itemTypes" value="${GPON_MODEL_VIEW.allItemTypes}" scope="page"/>
            <html-el:hidden  name="selectedAssociationTypeForm" property="itemATypeId"/>
            <html-el:select disabled="true" name="selectedAssociationTypeForm" property="itemATypeId">
              <html-el:options collection="itemTypes" property="id" labelProperty="name"/>
            </html-el:select>          
           </td>
           <td>
            Objekttyp:
           </td>
           <td>
           
            <!-- ItemType B --> 
            <c:set var="itemTypes" value="${GPON_MODEL_VIEW.allItemTypes}" scope="page"/>
            <html-el:hidden name="selectedAssociationTypeForm" property="itemBTypeId"/>
            <html-el:select  disabled="true" name="selectedAssociationTypeForm" property="itemBTypeId">
              <html-el:options collection="itemTypes" property="id" labelProperty="name"/>
            </html-el:select>
           </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <html-el:submit><bean-el:message key="form.buttons.delete"/></html-el:submit>
      </td>
    </tr>
   </table>
  </html-el:form>
  </body>
</html>
