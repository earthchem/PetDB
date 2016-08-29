function loadGMRTMap( divname ) {
  
  //Add navigation controls
  var navControls=new OpenLayers.Control.Navigation({});
  var control_array = [
                       navControls,
                       new OpenLayers.Control.LayerSwitcher({}),
                       new OpenLayers.Control.Graticule({displayInLayerSwitcher:true,visible:false}),
                       new OpenLayers.Control.KeyboardDefaults({})
                       ];
		
  //Define map options
  //var extent = new OpenLayers.Bounds(-180,-90,180,90);
 // var options = {restrictedExtent: extent, maxResolution: 'auto'};
  var options = {maxResolution: 'auto', numZoomLevels : 10, 
		  maxExtent:new OpenLayers.Bounds(-20037508.3427, -15496570.7397, 20037508.3427, 18764656.2314),
	      projection: 'EPSG:3857'};
	      
  //Create a map object     
  var map = new OpenLayers.Map(divname, options);  
  
  //map: '/public/mgg/web/www.marine-geo.org/htdocs/services/ogc/wms.map'
  //Create topo layer
  var topo = new OpenLayers.Layer.WMS
  (
     "IEDA/MGDS Global Multi-Resolution Topography (GMRT)",
     "http://gmrt.marine-geo.org/cgi-bin/mapserv?map=/public/mgg/web/gmrt.marine-geo.org/htdocs/services/map/wms_merc.map&", {layers: 'topo',format: 'png', SRS : "3857" },
     {isBaseLayer: true,wrapDateLine: true}); 
 
//allow testing of specific renderers via "?renderer=Canvas", etc
  var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
  renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;

  //Add all layers
  map.addLayers([topo]);
  
  //Add navigation controls
  for(var i=0;i<control_array.length;i++)
  {
    map.addControl(control_array[i]);
  }
  if(!map.getCenter()) {
	  map.zoomToMaxExtent();
  }
  //map.setCenter(new OpenLayers.LonLat(0, 0), 0.4);
  return map;
}


function MercatorToLatLon(mercX, mercY) { 
	
	var rMajor = 6378137; //Equatorial Radius, WGS84     
	var shift  = Math.PI * rMajor;     
	var lon    = (mercX / shift * 180.0).toFixed(2);     
	var lat    = mercY / shift * 180.0;     
	lat = (180 / Math.PI * (2 * Math.atan(Math.exp(lat * Math.PI / 180.0)) - Math.PI / 2.0)).toFixed(2);   
	return { 'lon': lon, 'lat': lat };
}

function loadClickableMap() {

  //Add navigation controls
  var control_array = [
                       new OpenLayers.Control.PanZoom({}),
                       new OpenLayers.Control.LayerSwitcher({}),
                       new OpenLayers.Control.ScaleLine({}),
                       new OpenLayers.Control.Graticule({displayInLayerSwitcher:true,visible:false}),
                       new OpenLayers.Control.OverviewMap({displayInLayerSwitcher:true,visible:true}),
                       new OpenLayers.Control.KeyboardDefaults({})
                       ];
		
  //Define map options
  //var extent = new OpenLayers.Bounds(-180,-90,180,90);
  var options = {controls:control_array,numZoomLevels : 10, 
	      maxExtent:new OpenLayers.Bounds(-20037508.3427, -15496570.7397, 20037508.3427, 18764656.2314),	 
	      projection: 'EPSG:3857'};


  //Create a map object     
  map = new OpenLayers.Map('map_canvas', options);
  	map.events.register("mousemove", map, function (e) {
  	 var position = map.getLonLatFromViewPortPx(e.xy)
  	 position =MercatorToLatLon(position.lon,position.lat) ;
	 OpenLayers.Util.getElement("mousePosition").innerHTML = "["+position.lon +", "+ position.lat + "]";	
 });

  
  //Create state layer
  var states = new OpenLayers.Layer.WMS
                   ( "States",
  "http://www.geochron.org/cgi-bin/mapserv?map=/public/mgg/web/www.geochron.org/htdocs/lines.map&",
  {layers: 'state_line,country_line', transparent: true, format: 'png', SRS : "3857" },{opacity:0.5,visibility:false,wrapDateLine: true}); 
  
  var labels = new OpenLayers.Layer.WMS
	                   ( "Location Labels",
	                      "http://vmap0.tiles.osgeo.org/wms/vmap0",
	                      {layers: 'clabel,ctylabel,statelabel',transparent:true},
	                      {isBaseLayer: false,opacity:0.5,visibility:false}
			           );  
  
  //Create topo layer 
  var topo = new OpenLayers.Layer.WMS
  (
     "IEDA/MGDS Global Multi-Resolution Topography (GMRT)",
     "http://gmrt.marine-geo.org/cgi-bin/mapserv?map=/public/mgg/web/gmrt.marine-geo.org/htdocs/services/map/wms_merc.map&", {layers: 'topo',format: 'png', SRS : "3857" },
     {isBaseLayer: true,wrapDateLine: true}); 
  
//allow testing of specific renderers via "?renderer=Canvas", etc
  var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
  renderer = (renderer) ? [renderer] : OpenLayers.Layer.Vector.prototype.renderers;
  var vectors = new OpenLayers.Layer.Vector("Box", {renderers: renderer}); 
  navTool = addDragableBox(vectors, map,options);  

 // var boxes = new OpenLayers.Layer.Boxes( "Boxes" );

  
  //Add all layers
 //bc- map.addLayers([topo,states,labels,vectors]);
  map.addLayers([topo,states,vectors]);

 // addBoxByClickOnMap(vectors,boxes,map);
 if(!map.getCenter()) {
	  map.zoomToMaxExtent();
  }
}

