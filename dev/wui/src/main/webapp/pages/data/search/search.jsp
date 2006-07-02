<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<c:choose>
<c:when test="${! empty param.result}">
 <jsp:include flush="true" page="searchResult.jsp"></jsp:include>
</c:when>
<c:otherwise>
 <jsp:include flush="true" page="searchDialog.jsp"></jsp:include>
</c:otherwise>
</c:choose>