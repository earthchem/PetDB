<%@ page import="java.util.*" %>
<%@ page import="java.lang.*" %>

<%
	if ( session.getAttribute("counter") != null ) 
		session.removeAttribute("counter");
	
	// check if the user is authorized!
	if (session.getAttribute("userAuthorized") == null) 
	{
		out.write("xxxxxx");
		 response.sendRedirect("index.jsp");
		 return;	
	}
		
	// run Configurator.populateConfig in a thread, and wait for it to finish.
	// This makes sure that when the working frames are loadded the configuration is all ready to serve.
	Thread configThread = new Thread(new Runnable(){
					public void run() {
						admin.config.Configurator.populateConfig();
					}
				});
	configThread.start();
	configThread.join(); 


	// discreminate the parameters passed to the main framed and pass them correspondly 
	// to the edit or display frame.
 
	String edit_parameters = "";
	String dis_parameters = "";
	for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) 
	{
		String name = (String)e.nextElement();
		String param_tuple =name+"="+((String)request.getParameter(name));
		if (!(name.indexOf("e_")<0))	
			if (edit_parameters.length() == 0) 
				edit_parameters +="?"+param_tuple;
			else 
				edit_parameters +="&"+param_tuple;
		if (!(name.indexOf("d_")<0))
			if (dis_parameters.length() == 0) 
				dis_parameters +="?"+param_tuple;
			else 
				dis_parameters +="&"+param_tuple;
	}

%>
<html>
	<head>
	<meta http-equiv="content-type" content="text/html;charset=ISO-8859-1">
	<title>PetDB MAnagment Site</title>
	</head>
	<frameset rows="50px,*">
        <frame src="topFrame.html"/>
	    <frameset cols="50%,*" border="2" frameborder="yes" style="background-color:#B5EAAA;border-top:2px solid;border-bottom:2px solid;"  id="oldFrameset">
			<frame src="edit_frame.jsp<%= edit_parameters %>" name="edit" scrolling="yes" style="height:100%;background-color:#C3FDB8;">
			<frame src="display_frame.jsp<%= dis_parameters %>" name="display" scrolling="yes"  >
		</frameset>
	</frameset>

</html>