function resetValues()
{  
	if(previousFeature != null ) previousFeature.destroy();
	if(navTool !=null)
	{
		navTool.controls[0].deactivate();
	    navTool.controls[1].activate();
	}
//	map.layers[4].clearMarkers();
	map.setCenter(new OpenLayers.LonLat(0, 0), 0);
	document.getElementById('latNorth').value ="";
    document.getElementById('longWest').value ="";
    document.getElementById('longEast').value = "";
    document.getElementById('latSouth').value = "";
  //  counter=0;
}

function addBoxByClickOnMap(vectorlayer,boxes,map)
{
  map.events.register('click',map,addBox);

  var counter=0;
  var point1;
  var point2;

  function addBox(event)
  {
	  var lonlat = map.getLonLatFromPixel(event.xy);
	  lonlat =MercatorToLatLon(lonlat.lon,lonlat);
	  if( counter == 0 )
	  {
	      point1=lonlat;
          boxes.clearMarkers();
		  counter++;
	  }
	  else
	  {	  
	      point2=lonlat;  
          var bounds = new OpenLayers.Bounds();
          bounds.extend(point1);
          bounds.extend(point2);
          var  box = new OpenLayers.Marker.Box(bounds);
          boxes.addMarker(box);
          updateElements(bounds.top,bounds.left,bounds.right,bounds.bottom);
	      counter=0;
	  }
    }//end of fucntion addBox
}

function addDragableBox(vectorlayer, map,options) 
{
	  //Create box control
	var style = { 
            strokeColor: '#FF0000', 
            strokeOpacity:1,
            strokeWidth: 1,
            fillColor:'#FF0000',
            fillOpacity:0.5,
            fill:false
        };

      var styleFeature = new OpenLayers.Feature.Vector(null, null, style);
	  var boxControl = new OpenLayers.Control.DrawFeature(vectorlayer,
			                                              OpenLayers.Handler.RegularPolygon, {
			                                                                                  handlerOptions: 
			                                                                                  {
			                                                                                     sides: 4,
			                                                                                     irregular: true,
			                                                                                     style:style
			                                                                                  }
			                                                                                 }
			                                              ) ;
			                                             
	  vectorlayer.events.on({ featuresadded: onFeaturesAdded });

	  function onFeaturesAdded(event)
	  {
			  var bounds = event.features[0].geometry.getBounds();
			  var size = bounds.getSize();
			  if ( firstClick ) 
			  {
				   if( (size.w + size.h) < 10 )
				   {
					   alert('Please  click and drag on the map to specify interested region');
					   firstClick=false;
					   event.features[0].destroy();
					   return ;
				   }
				   
			  }
			  if( (size.w + size.h) < 0.5 )
			  {
				  alert('Please specify larger region');
				  event.features[0].destroy();
				  return ;
	          }
			  
			  var westsouth = MercatorToLatLon(bounds.left, bounds.bottom);
			  var eastnorth = MercatorToLatLon(bounds.right, bounds.top);
			  updateElements(eastnorth.lat,westsouth.lon,eastnorth.lon,westsouth.lat);
			  
		//	  updateElements(bounds.top,bounds.left,bounds.right,bounds.bottom);

	          if(hasBox == false) //create the first bounding box
	          {
	        	  previousFeature = event.features[0];
	        	  vectorlayer.setOpacity(1);
	        	  hasBox=true;
	          }
	          else
	          { 
	        	if( previousFeature != null)
	        	{
	        	  previousFeature.destroy();
	            }
	            previousFeature = event.features[0];
	          }
	          var mybounds = bounds.clone();
	          map.zoomToExtent(mybounds);
	          //map.setCenter(bounds.getCenterLonLat(),1);
	  }

	  var navControl=new OpenLayers.Control.Navigation({'zoomWheelEnabled': true});
	  
	  var tb = OpenLayers.Class(OpenLayers.Control.NavToolbar, {
	      initialize: function() { 
	          OpenLayers.Control.Panel.prototype.initialize.apply(this, [options]);
	          this.addControls([              
	            navControl,
	            boxControl
	          ])
	       }
	      }
	  );
	  
	  var navTool = new tb();
	  map.addControl( navTool );
	  navControl.deactivate();
	  boxControl.activate();
	  return navTool;
}

