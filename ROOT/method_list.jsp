<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select Method list data</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="css/petdb.css" rel="stylesheet" type="text/css">

<script LANGUAGE="JavaScript">
//location.reload(force);

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
	MethodDS ds = null;
        String state ="";
        int test =0;

        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");

        c_criteria = c_c_collection.getCurrentCCriteria();
	
	if (request.getParameter("source") == null)
        {
		c = c_criteria.getSampleCriteria(request.getSession().getId());
		SummaryWrapper summary_wrapper = (SummaryWrapper)c.getWrapper();
        	if (summary_wrapper != null)
		{ 
			wrapper = new MethodListWrapper(summary_wrapper.getQryStr());
		}
	} else {
		wrapper = new MethodListWrapper((String)request.getParameter("source"));
	}
	//session.setAttribute("wrapper",wrapper);
	if (wrapper != null)
		ds  = (MethodDS)wrapper.getControlList("0");
%>
<body class="pop">
<div class="popTop">
<span class="emphasis">View Method list data</span><br />
<span class="regTxt">You can get chemistry and location data by clicking the "data
tables" link for each method.</span>
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td colspan="2" valign="middle">
	  <table width="100%" CELLPADDING="1" CELLSPACING="0" class="regTxt">
		<tr class="rowLabel2">
		<th width="20%" ALIGN="left" class="pad2">Method Code</th>
		<th ALIGN="left" class="pad2">Name</th>
		<th ALIGN="left" class="pad2">Location</th>
		<th ALIGN="left" class="pad2">Provided by</th>
		<th ALIGN="left" class="pad2">Comment</th>
		</tr>

<%
        if (ds == null) return;
        int index;
        
	while (ds.next())
        {
%>
                <tr valign="top" class="regTxt">
                  <td><a href="method_info.jsp?singlenum=<%= ds.getValue(MethodDS.Key)%>" target="set_win" onClick="openWindow2(this,350,750)">
                        <%= ds.getValue(MethodDS.Code) + "(" +ds.getValue(MethodDS.Key)+")"%></a></td>
                  <td><%= ds.getValue(MethodDS.Name)%></td>
                  <td class="pad2"><%= ds.getValue(MethodDS.Institution)%></td>
                  <td><a href="ref_info.jsp?singlenum=<%= ds.getValue(MethodDS.Ref_ID)%>" target="ref_win" onClick="openWindow2(this,350,750,this.target)"><%= ds.getValue(MethodDS.Reference)%></a></td>
                  <td><%= ds.getValue(MethodDS.Note)%>&nbsp;&nbsp;</td>
                </tr>
                <tr class="rowLabel5"><td colspan="4"><img src="images/shim.gif" width="5" height="1"></td>
                </tr>
<%
        }

	int r = wrapper.closeQueries();
%>
	  </table>
	  	</td>
    </tr>
    <tr>
      <td colspan="2" valign="middle"  align="left"><input type="button" name="back" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></td>
    </tr>
  </table>
</form>
</div>
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
