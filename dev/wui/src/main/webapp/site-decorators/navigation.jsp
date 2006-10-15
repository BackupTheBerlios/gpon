<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="de.berlios.gpon.wui.util.NavigationNode" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>
<%

String ctxPath = request.getContextPath();

 NavigationNode root = new NavigationNode("root","");
 
 NavigationNode data = new NavigationNode("data",ctxPath+"/data/search/intro.do");	
 NavigationNode model = new NavigationNode("model",ctxPath+"/model/intro.do");
 NavigationNode info = new NavigationNode("info",ctxPath+"/info/intro.do");
 root.addChildNode(data);
 root.addChildNode(model);
 root.addChildNode(info);
 
 NavigationNode search = new NavigationNode("search", ctxPath+"/data/search/intro.do"); 
 NavigationNode expertsearch = new NavigationNode("expertsearch",
		 ctxPath+"/data/expert-search/intro.do");
 
 data.addChildNode(search);
 data.addChildNode(expertsearch);
 
 
 NavigationNode modelit = new NavigationNode("items",ctxPath+"/model/it/list.do");	
 NavigationNode modelat = new NavigationNode("associations",ctxPath+"/model/at/list.do");	
 model.addChildNode(modelit);
 model.addChildNode(modelat);

 NavigationNode infoabout = new NavigationNode("about",ctxPath+"/info/about.do");
 info.addChildNode(infoabout);
 
 pageContext.setAttribute("navRootNode",root);
%>
<c:remove  var="subTopicList"/>

<fmt:setLocale value="${pageContext.request.locale}"/>

<fmt:bundle basename="de.berlios.gpon.wui.NavigationResources">
 <div id="maintopics">
  <ul>
    <c:forEach items="${navRootNode.childNodes}" var="node">
      <c:choose> 
      <c:when test="${node.shortname eq maintopic}">
        <li>
          <c:set var="subTopicList" value="${node.childNodes}"/>
          <a class="topic_selected" href="<c:out value="${node.url}"/>">
            <fmt:message key="${node.absolutePath}"/></a>
        </li>
      </c:when>
      <c:otherwise>
        <li>
          <a href="<c:out value="${node.url}"/>"><fmt:message key="${node.absolutePath}"/></a>
        </li>
      </c:otherwise> 
      </c:choose>
    </c:forEach>
  </ul>
 </div>
 
 <c:if test="${!empty subTopicList}">
 <div id="subtopics">
  <ul>
    <c:forEach items="${subTopicList}" var="node">
      <c:choose> 
      <c:when test="${node.shortname eq subtopic}">
        <li>
          <a class="topic_selected" href="<c:out value="${node.url}"/>"><fmt:message key="${node.absolutePath}"/></a>
        </li>
      </c:when>
      <c:otherwise>
        <li>
          <a href="<c:out value="${node.url}"/>"><fmt:message key="${node.absolutePath}"/></a>
        </li>
      </c:otherwise> 
      </c:choose>
    </c:forEach>
   </ul>
 </div>
 </c:if>

</fmt:bundle>
