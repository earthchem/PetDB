<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select/view Samples </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>
<script LANGUAGE="JavaScript">

function SelectAllSamp(a)
{

	for(var i=0; i<a.length; i++){
			a[i].checked=true;
	}
}
function DeSelectAllSamp(a)
{


	for(var i=0; i<a.length; i++){
			a[i].checked=false;
	}
}

</script>
</head>
<body class="pop">
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<div class="popTop">
<input  name="act" type="hidden" value="include">
<%

        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;
        Criteria criteria;
        String state ="";
        int test =0;

        //ccColl is the session place holder for the CombinedCriteriaCollaction object
        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");
        }
        String ipAddress = IPAddress.getIpAddr(request);
        new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('View/Pick Samples',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
        c_criteria = c_c_collection.getCurrentCCriteria();
        Criteria c = c_criteria.getSampleCriteria(request.getSession().getId());
        SummaryWrapper summary_wrapper = (SummaryWrapper)c.getWrapper();

        BySampleIDWrapper wrapper;
        SampleIDDS ds;
        if ( (criteria = c_criteria.getCriteria(CombinedCriteria.BySampleIDCriteria)) == null)
        {
		test = c_criteria.addCriteria(CombinedCriteria.BySampleIDCriteria,
			 new BySampleIDCriteria(summary_wrapper.getQryStr(true)));
                criteria = c_criteria.getCriteria(CombinedCriteria.BySampleIDCriteria);
        	wrapper   = (BySampleIDWrapper) criteria.getWrapper();
        	ds        = (SampleIDDS)wrapper.getControlList("0");
        } else {
        	wrapper   = (BySampleIDWrapper) criteria.getWrapper();
		wrapper.update(summary_wrapper.getQryStr(true), SampleIDDCtlQuery.View);
        	ds        = (SampleIDDS)wrapper.getControlList("0");
	}
%>
<span class="emphasis">Select Samples</span>
		<br /><span class="regTxt">Click the sample id to see the detailed info. The checkbox on the left is used to set the particular sample as a query criteria. Click the &quot;Apply&quot; button to finish sample query.</span>
</div><!-- end popTop -->
<table border="0" cellspacing="0" class="sub2">
    <tr>
      <td><table width="100%" CELLSPACING="0" CELLPADDING="0">
	    <tr>
          <td width="23%" nowrap><input type=button name=selectall onclick="SelectAllSamp(document.form1.checkbox_sample);" value="Select All">
<input type=button name=clearall onclick="DeSelectAllSamp(document.form1.checkbox_sample);" value="Clear All"></td>
          <td width="77%">&nbsp;
            <input type="button" name="Submit"  class="importantButton"  value="Apply" onClick="formSubmit(document.form1.checkbox_sample);"></td>
	    </tr>
	    </table>
	  </td>
	</tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td colspan="2" valign="middle">
	  <table width="100%" CELLPADDING="1" CELLSPACING="0" class="regTxt">
		<tr class="rowLabel2" valign="bottom">
		<th ALIGN="left" nowrap class="pad2">&nbsp;</th>
		<th ALIGN="left" nowrap class="pad2">Sample ID</th>
		<th ALIGN="left" nowrap class="pad2">IGSN</th>
		<th ALIGN="left" nowrap class="pad2">Station ID</th>
		<th ALIGN="left" nowrap class="pad2">Rock Description</th>
		<th ALIGN="left" nowrap class="pad2">Data Existence</th>
		<th ALIGN="left" nowrap class="pad2">Alteration</th>
		<th ALIGN="left" nowrap class="pad2">Sampling</th>
		<th ALIGN="left" nowrap class="pad2">Latitude</th>
		<th ALIGN="left" nowrap class="pad2">Longitude</th>
		<th ALIGN="left" nowrap class="pad2">Elevation Min</th>
		<th nowrap ALIGN="left">Elevation Max</th>
		</tr>
<!-- these rows repeat for each set of results-->

<%
	int index;
	if (ds == null) return;

        String key      = "";
        String s_id ="";
        String st_id = "";
        String st_num = "";
        String r_dsc ="";
        String alt ="";
        String samp ="";
        String lat ="";
        String lng ="";
        String e_min ="";
        String e_max ="";
        String data_e ="";
        String igsn="N/A";
        String igsnURL="N/A";
        boolean any_rec = false;
        while (ds.next())
        {
                any_rec= true;
                if (!key.equals(ds.getValue(SampleIDDS.KEY)))
                {
                        if (key.length() != 0 )
                        {
%>
	<tr valign="top" class="regTxt" >
	  <td><input type="checkbox" checked name="checkbox_sample" value="<%= key%>"></td>
	  <td><a href="sample_info.jsp?sampleID=<%= s_id%>" target="set_win" onClick="openWindow2(this,500,750)"><%= s_id%></a></td>
	  <td><%= igsnURL %></td>
	  <td><a href="statn_info.jsp?singlenum=<%= st_num%>" target="set_win" onClick="openWindow2(this,500,750)"><%= st_id%></a></td>
	  <td><%= r_dsc%></td>
	  <td><%= data_e%></td>
	  <td><%= alt%></td>
	  <td><%= samp%></td>
	  <td><%= lat%></td>
	  <td><%= lng%></td>
	  <td><%= e_min%></td>
	  <td><%= e_max%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="11"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<%
			}

		 	key 	= ds.getValue(SampleIDDS.KEY);
			s_id 	= ds.getValue(SampleIDDS.SAMPLE_ID);
			st_id 	= ds.getValue(SampleIDDS.STATION_ID);
			st_num 	= ds.getValue(SampleIDDS.STATION_NUM);
			igsn    = ds.getValue(SampleIDDS.IGSN);
		 	r_dsc 	= ds.getValue(SampleIDDS.ROCK_DESC); /* rock description SampleIDDS.ROCK_DESC=4*/
			alt 	= ds.getValue(SampleIDDS.ALTERATION);
		 	samp	= ds.getValue(SampleIDDS.SAMPLING);
			lat 	= ds.getValue(SampleIDDS.LATITUDE);
			lng	= ds.getValue(SampleIDDS.LONGITUDE);
		 	e_min 	= ds.getValue(SampleIDDS.ELEV_MIN);
		 	e_max 	= ds.getValue(SampleIDDS.ELEV_MAX);
	 		data_e 	= ds.getValue(SampleIDDS.DATA_E);
		} else 
			alt += "; " + ds.getValue(SampleIDDS.ALTERATION);
			
       if( "&nbsp;".equalsIgnoreCase(igsn) == false ) 
           igsnURL = "<a href='#IDBlock' onclick=\"openwindow('"+PetDBConstants.igsnSESARURL+igsn+"','"+igsn+"','600','400')\" >"+igsn+"</a>";
       else
           igsnURL="N/A";
	}

	if (any_rec)
	{
%>
	<tr valign="top" class="regTxt" >
	  <td><input type="checkbox" checked name="checkbox_sample" value="<%= key%>"></td>
	  <td><a href="sample_info.jsp?sampleID=<%= s_id%>" target="set_win" onClick="openWindow2(this,500,750)"><%= s_id%></a></td>
	  <td><%= igsnURL %></td>
	  <td><a href="statn_info.jsp?singlenum=<%= st_num%>" target="set_win" onClick="openWindow2(this,500,750)"><%= st_id%></a></td>
	  <td><%= r_dsc%></td>
	  <td><%= data_e%></td>
	  <td><%= alt%></td>
	  <td><%= samp%></td>
	  <td><%= lat%></td>
	  <td><%= lng%></td>
	  <td><%= e_min%></td>
	  <td><%= e_max%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="11"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<%
	}

	int r = wrapper.closeQueries();
%>
<!-- *** -->
	  </table>
	  </td>
    </tr>
	<tr><td align="left"><input name="close" type="button" id="close" value="Close" onClick="if(window.opener)window.opener.focus(); window.close();">
	
	</td>
	</tr>
  </table>
</form>
</body>
<SCRIPT TYPE="text/javascript">
<!--
function formSubmit(a)
{
var count = 0;
var u_count = 0;
var ok = true; 
	document.form1.act.value = "include";
       	for(var i=0; i<a.length; i++){
                if (a[i].checked)
			count++;
		else u_count++
        }
	if (count >1000)
	{
		ok = false;
		if  (u_count <1000)
		{
			document.form1.act.value = "exclude";
			ok = true;
			for(var i=0; i<a.length; i++){
				a[i].checked = !(a[i].checked)
			}
		}
	}

// see how many are checked!!! swich to unchecked if more then 1000!!!
if (!ok) {
var msg = "\nYou have selected/unselected more than 1000 samples"
		+ "\n Please select/unselect less!";
                confirm(msg);
} 
else 
{
document.form1.target = "MAIN_WINDOW";
document.form1.submit();
this.window.close();
}
return false;
}
//-->
</SCRIPT>
</html>
