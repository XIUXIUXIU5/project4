<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>Item Detail

	<head> 
		<meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> 
		<style type="text/css"> 
		html { height: 100% } 
		body { height: 100%; margin: 0px; padding: 0px } 
		#map_canvas { height: 100% } 
		</style> 
		<script type="text/javascript" 
		src="http://maps.google.com/maps/api/js?sensor=false"> 
		</script> 



		<c:choose>
		<c:when test="${result.latitude != null && result.longitude != null}">
		<script type="text/javascript"> 
		var latitude = ${result.latitude};
		var longitude = ${result.longitude};
		</script>
	</c:when>
	<c:otherwise>
	<script type="text/javascript"> 

	var latitude = null;
	var longitude = null;
	</script>
</c:otherwise>

</c:choose>

<script type="text/javascript"> 

var geocoder;
var map;

</script>
<script>
function initialize() {
	geocoder = new google.maps.Geocoder();
	var mapOptions;
	if (latitude != null && longitude != null) {
		mapOptions = { 
      				zoom: 14, // default is 8  
      				center: new google.maps.LatLng(latitude, longitude), 
      				mapTypeId: google.maps.MapTypeId.ROADMAP 
      			}; 
      			map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);

      		}
      		else
      		{  	

      			var address = "${result.location}" + " " + "${result.country}"; 
      			geocoder.geocode( { 'address': address}, function(results, status) {
      				if (status == google.maps.GeocoderStatus.OK) {
      					mapOptions = { 
      				zoom: 8, // default is 8  
      				mapTypeId: google.maps.MapTypeId.ROADMAP 
      			}; 
      			map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
      			map.setCenter(results[0].geometry.location);
      			var marker = new google.maps.Marker({
      				map: map,
      				position: results[0].geometry.location
      			});
      		} else {
      			mapOptions = { 
      				zoom: 1, // default is 8 
      				center: new google.maps.LatLng(0, 0), 
      				mapTypeId: google.maps.MapTypeId.ROADMAP 
      			}; 
      			map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
      		}
      	});
      		}
      	}
      	</script> 
      </head> 
  </head>
  <body onload="initialize()">

  	<form name="getItemForm" method="get" action="/eBay/item">
  		ItemID: <input type="text" name="id"/> <br/>
  		<input type="submit" value="submit" />
  	</form>

  	<h4>
  		Item Information:
  	</h4>
  	<p>ID: ${result.id} </p>
  	<p>Name: ${result.name} </p>
  	<p>Category: 
  		<c:forEach var="category" items="${result.categories}">
  		${category}
  		${'	'}
  	</c:forEach>
  </p>
  <p>Buy_price: ${result.buy_price} </p>
  <p>First_bid: ${result.first_bid} </p>
  <p>Number_of_bids: ${result.number_of_bids} </p>
  <p>Started: ${result.started} </p>
  <p>Ends: ${result.ends} </p>
  <p>SellerID: ${result.seller.id} </p>
  <p>SellerRating: ${result.seller.rating} </p>
</br>
<p>Location: ${result.location} </p>
<p>Latitude: ${result.latitude} </p>
<p>Longitude: ${result.longitude} </p>
<p>Country: ${result.country} </p>
<div id="map_canvas" style="width:50%; height:50%"></div> 




</br>
<h4>
	Bids Information:
</h4>

<c:forEach var="bid" items="${result.bids}" varStatus="loop">
<p>Bid ${loop.index+1}</p>
<p>Time: ${bid.time}</p>
<p>Amount: ${bid.amount}</p>
<p>BidderID: ${bid.bidder.id}</p>
<p>BidderRating: ${bid.bidder.rating}</p>
<p>Location: ${bid.bidder.location} </p>
<p>Country: ${bid.bidder.country}</p>
</br>
</c:forEach>


</body>
</html>