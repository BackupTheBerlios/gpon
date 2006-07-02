<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean-el"
	prefix="bean-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html-el"
	prefix="html-el"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic-el"
	prefix="logic-el"%>
<%@ taglib uri="http://displaytag.sf.net/el" prefix="display-el"%>
<%@ page contentType="text/html;charset=windows-1252"%>
<html>
<head>
<link rel="stylesheet" type="text/css" media="screen"
	href="<%= request.getContextPath() %>/css/remote-combo-ii.css" />
<script type="text/javascript"
	src="<%= request.getContextPath() %>/scripts/dojo/dojo.js"></script>
<script type="text/javascript">
	dojo.require("dojo.io.*");
	
	dojo.require("dojo.widget.*");
	dojo.require("dojo.widget.ComboBox");
	dojo.require("dojo.widget.html.ComboBox");
	dojo.require("dojo.widget.html.SplitSortableTable");
	dojo.hostenv.writeIncludes();
</script>
<script type="text/javascript"
	src="<%= request.getContextPath() %>/scripts/dojo-remote-combo-helper.js"></script>
<script type="text/javascript">
	dojo.event.connect(dojo, "loaded", "initAssocTables"); 
</script>
</head>
<body>
<html-el:errors />
<c:if test="${!empty ItemForm}">
	<c:set var="item" value="${ItemForm.item}" />
	<html-el:form action="/data/edit.do">
		<html-el:hidden property="itemId" />
		<table>
			<!-- Properties -->
			<c:forEach var="pDecl"
				items="${ItemForm.item.itemType.inheritedItemPropertyDecls}">
				<tr>
					<td><c:out value="${pDecl.description}" /></td>
					<td><html-el:text size="50"
						property="property(${pDecl.name}).value" /></td>
				</tr>
			</c:forEach>
		</table>
		<table>
			<!-- associations we are a -->
			<c:forEach var="associationTypeA"
				items="${ItemForm.item.itemType.inheritedAssociationTypesA}">

				<c:set var="tabMaxSize" value="0" />
				<c:set var="tabScroll" value="scroll" />

				<c:set var="control_id" value="a_${associationTypeA.id}" />
				<tr>
					<td>[<c:out value="${associationTypeA.description}" />] <c:out
						value="${associationTypeA.itemBRoleName}" /></td>
					<td>[<c:out value="${associationTypeA.name}" />] a</td>
				</tr>
				<tr>
					<td colspan="2">
					<table>
						<tr>
							<td style="width:500px;">
							<div style="display:none"
								id="<c:out value="${control_id}"/>result"><input
								name="associated(<c:out value="${control_id}"/>)" type="hidden"
								value="" /></div>
							<table id="<c:out value="${control_id}"/>"
								maxRows="<c:out value="${tabMaxSize}"/>"
								dojoType="SplitSortableTable" containerClass="container"
								headClass="header" tbodyClass="dates_<c:out value="${tabScroll}"/>" enableAlternateRows="true"
								rowAlternateClass="alternateRow" cellpadding="0" cellspacing="0"
								border="0">
								<!--  Kopf -->
								<thead>
									<tr>
										<th class="column" field="Id" dataType="Number">Id</th>
										<c:forEach var="propDecl"
											items="${associationTypeA.itemBType.inheritedItemPropertyDecls}">
											<c:if test="${propDecl.typic}">											
												<th class="column" field="<c:out value="${propDecl.name}"/>"
													dataType="String"
													nodeType="<c:out value="${GPON_MODEL_VIEW.allPropertyValueTypesMap[propDecl.propertyValueTypeId].nodeType}"/>"
													>
												 <c:out value="${propDecl.description}" />
												</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
									<c:set var="associatedSpec"
										value="a|${associationTypeA.description}" />
									<c:forEach var="itemB"
										items="${GPON_DATA_VIEW.focusItem[item.id].associatedItems[associatedSpec].list}">
										<tr>
											<td><c:out value="${itemB['id']}" /></td>
											<c:forEach var="propDecl"
												items="${associationTypeA.itemBType.inheritedItemPropertyDecls}">
												<c:if test="${propDecl.typic}">
													<td><c:out value="${itemB[propDecl.id].input}" /></td>
												</c:if>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<script type="text/javascript">
					  assocTables.push('<c:out value="${control_id}"/>');
				 </script></td>
							<td>Suggest:
							<table>
								<tr>
									<td>
									<div>
									<button
										onclick="addSelectionToTable('<c:out value="${control_id}"/>','combo_<c:out value="${control_id}"/>','<%= request.getContextPath() %>/ajax/getItem.do?itemId=');return false;">&lt;[add]&lt;</button>
									</div>
									</td>
									<td><select id="combo_<c:out value="${control_id}"/>"
										dojoType="combobox" value="this should be replaced!"
										dataUrl="<%= request.getContextPath() %>/ajax/suggestItems.do?typeId=<c:out value="${associationTypeA.itemBType.id}"/>&searchString=%{searchString}"
										mode="remote">
										</select>
										</td>
								</tr>
								<tr>

									<td>
									<div>
									<button
										onclick="removeSelectionFromTable('<c:out value="${control_id}"/>');return false;">&gt;[remove]&gt;</button>
									</div>
									</td>
								</tr>
							</table>


							</td>

						</tr>
					</table>
					</td>
				</tr>
			</c:forEach>


			<!-- associations we are b -->
			<c:forEach var="associationTypeB"
				items="${ItemForm.item.itemType.inheritedAssociationTypesB}">
				
				<!--  OneToMany here means ManyToOne -->
				<c:choose>
					<c:when test="${associationTypeB.multiplicity eq 'OneToMany'}">
						<c:set var="tabMaxSize" value="1" />
						<c:set var="tabScroll" value="noscroll" />
					</c:when>
					<c:otherwise>
						<c:set var="tabMaxSize" value="0" />
						<c:set var="tabScroll" value="scroll" />
					</c:otherwise>
				</c:choose>

				<c:set var="control_id" value="b_${associationTypeB.id}" />
				<tr>
					<td>[<c:out value="${associationTypeB.description}" />] <c:out
						value="${associationTypeB.itemARoleName}" /></td>
					<td>[<c:out value="${associationTypeB.name}" />] b</td>
				</tr>
				<tr>
					<td colspan="2">
					<table>
						<tr>
							<td style="width:500px;">
							<div style="display:none"
								id="<c:out value="${control_id}"/>result"><input
								name="associated(<c:out value="${control_id}"/>)" type="hidden"
								value="" /></div>
							<table id="<c:out value="${control_id}"/>"
							    maxRows="<c:out value="${tabMaxSize}"/>"
								dojoType="SplitSortableTable" containerClass="container"
								headClass="header" tbodyClass="dates_<c:out value="${tabScroll}"/>" enableAlternateRows="true"
								rowAlternateClass="alternateRow" cellpadding="0" cellspacing="0"
								border="0">
								<!--  Kopf -->
								<thead>
									<tr>
										<th class="column" field="Id" dataType="Number">Id</th>
										<c:forEach var="propDecl"
											items="${associationTypeB.itemAType.inheritedItemPropertyDecls}">
											<c:if test="${propDecl.typic}">
												<th class="column" field="<c:out value="${propDecl.name}"/>"
													nodeType="<c:out value="${GPON_MODEL_VIEW.allPropertyValueTypesMap[propDecl.propertyValueTypeId].nodeType}"/>"
													dataType="String"><c:out value="${propDecl.description}" />
												</th>
											</c:if>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
									<c:set var="associatedSpec"
										value="b|${associationTypeB.description}" />
									<c:forEach var="itemA"
										items="${GPON_DATA_VIEW.focusItem[item.id].associatedItems[associatedSpec].list}">
										<tr>
											<td><c:out value="${itemA['id']}" /></td>
											<c:forEach var="propDecl"
												items="${associationTypeB.itemAType.inheritedItemPropertyDecls}">
												<c:if test="${propDecl.typic}">
													<td><c:out escapeXml="false" value="${itemA[propDecl.id].input}" /></td>
												</c:if>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<script type="text/javascript">
					  assocTables.push('<c:out value="${control_id}"/>');
				 </script></td>
							<td>Suggest:
							<table>
								<tr>
									<td>
									<div>
									<button
										onclick="addSelectionToTable('<c:out value="${control_id}"/>','combo_<c:out value="${control_id}"/>','<%= request.getContextPath() %>/ajax/getItem.do?itemId=');return false;">&lt;[add]&lt;</button>
									</div>
									</td>
									<td><input id="combo_<c:out value="${control_id}"/>"
										dojoType="combobox" value="this should be replaced!"
										dataUrl="<%= request.getContextPath() %>/ajax/suggestItems.do?typeId=<c:out value="${associationTypeB.itemAType.id}"/>&searchString=%{searchString}"
										mode="remote"></td>
								</tr>
								<tr>

									<td>
									<div>
									<button
										onclick="removeSelectionFromTable('<c:out value="${control_id}"/>');return false;">&gt;[remove]&gt;</button>
									</div>
									</td>
								</tr>
							</table>


							</td>
						</tr>
					</table>
					</td>
				</tr>
			</c:forEach>



			<tr>
				<td><html-el:submit value="&Auml;ndern" /></td>
			</tr>
		</table>
	</html-el:form>
</c:if>
</body>
</html>

