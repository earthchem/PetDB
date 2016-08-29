<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - View Expedition/Cruise data</title>
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
        ExpeditionDS ds = null;
        String state ="";
        int test =0;

        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");
    String ipAddress = IPAddress.getIpAddr(request);
    new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('View Expeditions',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");

	c_criteria = c_c_collection.getCurrentCCriteria();
        if (request.getParameter("source") == null)
        {
                c = c_criteria.getSampleCriteria(request.getSession().getId());
                SummaryWrapper summary_wrapper = (SummaryWrapper)c.getWrapper();
		if (summary_wrapper != null)
                {
                        wrapper = new ByExpWrapper(summary_wrapper.getQryStr());
                }
        } else {
		Wrapper pg3wrapper = null;
        	if ((pg3wrapper = (Wrapper)session.getAttribute("pg3wrapper")) == null)
                	throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");

        	String criteria_type = ((DataWrapper)pg3wrapper).getCriteriaType();

        	c =c_criteria.getCriteria(criteria_type);

                if (c.isSet())
                        wrapper = new ByExpWrapper(c);
        }
        if (wrapper != null)
                ds  =(ExpeditionDS)wrapper.getControlList("0");

%>
<body class="pop">
<div class="popTop">
<span class="emphasis">View Expedition/Cruise data</span><br />
</div><!-- end popTop -->
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td colspan="2" valign="middle">
	  <table width="100%" CELLSPACING="0" CELLPADDING="1">
		<tr class="rowLabel2">
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
	String prev_key = "";
	String chief ="";
	String chief_id ="";
	String ship ="";
	String ship_id ="";
	String inst ="";
	String inst_id ="";
	String exp ="";
	String year ="";
	if (ds != null) 
	 while(ds.next())
	{
		String key = (String)ds.getValue(ExpeditionDS.KEY);
		if (!prev_key.equals(key)) 
		{
			if (prev_key.length() != 0)
			{
%>
	<tr align="justify" class="regTxt" >     
	  <td><a href="exped_info.jsp?singlenum=<%= prev_key%>" target="info_win" onClick="openWindow2(this,350,750,this.target)"><%= exp%></a></td>
	  <td><%= ship%></td>
	  <td><%= year%></td>
	  <td><%= chief%></td>
	  <td><%= inst%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="6"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<%
			}
			prev_key = key;
			exp =  ds.getValue(ExpeditionDS.EXP);
			year =  ds.getValue(ExpeditionDS.YEAR);
			ship =  ds.getValue(ExpeditionDS.SHIP);
			ship_id =  ds.getValue(ExpeditionDS.SHIP_ID);
			inst =  ds.getValue(ExpeditionDS.INST);
			inst_id =  ds.getValue(ExpeditionDS.INST_ID);
			chief =  ds.getValue(ExpeditionDS.CHIEF);
			chief_id =  ds.getValue(ExpeditionDS.CHIEF_ID);
		} 
		else 
		{
			String n_chief = ds.getValue(ExpeditionDS.CHIEF);
			 
			chief += ( 
				 ( (chief == null) || (chief.length()==0) )  
				 ? ( ( (n_chief == null) || (n_chief.length() ==0) ) ? "" : n_chief)
				 : ("; " + ds.getValue(ExpeditionDS.CHIEF))
				);
			chief_id += ", " +ds.getValue(ExpeditionDS.CHIEF_ID); 
		}
	}

	int r = wrapper.closeQueries();
%>
	<tr align="justify" class="regTxt" >
	  <td><% if("NOT PROVIDED ".equals(exp)) { out.println(exp);} else {%><a href="exped_info.jsp?singlenum=<%= prev_key%>" target="info_win" onClick="openWindow2(this,350,750,this.target)"><%= exp%></a><%}%></td>
	  <td><%= ship%></td>
	  <td><%= year%></td>
	  <td><%= chief%></td>
	  <td><%= inst%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="6"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<!-- *** -->
	  </table>
	  	</td>
    </tr>
    <tr>
      <td colspan="2" align="left" valign="middle"><input type="button" name="close" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></td>
    </tr>
  </table>
</form>
</body>
</html>
</html>
