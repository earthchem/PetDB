<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - set Database version filters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>
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


        if ( (criteria = c_criteria.getCriteria(CombinedCriteria.ByDataVersionCriteria)) == null)
                {
                        test = c_criteria.addCriteria(CombinedCriteria.ByDataVersionCriteria, new ByDataVersionCriteria());
                        criteria = c_criteria.getCriteria(CombinedCriteria.ByDataVersionCriteria);
                }

        Wrapper wrapper = criteria.getWrapper();

        DataSet ds = wrapper.getControlList(ByDataVersionCriteria.Version);

%>
<body class="pop" style="width:500px">
<div class="popTop" >
<span class="emphasis">Data Version</span><br />
<span class="regTxt">The pull-down menu below lists dates on which batches of new data were loaded into PetDB. 
			Please select a date from the menu if you want to get only those data entered after that date.
			For example: If you select 2004-01-23, you will get all data entered on 1/23/2004 AND 8/26/2004.
			Select samples for which new data is available since: </span>
</div><!-- end popTop -->
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW" style="width:500px">
<table border="0" cellspacing="0" class="sub2" >
    <tr>
      <td width="70%"><select id="data_version" name="data_version" size=1>
   
<%
        Vector v1 =  ds.getKeys();
        for (int i =0; i< v1.size(); i++) {
                String key  = (String)v1.elementAt(i);
                String v = (String)ds.getValue(key);
		if (DisplayConfigurator.isVersionActive(v))
		{
		if (criteria != null) {
%>
	 <option value="<%= key%>" <%= (criteria.isSelected(ByDataVersionCriteria.Version,key)) ? "selected" :""%>> <%= v%> </option>
<%
		}  else {  	
%>
	 <option value="<%= key%>" > <%= v%> </option>
<%
		}
		}
	}
%>
  </select>
</td>
     <td align='left'>
      <input type="button" name="Submit"  class="importantButton"  value="Apply" onClick="formSubmit();"></td>
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
