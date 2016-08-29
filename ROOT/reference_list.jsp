<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select Reference list data</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>
<script LANGUAGE="JavaScript">
location.reload(force);

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
	Wrapper wrapper = null ;
	DataSet ds = null;
        String state ="";
        String href_str ="reference_list.jsp";
        String TheQuery ="sample_ref";
        int test =0;


        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");
        }
    String ipAddress = IPAddress.getIpAddr(request);
    new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('View References',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");

        c_criteria = c_c_collection.getCurrentCCriteria();
	boolean previous = false;
	String ind ="";   
	if (request.getParameter("orderBy") != null)
	{
		previous = true;
		state += " PREVIOUS";
		ind = (String)request.getParameter("ind");
	} else if (request.getParameter("filter_type") != null) {
		TheQuery = "exp_ref";	
		String filter = request.getParameter("filter"); 
		wrapper = new ByPubWrapper(filter,ReferenceDCtlQuery.Expedition);
		state += " request.getParameter(filter_type)";
		ind = "ft";
	}
	 else if (request.getParameter("source") != null)
	{ 
		TheQuery = "method_ref";
		Wrapper pg3wrapper = null;
                if ((pg3wrapper = (Wrapper)session.getAttribute("pg3wrapper")) == null)
                        throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");

                String criteria_type = ((DataWrapper)pg3wrapper).getCriteriaType();

                c =c_criteria.getCriteria(criteria_type);

		if (c.isSet())
			wrapper = new ByPubWrapper(c);
		
		state += " request.getParameter(source) " + wrapper;
		ind = "mr";
	} 
	else
	{
		c = c_criteria.getSampleCriteria(request.getSession().getId());
		SummaryWrapper summary_wrapper = (SummaryWrapper)c.getWrapper();
        	if (summary_wrapper != null)
		{ 
			wrapper = new ByPubWrapper(summary_wrapper.getQryStr(), ReferenceDCtlQuery.Samples);
		}
		state += " else : c_criteria.getSampleCriteria(request.getSession().getId())";
		ind = "other";
	}

	if (previous)
		ds = (DataSet)session.getAttribute(ind+"orig_ds");
	 else 
		if (wrapper != null)
			ds  = wrapper.getControlList("0");

	session.removeAttribute(ind+"orig_ds");
	session.setAttribute(ind+"orig_ds",ds);
	
	state += " DS = " + ds;
%>
<body class="pop">

<div class="popTop">
<form name="download_frm" method="post" action="ReferenceDownload"><input type ="hidden" name="filtered" value="<%= TheQuery%>">
 <input name="download" type="button" id="download"  value="Download" onClick="submit(); return false;"> Download this list of References.</form><br />
<span class="emphasis">View Reference list data</span><br />
<span class="regTxt">You can get chemistry and location data by clicking the "data
tables" link for each reference.</span>
</div><!-- end popTop -->

<table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      	<td colspan="2" valign="middle">
	<%@ include file="reference_list_details.jspf"%>
	</td>
    </tr>
    <% if(!ReferenceDCtlQuery.Expedition.equals(request.getParameter("filter_type")))  {%>
    <tr>
      <td colspan="2" valign="middle"  align="left"><input type="button" name="back" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></td>
    </tr>
    <% } %>
  </table>
</body>
<SCRIPT TYPE="text/javascript">

<!--
function formSubmit()
{
document.form1.target = "MAIN_WINDOW";
document.form1.submit();
this.window.close();
return false;
}
//-->
</SCRIPT>
</html>
