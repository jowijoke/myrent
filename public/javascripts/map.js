function initialize() {
	let map;
	let marker;
	const latlng = new google.maps.LatLng(53.347298, -6.268344);	
	const mapOptions = {
		zoom : 8,
		center : new google.maps.LatLng(53.347298, -6.268344),
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};
	const mapDiv = document.getElementById('map_canvas');
	map = new google.maps.Map(mapDiv, mapOptions);
//	mapDiv.style.width = '500px';
//	mapDiv.style.height = '600px';
	// place a marker
	marker = new google.maps.Marker({
		map : map,
		draggable : true,
		position : latlng,
		title : "Drag and drop on your property!"
	});
	// To add the marker to the map, call setMap();
	marker.setMap(map);
	// marker listener populates hidden fields ondragend
	google.maps.event.addListener(marker, 'dragend', function() {
		const latLng = marker.getPosition();
		const latlong = latLng.lat().toString().substring(0, 10) + ','
				+ latLng.lng().toString().substring(0, 10);
		// publish lat long in geolocation control in html page
		$("#geolocation").val(latlong);
		// update the new marker position
		map.setCenter(latLng);
	});
}
google.maps.event.addDomListener(window, 'load', initialize);