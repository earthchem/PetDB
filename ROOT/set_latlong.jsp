        <%@ page import="petdb.criteria.*" %>
        <%@ page import="petdb.query.*" %>
        <%@ page import="petdb.data.*" %>
        <%@ page import="petdb.wrapper.*" %>
        <%@ page import="java.util.*" %>
        <%@ page import="java.lang.*" %>
        <%@ page import="java.util.regex.*" %>
        <%@ include file="headCode2.jspf"%>
      
<%
   String ipAddress = IPAddress.getIpAddr(request);
   new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Latitude/Longitude',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
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
<link href="css/petdb.css" rel="stylesheet" type="text/css"/>
<script  language= "JavaScript" type="text/javascript" src=js/windows.js></script>
<script  language= "JavaScript" type="text/javascript" src="js/OpenLayers-2.12/OpenLayers.js"></script>
<script  language= "JavaScript" type="text/javascript" src=js/openlayermap.js></script>
<script  language= "JavaScript" type="text/javascript" src=js/set_latlong.js></script>
<script type="text/javascript">
<!--
window.focus();
var browsr = navigator.appName;
var hasBox=false;
var firstClick=true;
var previousFeature=null;
var previousBox=null;
var navTool=null;
var plat = navigator.platform.toLowerCase();
var isMacwIE = false;
if((plat.indexOf("mac") > -1)&&(browsr.indexOf("Explorer") > -1)){
isMacwIE = true;
document.write('<font color="red">You are using IE on a Mac. If the server response time is very slow you may experience problems with these popout window settings being properly recorded.</font>');
document.close();

}
//-->
</script> 
<style type='text/css'>
.olControlNavToolbar .olControlDrawFeatureItemInactive {
    background-position: -128px -1px;
}

.olControlNavToolbar .olControlDrawFeatureItemActive {
    background-position: -128px -24px;
}

#OpenLayers.Layer.Vector_45_vroot 
{
 fill:#FF0000
}
</style>
</head>
<body onload="loadClickableMap()">

<% try { %>
<form id="form1" name="form1" method="post" action="pg2.jsp" target="MAIN_WINDOW"> 
  <table border="0" cellspacing="0" class="sub2">
    <tr> 
      <td><img src="images/shim.gif" width="5" height="15"/></td>
    </tr>
    <tr class="emphasis">
        <td><i><font size="3"><i><span class="emphasis">Latitude/Longitude/Elevation</span></i></font></i></td>
    </tr> 
     
    <tr align="center" valign="bottom"> 
      <td>
        <div style="border:solid 1px">
          <div id="map_canvas" style="width: 800px; height: 400px" title="IEDA/MGDS Global Multi-Resolution Topography (GMRT)">
          </div> <!--  GMRT Map display here -->
          <span  style="float:left;width:700px;" class="smText">To select your region of interest click and drag </span>
          <span id="mousePosition" style="float:right;width:100px;"></span>
        </div>
      </td>
    </tr>
    
    
    <tr> 
      <td><img src="images/shim.gif" width="5" height="5"/></td>
    </tr>
    
    <tr>
     <td>
      <table width="100%">
        <tr> 
           <td align="center" valign="top">
             <table>
              <tr align="center">
               <td align="center" colspan="2"> <b>Elevation&nbsp;</b><span class="smText">(meters)&nbsp;</span></td>
               </tr>
               <tr><td colspan="2" align="center">
                      <span class="smText">negative for ocean floor&nbsp;</span>
                     </td>
              </tr>
              <tr>
                <td class="smText" align="center">Top</td>
                <td class="smText" align="center">Bottom</td>
              </tr>
              <tr>
                <td><input  type="text" name="depthtop" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.D_TOP) : "")%>" onblur="checkNumeric(this,'-10000','0','','.','-')"></input>
                </td>
                <td><input  type="text" name="depthbottom" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.D_BOTTOM) : "")%>" onblur="checkNumeric(this,'-10000','0','','.','-')"></input>
                </td>
             </tr>
             <tr>
                <td class="smText" align="center">eg. -1000</td>
                <td class="smText" align="center">eg. -3000</td>
              </tr>
            </table>
        </td>
       <td align="center">
       <table>
       <tr align="center">
          <td align="center" colspan="3"><b>Latitude/Longitude&nbsp;</b><span class="smText">(degrees)</span></td>
       </tr>
       <tr>
            <td class="smText" align="center"  colspan="3">North<br/>
                <input type="text" id="latNorth" name="latNorth" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.L_NORTH) : "")%>" onblur="checkNumeric(this,'-90','90','','.','-')"/>
            </td>
       </tr>
       <tr>
            <td class="smText" align="center">West<br/>
                <input  type="text" id="longWest" name="longWest" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.L_WEST) : "")%>" onblur="checkNumeric(this,'-180','180','','.','-')"/>
            </td>
            <td>&nbsp;</td>
            <td class="smText" align="center">East<br/>
                <input  type="text" id="longEast" name="longEast" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.L_EAST) : "")%>" onblur="checkNumeric(this,'-180','180','','.','-')"/>
            </td>
        </tr>
        <tr>
            <td class="smText"  colspan="3" align="center">South<br/>
                <input  type="text" id="latSouth" name="latSouth" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.L_SOUTH) : "")%>" onblur="checkNumeric(this,'-90','90','','.','-')"/>
            </td>
         </tr>
        </table>
      </td>
      </tr>
      </table>
      </td>
   </tr>
    <tr>
      <td colspan="2" valign="middle"> 
        <button onclick="resetValues();">Clear</button> <input type="button" name="Submit"  class="importantButton"  value="Apply" onclick="formSubmit();"/>
      </td>
      </tr>
      </table> 
</form>

<% } catch (Exception e) { %>
    <h1>Exception: <%= e.getMessage() %></h1>
<% } %>

<script type="text/javascript">
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
</script>

</body>
</html>