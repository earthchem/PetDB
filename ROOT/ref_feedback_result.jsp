<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - Reference Feedback</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
</head>
<body class="pop"><br>
<div>&nbsp;&nbsp;<%= request.getParameter("feedbackMsg")%><div><br>
&nbsp;&nbsp;<input type="button" name="txtSubmit" value="Back" onclick="javascript:history.back();">
<input type="button" name="txtSubmit" value="Close" onclick="window.close();">
</body>
</html>
