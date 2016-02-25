<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>Item Detail
</head>
<body>
	<form name="getItemForm" method="get" action="http://localhost:1448/eBay/item?">
		ItemID: <input type="text" name="id"/> <br/>
		<input type="submit" value="GO" />
	</form>

<h4>
	Item Information:
	</h4>
	<table border="1" style="width:100%">
		<tr>
			<td>ID</td>
			<td> ${result.id} </td> 
		</tr>
		<tr>
			<td>Name</td>
			<td> ${result.name} </td> 
		</tr>

		<tr>
			<td>Category</td>
			<td> 
				<c:forEach var="category" items="${result.categories}">
				${category}
				${'	'}
			</c:forEach>
		</td> 
	</tr>

	<tr>
		<td>Currently</td>
		<td> ${result.currently} </td> 
	</tr>

	<tr>
		<td>Buy_price</td>
		<td> ${result.buy_price} </td> 
	</tr>

	<tr>
		<td>First_bid</td>
		<td> ${result.first_bid} </td> 
	</tr>

	<tr>
		<td>Number_of_bids</td>
		<td> ${result.number_of_bids} </td> 
	</tr>

	<tr>
		<td>Started</td>
		<td> ${result.started} </td> 
	</tr>

	<tr>
		<td>Ends</td>
		<td> ${result.ends} </td> 
	</tr>

	<tr>
		<td>Location</td>
		<td> ${result.location} </td> 
	</tr>

	<tr>
		<td>Latitude</td>
		<td> ${result.latitude} </td> 
	</tr>

	<tr>
		<td>Longitude</td>
		<td> ${result.longitude} </td> 
	</tr>

	<tr>
		<td>Country</td>
		<td> ${result.country} </td> 
	</tr>


	<tr>
		<td>SellerID</td>
		<td> ${result.seller.id} </td> 
	</tr>

	<tr>
		<td>SellerRating</td>
		<td> ${result.seller.rating} </td> 
	</tr>

</table>
</br>
<h4>
Bids Information:
</h4>
<table border="1" style="width:100%">

	<c:forEach var="bid" items="${result.bids}" varStatus="loop">
	<tr>
		<td>Bid ${loop.index+1}</td>
	</tr>
	<tr>
		<td>Time</td>
		<td> ${bid.time} </td> 
	</tr>
	<tr>
		<td>Amount</td>
		<td> ${bid.amount} </td> 
	</tr>

	<tr>
		<td>BidderID</td>
		<td> ${bid.bidder.id} </td> 
	</tr>

	<tr>
		<td>Rating</td>
		<td> ${bid.bidder.rating} </td> 
	</tr>

	<tr>
		<td>Location</td>
		<td> ${bid.bidder.location} </td> 
	</tr>

	<tr>
		<td>Country</td>
		<td> ${bid.bidder.country} </td> 
	</tr>
</c:forEach>


</table>
</body>
</html>