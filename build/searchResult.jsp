<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>Search Result
		<script type="text/javascript" src="AutoSuggest.js">
</script>
</head>
	<c:url value="http://localhost:1448/eBay/search" var="nexturl">
			<c:param name="q" value="${query}"/>
			<c:param name="numResultsToSkip" value="${numResultsToReturn+numResultsToSkip}"/>
			<c:param name="numResultsToReturn" value="${numResultsToReturn}"/>
	</c:url>

	<c:url value="http://localhost:1448/eBay/search" var="prevurl">
			<c:param name="q" value="${query}"/>
			<c:param name="numResultsToSkip" value="${numResultsToSkip-numResultsToReturn}"/>
			<c:param name="numResultsToReturn" value="${numResultsToReturn}"/>
	</c:url>

<body>
	<form name="searchForm" method="get" action="http://localhost:1448/eBay/search?" >
    Query: <input type="text" id="querybox" name="q" /> 
    <input type="text" name="numResultsToSkip" value="0" hidden>
    <input type="text" name="numResultsToReturn" value="10" hidden>
    <input type="submit" value="GO" />
	</form>

<c:choose>
    <c:when test="${empty result}">
        No more result found
    </br>
        <c:if test="${numResultsToSkip != 0}">
    		<a href="${prevurl}">Previous</a>
		</c:if>
    </c:when>
    <c:otherwise>

	<c:forEach var="item" items="${result}">
		<c:url value="http://localhost:1448/eBay/item" var="itemurl">
		<c:param name="id" value="${item.itemId}"/>
		</c:url>
		<a href="${itemurl}">${item.itemId} </a> ${item.name}
	</br>
	</c:forEach>

		<c:if test="${numResultsToSkip != 0}">
    			<a href="${prevurl}">Previous</a>
		</c:if>
				<a href="${nexturl}">Next</a>


    </c:otherwise>

        </c:choose>



</body>
</html>
