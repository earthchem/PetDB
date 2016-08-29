/* $Id:$ */
var ZOOMLEVELS = [[10, 5], [15, 10], [30, 15], [65, 40], [225, 80], [360, 180]];
/* initialize google map location, zoom, and type. This google map is tied to DOM 'map_canvas' element */
function initializeMap() {
	//alert("initialize");
  var myLatlng = new google.maps.LatLng(37.8,-122.4819);
  var myOptions = {
    zoom: 1,
    center: myLatlng,
    mapTypeId: google.maps.MapTypeId.SATELLITE
  }
  var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
  return map;
}

/* Create a google map according the lat/lon, DOM element id which is map id. */
function creategoogleMap(lat1,lon1,mapElementId) {
	//alert("initialize");
  var myLatlng = new google.maps.LatLng(lat1, lon1);
  var myOptions = {
    zoom:2,
    center: myLatlng,
    //mapTypeId: google.maps.MapTypeId.ROADMAP
    mapTypeId: google.maps.MapTypeId.SATELLITE
  }
  var map = new google.maps.Map(document.getElementById(mapElementId), myOptions);
  return map;
}

var counter=0;
var markCounter=0;
var point1=null;
var point2=null;
var mark1=null; 
var mark2=null;  
var rect=null; /* the rectangle to be deleted */

/* Pass in Id for google map div */
function createClickableGoogleMap() {
		//alert("initialize");
  var myLatlng = new google.maps.LatLng(37.8,-122.4819);
  var myOptions = {
    zoom:2,
    center: myLatlng,
    //mapTypeId: google.maps.MapTypeId.ROADMAP
    mapTypeId: google.maps.MapTypeId.SATELLITE
  }
  
  /* add markers */
  /*var map = new google.maps.Map(document.getElementById(mapElementId), myOptions);*/
  var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
  google.maps.event.addListener(map, 'click', function(event) {
	    markCounter++;
	    if( (markCounter %2) ==1 ) /* Odd number */
	    {
	    	if(markCounter >2)
    		  { mark1.setVisible(false);}
	    	mark1=placeMarker(event.latLng,map);
	    	
	    }
	    else /* even number */
	    {	
	    	if(markCounter >3)
  		    { mark2.setVisible(false);}
	    	mark2=placeMarker(event.latLng,map); 
	    }
   });
  
  /* add rectangle overlay and update coordinates in form fields */
  google.maps.event.addListener(map, 'click', function(event) {
	    counter++;
	    if( (counter %2) ==1 ) /* Odd number */
	    {
	      point1=event.latLng;
	    }
	    else /* event number */
	    {	  
	    	point2=event.latLng;  
	    }
	    if( counter > 1)
	    {
	    	/* Intersted region coordinates */
	      var rectCoords = [
	                          new google.maps.LatLng(point1.lat(),point1.lng()),
	                          new google.maps.LatLng(point1.lat(),point2.lng()),
	                          new google.maps.LatLng(point2.lat(),point2.lng()),
	                          new google.maps.LatLng(point2.lat(),point1.lng()),
	                          new google.maps.LatLng(point1.lat(),point1.lng())
	                   ];
	      if(counter > 2)
	      {
	    	  rect.setMap(null);/* removed previous rectangle */
	      }
	      rect=addRectangleOverlay(rectCoords, map);
        }
	    updateFormFields(point1,point2);
	  });
  return map;
}

/* load google map api and make the map clickable */
function loadClickableGoogleMap() {
  var script = document.createElement("script");
  script.type = "text/javascript";
  script.src = "http://maps.googleapis.com/maps/api/js?sensor=false&callback=createClickableGoogleMap";
  document.body.appendChild(script);
  
}

/* create a marker on the map */
function placeMarker(location, map) {
  var image = 'images/map_marker.png';
  var marker = new google.maps.Marker({
      position: location, 
      map: map,
      icon: image
  }); 
  return marker;
}

/* create a marker on the map */
function placeMarkerWithGoogleDefaultIcon(location, map) {
  var marker = new google.maps.Marker({
      position: location, 
      map: map
  }); 
  return marker;
}
/* create a marker on the map */
function placeMarkerWithIcon(location, map,iconImageName) {
  var image = iconImageName;
  var marker = new google.maps.Marker({
      position: location, 
      map: map,
      icon: image
  }); 
  return marker;
}
function addRectangleOverlay(coords, map)
{
  var rectIntersted;

  // Construct the polygon
  // Note that we don't specify an array or arrays, but instead just
  // a simple array of LatLngs in the paths property
  rectIntersted = new google.maps.Polygon({
                  paths: coords,
                  strokeColor: "#FF0000",
                  strokeOpacity: 0.8,
                  strokeWeight: 2,
                  fillColor: "#FF0000",
                  fillOpacity: 0.35
                  });

  rectIntersted.setMap(map);
  return rectIntersted;
}


function updateFormFields(point1,point2)
{
	if(point1==null) { return; }
	if(point2==null) 
	{
		document.getElementById('latNorth').value=point1.lat().toFixed(4);
		document.getElementById('longEast').value=point1.lng().toFixed(4);
		return ;
	}
	if(point1.lat() > point2.lat())
	{
		document.getElementById('latNorth').value=point1.lat().toFixed(4);
		document.getElementById('latSouth').value=point2.lat().toFixed(4);
	}
	else
	{
		document.getElementById('latNorth').value=point2.lat().toFixed(4);
		document.getElementById('latSouth').value=point1.lat().toFixed(4);
	}
	
	if(point1.lng() > point2.lng())
	{
		if(point1.lng()>0 && point2.lng()<0)
		{
			document.getElementById('longEast').value=point2.lng().toFixed(4);
			document.getElementById('longWest').value=point1.lng().toFixed(4);
			
		}
		else
		{
		    document.getElementById('longEast').value=point1.lng().toFixed(4);
		    document.getElementById('longWest').value=point2.lng().toFixed(4);
		}
	}
	else
	{
	//	alert("point1.lng() < point2.lng()"+" point1.lng="+point1.lng()+" west="+point2.lng());
		document.getElementById('longEast').value=point2.lng().toFixed(4);
		document.getElementById('longWest').value=point1.lng().toFixed(4);
	}
}
	
/* create a marker on the map and set its event listener, mouseover will pop information window, click will zoom in to the point */
function createClickableMarkerWithText(point, text, gmap) {

//	  var image = 'images/map_marker_result.gif';
	  var marker = new google.maps.Marker({
	      position: point, 
	      map: gmap,
	      title:'Click to Show Information Window'
	     /* icon: image*/
	  }); 
	  
	  var infowindow = new google.maps.InfoWindow(
		      { content: text,
		        size: new google.maps.Size(50,25)
		      });
/*
	  google.maps.event.addListener(marker, "mouseover", function() {
		  infowindow.open(gmap,marker);
	  }); 
	  
	  google.maps.event.addListener(marker, "mouseout", function() {
		  infowindow.close(gmap,marker);
	  }); */
	  google.maps.event.addListener(marker, "click", function() {
		    infowindow.open(gmap,marker);
		 /*   gmap.setCenter(point); */
		   /* gmap.setZoom(8); */
		  });
	  return marker;
}

/* create a marker on the map and set its event listener, mouseover will pop information window, click will zoom in to the point */
function createMarkerWithText(point, text, gmap) {

//	  var image = 'images/map_marker_result.gif';
	  var marker = new google.maps.Marker({
	      position: point, 
	      map: gmap,
	      title: text
	     /* icon: image*/
	  }); 
	  
	  return marker;
}