function updateElements(latNorth,longWest,longEast,latSouth)
{
  document.getElementById('latNorth').value = latNorth ;
  document.getElementById('longWest').value = longWest;
  document.getElementById('longEast').value = longEast;
  document.getElementById('latSouth').value = latSouth;
}

/**
* Function: addClickableMarker
* Add a new marker to the markers layer given the following latlon and popup contents HTML. Also allow specifying
* whether or not to give the popup a close box.
*
* Parameters:
* latlon - {<OpenLayers.LonLat>} Where to place the marker * when the marker is clicked.
* popupContentHTML - {String} What to put in the popup
*/
function addClickableMarker(latlon, popupContentHTML,markerlayer, map) 
{
  var feature = new OpenLayers.Feature(markerlayer, latlon, markerlayer);
  feature.popupClass = OpenLayers.Class(OpenLayers.Popup.Anchored, {
	  'autoSize': true
  }); 
  feature.data.popupContentHTML = popupContentHTML;
  feature.data.overflow = "auto";
  var size = new OpenLayers.Size(10,10);
  feature.data.icon = new OpenLayers.Icon('images/reddot.png',size);
  var marker = feature.createMarker();
  var markerClick = function (evt) 
  {
    if (this.popup == null) {
      this.popup = this.createPopup(true);
      map.addPopup(this.popup);
      this.popup.show();
    } else {
      this.popup.toggle();
    }
    currentPopup = this.popup;
    OpenLayers.Event.stop(evt);
    };
    marker.events.register("mousedown", feature, markerClick);
    markerlayer.addMarker(marker);
}

function addPlainMarker(latlon, markerlayer, map) 
{
  var feature = new OpenLayers.Feature(markerlayer, latlon, markerlayer);
  feature.popupClass = OpenLayers.Class(OpenLayers.Popup.Anchored, {
	  'autoSize': true
  }); 
  feature.data.overflow = "auto";
  var size = new OpenLayers.Size(10,10);
  feature.data.icon = new OpenLayers.Icon('images/reddot.png',size);
  var marker = feature.createMarker();
    markerlayer.addMarker(marker);
}

function addMarkerWithText(latlon, textHTML, markerlayer, map) 
{
  var feature = new OpenLayers.Feature(markerlayer, latlon, markerlayer);
  feature.popupClass = OpenLayers.Class(OpenLayers.Popup.Anchored, {
	  'autoSize': true
  }); 
  feature.data.popupContentHTML = textHTML;
  feature.data.overflow = "auto";
  var size = new OpenLayers.Size(10,10);
  feature.data.icon = new OpenLayers.Icon('images/reddot.png',size);
  var marker = feature.createMarker();
  var markerClick = function (evt) 
  {
    if (this.popup == null) {
      this.popup = this.createPopup(true);
      this.popup.setSize(new OpenLayers.Size(250,200));
      map.addPopup(this.popup);
      this.popup.show();
    } else {
      this.popup.toggle();
    }
    currentPopup = this.popup;
    OpenLayers.Event.stop(evt);
    };
    marker.events.register("mouseover", feature, markerClick);
    markerlayer.addMarker(marker);
}

function addLines(map, latlonArray)
{
	var lineLayer = new OpenLayers.Layer.Vector("Line Layer"); 

	map.addLayer(lineLayer);                    
	map.addControl(new OpenLayers.Control.DrawFeature(lineLayer, OpenLayers.Handler.Path));
	var total = latlonArray.length;
	if( total < 2 ) return;
	for( var i=1;i<total;i++)
	{
	   var points = new Array(
	                         new OpenLayers.Geometry.Point(latlonArray[i-1].lon, latlonArray[i-1].lat),
	                         new OpenLayers.Geometry.Point(latlonArray[i].lon, latlonArray[i].lat)
	                         );
       var line = new OpenLayers.Geometry.LineString(points);
       var style = { 
	                   strokeColor: '#0000ff', 
	                   strokeOpacity: 0.5,
	                   strokeWidth: 1
	               };

	  var lineFeature = new OpenLayers.Feature.Vector(line, null, style);
	  lineLayer.addFeatures([lineFeature]);
	}
}