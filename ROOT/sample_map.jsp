        <%@ page import="petdb.criteria.*" %>
        <%@ page import="petdb.query.*" %>
        <%@ page import="petdb.data.*" %>
        <%@ page import="petdb.wrapper.*" %>
        <%@ page import="java.util.*" %>
        <%@ page import="java.lang.*" %>
        <%@ page import="java.util.regex.*" %>
        <%@ include file="headCode2.jspf"%>
      
<%
   CombinedCriteria c_criteria = null;
	Criteria criteria = null;
    CCriteriaCollection c_c_collection;
   
     if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
		session.setAttribute("ccColl",new CCriteriaCollection());
		c_c_collection =  (CCriteriaCollection)session.getAttribute("ccColl");
        }
        
        c_criteria = c_c_collection.getCurrentCCriteria();
        if (c_criteria != null)
             criteria = c_criteria.getCriteria(CombinedCriteria.ByLongLatCriteria);

%>
 <% ByLongLatCriteria ll_criteria = (ByLongLatCriteria)c_criteria.getCriteria(CombinedCriteria.ByLongLatCriteria); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">

<head>
<title>PETDB query - set latitute/longitude filters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script language= "JavaScript" src=js/windows.js></script>
<script  language= "JavaScript" type="text/javascript" src="js/OpenLayers-2.12/OpenLayers.js"></script>
<script  language= "JavaScript" type="text/javascript" src=js/openlayermap.js></script>

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
<body>

<% 

    Criteria c = c_criteria.getSampleCriteria(request.getSession().getId());
    if (c == null) throw new Exception("Your Sample Criteria is outdated!"); 
        SummaryWrapper summary_wrapper = (SummaryWrapper)c.getWrapper();
        String qry = summary_wrapper.getQryStr(false);
        if("true".equals(request.getParameter("andVariable"))) {
           String itemCodes = request.getParameter("itemCode");
           int size = itemCodes.split(",").length;
            String [] arr = qry.split("\\(");
            qry = arr[0]+" (select sc2.SAMPLE_NUM from SAMPLE_CHEMISTRY sc2, ITEM_MEASURED im2 where sc2.ITEM_MEASURED_NUM = im2.ITEM_MEASURED_NUM and im2.ITEM_CODE in ("+itemCodes+") and sc2.SAMPLE_NUM in ("+
                    arr[1]+" group by  sc2.SAMPLE_NUM having count(distinct im2.ITEM_CODE) ="+size+")";
        }
       
        BySampleIDWrapper wrapper = new BySampleIDWrapper(qry, SampleIDDCtlQuery.View);
   // BySampleIDWrapper wrapper = new BySampleIDWrapper(summary_wrapper.getQryStr(false), SampleIDDCtlQuery.View);
    SampleIDDS ds = (SampleIDDS)wrapper.getControlList("0");
    
%>
<br/>
<% try { %>

  <table border="0" cellspacing="0">
    <tr align="center" valign="bottom"> 
      <td  nowrap>
      <div id="map_canvas" style="width: 800px; height: 400px"></div>
      </td>
    </tr>
    <tr> 
      <td align="center" valign="middle" colspan="4"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
  </table>
    <br>
<% } catch (Exception e) { %>
    <h1>Exception: <%= e.getMessage() %></h1>
<% } %>
</body>


<script language="JavaScript" >
var map=null; // will be initialized
var markers=null;
//var size = new OpenLayers.Size(10,10);
//var icon = new OpenLayers.Icon('images/bullet.gif',size);

<%
HashMap found = new HashMap();

while (ds.next()) {
    if (!found.containsKey(ds.getValue(SampleIDDS.STATION_ID))) {
        found.put(ds.getValue(SampleIDDS.STATION_ID), ds.getValue(SampleIDDS.STATION_NUM));
        String clat = ds.getValue(SampleIDDS.LATITUDE);
        String clong = ds.getValue(SampleIDDS.LONGITUDE);
             
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
            if(map==null) { map=loadGMRTMap('map_canvas');}
            var lonLat = new OpenLayers.LonLat(<%= clong_num %>, <%=  clat_num%> );
            lonLat = lonLat.transform(
            		new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
                    new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator
           );
            
           markers = new OpenLayers.Layer.Markers( "Markers",{displayInLayerSwitcher:false} );
            map.addLayer(markers);

           var popupContentHTML="Station ID: <a href=\"<%= request.getRequestURI().substring(0, request.getRequestURI().indexOf("sample_map")) %>statn_info.jsp?singlenum=<%= ds.getValue(SampleIDDS.STATION_NUM) %>\"><%= ds.getValue(SampleIDDS.STATION_ID) %></a>";
           addClickableMarker(lonLat, popupContentHTML,markers, map);
    		map.setCenter(null,16);
// map.setCenter(lonLat,6); 
 <%
   }//end of if
} //end of while
// Close all ResultSet

int r = wrapper.closeQueries();
%>
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
