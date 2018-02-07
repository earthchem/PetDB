<%@ include file="headCode2.jspf"%>

<%

//copied from saveTagAlongChoices.jsp . saveTagAlongChoices.jsp no longer needed.
session.setAttribute("MAJ",(String) request.getParameter("MAJ"));
session.setAttribute("TE",(String) request.getParameter("TE"));
session.setAttribute("IR",(String) request.getParameter("IR"));
       
%>

<html>
<head>
<title>PETDB - Download Feedback</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="expires" content="0">
<script  type= "JavaScript" src=js/windows.js></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function isEmailAddr(email)
{
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

function validate(theForm)
{	   
   if( !theForm.email.value )
        return true;
    
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

</script>
</head>
<%  if(session.getAttribute("Method") == null)
    throw new RuntimeException("Permission denied!");
 
 String refNum = request.getParameter("refNum");
%>
<body class="pop">
<form name="downloadFeedback" method="POST" action="disclaimer.jsp" onsubmit="return validate(this)">
  <table class="basic" style="width:100%;">
  <tr><td><h1>PetDB Download Feedback</h1>
  <br>
  <h3>To better track use of the PetDB data collection, we would like to know how you will use the dataset that you are about to download:</h3>
  
  </td></tr>
  <tr>
  <td><input type="radio" name="purpose" value="research" checked/>Research</td>
  </tr>
  <tr>
    <td><input type="radio" name="purpose" value="education"/>Education (ie. lectures, course work)</td>
  </tr>
   <tr>
    <td><input type="radio" name="purpose" value="commercial"/>Commercial Use</td>
  </tr>
    <tr>
    <td><input type="radio" name="purpose" value="other"/>Other</td>
  </tr>  
  <tr>
  <td>Provide your email:</td>
  </tr>
  <tr>
  <td><input type="text" maxlength="200" size="60" id="email" name = "email"/></td>
  </tr> 
   <tr>
  <td><INPUT TYPE="SUBMIT" NAME="Send" VALUE="Send"></td>
  </tr> 
  </table> 
</form>
 <div id="errorMsg"></div>
</body>
</html>
