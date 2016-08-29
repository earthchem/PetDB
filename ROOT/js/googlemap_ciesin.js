/*
 * Requirements: google map key and the prototype javascript library (make sure you include this file
 *                and prototype.js in your page).
 *
 * To use:
 *
 * 1. Include a <div> element with an id equal to 'map' in the page where you want the map (e.g., <div id="map" style="width: 400px; height: 300px"></div>)  
 *
 * 2. Set the form field variables below to the names of your input fields and form
 *
 * Optional:
 *
 * - You can set the precision of the numbers, the width of the line and the names of additional (non-form)
 *   html elements that are updated with the lat/lng values dynamically
 */

// BEGIN CHANGABLE SETTINGS

// Form
var formName = "form1";

// Form fields
var northName = "latnorth";
var southName = "latsouth";
var eastName = "longeast";
var westName = "longwest";

// Precision of lat/longs
var prec = 4;

// Width of bounding box lines
var linewidth = 2;

// These (non-form) elements will updated dynamically with the lat/long values (set to null if not using)
var northId = null;
var southId = null;
var eastId = null;
var westId = null;

// Include PetDB station data
var includePetDB = false;

// END CHANGABLE SETTINGS

var overlays = [];
var points = []
var marker;
var polygon;

// bounds
var north = null;
var south = null;
var east = null;
var west = null;

var markSet = false;
var boxDrawn = false;

function GLoad() {
  if (GBrowserIsCompatible()) {
    var map = new google.maps.Map2($('map'));
    
    var tileGMA = new GMATileLayer();
	var tilePetDB = new PetDBTileLayer();
    
    if (includePetDB) {
        var layer4=[tileGMA, tilePetDB];
    } else {
        var layer4=[tileGMA];
    }

    var location = window.location;
    
    var icon = new google.maps.Icon(G_DEFAULT_ICON);
   
    icon.image = "http://" + location.host + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "images/map_marker.png"; 
    icon.shadow = "http://" + location.host + location.pathname.substring(0, location.pathname.lastIndexOf("/") + 1) + "images/map_marker_shadow.png";
     
    icon.iconSize = new google.maps.Size(18, 30);
    icon.shadowSize = new google.maps.Size(33, 30);
    
    icon.iconAnchor = new google.maps.Point(6, 20);
    
    var custommap4 = new google.maps.MapType(layer4, google.maps.SATELLITE_MAP.getProjection(), "Bathymetry", google.maps.SATELLITE_MAP);

 	map.getMapTypes().length = 0;
    map.addMapType(custommap4);
    
    map.setCenter(new google.maps.LatLng(37.8,-122.4819), 2);
    map.addControl(new google.maps.SmallMapControl());
    markSet = false;
    
    google.maps.Event.addListener(map, "click", function(mark, latlng) {
        
          north = null;
          south = null;
          east = null;
          west = null;
        
          if (markSet == false) {
            markSet = true;
            
            marker = new google.maps.Marker(latlng, {icon: icon});
            map.addOverlay(marker);

            points = [];
            overlays = [];
            
            points.push(marker.getPoint());
            overlays.push(marker);
            
          } else if (boxDrawn == false) {
            boxDrawn = true;
            
            marker = new google.maps.Marker(latlng, {icon: icon});
            map.addOverlay(marker);
            overlays.push(marker);
            
            points.push(marker.getPoint());
                
            if (points[0].lat() > points[1].lat()) {
                north = points[0].lat();
                south = points[1].lat();
            } else {
                north = points[1].lat();
                south = points[0].lat();
            }
        
            if (points[0].lng() > points[1].lng()) {
                east = points[0].lng();
                west = points[1].lng();
            } else {
                east = points[1].lng();
                west = points[0].lng();
            }
        
            var new1 = new google.maps.LatLng(points[0].lat(), points[1].lng());
            var new2 = new google.maps.LatLng(points[1].lat(), points[0].lng());
            points.push(new1);
            points.push(new2);
                
            var bounds = new google.maps.Bounds(points);
            var newPoints = [];
            newPoints.push(new google.maps.Point(bounds.minX, bounds.minY));
            newPoints.push(new google.maps.Point(bounds.maxX, bounds.minY));
            newPoints.push(new google.maps.Point(bounds.maxX, bounds.maxY));
            newPoints.push(new google.maps.Point(bounds.minX, bounds.maxY));
            newPoints.push(new google.maps.Point(bounds.minX, bounds.minY));
                
            polygon = new google.maps.Polygon(newPoints, "#ff0000", linewidth);
            overlays.push(polygon);
            map.addOverlay(polygon);
        
            var form = $(formName);
            // var form = document.forms[formName];
                
            form.getInputs('text', northName)[0].value = north.toFixed(prec);
            form.getInputs('text', southName)[0].value = south.toFixed(prec);
            form.getInputs('text', eastName)[0].value = east.toFixed(prec);
            form.getInputs('text', westName)[0].value = west.toFixed(prec);
        
            if (northId) {
                $(northId).innerHTML = north.toFixed(prec);
            }
            if (southId) {
                $(southId).innerHTML = south.toFixed(prec);  
            }
            if (eastId) {
                $(eastId).innerHTML = east.toFixed(prec);
            }
            if (westId) {
                $(westId).innerHTML = west.toFixed(prec);
            }
        
          } else {
          //  map.clearOverlays();
            
            points.clear();
            
            while (overlays.length > 0) {
                map.removeOverlay(overlays.pop());
            }
            
            boxDrawn = false;
            markSet = false;
          }
    });
  }
}

