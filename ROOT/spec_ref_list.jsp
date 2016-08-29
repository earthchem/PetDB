<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select Reference list data</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>

<script LANGUAGE="JavaScript">
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
	String state= "";
	String TheQuery = "spec_ref";
	String href_str = "spec_ref_list.jsp";
        Criteria criteria;
	Wrapper wrapper = null;
        DataSet ds = null; 
	String ind = "spec";
	if (request.getParameter("orderBy") != null)
	{
		ds = (DataSet)session.getAttribute("sr_ds");
		if (ds == null) throw new Exception(" No sr_ds found in the session"); 
	} else
	{ 
		if ((criteria = (Criteria)session.getAttribute("pub_criteria")) != null)  
        	{
		criteria.setValues(ByPub2Criteria.AUTH,request.getParameterValues("author"));
        	criteria.setValues(ByPub2Criteria.YEAR,request.getParameterValues("pubyear"));
        	criteria.setValues(ByPub2Criteria.JOUR,request.getParameterValues("journal"));
        	criteria.setValues(ByPub2Criteria.A_ORDER, request.getParameterValues("authororder"));
        	criteria.setValues(ByPub2Criteria.KEYWORD, request.getParameterValues("title"));

		wrapper = new ByPubWrapper(criteria);
       		ds = wrapper.getControlList("0");
		session.setAttribute("sr_ds", ds);
		}
	} 
	state += "DS " + ds;
%>
<body class="pop">
<div class="popTop">
<form name="download_frm" method="post" action="ReferenceDownload"><input type ="hidden" name="filtered" value="<%= TheQuery%>">
 <input name="download" type="button" id="download"  value="Download" onClick="submit(); return false;"> Download this list of References.</form><br />
<span class="emphasis">View Reference list data</span><br />
<span class="regTxt">You can get chemistry and location data by clicking the "data tables" link for each reference.</span>
</div><!-- end popTop -->
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      	  <td colspan="2" valign="middle">
<%@ include file="reference_list_details.jspf" %>
	  </td>
    </tr>
    <tr align="right">
      <td colspan="2" align="left" valign="middle"><input name="back" type="button" id="back" value="Back" onClick="javascript:location='set_pub_spec.jsp';">&nbsp;&nbsp;&nbsp;<input type="button" name="close" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></td>
    </tr>
  </table>
</form>
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
