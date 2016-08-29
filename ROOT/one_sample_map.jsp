        <%@ page import="petdb.criteria.*" %>
        <%@ page import="petdb.query.*" %>
        <%@ page import="petdb.data.*" %>
        <%@ page import="petdb.wrapper.*" %>
        <%@ page import="java.util.*" %>
        <%@ page import="java.lang.*" %>
        <%@ page import="java.util.regex.*" %>
        <%@ include file="headCode2.jspf"%>
      
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">

<head>
<title>PETDB query - set latitute/longitude filters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<!-- 
<script language= "JavaScript" src=js/googleMaps.js></script>
 -->
<script  language= "JavaScript" type="text/javascript" src="js/OpenLayers-2.12/OpenLayers.js"></script>
<script  language= "JavaScript" type="text/javascript" src=js/openlayermap.js></script>
<script LANGUAGE="JavaScript" src="js/set_latlong.js"></script>
<script type="text/javascript" src="js/prototype.js"></script>
<script language= "JavaScript" src=js/windows.js></script>

<SCRIPT TYPE="text/javascript">

window.focus();
var browsr = navigator.appName;
var plat = navigator.platform.toLowerCase();
var isMacwIE = false;
if((plat.indexOf("mac") > -1)&&(browsr.indexOf("Explorer") > -1)){
isMacwIE = true;
document.write('<font color="red">You are using IE on a Mac. If the server response time is very slow you may experience problems with these popout window settings being properly recorded.</font>');
document.close();
}

</SCRIPT>

</head>
<body onload="GLoad();">

<%    
    String sample_num = null;
    String sampleName = request.getParameter("sampleID");
    if (sampleName == null) {
        sample_num = request.getParameter("singlenum");
    } else {
        
    }
    if (sample_num == null) throw new Exception("You are trying to go to Sample Info page, with NO sample specified");
    SampleInfoWrapper wrapper 		    = new SampleInfoWrapper(sample_num);
    SampleInfo1DS ds 		    = (SampleInfo1DS)wrapper.getControlList("0");
    
%>
<div>
<br/>
<% try { %>

  <table border="0" cellspacing="0">
    <tr align="center" valign="bottom"> 
      <td>
      <div id="map" style="width: 350px; height: 200px">map should show here</div>
      </td>
    </tr>
    <tr> 
      <td align="center" valign="middle" colspan="4"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
  </table>
</div>
    <br>
<% } catch (Exception e) { %>
    <h1>Exception: <%= e.getMessage() %></h1>
<% } %>
</body>


<script language="JavaScript" >

var markers = [];
var map=null; // will be initialize in Gload

<!--   The longitude/latitude coverage of each zoom level
// The zoom level (in google maps) corresponds to the index of the element from the end of the array
// (starting at 1).  So, for instance, zoom level 2 covers 225 degrees longitude and 80 degrees latitude.
-->

<%
HashMap found = new HashMap();

while (ds.next()) {
    if (!found.containsKey(ds.getValue(SampleInfo1DS.STATION))) {
    	String station_id = ds.getValue(SampleInfo1DS.STATION);
    	String station_num = ds.getValue(SampleInfo1DS.STATION_Num);
        found.put(ds.getValue(SampleInfo1DS.STATION), ds.getValue(SampleInfo1DS.STATION_Num));
        String clat = ds.getValue(SampleInfo1DS.LATITUDE);
        String clong = ds.getValue(SampleInfo1DS.LONGITUDE);        
        float clat_num = new Float(clat.substring(0, clat.indexOf("\260"))).floatValue();
        float clong_num = new Float(clong.substring(0, clong.indexOf("\260"))).floatValue();
                
        String clat_hemi = clat.substring(clat.indexOf("\260") + 1, clat.indexOf("\260") + 2);
        String clong_hemi = clong.substring(clong.indexOf("\260") + 1, clong.indexOf("\260") + 2);
                
        if (clat_hemi.equals("S")) {
            clat_num = - clat_num;
        }
                
        if (clong_hemi.equals("W")) {
            clong_num = - clong_num;
        }
%>
            point = new google.maps.LatLng(<%= clong_num %>, <%= clat_num %>);
            point = point.transform(
            		new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
                    new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator
            );
            if(map==null) {map = creategoogleMap(<%= clat_num %>, <%= clong_num %>,'map');}
            
            markers.push(createMarkerClickableWithText(point, "Station ID: <a href=\"<%= request.getRequestURI().substring(0, request.getRequestURI().indexOf("sample_info")) %>statn_info.jsp?singlenum=<%= ds.getValue(SampleInfo1DS.STATION_Num) %>\"><%= ds.getValue(SampleInfo1DS.STATION) %></a>",map));
<%
   }
}
// Close all ResultSet
int r = wrapper.closeQueries();
%>

//Call Gload();
GLoad();

</script>


<SCRIPT TYPE="text/javascript">
<!--
function formSubmit()
{
//if (!isMacwIE)alert(isMacwIE);
document.forms["form1"].target = "MAIN_WINDOW";
document.forms["form1"].submit();
window.focus();
if(!isMacwIE){
window.close();
return;
}
else {
setTimeout("winClose()", 20000); // 60000 ms is 1 minute.
return;
}}

function winClose(){
//alert('finished!'); 
window.close();
return;
}
//-->
</SCRIPT>
 
</html>
