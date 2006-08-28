<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el"
	prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el"
	prefix="html-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic-el"
	prefix="logic-el"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<%-- get item object --%>
<c:set var="item" value="${ItemForm.item}" />
<html>
<head>
<meta name="maintopic" content="data">

</head>
<body>
<table>
	<thead>
		<tr>
			<th width="10%">Eigenschaft</th>
			<th>Wert</th>
		</tr>
	</thead>
	<tbody>
		<tr class="odd">
			<td>Objekt-Id:</td>
			<td><c:out value="${item.id}" /></td>
		</tr>
		<tr class="even">
			<td>Objekttyp:</td>
			<td><c:out value="${item.itemType.description}" /></td>
		</tr>
		<c:set var="rowClass" value="even" />
		<!-- Properties -->
		<logic-el:iterate name="item" property="properties" id="property"
			indexId="ctr">
			<c:choose>
				<c:when test="${rowClass=='even'}">
					<c:set var="rowClass" value="odd" />
				</c:when>
				<c:otherwise>
					<c:set var="rowClass" value="even" />
				</c:otherwise>
			</c:choose>
			<tr class="<c:out value="${rowClass}"/>">

				<td><c:out value="${property.propertyDecl.description}" /></td>
				<td><c:out escapeXml="false"
					value="${itemAsMap[property.propertyDecl.id].html}" /></td>
			</tr>
		</logic-el:iterate>

		<!-- associations we are a -->
		<c:forEach var="associationTypeA"
			items="${item.itemType.inheritedAssociationTypesA}">
			<tr>
				<td><c:out value="${associationTypeA.itemBRoleName}"/>
				    [<c:out value="${associationTypeA.name}" />]</td>
						<td style="width:500px;">
						<table>
							<!--  Kopf -->
							<thead>
								<tr>
									<th>Id</th>
									<c:forEach var="propDecl"
										items="${associationTypeA.itemBType.inheritedItemPropertyDecls}">
										<c:if test="${propDecl.typic}">
											<th><c:out value="${propDecl.description}"/></th>
										</c:if>
									</c:forEach>
									<th>Link</th>
								</tr>
							</thead>
							<tbody>
							    <!--  instruct the lazy map --> 
							    <c:set var="associatedSpec"
										value="a|${associationTypeA.description}" />
								<c:forEach var="itemB"
									items="${GPON_DATA_VIEW.focusItem[item.id].associatedItems[associatedSpec].list}">
									<tr>
										<td><c:out value="${itemB['id']}" /></td>
										<c:forEach var="propDecl"
											items="${associationTypeA.itemBType.inheritedItemPropertyDecls}">
											<c:if test="${propDecl.typic}">
												<td><c:out escapeXml="false" value="${itemB[propDecl.id].html}" /></td>
											</c:if>
										</c:forEach>
										<td><A HREF="itemDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&itemId=<c:out value="${itemB['id']}" />">Link</A></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</td>
			</tr>
		</c:forEach>


		<!-- associations we are b -->
		<c:forEach var="associationTypeB"
			items="${item.itemType.inheritedAssociationTypesB}">
			<tr>
				<td><c:out value="${associationTypeB.itemARoleName}" />
				    [<c:out value="${associationTypeB.name}" />] b</td>
				<td style="width:500px;">
						<table>
							<!--  Kopf -->
							<thead>
								<tr>
									<th>Id</th>
									<c:forEach var="propDecl"
										items="${associationTypeB.itemAType.inheritedItemPropertyDecls}">
										<c:if test="${propDecl.typic}">
											<th><c:out value="${propDecl.description}" /></th>
										</c:if>
									</c:forEach>
									<th>Link</th>
								</tr>
							</thead>
							<tbody>
							     <!--  instruct the lazy map --> 
							    <c:set var="associatedSpec"
										value="b|${associationTypeB.description}" /> 
								<c:forEach var="itemA"
									items="${GPON_DATA_VIEW.focusItem[item.id].associatedItems[associatedSpec].list}">
									<tr>
										<td><c:out value="${itemA['id']}" /></td>
										<c:forEach var="propDecl"
											items="${associationTypeB.itemAType.inheritedItemPropertyDecls}">
											<c:if test="${propDecl.typic}">
												<td><c:out escapeXml="false" value="${itemA[propDecl.id].html}" /></td>
											</c:if>
										</c:forEach>
										<td><A HREF="itemDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&itemId=<c:out value="${itemA['id']}" />">Link</A></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				</td>		
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>
