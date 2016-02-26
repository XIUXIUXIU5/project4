<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>Search Result
	<style>

div.suggestions {
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    border: 1px solid black;
    position: absolute; 
        z-index:99;
  
}

div.suggestions div {
    cursor: default;
    padding: 0px 3px;
        background-color: white;

}

div.suggestions div.current {
    background-color: #3366cc;
    color: white;
}

</style>
		<script type="text/javascript" src="AutoSuggest.js">
</script>
</head>
	<c:url value="/eBay/search" var="nexturl">
			<c:param name="q" value="${query}"/>
			<c:param name="numResultsToSkip" value="${numResultsToReturn+numResultsToSkip}"/>
			<c:param name="numResultsToReturn" value="${numResultsToReturn}"/>
	</c:url>

	<c:url value="/eBay/search" var="prevurl">
			<c:param name="q" value="${query}"/>
			<c:param name="numResultsToSkip" value="${numResultsToSkip-numResultsToReturn}"/>
			<c:param name="numResultsToReturn" value="${numResultsToReturn}"/>
	</c:url>

<body>
	<form name="searchForm" method="get" action="/eBay/search" >
    Query: <input type="text" id="querybox" name="q" autocomplete="off"/> 
    <input type="text" name="numResultsToSkip" value="0" hidden>
    <input type="text" name="numResultsToReturn" value="10" hidden>
    <input type="submit" value="submit" />
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
		<c:url value="/item" var="itemurl">
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
