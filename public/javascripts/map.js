function initialize() 
{
    var mapOptions = 
    {
        center : new google.maps.LatLng(52.254427, -7.185281),
        zoom : 12
    };
    var map = new google.maps.Map(document.getElementById("map-canvas"),
            mapOptions);
}
google.maps.event.addDomListener(window, 'load', initialize);