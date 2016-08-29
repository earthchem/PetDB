<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - Reference Feedback</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<script  language= "JavaScript" src=js/set_pub.js></script>
<script  language= "JavaScript" src=js/windows.js></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script Language="JavaScript">
function isEmailAddr(email)
{
  var result = false
  var theStr = new String(email)
  var index = theStr.indexOf("@");
  if (index > 0)
  {
    var pindex = theStr.indexOf(".",index);
    if ((pindex > index+1) && (theStr.length > pindex+1))
	result = true;
  }
  return result;
}

function RfFormValidator(theForm)
{
    if(!theForm.email.value ||
        (!theForm.subject.value || theForm.subject.value.trim()=="") ||
        (!theForm.comment.value || theForm.comment.value=="")) {
        alert("Please fill out all fields");
        return false;
    }

  if (!isEmailAddr(theForm.email.value))
  {
    alert("Please enter a complete email address in the form: yourname@yourdomain.xxx");
    theForm.email.focus();
    return false;
  }
   
  if (theForm.email.value.length < 3)
  {
    alert("Please enter at least 3 characters in the \"email\" field.");
    theForm.email.focus();
    return false;
  }
 // setTimeout("winClose()", 20000); // 60000 ms is 1 minute.
  return true;
}

function winClose(){
window.close();
return;
}
</script>
</head>
<%  if(session.getAttribute("Method") == null)
    throw new RuntimeException("Permission denied!");
 
 String refNum = request.getParameter("refNum");
%>
<body class="pop">
<form name="referenceFeedback" method="POST" action="referenceFeedback" onsubmit="return RfFormValidator(this)">
<div class="popTop">
<span class="emphasis">Reference Feedback</span>&nbsp;&nbsp;(all fields are required)</div>
 <div>&nbsp;&nbsp;<% String errorMsg = request.getParameter("errorMsg");
  if(errorMsg != null) out.println(errorMsg); %>
  <div>
  <table class="basic" width="100%">
  <tr>
  <td>Subject</td><td><input type="text" size="90" id="subject" name="subject"/></td>
  </tr>
  <tr>
  <td>Reference Number</td><td><input type="text" size="90" id="refNum" name="refNum" value="<%= refNum %>" readonly="true" style="color:gray"/></td>
  </tr>
  <tr>
  <td><nobr>Your Email Address</nobr></td><td><input type="text" size="90" id="email" name = "email"/></td>
  </tr>
  <tr>
  <td valign="top">Comment</td><td><textarea cols="70" rows="15" id="comment" name="comment"></textarea></td>
  </tr> 
   <tr>
  <td colspan="2" align="right"><%= request.getAttribute("msg")==null?"":request.getAttribute("msg") %><INPUT TYPE="SUBMIT" NAME="Send" VALUE="Send"></td>
  </tr> 
  </table> 
</form>
</body>
</html>
