	<%@ page import="admin.dbAccess.*" %>
        <%@ page import="admin.config.*" %>
        <%@ page import="admin.data.*" %>
        <%@ page import="java.util.*" %>
        <%
		
		// Variables that read their values from users entries and pass them 
		// to the persistent objects in the session.
		// Most of them are in both display_frame.jsp and edit_frame.jsp
		// assigned respectively so that the statically included  jsp  use the same
		// variable names.
		
		String tn = "e_tn";
		String ss = "e_ss";	
		String id = "e_id";
		String rs_c1_n = "e_U";
		String rs_c1_v = UpdateableRecordSearch.EDIT;
		String rs_c2_n = "e_I";
		String rs_c2_v = UpdateableRecordSearch.NEW;
		String rs_c3_n = "e_D";
		String rs_c3_v = UpdateableRecordSearch.DELETE;
		String rs_c4_n = "e_C";
		String rs_c4_v = UpdateableRecordSearch.CLEAN_REF;
		String rc_c1_n = "e_commit";
		String COMMIT = "COMMIT";
		String rc_c1_v = COMMIT;
		String CANCEL = "CANCEL";
		String rc_c2_n = "e_cancel";
		String rc_c2_v = "CANCEL";
		String nextPage = "admin_frame.jsp"; //"edit_frame.jsp";
		String tablesList = "edit";
		boolean edit = true;
		int actionResult = -1;

		QueryResultSet multiSrcRS, singleSrcRS;
		

		if ( session.getAttribute("counter") != null )
                	session.removeAttribute("counter");

        	if (session.getAttribute("userAuthorized") == null)
        	{
                 response.sendRedirect("index.jsp");
                 return;
        	}

		FrameData frameData = (FrameData)session.getAttribute("editableFD");
		if (frameData == null){
			session.setAttribute("editableFD", new EditableFrameData());
                	frameData = (FrameData)session.getAttribute("editableFD");
                }
                
		String tN =  request.getParameter(tn);
                if (tN != null) frameData.setTlbName(tN);
		if (frameData.getTlbName() == "") 
			frameData.setTlbName((String)Configurator.tablesList.elementAt(0));
			

                String sS = request.getParameter(ss);
                if (sS != null) 
                	try { frameData.setMltSearchStr(sS); }
                        catch (Exception e ) { out.write(e.getMessage()); }
                
		String recID = request.getParameter(id);
                if (recID != null) 
                	try { frameData.setSglSearchStr(recID); }
                	catch (Exception e ) { out.write(e.getMessage()); }
                        
		if (request.getParameter(rs_c1_n) != null ) {
			((EditableFrameData)frameData).setAction(request.getParameter(rs_c1_n));
		}

		if (request.getParameter(rs_c2_n) != null ) {
			((EditableFrameData)frameData).setAction(request.getParameter(rs_c2_n));
		}

		if (request.getParameter(rs_c3_n) != null) {
			((EditableFrameData)frameData).setAction(request.getParameter(rs_c3_n));
		}
		if (request.getParameter(rs_c4_n) != null) {
            
			((EditableFrameData)frameData).setAction(request.getParameter(rs_c4_n));
		}
		String state ="No COMMIT";
		if ( COMMIT.equals(request.getParameter(rc_c1_n)) ) {
			state =" COMMIT in Request";
            Executable ex= ((EditableFrameData)frameData).getExecutable();
		    if (!UpdateableRecordSearch.DELETE.equals(((EditableFrameData)frameData).getSglSearchAction())) 
            {
               		Vector columns = ex.getColumns();
               		for (int i =0; i<columns.size(); i++) 
                    {
                       	String c = (String)columns.elementAt(i);
                       	String v = request.getParameter("e_"+c);
                        state +=v+" : ";
                       	ex.setValue(i,v);
               		}
            }
		    actionResult = (ex.runAction())?1:0;
		    frameData.setMltSearchChanged(true);
		    frameData.setMltSearchStr(frameData.getMltSearchStr());
		}
		
		if (CANCEL.equals(request.getParameter(rc_c2_n)) ) {
			((EditableFrameData)frameData).setAction(request.getParameter(rc_c2_n));
		}

		String s = ((EditableFrameData)frameData).getUpdateableRec().getMessage();
		
		
		multiSrcRS = frameData.getMltSearchRS();
                singleSrcRS = frameData.getSglSearchRS();

                
	%>
<html>
<head>
<script type="text/javascript" src="js/institution.js"></script>  
</head>

<body bgcolor="#B9B997" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" link="#003333" vlink="#333399" alin
k="#99FFFF">
    <LINK rel=stylesheet HREF="presentation_style.css" TYPE="text/css">
	<%@ include file="tables_list.jspf"%>
	<P>
	<%@ include file="search_subpanel.jspf" %>
	<% 
	if (  (multiSrcRS == null) || (multiSrcRS.getRecordCount() == 0) )
	{ 
		;
		
	} else { 
	%>
		<%@ include file="results_subpanel.jspf"%>
	<% 	
	} 
	%>
	<P>
	<%= (actionResult==0 ? ((EditableFrameData)frameData).getExecutable().getMessage() :"")%> 
	<P>
	<%
	if ( (singleSrcRS != null)  && 
		(
		 (singleSrcRS.getRecordCount() != 0) 
		 ||
		 (UpdateableRecordSearch.NEW.equals(((EditableFrameData)frameData).getSglSearchAction())) 
		) 
	) {  
	if (!UpdateableRecordSearch.CANCEL.equals(((EditableFrameData)frameData).getSglSearchAction()) )
	{
		if ( UpdateableRecordSearch.DELETE.equals(((EditableFrameData)frameData).getSglSearchAction()) )
		{
	%>
			<%@ include file="record_subpanel.jspf"%>
	<%
		} 
        else  
        {
			if ( ((EditableFrameData)frameData).getExecutable().actionPerformed() )
			{ 
				if (actionResult == 0 ) { 
					out.write( ((EditableFrameData)frameData).getExecutable().getMessage());
				} else {  
	%>
					<%@ include file="record_subpanel.jspf"%>
	<%			}
			} else {
	%>
				<%@ include file="update_record_subpanel.jspf"%>
	<%  
			}
		} 
	}
	} 
	%>
	</html>
