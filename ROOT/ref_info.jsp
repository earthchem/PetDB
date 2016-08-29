<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - Reference Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<script  language= "JavaScript" src=js/set_pub.js></script>
<script  language= "JavaScript" src=js/windows.js></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
</head>
<%
	String ref_id = request.getParameter("singlenum");
    if(!PetdbUtil.isInteger(ref_id)) ref_id = null;
        if (ref_id == null) throw new Exception("You are trying to go to Reference Info page, with NO Reference specified");
        Wrapper wrapper = new ReferenceInfoWrapper(ref_id);
        ReferenceInfo1DS  ds1 = (ReferenceInfo1DS)wrapper.getControlList("0");
        ReferenceInfo2DS  ds2 = (ReferenceInfo2DS)wrapper.getControlList("1");

	String authors = "";
	String year = "";
	String title = "";
	String journal = "";
	String locations = "";
	String book = "";
	String editors = "";
	String pubs = "";
	String doi = "";
	String doiURL="N/A";
	String status = "";
	String comment="";
 //   String data_entered = "N";
	boolean first = true;
	if (ds1  != null)
	while (ds1.next())
	{
		if (authors.length() == 0)
			authors += ds1.getValue(ReferenceInfo1DS.Author);
		else  
			authors += "; " + ds1.getValue(ReferenceInfo1DS.Author);
		if (first) 
		{
			year 		= ds1.getValue(ReferenceInfo1DS.Pub_Year);
			title 		= ds1.getValue(ReferenceInfo1DS.Title);
			journal 	= ds1.getValue(ReferenceInfo1DS.Pub_Desc);
			locations 	= ds1.getValue(ReferenceInfo1DS.Locations_Num);
			book 		= ds1.getValue(ReferenceInfo1DS.Book_Title);
			editors 	= ds1.getValue(ReferenceInfo1DS.Editors);
			pubs 		= ds1.getValue(ReferenceInfo1DS.Publisher);
			doi 		= ds1.getValue(ReferenceInfo1DS.Publication_Doi);
			status 		= ds1.getValue(ReferenceInfo1DS.Status);
			comment 	= ds1.getValue(ReferenceInfo1DS.Comment);
      //      data_entered = ds1.getValue(ReferenceInfo1DS.Data_entered);
            if("&nbsp;".equalsIgnoreCase(doi) == false	) 
            {
                doiURL ="<a href='"+PetDBConstants.DOIURL+doi+"' target='_blank'>"+PetDBConstants.DOIURL+doi+"</a>";
            }
			first 		= false;
		} 
			
	}
%>
<body class="pop">
<div class="popTop">
<span class="emphasis">Reference Information </span><% if(request.getParameter("fb") !=null) {%>
<input type="image" src="images/feedback.png" name="feedback" width="80" height="60" onClick="location.href='ref_feedback.jsp?refNum=<%= ref_id %>'"><%}%>
<br />
</div><!-- end popTop -->
  <table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td align="left" valign="middle">
	    <table cellspacing="0" cellpadding="2" border="1" class="regTxt">
          <tr class="pad">
            <th scope="row" align="left">Author:</th>
            <td><%= authors%></td>
          </tr>
          <tr class="regTxt">
            <th scope="row" align="left">Year:</th>
            <td><%= year%></td>
          </tr>
          <tr class="regTxt">
            <th scope="row" align="left">Title:</th>
            <td><%= title%></td>
          </tr>
          <tr class="regTxt">
            <th scope="row" align="left">Journal:</th>
            <td><%= journal%></td>
          </tr>
          <tr class="regTxt">
            <th scope="row" align="left">Book Title:</th>
            <td><%= book%></td>
          </tr>
          <tr class="regTxt">
            <th scope="row" align="left">Editors:</th>
            <td><%= editors%></td>
          </tr>
          <tr class="regTxt">
            <th scope="row" align="left">Publisher:</th>
            <td><%= pubs%></td>
          </tr>
          <tr class="regTxt">
            <th scope="row" align="left">DOI:</th>
            <td><%= doiURL %></td>
          </tr>
        </table></td>
    </tr>
    
    
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
     <tr align="right">
      <td align="left" valign="middle"><b>PetDB Administrative Information</b>
	    <table cellspacing="0" cellpadding="2" border="1" class="regTxt">
          
          <tr class="regTxt">
            <th scope="row" align="left">Data Entry Status:</th>
            <td><%= status%></td>
          </tr>
          <tr class="regTxt">
            <th scope="row" align="left">Data Entry Comment:</th>
            <td><%= comment %></td>
          </tr>
        </table></td>
    </tr>
    
     <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <% if(status.startsWith("COMPLETED")) { %>
	<tr align="right">
      <td align="left" valign="middle"><b>Data Table</b>
        <table cellspacing="0" cellpadding="2" border="1" summary="data table" class="regTxt">
	<form method="POST" action="table_info.jsp" name="form0">
            <input type="hidden" name=singlenum value="">
            <input type="hidden" name=table_num value="">
            <input type="hidden" name=table_title value="">
            <input type="hidden" name=rows value="">
            <input type="hidden" name=items value="">
<%
	if (ds2 !=null)
	while (ds2.next())
	{
		String new_t = "";
		String t = ds2.getValue(ReferenceInfo2DS.Table_Title);
		int ind = -1;
		if ( !((ind = t.indexOf('\'')) <0 ))
		{
			new_t = t.substring(0,ind) + "\\" + t.substring(ind,t.length());
		} else  new_t = t;
			
	
%>
		
          <tr valign="top">
            <td>
              <input type="submit" value="<%= "Table" + ds2.getValue(ReferenceInfo2DS.Table)%>" onClick="SubmitForm0('<%=  ds2.getValue(ReferenceInfo2DS.Table_Num)%>','<%= ds2.getValue(ReferenceInfo2DS.Table)%>', '<%= new_t%>','<%= ds2.getValue(ReferenceInfo2DS.Row_Count)%>','<%= ds2.getValue(ReferenceInfo2DS.Item_Count)%>');">  
&nbsp;&nbsp;            </td>
            <td><%= ds2.getValue(ReferenceInfo2DS.Row_Count)%>&nbsp;rows<br />
      <%= ds2.getValue(ReferenceInfo2DS.Item_Count)%>&nbsp;items</td>
            <td><%= ds2.getValue(ReferenceInfo2DS.Table_Title)%></td>
          </tr>
<%
	}
%>
	</form>
<%
	if ( (locations != null)
	      &&
	     (locations.length() != 0)
	      &&
	     (!locations.equals("&nbsp;"))
	   )
	{
%>
	<form method="POST" action="view_locations.jsp" name="form1">
          <tr valign="top" class="regTxt">
           <td>
            <input type="hidden" name=singlenum value="<%= ref_id%>">
            <input type="hidden" name=title value="<%= authors + ", " + year%>">
            <input type="hidden" name=number value="<%= locations%>">
            
              <input type="submit" value="locations" onClick="SubmitForm1();">&nbsp;&nbsp;
            </td>
            <td> <%= locations%>&nbsp;rows </td>
            <td>&nbsp;</td>
          </tr>
	</form>
<%
	}

	int r = wrapper.closeQueries();
%>
        </table>
      </td>
    </tr>
<% } %>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td valign="middle" align="left">
        <form><input type="button" name="close" value="close window" onClick="window.opener.focus(); window.close();"></form>
      </td>
    </tr>
  </table>
</body>
<script language="JavaScript" src="js/windows.js" type="text/javascript"></script>
</html>
