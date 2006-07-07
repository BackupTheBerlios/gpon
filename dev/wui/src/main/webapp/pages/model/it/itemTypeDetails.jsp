<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic-el" prefix="logic-el"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <body>
    <!-- Wertetyp &auml;ndern-->
    <table class="inputForm">
      <tr class="odd">
        <td>Kopfdaten</td>
        <td>
          <table>
            <thead>
             <th><bean-el:message key="itemtype.name.prompt"/></th>
             <th><bean-el:message key="itemtype.description.prompt"/></th>
             <th><bean-el:message key="itemtype.baseitemtype.prompt"/></th>
            </thead>
            <tr class="even">
              <td>
                <c:out value="${selectedItemType.name}"/>
              </td>
              <td>
                <c:out value="${selectedItemType.description}"/>
              </td>
              <td>
                <c:if test="${selectedItemType.baseType.description}">
                <A HREF="itemTypeDetails.do?objectId=<c:out value="${selectedItemType.baseType.id}&decorator=popup&confirm=true"/>">
                  <c:out value="${selectedItemType.baseType.description}"/>
                </A>
                </c:if>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <tr class="even">
        <td>Attribute</td>
        <td colspan="2">
          <table>
            <thead>
              <th>Rang</th>
              <th>Name</th>
              <th>Beschreibung</th>
              <th>Wertetyp</th>
              <th>Pflichtfeld</th>
            </thead>
            <logic-el:iterate name="selectedItemTypeForm" property="propertyDecl" id="propertyDeclItem" indexId="ctr">
              <!-- Ist es eine geerbte Eigenschaft ? -->
              <tr class="mod2_<c:out value="${ctr%2}"/>">
                <!-- Rang -->
                <td>
                  <c:out value="${propertyDeclItem.rank}"/>
                </td>
                <!-- Name der Eigenschaft -->
                <td>
                  <c:out value="${propertyDeclItem.name}"/>
                </td>
                <!-- Beschreibung -->
                <td>
                  <c:out value="${propertyDeclItem.description}"/>
                </td>
                <!-- Typ -->
                <td>
                  <c:out value="${propertyValueTypeView.byId[propertyDeclItem.propertyValueTypeId].description}"/>
                </td>
                <!-- Pflicht ? -->
                <td>
                  <c:if test="${propertyDeclItem.mandatory}">ja</c:if>
                </td>
              </tr>
            </logic-el:iterate>
          </table>
        </td>
      </tr>
      <tr/>
      <tr class="odd">
        <td>Assoziationen:</td>
        <td>
          <c:if test="${selectedItemType.associated}">
            <table>
              <thead>
                <th>Beschreibung</th>
                <th>Name</th>
                <th>in Rolle</th>
                <th>als Typ</th>
                <th>zu</th>
                <th>Kardinalit&auml;t</th>
              </thead>
              <tbody>
                <c:set var="idx" value="0"/>
                <c:forEach var="associationType" 
                           items="${selectedItemType.inheritedAssociationTypesA}">
                      <c:set var="idx" value="${idx+1}"/>
                      <tr class="mod2_<c:out value="${idx%2}"/>">
                        <td>
                          <A HREF="../at/associationTypeDetails.do?objectId=<c:out value="${associationType.id}&decorator=popup&confirm=true"/>">
                            <c:out value="${associationType.description}"/>
                          </A>
                        </td>
                        <td>
                          <c:out value="${associationType.name}"/>
                        </td>
                        <td>
                          <c:out value="${associationType.itemARoleName}"/>
                        </td>
                        <td>
                          <A HREF="itemTypeDetails.do?objectId=<c:out value="${associationType.itemAType.id}&decorator=popup&confirm=true"/>">
                            <c:out value="${associationType.itemAType.description}"/>
                          </A>
                        </td>
                        <td>
                          <A HREF="itemTypeDetails.do?objectId=<c:out value="${associationType.itemBType.id}&decorator=popup&confirm=true"/>">
                            <c:out value="${associationType.itemBType.description}"/>
                          </A>
                        </td>
                        <td>
                          <bean-el:message key="associations.multiplicity.${associationType.multiplicity}"/>
                        </td>
                      </tr>
                    </c:forEach>

                <c:forEach var="associationType" 
                           items="${selectedItemType.inheritedAssociationTypesB}">
                    <!-- on B side (both sides are possible) -->
                      <c:set var="idx" value="${idx+1}"/>
                      <tr class="mod2_<c:out value="${idx%2}"/>">
                        <td>
                          <A HREF="../at/associationTypeDetails.do?objectId=<c:out value="${associationType.id}&decorator=popup&confirm=true"/>">
                            <c:out value="${associationType.description}"/>
                          </A>
                        </td>
                        <td>
                          <c:out value="${associationType.name}"/>
                        </td>
                        <td>
                          <c:out value="${associationType.itemBRoleName}"/>
                        </td>
                        <td>
                          <A HREF="itemTypeDetails.do?objectId=<c:out value="${associationType.itemBType.id}&decorator=popup&confirm=true"/>">
                            <c:out value="${associationType.itemBType.description}"/>
                          </A>
                        </td>
                        <td>
                          <A HREF="itemTypeDetails.do?objectId=<c:out value="${associationType.itemAType.id}&decorator=popup&confirm=true"/>">
                            <c:out value="${associationType.itemAType.description}"/>
                          </A>
                        </td>
                        <td>
                          <bean-el:message key="associations.multiplicity.${associationType.multiplicity}Reversed"/>
                        </td>
                      </tr>
                      <%-- <c:out value="${associationType}"/><br> --%>

                </c:forEach>
              </tbody>
            </table>
          </c:if>
        </td>
      </tr>
    </table>
  </body>
</html>
