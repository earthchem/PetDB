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
<script LANGUAGE="JavaScript" src="./js/set_latlong.js"></script>
<script type="text/javascript" src="./js/prototype.js"></script>
<SCRIPT TYPE="text/javascript">
<!--
window.focus();
var browsr = navigator.appName;
var plat = navigator.platform.toLowerCase();
var isMacwIE = false;
if((plat.indexOf("mac") > -1)&&(browsr.indexOf("Explorer") > -1)){
isMacwIE = true;
document.write('<font color="red">You are using IE on a Mac. If the server response time is very slow you may experience problems with these popout window settings being properly recorded.</font>');
document.close();
}
//-->
</SCRIPT>
<%
    Pattern p = Pattern.compile("http:\\/\\/(.*\\.?www)\\.petdb\\.org.*");
    Matcher m = p.matcher(request.getRequestURL());
    
    if (m.matches()) {
        if (m.group(1).equals("alpha.www")) {
            response.getWriter().print("<script src=\"http://www.google.com/jsapi?key=ABQIAAAA2xoC77Jzq8IkApRdognRXxSrinGITxNcGyZsr7K0wKNSyc_9dRSoxEhOECsi7GuRGGbuvqDzcRJ8OA\" type=\"text/javascript\"></script>");
        } else if (m.group(1).equals("beta.www")) {
            response.getWriter().print("<script src=\"http://www.google.com/jsapi?key=ABQIAAAA2xoC77Jzq8IkApRdognRXxT-bYY_aUHZW22jKDC7cbyutF4SqRTM_UAKk7Cd3hFLEIzJUgeXqW6PfA\" type=\"text/javascript\"></script>");
        } else if (m.group(1).equals("www")) {
            response.getWriter().print("<script src=\"http://www.google.com/jsapi?key=ABQIAAAA2xoC77Jzq8IkApRdognRXxTCqZ74Rfpw_6R0F-dJwTmJt4R00BSDL-Dyv90R2hjrhIB6kzT_W-aUKw\" type=\"text/javascript\"></script>");
        }
    } else {
        response.getWriter().print("<script src=\"http://www.google.com/jsapi?key=ABQIAAAALohpJMvcCbQQqgE6UDNDwBTZIV0sZz3jm9cJWuRNkKX6SZ-U9xQpRcwjvFTBBt_z_xwKCU4XcU6JOQ\" type=\"text/javascript\"></script>");
    }
%>        

<script type="text/javascript">
google.load("maps", "2");
</script>

<!-- <script src="./js/dragzoom.js" type="text/javascript"></script> -->
<script src="./js/googlemap_latlng_corners.js" type="text/javascript"></script>

</head>
<body>

<% try { %>
<form id="form1" name="form1" method="post" action="pg2.jsp" target="MAIN_WINDOW">
    
    <table width="80%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="images/shim.gif" width="10" height="5"></td> 
        <td><img src="images/shim.gif" width="5" height="5"></td>
      </tr>
      <tr class="emphasis">
        <td><img src="images/shim.gif" width="10" height="5"></td> 
        <td width="100%"><i><font size="3"><i><span class="emphasis">Latitude/Longitude/Elevation</span></i></font></i></td>
      </tr>
      <tr>
        <td><img src="images/shim.gif" width="10" height="5"></td> 
        <td><img src="images/shim.gif" width="5" height="5"></td>
      </tr>
      <tr>
        <td><img src="images/shim.gif" width="10" height="5"></td> 
        <td><i><span class="smText">please specify below</span></i> </td>
      </tr>
      <tr>
        <td><img src="images/shim.gif" width="10" height="5"></td>
        <td><img src="images/shim.gif" width="5" height="5"></td>
      </tr>
    </table>  
  <table border="0" cellspacing="0" class="sub2">
    <tr align="center" valign="bottom"> 
      <td width="70%" nowrap>
      <div id="map" style="width: 400px; height: 300px"></div>
      <br/><br/>
      <span class="smText">Define two corners of a bounding box by clicking on the map.</span>
      </td>
      <td valign="top">
      <br/><br/>
      <table>
        <tr align="center" width="100%">
        <td align="center" width="100%" colspan="4" nowrap> <b>Elevation&nbsp;</b><span class="smText">(meters)&nbsp;<br>
        negative for ocean floor&nbsp;</span></td>
        </tr>
        <tr valign="top">
          <td></td>
          <td class="smText" nowrap>Top<br>
          <input name="depthtop" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.D_TOP) : "")%>" onBlur="checkNumeric(this,'-10000','0','','.','-')">
          </td>
        
          <td class="smText" nowrap>Bottom<br>
          <input name="depthbottom" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.D_BOTTOM) : "")%>" onBlur="checkNumeric(this,'-10000','0','','.','-')">
          </td>
          <td></td>
        </tr>
        <tr align="center">
        <td width="100%" colspan="4" nowrap><br/><br/> <b>Latitude/Longitude&nbsp;</b><span class="smText">(degrees)&nbsp;<br></span></td>
        </tr>
        <tr>
            <td class="smText" nowrap>North<br>
                <input type="text" id="latnorth" name="latnorth" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.L_NORTH) : "")%>" onBlur="checkNumeric(this,'-90','90','','.','-')">
            </td>
            <td class="smText" nowrap>South<br>
                <input id="latsouth" name="latsouth" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.L_SOUTH) : "")%>" onBlur="checkNumeric(this,'-90','90','','.','-')">
            </td>
            <td class="smText" nowrap>East<br>
                <input id="lateast" name="longeast" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.L_EAST) : "")%>" onBlur="checkNumeric(this,'-180','180','','.','-')">
            </td>
            <td class="smText" nowrap>West<br>
                <input id="latwest" name="longwest" size="10" value="<%= (criteria != null ? criteria.getValueAsStr(ByLongLatCriteria.L_WEST) : "")%>" onBlur="checkNumeric(this,'-180','180','','.','-')">
            </td>
        </tr>
       </table>
      </td>
    </tr>
    <tr> 
      <td align="center" valign="middle" colspan="4"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right"> 
      <td colspan="3" valign="middle"> 
        <input type="button" name="Submit"   class="importantButton" value="Apply" onClick="formSubmit();">
      </td>
    </tr>
  </table>
</form><br>
<% } catch (Exception e) { %>
    <h1>Exception: <%= e.getMessage() %></h1>
<% } %>
</body>
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
