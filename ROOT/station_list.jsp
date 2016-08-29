<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - View Station data</title>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<meta http-equiv="no-cache">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<script  language= "JavaScript" src=js/windows.js></script>
<script language="JavaScript">
//location.reload();

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
<%

        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;
        Criteria c;
	Wrapper wrapper = null;
        StationDS ds = null;
        String state ="";
        int test =0;

        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");
	
	
	String type = StationDCtlQuery.All;
	String filter = "";
        if (request.getParameter("filter_type") != null)
        {
		type = request.getParameter("filter_type");
		filter =  request.getParameter("filter");
	}
        wrapper = new StationListWrapper(filter,type);
        
	if (wrapper != null)
                ds  =(StationDS)wrapper.getControlList("0");

%>
<body class="pop">
<div class="popTop">
<span class="emphasis">View Station data</span>
</div><!-- end popTop -->
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td colspan="2">
	  <table width="100%" CELLPADDING="1" CELLSPACING="0" class="regTxt" >
		<tr class="rowLabel2" valign="bottom">
		<th width="100" ALIGN="left" class="pad2">Station ID</th>
		<th width="100" ALIGN="left" class="pad2">Sampling Method</th>
		<th width="50" ALIGN="left" class="pad2">Latitude</th>
		<th width="150" ALIGN="left" class="pad2">Longitude</th>
		<th width="150" ALIGN="left" class="pad2">Elevation Minimum</th>
		<th width="150" ALIGN="left" class="pad2">Elevation Maximum</th>
		<th width="150" ALIGN="left" class="pad2">Tectonic</th>
		</tr>
<!-- these rows repeat for each set of results-->

<%
	if (ds != null) 
	 while(ds.next())
	{
%>
	<tr align="top" class="regTxt" >
	  <td><a href="statn_info.jsp?singlenum=<%=  ds.getValue(StationDS.Station_Num)%>" target="info_win" onClick="openWindow2(this,350,750,this.target)"><%= ds.getValue(StationDS.Station_ID)%></a></td>
	  <td><%= ds.getValue(StationDS.Sampling)%></td>
	  <td><%= ds.getValue(StationDS.Latitude)%></td>
	  <td><%= ds.getValue(StationDS.Longitude)%></td>
	  <td><%= ds.getValue(StationDS.Elev_Min)%></td>
	  <td><%= ds.getValue(StationDS.Elev_Max)%></td>
	  <td><%= ds.getValue(StationDS.Tectonic)%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="8"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<%
	}

	int r = wrapper.closeQueries();
%>
	  </table>
  	  </td>
    </tr>
     <% if(!StationDCtlQuery.Exp_Based.equals(request.getParameter("filter_type")))  {%>
    <tr align="right">
      <td colspan="2" align="left" valign="middle"><input type="button" name="close" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></td>
    </tr>
    <% } %>
  </table>
</form>
</body>
</html>
</html>
