        <%@ page import="petdb.criteria.*" %>
        <%@ page import="petdb.query.*" %>
        <%@ page import="petdb.data.*" %>
        <%@ page import="petdb.wrapper.*" %>
        <%@ page import="java.util.*" %>
        <%@ page import="java.lang.*" %>
        <%@ page errorPage="error.jsp"%>
<%
	
	CCriteriaCollection c_c_collection;  
	CombinedCriteria c_criteria;
	Criteria criteria;
	String state ="";
	int test =0;
        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
		session.setAttribute("ccColl",new CCriteriaCollection());
		c_c_collection =  (CCriteriaCollection)session.getAttribute("ccColl");
        } else {
		session.removeAttribute("ccColl");
		session.setAttribute("ccColl",new CCriteriaCollection());
                c_c_collection =  (CCriteriaCollection)session.getAttribute("ccColl");
	}
	
	
	c_criteria = c_c_collection.getCurrentCCriteria();
	state += c_c_collection + " criteria " + c_criteria;
	session.setAttribute("state",state);


	//---- Get parameters when refered from extarnal links!!
	if (
		( request.getParameterValues("expedition_id") != null)
		)
	{
                if ( (criteria = c_criteria.getCriteria(CombinedCriteria.ByExpCriteria)) == null)
                {
                        test = c_criteria.addCriteria(CombinedCriteria.ByExpCriteria,
					 new ByExpCriteria(ByExpCriteria.EXPNAMEs));
                        criteria = c_criteria.getCriteria(CombinedCriteria.ByExpCriteria);
                }
                criteria.setValues(ByExpCriteria.EXPIDs, request.getParameterValues("expedition_id"));
		test = c_criteria.clearSampleID();
	}
	c_criteria.runCriteria(request.getSession().getId());
	
	criteria = c_criteria.getSampleCriteria(request.getSession().getId());
	Wrapper wrapper = criteria.getWrapper();
	DataSet sDS = wrapper.getControlList("0");
	if ( ((CriteriaDS)sDS).getCount() == 0)
		 throw new Exception("PetDB has no data on this Cruise: "
			+ request.getParameterValues("expedition_id"));
	response.sendRedirect("pg3.jsp");
%>
<html>
<body>
<form>
	wrapper = <%= wrapper%>
	<P>
	sDS = <%= sDS%>
	<P>
	count= <%= ((CriteriaDS)sDS).getCount()%> 
</form>
</body>
</html>
