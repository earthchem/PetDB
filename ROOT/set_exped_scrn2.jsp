<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select Expedition/Cruise data</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>
<script LANGUAGE="JavaScript">

var browsr = navigator.appName.toLowerCase();
var plat = navigator.platform.toLowerCase();
var isMacwIE = false;
if((plat.indexOf("mac") > -1)&&(browsr.indexOf("explorer") > -1)){
isMacwIE = true;
document.write('<font color="red">You are using IE on a Mac. If the server response time is very slow you may experience problems with these popout window settings being properly recorded.</font>');
document.close();
}

function SelectAllSamp(a)
{

	for(var i=0; i<a.length; i++){
			a[i].checked=true;
	}
  if (!a.length) a.checked=true;
}
function DeSelectAllSamp(a)
{


	for(var i=0; i<a.length; i++){
			a[i].checked=false;
	}
  if (!a.length) a.checked=false;
}
</script>
</head>
<body class="pop">
<div class="popTop">
<%
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

	criteria = c_criteria.getCriteria(CombinedCriteria.ByExpCriteria);
	Criteria i_criteria = ((CompositeCriteria)criteria).getSubCriteria();
	if (request.getParameterValues("ItemNum") != null)
		i_criteria.setValues(ByExp2Criteria.EXP, request.getParameterValues("ItemNum"));
	else {
		i_criteria.setValues(ByExp2Criteria.SHIP,request.getParameterValues("ship"));
		i_criteria.setValues(ByExp2Criteria.YEAR,request.getParameterValues("year"));
		i_criteria.setValues(ByExp2Criteria.CHIEF,request.getParameterValues("chief"));
		i_criteria.setValues(ByExp2Criteria.INST,request.getParameterValues("institution"));
	
	}
		//out.print(i_criteria.getQryStr());
        Wrapper wrapper = ((CompositeCriteria)criteria).getWrapper();
        DataSet ds = wrapper.getControlList(ByExpCriteria.EXPIDs);

%>
<span class="emphasis">Select Expedition/Cruise data</span>
		<br /><span class="regText"><i>Click the expedition name to see the detailed info. The checkbox on the left is used to set the particular expedition as a query criteria.</i><br />Click the &quot;Apply&quot; button to finish expedition query.</span></td>
</div><!-- end popTop -->
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<table border="0" cellspacing="0" class="sub2">
    <tr>
      <td><table width="100%" CELLSPACING="0" CELLPADDING="0">
	    <tr>
          <td width="29%" nowrap><input type=button name=selectall onclick="SelectAllSamp(document.form1.checkboxexp);" value="Select All">
<input type=button name=clearall onclick="DeSelectAllSamp(document.form1.checkboxexp);" value="Clear All"></td>
          <td width="61%">&nbsp;
            <input type="button" name="Submit"  class="importantButton"  value="Apply" onClick="formSubmit();"></td>
          <td width="10%" align="right"><input name="back" type="button" id="back" value="Back" onClick="javascript:history.back();"></td>
	    </tr>
	    </table>
	  </td>
	</tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td colspan="2" valign="middle">
	  <table width="100%" CELLSPACING="0" CELLPADDING="1">
		<tr class="rowLabel2">
		<th width="20" ALIGN="left"><font size="2">&nbsp;</font></th>
		<th width="100" ALIGN="left"><font size="2">Name/Leg</font></th>
		<th width="100" ALIGN="left"><font size="2">Ship</font></th>
		<th width="50" ALIGN="left"><font size="2">Year</font></th>
		<th width="150" ALIGN="left"><font size="2">Chief scientist</font></th>
		<th width="150" ALIGN="left"><font size="2">Institution</font></th>
		</tr>
<!-- these rows repeat for each set of results-->

<%
	int index;
	if (ds == null) return;
	Vector keys = ds.getKeys();
	for (index = 0; index < keys.size(); index++)
	{
		String key = (String)keys.elementAt(index);
		ExpRecord er = (ExpRecord)ds.getValue(key);
%>
	<tr align="top" class="regTxt" >
	  <td><input type="checkbox" checked name="checkboxexp" value="<%= key%>"></td>
	  <td><a href="exped_info.jsp?singlenum=<%= key%>" target="_self"><%= er.getValue(ExpRecord.EXP)%></a></td>	 
	  <td><% String ship =er.getValue(ExpRecord.SHIP); if(!"0".equals(ship)) out.println(ship); %></td>
	  <td><%= er.getValue(ExpRecord.YEAR)%></td>
	  <td><%= er.getValue(ExpRecord.CHIEF)%></td>
	  <td><%= er.getValue(ExpRecord.INST)%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="6"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<%
	}
%>
<!-- *** -->
	  </table>
	  </td>
    </tr>
  </table>
</form>
</body>
<SCRIPT TYPE="text/javascript">
<!--
function formSubmit()
{
//if (!isMacwIE)alert(isMacwIE);
document.form1.target = "MAIN_WINDOW";
document.form1.submit();
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
</html>
