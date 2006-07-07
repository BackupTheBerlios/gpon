<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic-el" prefix="logic-el"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <c:set scope="request" var="pageTitle" value="Assoziationstyp: ${selectedAssociationTypeForm.description}"/>
  <body>
  <html:hidden property="id" name="selectedAssociationTypeForm"/>
    <table class="inputForm">
      <tr class="odd">
        <td>
          <table>
            <thead>
              <th>
                <bean-el:message key="associationtype.name.prompt"/>
              </th>
              <th>
                <bean-el:message key="associationtype.description.prompt"/>
              </th>
            </thead>
            <tr>
              <td>
                <c:out value="${selectedAssociationTypeForm.name}"/>
              </td>
              <td>
                <c:out value="${selectedAssociationTypeForm.description}"/>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr>
        <td colspan="3">
          <table>
            <thead>
              <th colspan="2">Objekttyp A</th>
              <th/>
              <th colspan="2">Objekttyp B</th>
            </thead>
            <tr class="even">
              <td>Rolle:</td>
              <td>
                <!-- Item A Role -->
                <c:out value="${selectedAssociationTypeForm.itemARoleName}"/>
              </td>
              <!-- Visibility -->
              <td rowspan="2" valign="middle">Mult.:
                <br/>
                <bean-el:message key="associations.multiplicity.${selectedAssociationTypeForm.multiplicity}"/>
                <br/>sichtbar:
                <br/>
                <bean-el:message key="associations.visibility.${selectedAssociationTypeForm.visibility}"/>
              </td>
              <td>Rolle:</td>
              <td>
                <!-- Item B Role -->
                <c:out value="${selectedAssociationTypeForm.itemBRoleName}"/>
              </td>
            </tr>
            <tr class="odd">
              <td>Objekttyp:</td>
              <td>
                <!-- ItemType A -->
                <A HREF="../it/itemTypeDetails.do?objectId=<c:out value="${selectedAssociationTypeForm.itemATypeId}&decorator=popup&confirm=true"/>">
                  <c:out value="${selectedAssociationType.itemAType.description}"/>
                </A>
              </td>
              <td>Objekttyp:</td>
              <td>
                <!-- ItemType B -->
                <A HREF="../it/itemTypeDetails.do?objectId=<c:out value="${selectedAssociationTypeForm.itemBTypeId}&decorator=popup&confirm=true"/>">
                  <c:out value="${selectedAssociationType.itemBType.description}"/>
                </A>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </body>
</html>
