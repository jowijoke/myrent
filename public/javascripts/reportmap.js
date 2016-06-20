let circle;
function requestReport() {
  const center = circle.getCenter();
  const latcenter = center.lat().toString();
  const lngcenter = center.lng().toString();
  const radius = circle.getRadius().toString();
  $('#radius').val(radius);
  $('#latcenter').val(latcenter);
  $('#lngcenter').val(lngcenter);
}

function initialize() {
  const center = new google.maps.LatLng(53.347298, -6.268344);
  const initRadius = 10000;
  const mapProp = {
    center: center,
    zoom: 7,
    mapTypeId: google.maps.MapTypeId.ROADMAP,
  };
  const mapDiv = document.getElementById('map_canvas');
  const map = new google.maps.Map(mapDiv, mapProp);
  mapDiv.style.width = '500px';
  mapDiv.style.height = '500px';

  circle = new google.maps.Circle({
    center: center,
    radius: initRadius,
    strokeColor: '#0000FF',
    strokeOpacity: 0.4,
    strokeWeight: 1,
    fillColor: '#0000FF',
    fillOpacity: 0.4,
    draggable: true,
  });
  circle.setEditable(true);//allows constying radius be dragging anchor point
  circle.setMap(map);
}

google.maps.event.addDomListener(window, 'load', initialize);
