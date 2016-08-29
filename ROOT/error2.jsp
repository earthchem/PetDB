<%@ include file="headCode2.jspf"%>
<%@page isErrorPage="true" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta name="keywords" content="PetDB,&quot;petrology database&quot;,&quot;petrological database&quot;, Ridge, basalt, basalts, petrology, database,petdb,chemistry,customized,chemical,analyses,meta-data,&quot;meta data&quot;,meta,data">
<meta name="author" content="Lamont-Doherty Earth OBS, Petrology database group">
<meta NAME="description" CONTENT="The most complete compilation of ocean rock petrology and geochemistry. Up-to-date web and database technique.">
<meta name="robots" content="index, follow">
<META http-equiv="expires" content="0">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>Error Page</title>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
</head>
<body class="pop"><br />
<div class="popTop">
      <span class="emphasis">ERROR</span><br /><br />
<!-- print error message here (if dynamic), or -->
<% if(errMsg == null) { %>
Sorry, the PetDB system has encountered an error.<br /><blockquote><%= request.getSession().getAttribute("error")%></blockquote>
Please close this window and try again. If problems continue, please contact Support.
<% } else { %>
<%= errMsg %>
<% } %>
</div><!-- end popTop --><br />
</body>
</html>
