         
	<%@ page import="admin.dbAccess.*" %>
        <%@ page import="admin.config.*" %>
        <%@ page import="admin.data.*" %>
        <%@ page import="java.util.*" %>
        <%

		
		String tn = "d_tn";
		String ss = "d_ss";	
		String id = "d_id";
		String rs_c1_n = "d_D";
		String rs_c1_v = "DISPLAY";
		String rs_c2_n = "";
		String rs_c3_n = "";
        String rs_c4_n = "";
		String rs_c2_v = "";
		String rs_c3_v = "";
        String rs_c4_v = "";
		String rc_c1_n = "d_D";
		String rc_c1_v = "DISPLAY";
		String rc_c2_n = "d_D";
		String rc_c2_v = "DISPLAY";
		String rec_label = "OK";
		String nextPage = "admin_ByXenolithQryModel"; //"display_frame.jsp";
		String tablesList = "display";
		boolean edit = false;
		QueryResultSet multiSrcRS, singleSrcRS;
                FrameData frameData = (FrameData)session.getAttribute("displayableFD");
		if ( session.getAttribute("counter") != null )
                	session.removeAttribute("counter");

        	if (session.getAttribute("userAuthorized") == null)
        	{
                	response.sendRedirect("index.jsp");
                 	return;
        	}

                if (frameData == null){
			session.setAttribute("displayableFD", new DisplayableFrameData());
                	frameData = (FrameData)session.getAttribute("displayableFD");
                }

                String tN =  request.getParameter(tn);
                if (tN != null) frameData.setTlbName(tN);   
                if (frameData.getTlbName() == "")
                        frameData.setTlbName((String)Configurator.tablesList.elementAt(0));

                String  sS = request.getParameter(ss);
                if (sS != null) {
				try {
				    frameData.setMltSearchStr(sS);
				} catch (Exception e ) {out.write(e.getMessage()); }
			}
		
		multiSrcRS =frameData.getMltSearchRS();
                
		String recID = request.getParameter(id);
                        if (recID != null) { 
				try {
				 frameData.setSglSearchStr(recID);
				} catch (Exception e ) {out.write(e.getMessage()); }
			}
		
		singleSrcRS = frameData.getSglSearchRS();
	%>

	<html>
<body bgcolor="#B9B997" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" link="#003333" vlink="#333399" alink="#99FFFF">
    <LINK rel=stylesheet HREF="presentation_style.css" TYPE="text/css">
        <FORM name="error" target="_parent">
        </FORM>

	<%@ include file="tables_list.jspf"%>
	<P>
	<%@ include file="search_subpanel.jspf" %>
	<P>
	<% if 	( (multiSrcRS == null) || (multiSrcRS.getRecordCount() == 0) )
	{ 
		;
	} else  {  
		if (!frameData.getTlbName().equals("RegisteredUser"))
		{
	%> 
		<%@ include file="results_subpanel.jspf"%>
	<%
		} else {
	%>
		<%@ include file="table_results.jspf"%>
	<%
		}
	} 
	%>
	<P>
	<% if ( (singleSrcRS == null) || (singleSrcRS.getRecordCount() == 0) )
	 { 	
		;
	 } else  {  %> 
	<%@ include file="record_subpanel.jspf"%>
	<% 
	} 
	%>
	</html>
