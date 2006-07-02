<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic-el" prefix="logic-el"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el" prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el" prefix="html-el"%>

<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <body>
  <%--  Trick to acquire a session from jsp --%>
  <% request.getSession(); %>
  <html-el:errors />

  <!-- Wertetyp ändern-->
   <html-el:form  
      action="model/it/edit.do">
   <html-el:hidden  name="selectedItemTypeForm" property="id"/>
   <table class="inputForm">
    <tr class="odd">
      <td rowspan="3">
        Kopfdaten
      </td>
      <td>
        <bean-el:message  key="itemtype.name.prompt"/>
      </td>
      <td>
        <html-el:text name="selectedItemTypeForm" property="name" titleKey="itemtype.name.prompt"/>
      </td>
    </tr>
    <tr class="even">
      <td>
        <bean-el:message  key="itemtype.description.prompt"/>
      </td>
      <td>
        <html-el:text name="selectedItemTypeForm" property="description" titleKey="itemtype.description.prompt"/>
      </td>
    </tr>
    <tr class="odd">
      <td>
        <bean-el:message  key="itemtype.baseitemtype.prompt"/>
      </td>
      <td>
       <c:set var="itemTypes" value="${GPON_MODEL_VIEW.allItemTypes}" scope="page"/>
       <html-el:select name="selectedItemTypeForm" property="baseItemTypeId">
        <html-el:option value="" key="label.option.unknown"/>
        <html-el:options collection="itemTypes" property="id" labelProperty="name"/>
       </html-el:select>
      </td>
    </tr>
   <tr>
   
   </tr>
   <tr>
     <td>
      Attribute
     </td>
     <td colspan=2>
       <table>
       <thead>
       <th>Rang</th>
       <th>Name</th>
       <th>Beschreibung</th>
       <th>Wertetyp</th>
       <th>Pflichtfeld</th>
       <th>Typisch</th>
       <th>L&ouml;schen?</th>
       </thead>
       
       <logic-el:iterate
              name="selectedItemTypeForm"
              property="propertyDecl" 
              id="propertyDeclItem"
              indexId="ctr">
        <!-- Ist es eine geerbte Eigenschaft ? -->      
        
        <c:set var="disabled" value="${selectedItemTypeForm.id != propertyDeclItem.itemType.id}"/>
        <tr>
        <!-- Rang -->
        <td>
         <html-el:select disabled="${disabled}"  
                         name="selectedItemTypeForm" 
                         property="propertyDecl[${ctr}].rank">
           <html-el:option value="" key="label.option.unknown"/>
           <c:forEach var="rankNo" begin="1" end="100">
            <html-el:option value="${rankNo}"><c:out value="${rankNo}"/></html-el:option>
           </c:forEach>
          </html-el:select>        
        </td>
         <!-- Name der Eigenschaft -->
         <td>
          <!-- id der Eigenschaft -->
          <c:if test="${not disabled}">
           <html-el:hidden name="selectedItemTypeForm" property="propertyDecl[${ctr}].id" />
          </c:if>
          <html-el:text  disabled="${disabled}"
                  name="selectedItemTypeForm" property="propertyDecl[${ctr}].name"/>
          
         </td>
         <!-- Beschreibung -->
         <td>
          <html-el:text disabled="${disabled}" 
                  name="selectedItemTypeForm" property="propertyDecl[${ctr}].description"/>
          
         </td>
         
         <!-- Typ -->
         <td>
           <c:set var="propertyValueTypes" value="${GPON_MODEL_VIEW.allPropertyValueTypes}" scope="page"/>
           <html-el:select disabled="${disabled}" 
               name="selectedItemTypeForm" property="propertyDecl[${ctr}].propertyValueTypeId">
             <html-el:options collection="propertyValueTypes" property="id" labelProperty="description"/>
           </html-el:select>
         </td>
         
         <!-- Pflicht ? -->
         <td>
          <html-el:checkbox disabled="${disabled}" property="propertyDecl[${ctr}].mandatory"/>
         </td>

         <!-- Typisch ? -->
         <td>
          <html-el:checkbox disabled="${disabled}" property="propertyDecl[${ctr}].typic"/>
         </td>


         <!-- Löschen ? --> 
         <td>
         <html-el:checkbox disabled="${disabled}" property="propertyDeclDelete[${ctr}]"/>
         </td>         
         
                  
        </tr> 
       </logic-el:iterate>
       
       <!-- Neue Eigenschaften -->
       <tr><td colspan="6">Neue Eigenschaften --&gt;</td></tr>
       <c:forEach var="newDeclId" begin="0" end="9">
        <tr>
         <!-- Rang -->
         <td>
          <html-el:select 
                  name="selectedItemTypeForm" property="newPropertyDecl[${newDeclId}].rank">
           <html-el:option value="" key="label.option.unknown"/>
           <c:forEach var="rankNo" begin="1" end="100">
            <html-el:option value="${rankNo}"><c:out value="${rankNo}"/></html-el:option>
           </c:forEach>
          </html-el:select>        
  
         </td>
        
         <!-- Name der Eigenschaft -->
         <td>
          <html-el:text 
                  name="selectedItemTypeForm" property="newPropertyDecl[${newDeclId}].name"/>
          
         </td>
         <!-- Beschreibung -->
         <td>
          <html-el:text 
                  name="selectedItemTypeForm" property="newPropertyDecl[${newDeclId}].description"/>
          
         </td>
         <!-- Typ -->
         <td>
           <c:set var="propertyValueTypes" value="${GPON_MODEL_VIEW.allPropertyValueTypes}" scope="page"/>
           <html-el:select value="-1"
              name="selectedItemTypeForm" property="newPropertyDecl[${newDeclId}].propertyValueTypeId">
             <option value="-1">-</option>
             <html-el:options collection="propertyValueTypes" property="id" labelProperty="description"/>
           </html-el:select>
         </td>
         
         <!-- Pflicht ? -->
         <td>
          <html-el:checkbox property="newPropertyDecl[${newDeclId}].mandatory"/>
         </td>
         
         <!-- Typisch ? -->
         <td>
          <html-el:checkbox property="newPropertyDecl[${newDeclId}].typic"/>
         </td>
         
        </tr>
       </c:forEach>
       
       </table>
     </td>
   </tr>
   <tr>
      <td colspan="3" align="center">
        <html-el:submit  value="&Auml;nderungen speichern"/>
        <html-el:button  property="cancel" value="&Auml;nderungen verwerfen" onclick="history.back()"/>
      </td>
    </tr>
   </table>
  </html-el:form>
  </body>
</html>