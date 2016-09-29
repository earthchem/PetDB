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

<p>This web site is designed for both public and academic use. Use of digital materials obtained from PetDB is for non-commercial purposes only. Data are licensed under Creative Commons Attribution-NonCommercial-Share Alike 3.0 United States [CC BY-NC-SA 3.0].</p>

<p>You may browse freely, but you may not circulate or publish materials you obtained from this site if you do not accept the terms of providing adequate citation.</p>
<h3>How to Cite</h3>

<p>Please cite the specific download from PetDB by giving the date and, if possible, parameters of the download. Here is an example:</p>

<p>"The data were downloaded from the PetDB Database (www.earthchem.org/petdb) on 31 August, 2016, using the following parameters: feature name = Gakkel Ridge and rock classification= basalt."</p>

<p>In addition, you should also cite the original scientists who contributed to the downloaded dataset. We also strongly encourage that you create a secondary bibliography for work that uses large datasets. You can easily download all of the references that contributed to a dataset for this secondary bibliography. Many journals will accept a secondary bibliography as a supplementary material file, and this type of citation helps ensure that the hard work performed by members of our community is acknowledged properly.</p> 

<h3>Accuracy</h3>

<p>We make no claims to accuracy of the data identification, acquisition parameters, processing methods, navigation, or database entries or reference information. We ask that you notify us immediately of any contributions that contain objectionable material.
</p>

<h3>Acknowledgment</h3>

<p>PetDB is supported by the National Science Foundation (NSF) as part of the IEDA data facility.


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