google.setOnLoadCallback(GLoad);

function PetDBTileLayer() {
    this.myFormat='image/png';
    this.myBaseURL='http://beta.sedac.ciesin.columbia.edu/mapserver/wfs/petdbora?';
    this.myLayers='samplechem';
    this.getTileUrl= function(a,b,c) {
        if (this.myMercZoomLevel == undefined) {
            this.myMercZoomLevel = 15;
        }

        if (typeof(window['this.myStyles'])=="undefined") this.myStyles="";
        var lULP = new google.maps.Point(a.x*256,(a.y+1)*256);
        var lLRP = new google.maps.Point((a.x+1)*256,a.y*256);
 
        var lUL = google.maps.NORMAL_MAP.getProjection().fromPixelToLatLng(lULP,b,c);
        var lLR = google.maps.NORMAL_MAP.getProjection().fromPixelToLatLng(lLRP,b,c);


        var lBbox=lUL.x+","+lUL.y+","+lLR.x+","+lLR.y;
        var lSRS="EPSG:4326";

        var lURL=this.myBaseURL;
        lURL+="&REQUEST=GetMap";
        lURL+="&SERVICE=WMS";
        lURL+="&VERSION=1.1.1";
        lURL+="&LAYERS="+this.myLayers;
        lURL+="&STYLES="+this.myStyles;
        lURL+="&FORMAT="+this.myFormat;
        lURL+="&BGCOLOR=0xFFFFFF";
        lURL+="&TRANSPARENT=TRUE";
        lURL+="&SRS="+lSRS;
        lURL+="&BBOX="+lBbox;
        lURL+="&WIDTH=256";
        lURL+="&HEIGHT=256";
        lURL+="&reaspect=false";
        
        return lURL;
    }
	this.getOpacity = function() {return 1.0;}
}

function GMATileLayer() {
    this.myLayers='topo';
	this.myFormat='image/jpeg';
    this.myBaseURL='http://www.marine-geo.org/services/wms?';
 	
    var MAGIC_NUMBER = 6356752.3142;
    var WGS84_SEMI_MAJOR_AXIS = 6378137.0;
    var WGS84_ECCENTRICITY = 0.0818191913108718138;
	 	
    var DEG2RAD = 0.0174532922519943;
    var PI = 3.14159267;
	 	
    function dd2MercMetersLng(p_lng) {
        return WGS84_SEMI_MAJOR_AXIS * (p_lng*DEG2RAD);
    }
	 	
    function dd2MercMetersLat(p_lat) {
        var lat_rad = p_lat * DEG2RAD;
        return WGS84_SEMI_MAJOR_AXIS * Math.log(Math.tan((lat_rad + PI / 2) / 2) * Math.pow( ((1 - WGS84_ECCENTRICITY * Math.sin(lat_rad)) / (1 + WGS84_ECCENTRICITY * Math.sin(lat_rad))), (WGS84_ECCENTRICITY/2)));
    }
    this.getTileUrl= function(a,b,c) {
        if (this.myMercZoomLevel == undefined) {
            this.myMercZoomLevel = 15;
        }

        if (typeof(window['this.myStyles'])=="undefined") this.myStyles="";
        var lULP = new google.maps.Point(a.x*256,(a.y+1)*256);
        var lLRP = new google.maps.Point((a.x+1)*256,a.y*256);
 
        var lUL = google.maps.NORMAL_MAP.getProjection().fromPixelToLatLng(lULP,b,c);
        var lLR = google.maps.NORMAL_MAP.getProjection().fromPixelToLatLng(lLRP,b,c);

        var lBbox=dd2MercMetersLng(lUL.x)+","+dd2MercMetersLat(lUL.y)+","+dd2MercMetersLng(lLR.x)+","+dd2MercMetersLat(lLR.y);
        var lSRS="EPSG:3395";
            
      //   var lBbox=lUL.x+","+lUL.y+","+lLR.x+","+lLR.y;
       // var lSRS="EPSG:4326";

        var lURL=this.myBaseURL;
        lURL+="&REQUEST=GetMap";
        lURL+="&SERVICE=WMS";
        lURL+="&VERSION=1.1.1";
        lURL+="&LAYERS="+this.myLayers;
        lURL+="&STYLES="+this.myStyles;
        lURL+="&FORMAT="+this.myFormat;
        lURL+="&BGCOLOR=0xFFFFFF";
        lURL+="&TRANSPARENT=TRUE";
        lURL+="&SRS="+lSRS;
        lURL+="&BBOX="+lBbox;
        lURL+="&WIDTH=256";
        lURL+="&HEIGHT=256";
        lURL+="&reaspect=false";
        
        return lURL;
    }
	this.getOpacity = function() {return 1.0;}
}

GMATileLayer.prototype = new google.maps.TileLayer(new google.maps.CopyrightCollection(""),1,17);
PetDBTileLayer.prototype = new google.maps.TileLayer(new google.maps.CopyrightCollection(""),1,17);