<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query -  set geographical name filters</title>
<script  language= "JavaScript" src=js/windows.js></script>
<script LANGUAGE="JavaScript" src="js/set_geo_name.js"></script>
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">

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
</head>
<body class="pop" OnLoad="BodyLoadFunction()">
<div class="popTop">
<span class="emphasis">Set Geographic Criteria</span><br /><br />
</div><!-- end popTop -->    
<%      IPAddress ipAdd = new IPAddress();
        String ipAddress = ipAdd.getIpAddrWithFilter(request);
        if(ipAddress != null)
            new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Feature Name',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;
        Criteria criteria;
        String state ="";
        int test =0;

        //ccColl is the session place holder for the CombinedCriteriaCollaction object
        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
                //session.setAttribute("ccColl",new CCriteriaCollection());
                //c_c_collection =  (CCriteriaCollection)session.getAttribute("ccColl");
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");
        }

        c_criteria = c_c_collection.getCurrentCCriteria();

        if ( (criteria = c_criteria.getCriteria(CombinedCriteria.ByGeoCriteria)) == null)
        {
               test = c_criteria.addCriteria(CombinedCriteria.ByGeoCriteria, new ByGeoCriteria());
               criteria = c_criteria.getCriteria(CombinedCriteria.ByGeoCriteria);
        }

    //Will get ByGeoWrapper, the ByGeoWrapper will add contents to controlLists as 
    //controlLists.add(new GeoCtlQuery().getDataSet());
	Wrapper wrapper = criteria.getWrapper(); 
	//ByGeoCriteria.LOCATION => "0"
	//The following returns controlLists => Vector
	DataSet ds = wrapper.getControlList(ByGeoCriteria.LOCATION);
%>

<form method="POST" action="pg2.jsp" target="MAIN_WINDOW" name="frm_main">
<input type=hidden name="fromwhere" value="ItemNum">	
<table width="90%" border="0" cellspacing="0" class="sub2">
<tr align="center" valign="top"> 
<td>
 <div style="height:350px;width:320px;overflow:auto;" >
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <!--  Create the Geographic Criteria List dynamically -->
    <%
        Vector v1 =  ds.getKeys();
            for (int i =0; i< v1.size(); i++) {
                    String key  = (String)v1.elementAt(i);
                   
            Vector v = (Vector)ds.getValue(key);
            //Vector k4v = (Vector)((UniformKeyedValueDS)ds).getKey4Value(key);
        
    %>
        <tr><td class="pad"><%= i+1%></td>
        <td align=left><input type=button value=<%= key%>
        onclick	=
        "SelectedListDynamic(<%= i+1%>,'<%= JavaSCSerializer.getGeoList(v)%>','<%= JavaSCSerializer.getGeoList(v)%>','@@@','<%= key%>');"></td>
        </tr>
    <% } %>
    </table>
  </div>
</td>
<td valign=bottom width="100%"> <!-- Right three columns within a table (Available | | Selected ) -->
<table border="0" CellSpacing="0" CellPadding="3">
   <tr>
      <th align=center class="select">Available</th>
      <th class="select">&nbsp;</th>
      <th align=center valign="middle" class="select"><b>Selected</b></th>
   </tr>
   <tr>
      <td class="select">
      <select Name="ItemsAvailable" Multiple Size=10 style="height:320px;width:200px;overflow:auto;">
         <option Value="#"> </option>
      </select>
      </td>
      <td class="select">
         <table Border="0">
         <tr><td align="center"><input type="button" name="AddAll" value="Add All &gt;&gt;" onClick="SelectAllOptions(ItemsAvailable);SelectA2SelectB(ItemsAvailable,ItemNum)"></td></tr>
         <tr><td align=center>
	<input Type="button" Name="AddFromAvailable" Value="Add &gt;" onclick=SelectA2SelectB(ItemsAvailable,ItemNum)></td></tr>
         <tr><td>&nbsp;</td></tr>
         <tr><td align=center>
	<input Type="button" Name="RemoveFromChosen" Value="&lt; Remove" onclick=SelectA2SelectB(ItemNum,ItemsAvailable)></td></tr>
         <tr>
           <td align=center>
        <input type="button" name="RemoveAll" value="&lt;&lt; Remove All" onClick="AllSelectA2SelectB(ItemNum,ItemsAvailable);">
           </td></tr>
         </table>
      </td>
      <td class="select">
      <select Name="ItemNum" Multiple Size="10" style="height:320px;width:200px;overflow:auto;">
         <option Value="#">
         </option>
      </select>
      </td>
   </tr>
  </table>
</td>
<tr><td colspan="2" class="select" align="center"><i><span class="regTxt">Hold CTRL key (Command key on Mac) for multiple selections</span></i></td>
</tr>
<tr><td align="center"  colspan="2">
       <input type="button" name="query"  class="importantButton" value="Apply" onClick="SelectAllOptions(document.frm_main.ItemNum); formSubmit()">
       <input type=reset value="reset" onClick=BodyLoadFunction();>
     </td>
   </tr>
</table>
<p>
</p>
</form>
</body>
<SCRIPT TYPE="text/javascript">
<!--

function formSubmit()
{
//if (!isMacwIE)alert(isMacwIE);
document.frm_main.target = "MAIN_WINDOW";
document.frm_main.submit();
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
