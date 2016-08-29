<%@ include file="headCode.jspf"%>
<% 
// part of page title can be dynamic from criteria?
String pgTitle = "PETDB: Petrological Database of the Ocean Floor - New Data";
String pgType = "info";
boolean search;
CCriteriaCollection c_c_collection;
CombinedCriteria c_criteria;
  if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
  {
          search = false;
  } else
  {
       c_criteria = c_c_collection.getCurrentCCriteria();
       search = c_criteria.isSet();
  }  
  
        String date_filter ="";
	Vector keys = null;  
        Wrapper date_wrapper = WrapperCollection.get(WrapperCollection.DateEnteredWrapper);
        DataSet ds = date_wrapper.getControlList("0");
	if (ds != null)
	{
		keys = ds.getKeys();
		if ((keys != null) && ( keys.size()>0 ) )
			date_filter = (String)keys.elementAt(0);
	}

	Wrapper wrapper = null ;
	Wrapper c_wrapper = null ;
        String state ="";
        String href_str ="new_data.jsp";
        String TheQuery ="sample_ref";
        int test =0;

        boolean previous = false;
        String ind ="";
        if (request.getParameter("orderBy") != null)
        {
                previous = true;
                state += " PREVIOUS";
                ind = (String)request.getParameter("ind");
        } else  {
                TheQuery = "data_ref";
                if (request.getParameter("date_filter") != null)
			 date_filter = request.getParameter("date_filter");
                wrapper   = new ByPubWrapper(date_filter,ReferenceDCtlQuery.Publications);
                state += " request.getParameter(filter_type)";
                session.setAttribute("date_filter",date_filter);
		ind = "ft";
        }

        if (previous)
	{
		date_filter = (String)session.getAttribute("date_filter");
                ds = (DataSet)session.getAttribute(ind+"orig_ds");
         }
	else
                if (wrapper != null)
                        ds  = wrapper.getControlList("0");

        c_wrapper = new ByPubWrapper(date_filter,ReferenceDCtlQuery.Contributions);
        DataSet c_ds = c_wrapper.getControlList("0");
	session.removeAttribute(ind+"orig_ds");
        session.setAttribute(ind+"orig_ds",ds);

        state += " DS = " + ds;

%>
<%@ include file="head.jsp" %>
<script LANGUAGE="JavaScript">
document.title = "<%=pgTitle%>";
</script>
<div class="pad" align="left">
<br />
<h1>New Datasets</h1>
			 <table width="100%" cellpadding="3" cellspacing="0" class="content">
				<tr>
				  <td colspan="2" nowrap><img src="images/shim.gif" height="1" width="5" border="0"></td>
				</tr>
				<tr valign="top">
				  <td colspan="2" align="left" valign="top" class="emphasis">Data Contributions: </td>
				</tr>
				<tr class="regTxt" valign="top">
				  <td colspan="2" align="left" valign="top">
					<%@ include file="contributions_details.jspf"%>
				</td>
				</tr>
				<tr class="regTxt" valign="top">
				  <td colspan="2" align="center" valign="top">&nbsp;</td>
				</tr>
				<form name="date_filter" method="POST" action="new_data.jsp">
				  <tr valign="top">
					<td align="left" valign="top" class="emphasis">Publications:</td>
					<td align="right" class="regTxt" nowrap>Find new Publications from&nbsp;
					  <select id="date_filter" name="date_filter" size="1" onChange="submit();">
						<%
				for (int i =0; i< keys.size(); i++) 
			{
					String key  = (String)keys.elementAt(i);
				if (key.equals(date_filter))
				{
		%>
						<option value="<%= key%>" selected> <%= key%> </option>
						<%
						}  else 
				{
		%>
						<option value="<%= key%>" > <%= key%> </option>
						<%
						}
				}
		%>
					  </select>&nbsp;through the current date.&nbsp;&nbsp;&nbsp;</td>
				  </tr>
				</form>
				<tr valign="top">
				  <td colspan="2" align="left" valign="top">
					<%@ include file="reference_list_details.jspf"%>
				  </td>
				</tr>
				<tr valign="top">
				  <td colspan="2" align="left" valign="top" class="regTxt">&nbsp;</td>
				</tr>
				<tr class="regTxt" valign="top">
				  <td colspan="2" align="center" valign="top">&nbsp;</td>
				</tr>
			  </table>
<%
	if (wrapper != null) wrapper.closeQueries();
	if (c_wrapper != null) c_wrapper.closeQueries();
%>

</div><!-- end pad -->
 <%@ include file="footer.jsp" %>
