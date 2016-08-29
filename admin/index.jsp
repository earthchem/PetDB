<%@ page import="java.net.HttpURLConnection"%>
<%@ page import="java.net.MalformedURLException"%>
<%@ page import="java.net.URL"%>
<%@ page import="javax.xml.parsers.ParserConfigurationException"%>
<%@ page import="javax.xml.parsers.SAXParser"%>
<%@ page import="javax.xml.parsers.SAXParserFactory"%>
<%@ page import="org.xml.sax.SAXException"%>
<%@ page import="org.xml.sax.helpers.DefaultHandler"%>
<%
String error=request.getParameter("errmsg");
session.invalidate();
%>
<html>
	<head>
		<title>PetDB Management Site</title>
		<meta http-equiv="Content-Type" content="text/html; charset= UTF-8">
	</head>
<LINK rel=stylesheet HREF="presentation_style.css" TYPE="text/css">


<body bgcolor="#B9B997" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" link="#003333" vlink="#333399" alink="#99FFFF">
   
	
	<FORM ACTION="login" METHOD=POST>
  	<TABLE BORDER=0 width="90%" cellpadding="10" cellspacing="0" align="center" >
    		<TR> 
      		<TD colspan="2" class="anotherLabel"><P> Login to PetDB Data Management Services. (Version 2.0.0) </TD>
    		</TR>
    		<TR> 
      		<TD class="regText" width="10%" align="right">User Id:</TD>
      		<TD> 
        		<INPUT TYPE=TEXT SIZE=20 MAXLENGTH=20 NAME="u">
      		</TD>
    		</TR>
    		<TR> 
      		<TD class="regText" align="right">Password:</TD>
      		<TD> 
        		<INPUT TYPE=PASSWORD SIZE=20 MAXLENGTH=20 NAME="p">
      		</TD>
    		</TR>
    		<TR> 
      		<TD colspan="2"><INPUT TYPE=SUBMIT NAME="Login" VALUE="Login"></TD>
    		</TR>
  	</TABLE>
  	<% if(error !=null) { %>
  	<h3><%= error %></h3>
  	<% } %>
	</FORM>
</html>
