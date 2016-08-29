<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - set tectonic search filters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<script  language= "JavaScript" src=js/windows.js></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
</head>
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
<%      String ipAddress = IPAddress.getIpAddr(request);
        new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Tectonic Setting',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
        CCriteriaCollection c_c_collection=null;
        CombinedCriteria c_criteria=null;
        Criteria criteria=null;
        String state ="";
        int test =0;
        String errorMsg = null;
        Wrapper wrapper=null;
        DataSet ds=null;
        //ccColl is the session place holder for the CombinedCriteriaCollaction object
        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
             errorMsg="Your Session is EXPIRED. Please close this window and start over.";
        }
        else
        {
            c_criteria = c_c_collection.getCurrentCCriteria();
            if ( (criteria = c_criteria.getCriteria(CombinedCriteria.ByTectCriteria)) == null)
            {
        	test = c_criteria.addCriteria(CombinedCriteria.ByTectCriteria, new ByTectCriteria());
            criteria = c_criteria.getCriteria(CombinedCriteria.ByTectCriteria);
            }
        
            wrapper = criteria.getWrapper();
                
            ds = wrapper.getControlList(ByTectCriteria.TECTONIC);
       }
%>
<body class="pop" style="width:500px">
<% if(errorMsg == null ) 
{ %>
<div class="popTop" style="width:500px">

<span class="emphasis">Tectonic Setting</span> <i><br />
          <span class="regTxt">Please select a tectonic setting from the list
          below. <br/><i>Use CTRL key (Command key on Mac) to select multiple options.</i></span>
</div><!-- end popTop -->
<form name="set_tect_form" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<table width="100%" border="0" cellspacing="0" class="sub2" >
    <tr>
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr>
      <td><select id="tectonic" name="tectonic" size="15" style="width:400px" multiple>
   
<%
        Vector v1 =  ds.getKeys();
        for (int i =0; i< v1.size(); i++) {
                String key  = (String)v1.elementAt(i);
                String v = (String)ds.getValue(key);

		if (criteria != null) {
%>
	 <option value="<%= key%>" <%= (criteria.isSelected(ByTectCriteria.TECTONIC,key)) ? "selected" :""%>> <%= v%> </option>
<%
		}  else {  	
%>
	 <option value="<%= key%>" > <%= v%> </option>
<%
		}
	}
%>
  </select>
</td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td align="left">
	 <input type="button" name="Submit"  class="importantButton"  value="Apply" onClick="formSubmit();"></td>
    </tr>
  </table>
</form>
<% } else {%>
<span><%= errorMsg %></span>
<% } %>
</body>
<SCRIPT TYPE="text/javascript">
<!--
function formSubmit()
{
//if (!isMacwIE)alert(isMacwIE);
document.set_tect_form.target = "MAIN_WINDOW";
document.set_tect_form.submit();
window.focus();
if(!isMacwIE){
window.close();
return;
}
else {
	window.close();
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
