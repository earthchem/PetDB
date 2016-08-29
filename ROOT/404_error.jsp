<%@ include file="headCode.jspf" %>
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
String pgTitle ="PetDB - Error!";
String conPath = request.getContextPath();
%>
<%@ include file="head.jsp" %>
<script LANGUAGE="JavaScript">
document.title = "<%=pgTitle%>";
</script>
<meta http-equiv="refresh" content="3;url="index.jsp">
    <table width="98%" align="left" border="0" cellspacing="0" cellpadding="8">
      <tr> 
        <td valign="bottom">
          <table width="80%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="images/shim.gif" width="5" height="5"></td>
            </tr>
            <tr>
              <td width="100%" align="left" class="emphasis">Your session is expired. Please click <a href="<%= conPath %>" >here</a> to search again.<br></td>
            </tr>
          </table>
        </td>
      </tr>
  </table>
 <%@ include file="footer.jsp" %>
