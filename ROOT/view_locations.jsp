<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select/view Samples </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>
</head>
<body class="pop">
<div class="popTop">
<%
       String ref_id = request.getParameter("singlenum");
       String l_title = request.getParameter("l_title");
       String number= request.getParameter("number");
       if (ref_id == null)
                        throw new Exception("You are trying to go to Table in Reference page. "
                        + " with NO Table in Reference specified");
        Wrapper wrapper = new LocationsWrapper(ref_id);
        SampleIDDS  ds = (SampleIDDS)wrapper.getControlList("0");

	if (ds == null) throw new Exception("There is not dataset"); 
	String igsnURL="Not Available";
%>
<span class="emphasis">List of Samples</span><br />
<span class="regTxt">View the selected samples.</span>
</div><!-- end popTop -->
<table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td colspan="2" valign="middle">
	  <table width="100%" CELLPADDING="1" CELLSPACING="0" class="regTxt">
		<tr class="rowLabel2">
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
		<th ALIGN="left" nowrap class="pad2">Elevation Max</th>
		</tr>
<!-- these rows repeat for each set of results-->

<%
	int index;
	if (ds == null) return;
	
	while (ds.next())
	{
		String key 	    = ds.getValue(SampleIDDS.KEY);
		String s_id 	= ds.getValue(SampleIDDS.SAMPLE_ID);
		String st_id 	= ds.getValue(SampleIDDS.STATION_ID);
		String igsn 	= ds.getValue(SampleIDDS.IGSN);
		String st_num   = ds.getValue(SampleIDDS.STATION_NUM);
		String r_dsc 	= ds.getValue(SampleIDDS.ROCK_DESC);
		String alt 	    = ds.getValue(SampleIDDS.ALTERATION);
		String samp	    = ds.getValue(SampleIDDS.SAMPLING);
		String lat 	    = ds.getValue(SampleIDDS.LATITUDE);
		String lng	    = ds.getValue(SampleIDDS.LONGITUDE);
		String e_min 	= ds.getValue(SampleIDDS.ELEV_MIN);
		String e_max 	= ds.getValue(SampleIDDS.ELEV_MAX);
		String data_e 	= ds.getValue(SampleIDDS.DATA_E);
	  	  
		if( "&nbsp;".equalsIgnoreCase(igsn) == false ) 
	  		igsnURL = "<a href='#IDBlock' onclick=\"openwindow('"+PetDBConstants.igsnSESARURL+igsn+"','"+igsn+"','600','400')\" >"+igsn+"</a>";
	  	else
	  		igsnURL="N/A";

%>
	<tr valign="top" class="pad">
	  <td nowrap><a href="sample_info.jsp?sampleID=<%= s_id%>" target="set_win" ><%= s_id%></a></td>
	  <td><%= igsnURL %></td>
	  <td nowrap><a href="statn_info.jsp?singlenum=<%= st_num%>" target="set_win" ><%= st_id%></a></td>
	  <td nowrap><%= r_dsc%></td>
	  <td nowrap><%= data_e%></td>
	  <td nowrap><%= alt%></td>
	  <td nowrap><%= samp%></td>
	  <td nowrap><%= lat%></td>
	  <td nowrap><%= lng%></td>
	  <td nowrap><%= e_min%></td>
	  <td nowrap><%= e_max%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="10"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<%
	}

	int r = wrapper.closeQueries();
%>
<!-- *** -->
	  </table>
  	  </td>
    </tr>
	<tr><td align="left"><form name="close">
	<script>
	if (window.history.length > 0)document.write('<input name="back" type="button" id="back" value="Back" onClick="javascript:history.back();">&nbsp;&nbsp;&nbsp;');
	</script>
	<input type="button" name="close" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></form></td></tr>
  </table>
</body>
</html>
