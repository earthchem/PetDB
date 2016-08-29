<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="javax.naming.NamingException"%>
<%@ page import="petdb.data.Expedition"%>
<%@ page import="petdb.data.ExpeditionWS"%>
<%@ include file="headCode2.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>PETDB query - Expedition Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">

<link href="js/JQuery/css/overcast/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css"/> 
<link href="css/petdb.css" rel="stylesheet" type="text/css"/>
<script  language= "JavaScript" src=js/set_pub.js></script>
<script  language= "JavaScript" src=js/windows.js></script>
<script language="JavaScript" src="js/JQuery/js/jquery-1.6.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="js/JQuery/js/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>
<script>
  $(function() {
    $( "#tabs" ).tabs();
  });
</script>


</head>
<body class="pop">
<div class="popTop">
<p class="emphasis">
<font size="+1">Expedition information</font><br />
</p>
</div><!-- end popTop -->
<%
    String cruise_id = request.getParameter("cruise_id");
	String exp_id = request.getParameter("singlenum");
    if(exp_id !=null && !PetdbUtil.isInteger(exp_id)) exp_id = null;
    if(cruise_id != null) {
        String q = "select expedition_num from expedition where EXPEDITION_NAME = ? ";
        String arr[] = {cruise_id};
        int [] qType = {PreparedQuery.STRING};
        PreparedQuery sq = new PreparedQuery(q, arr, qType);
        exp_id = sq.getSingleResult();
        sq.close();
    }
	if (exp_id == null) {out.println("<b>&nbsp;&nbsp;&nbsp;&nbsp;No data were found!</b>"); return;}
    String expedition = null;
    String ship = "";
    String chief="";
    String institution="";
    String appcode = "";
    String eaExpCode = "";
    String dateName = "Date";
    String dateValue ="";
   
    
    //The following block is used for external access
    if (session.getAttribute("ccColl") == null )
    {
            session.setAttribute("ccColl",new CCriteriaCollection());
    }
    
    Wrapper wrapper = new ExpeditionInfoWrapper(exp_id);
    ExpeditionWS ws = new ExpeditionWS(exp_id);
    Expedition ex = ws.getExpedition();   
	
	ExpeditionInfoDS ds = (ExpeditionInfoDS)wrapper.getControlList("0");
	if (ds == null) return;
    if (ds.next())
	{
       appcode = ds.getValue(ExpeditionInfoDS.Appcode); 
       eaExpCode = ds.getValue(ExpeditionInfoDS.EA_expcode);
%>
<table border="0" class="sub2a">
  <tr align="right">
    <td align="left" valign="middle">
	    <table border="0">
<%      
    if(ex != null) {
        chief = ex.getChiefScientists();
        institution = "<a href='#' onClick='openWindow2(\""+ex.getInstitutionUrl()+"\",500,700)'>" +ex.getInstitution()+"</a>";
        expedition= "<a href='#' onClick='openWindow2(\""+ex.getNameUrl()+"\",500,700)'>" +ex.getName()+"</a> (Click for more information)"; 
        String shipUrl = ex.getShipUrl();
        if(shipUrl == null) ship = ex.getShip();
        else ship = "<a href='#' onClick='openWindow2(\""+shipUrl +"\",500,700)'>"+ ex.getShip() +"</a>";
        dateValue = ex.getDate();
    } else { 
        dateName="Year";  
        expedition=ds.getValue(ExpeditionInfoDS.Exp_Name);
         if ("DMS".equals(appcode)) {
         expedition= "<a href='#' onClick='openWindow2(\"http://www.marine-geo.org/link/entry.php?id="+eaExpCode+"\",500,700)'>" +expedition+"</a> (Click for more information)"; 
       }
       ship = ds.getValue(ExpeditionInfoDS.Ship_Name);
       institution = ds.getValue(ExpeditionInfoDS.Ints);
       chief = ds.getValue(ExpeditionInfoDS.Chief); 
       ship = ds.getValue(ExpeditionInfoDS.Ship_Name);
       dateValue = ds.getValue(ExpeditionInfoDS.Year);
    }
    int r =wrapper.closeQueries();   
    }
%>

        <tr>
          <th align="left">Expedition:</th>
          <td  class="regTxt"><%= expedition %> </td>
        </tr>
        <tr>
          <th align="left">Ship:</th>
          <td class="regTxt"><%= ship %> </td>
        </tr>
        <tr>
          <th align="left"><%= dateName%>:</th>
          <td class="regTxt"><%= dateValue %> </td>
        </tr>
        <tr>
          <th align="left">Chief Scientists:</th>
          <td class="regTxt"><%= chief %></td>
        </tr>
        <tr>
          <th align="left">Institution:</th>
          <td class="regTxt"><%= institution %></td>
        <tr>
      </table>
    </td>
  </tr>
 

  <tr align="right">
    <td align="left" valign="middle">&nbsp; 
 
 <div id="tabs">
  <ul class="tab_ul">
    <li><a href="#tabs-Station"><b>Station Info</b></a></li>
    <li><a href="#tabs-Sample"><b>Sample Info</b></a></li>
    <li><a href="#tabs-Reference"><b>Reference Info</b></a></li>
  </ul>
  <div id="tabs-Station">
    <jsp:include page="station_list.jsp">  
        <jsp:param name="filter_type" value="<%= StationDCtlQuery.Exp_Based%>" />        
        <jsp:param name="filter" value="<%=  exp_id %>" />     
    </jsp:include>
    </div>
  <div id="tabs-Sample">
    <jsp:include page="view_samples2.jsp">  
        <jsp:param name="type" value="<%= SampleIDDCtlQuery.Srch_Exped %>" />        
        <jsp:param name="srch_value" value="<%=  exp_id %>" />     
    </jsp:include> 
   </div>
  <div id="tabs-Reference">
     <jsp:include page="exp_ref_list.jsp">  
        <jsp:param name="filter_type" value="<%= ReferenceDCtlQuery.Expedition %>" />        
        <jsp:param name="filter" value="<%=  exp_id %>" />     
    </jsp:include>
</div>

 
    </td>
  </tr>
  <tr align="right">
    <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
  </tr>
 <tr >
    <td valign="middle" align="left"> 
      <form name="close"><input type="button" name="close" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></form>
    </td>
  </tr>
</table>
</body>
</html>
