<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PetDB Disclaimer</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<body class="pop">
<table><tr><td>

<noscript>
<p>Sorry, You must currently have JavaScript enabled to confirm your agreement with terms of use.</p>
</noscript>

<%
  String email = (String) request.getParameter("email");
  String use = (String) request.getParameter("purpose");
%>

<h1>Terms of use</h1>
<br>
<h3>Use of digital materials obtained from the PetDB is for non-commercial purposes only.</h3>

<p>You may browse freely, but you may not circulate or publish materials you obtained from this site if you do not
accept the terms of providing adequate citation.</p>

<p>This web site is designed for both public and academic use. However, appropriate acknowledgment with a byline/credit/link
<b>must</b> be given to both the original scientists by reference to their relevant publications and to PetDB
(<span class="nobr"><a href="http://www.earthchem.org/petdb" rel="nofollow">www.earthchem.org/petdb</a></span>).</p>

<h3>Accuracy</h3>

<p>We make no claims to accuracy of the data identification, acquisition parameters, processing methods, navigation, or
database entries or reference information. We ask that you <span class="nobr"><a href="mailto:info@earthchem.org" rel="nofollow">notify us</a></span>
immediately of any contributions that contain objectionable material.</p>

<h3>Acknowledgment</h3>

<p>The PetDB is supported by the National Science Foundation
(<span class="nobr"><a href="http://www.nsf.gov" rel="nofollow">NSF</a></span>) as part of the
<span class="nobr"><a href="http://www.iedadata.org" rel="nofollow">IEDA</a></span> data facility. .


<form name="formUse" action="pg4.jsp" method="post">

<% if( (use != null) && (use.length() !=0 ) ){ %>
<input type="hidden" name="purpose" value="<%= use %>" />
<% } %>
<input type="hidden" name="email" value="<%= email %>" />
<input type="hidden" name="sub" value="y" />

<font size="+1" color="red">I agree to the terms of use.</font>
<br><br>
<input type="submit" value="Accept" name = "accept"/>
 <input type="button" value="Decline" onclick="gotoResultPage();"/>
</form>

</td></tr>

<tr><td style="color:red">Note: For large data sets, there will be a delay before the popup window appears. The user does not need to click the "Download" button on the next page.
</td></tr>
</table>
</body>
</html>
<script>
//Wait for document is ready
function gotoResultPage()
{
	console.log('gotoResultPage');
	window.location.href = "pg4.jsp";	
}
	 
</script>