<%@ page import="jxl.*" %>
<%@ page import="jxl.write.*" %>
<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - set Publication Info filters</title>
<script  language= "JavaScript" src=js/windows.js></script>
<script  language= "JavaScript" src=js/set_pub.js></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
<script language="JavaScript" src="js/JQuery/js/ScrollableTablePlugin_1.0_min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        $('#Table1').Scrollable({
            ScrollHeight: 300
        });
         $('#Table2').Scrollable({
            ScrollHeight: 300
        });
    });
</script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">


</head>
<body class="pop">
<div class="popTop">
<%@ include file="table_info_argv.jspf" %>

<form name="download_frm" method="post" action="TableInfoDownload">
<input  name="singlenum" type="hidden" value="<%= table_id%>">
<input  name="table_num" type="hidden" value="<%= table_num%>">
<input  name="table_title" type="hidden" value="<%= table_title%>">
<input  name="rows" type="hidden" value="<%= rows%>">
<input  name="items" type="hidden" value="<%= items%>">
<input name="download" type="submit" id="download" value="Download">
&nbsp;Download this table.
</form><br />
<span class="emphasis">Table <%= table_num%> &#8212; &nbsp; <%= table_title%></span><br />
<span class="regTxt"><%= rows%>&nbsp;batches returned for&nbsp;<%= items%>&nbsp;items</span>
 </div><!-- end popTop -->
 <table border="0" cellspacing="0" class="sub2">
<% if(ds2 != null && ds2.isBeforeFirst()) { %>
<%@ include file="table_info_details.jspf" %>
<%} %>  
<% if(ds4 != null) {
    tableId="Table2";
    ds1 = ds3;
    ds2 = ds4;    
 %>
<tr><td>&nbsp;</td></tr>
<%@ include file="table_info_details.jspf" %>
<%} int r = wrapper.closeQueries(); %>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="left">
      <td valign="middle">
        <form name="close"><script>
	if (window.history.length > 0)document.write('<input name="back" type="button" id="back" value="Back" onClick="javascript:history.back();">&nbsp;&nbsp;&nbsp;');
	</script><input type="button" name="close" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></form>
      </td>
    </tr>
  </table>
</body>
</html>
