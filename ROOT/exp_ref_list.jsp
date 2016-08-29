<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select Reference list data</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>

</head>
<%
	Wrapper wrapper = null ;
	DataSet ds = null;
    Wrapper wrapper2 = null ; 
	DataSet ds2 = null; 
    boolean isCompleted = false;
    boolean previous = false;

    String href_str ="exp_ref_list.jsp";
    String TheQuery ="exp_ref";	
	String ind ="";   
    
	if (request.getParameter("orderBy") != null)
	{
		previous = true;
		ind = (String)request.getParameter("ind");
	} else if (request.getParameter("filter_type") != null) {
		String filter = request.getParameter("filter"); 
		wrapper = new ByPubWrapper(filter,ReferenceDCtlQuery.Expedition);
        wrapper2 = new ByPubWrapper(filter,ReferenceDCtlQuery.Expedition2);
		ind = "ft";
	}
	

	if (previous) {
		ds = (DataSet)session.getAttribute(ind+"orig_ds");
        ds2 = (DataSet)session.getAttribute(ind+"orig_ds2");
    }
	 else {
		if (wrapper != null) ds  = wrapper.getControlList("0");
        if (wrapper2 != null) ds2  = wrapper2.getControlList("0");
     }

	session.setAttribute(ind+"orig_ds",ds);
	session.setAttribute(ind+"orig_ds2",ds2);
    
    int orderBy;
        String order2Status = request.getParameter("orderBy2")==null? "":"orderBy2="+request.getParameter("orderBy2")+"&";
        String orderDesc =order2Status+"orderBy";
        
        if ((""+PubRecord.YEAR).equals(request.getParameter("orderBy")))
                orderBy = PubRecord.YEAR;
        else  orderBy = PubRecord.AUTH;

        if (ds == null) return;
        int index;
        String r_keys= "";
        String c_title= "";
        PubRecordDS pr_ds = (PubRecordDS)ds;
        pr_ds.goFirst();
        int kk = pr_ds.orderBy(orderBy);
%>
<body class="pop">

<div class="popTop">
<form name="download_frm" method="post" action="ReferenceDownload"><input type ="hidden" name="filtered" value="<%= TheQuery%>">
 <input name="download" type="button" id="download"  value="Download" onClick="submit(); return false;"> Download this list of References.</form><br />
<span class="emphasis">View Reference list data</span><br />
<span class="regTxt">You can get chemistry and location data by clicking the "data
tables" link for each reference.</span>
</div><!-- end popTop -->

 <table class="sub2a">
     <thead>
     <tr class="rowLabel2"><td class="pad2" colspan=5 rowspan=2>Available 
     <% if(pr_ds.getKeys().size()== 0) {%> &nbsp; (No reference found for your selection. Please redefine your selection) <%}%>  
     </td></tr>
     </thead>
     <tbody>
      <% if(pr_ds.getKeys().size() > 0) { %>
        <%@ include file="set_ref_scrn2_details.jspf" %>
      <%}%>
     </tbody>
  
   <%   session.removeAttribute(TheQuery);
        session.setAttribute(TheQuery,r_keys);
   %><%
        int orderBy2;
        String orderStatus = request.getParameter("orderBy")==null? "":"orderBy="+request.getParameter("orderBy")+"&";
        orderDesc =orderStatus+"orderBy2";
        if ((""+PubRecord.YEAR).equals(request.getParameter("orderBy2")))
                orderBy2 = PubRecord.YEAR;
        else  orderBy2 = PubRecord.AUTH;
        pr_ds = (PubRecordDS)ds2;
        pr_ds.goFirst();
        int kk2 = pr_ds.orderBy(orderBy2);
        isCompleted = false;	
 
   if(pr_ds.getKeys().size()>0) { %>  
    <thead>
    <tr class="rowLabel2"><td class="pad2" colspan=5> In Progress</td></tr>
    </thead>
    <tbody>  
   <%@ include file="set_ref_scrn2_details.jspf" %>  
   <% if (wrapper != null) wrapper.closeQueries(); %> 
    </tbody>
   </table>  
   <%}%>
</body>

</html>
