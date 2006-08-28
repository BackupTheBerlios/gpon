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
  <html-el:form action="model/at/edit.do">
  <html-el:hidden property="id" name="selectedAssociationTypeForm"/>
   <table class="inputForm">
    <thead>
     <tr>
     <th>Feld</th>
     <th>Wert</th>
     </tr>
    </thead>
    <tr class="odd">
      <td>
        <bean-el:message  key="associationtype.name.prompt"/>
      </td>
      <td>
        <html-el:text name="selectedAssociationTypeForm" 
        			  property="name" 
        			  titleKey="associationtype.name.prompt"/>
      </td>
    </tr>
    <tr class="even">
      <td>
        <bean-el:message  key="associationtype.description.prompt"/>
      </td>
      <td>
        <html-el:text name="selectedAssociationTypeForm" 
        			  property="description" 
        			  titleKey="associationtype.description.prompt"/>
      </td>
    </tr>
    <tr class="odd">
			<td><bean-el:message key="associationtype.predicates.prompt" /></td>
			<td><html-el:text name="selectedAssociationTypeForm"  
				property="predicates"
				titleKey="associationtype.predicates.prompt" /></td>
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
            <html-el:text name="selectedAssociationTypeForm" 
            			  property="itemARoleName" 
            			  titleKey="associationtype.itemarolename.prompt"/> 
           </td>
           <!-- Visibility -->
           <td rowspan="3" valign="middle">
           <c:set var="multiplicityTypes" 
           		  value="${GPON_MODEL_VIEW.allMultiplicities}" 
                  scope="page"/>
            <html-el:hidden  
            		name="selectedAssociationTypeForm" 
            		property="multiplicity"/>      
            <html-el:select disabled="true" 
            				name="selectedAssociationTypeForm" 
            				property="multiplicity">
              <html-el:options collection="multiplicityTypes" property="name" labelProperty="label"/>
            </html-el:select>   
            <br>
            <c:set var="visibilityTypes" value="${GPON_MODEL_VIEW.allVisibilities}" 
                  scope="page"/>
            <html-el:select  name="selectedAssociationTypeForm" property="visibility">
              <html-el:options collection="visibilityTypes" property="name" labelProperty="label"/>
            </html-el:select>    
           </td>
           <td>
             Rolle:       
           </td>
           <td><!-- Item B Role --> 
            <html-el:text name="selectedAssociationTypeForm" property="itemBRoleName" titleKey="associationtype.itembrolename.prompt"/>
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
          <tr>
		<td><bean-el:message key="associationtype.predicatesA.prompt" /></td>
		<td><html-el:text property="predicatesA" name="selectedAssociationTypeForm"
					titleKey="associationtype.predicatesA.prompt" /></td>
		<td><bean-el:message key="associationtype.predicatesB.prompt" /></td>
		<td><html-el:text property="predicatesB" name="selectedAssociationTypeForm"
						titleKey="associationtype.predicatesB.prompt" /></td>
				</tr>
    <tr>
        </table>
      </td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <html-el:submit  value="&Auml;ndern"/>
      </td>
    </tr>
   </table>
  </html-el:form>
  </body>
</html>
