<%@ include file="headCode.jspf"%>
<%@page isErrorPage="true" %>

<%

	String pgType = "";

	String s_state ="";
	Wrapper w_wrapper = null;
	Criteria cri = null;
     	
	//session.removeAttribute("ccColl");

 if ( (cri = (Criteria)session.getAttribute("final_criteria")) != null)
	; //s_state +=cri.toString();

 if ( (w_wrapper = (Wrapper)session.getAttribute("final_wrapper")) != null)
		; //s_state += w_wrapper.toString();
 
 if ( (w_wrapper = (Wrapper)session.getAttribute("wrapper")) != null)
		; //s_state += w_wrapper.toString(); 

 s_state += " " + (String)session.getAttribute("state");

boolean search = false; // c_criteria.isSet();
String pgTitle = "PetDB - Error!";
%>
<%@ include file="head.jsp" %>
<script LANGUAGE="JavaScript">
document.title = "<%=pgTitle%>";
</script>
<br />
<div class="popTop">
      <span class="emphasis">ERROR</span><br /><br />
<!-- print error message here (if dynamic), or -->Sorry, the PetDB system has encountered an error.<br />
<blockquote>
<%
if(request.getContextPath().indexOf("alpha") != -1 || request.getContextPath().indexOf(".loc") != -1)
{
    exception.getStackTrace();
%>
<br /><%=exception.getStackTrace()%> OR <br />
<%
}
%>!!!<%= exception.getMessage()%></blockquote>
      Please close your browser and try again later. If problems continue, please contact Support.
</div><!-- close popTop --><br />
 <%@ include file="footer.jsp" %>
