<%@ page import="java.net.URLEncoder"%>
<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select/view Samples </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>
<script LANGUAGE="JavaScript">

function SelectAllSamp(a)
{

	for(var i=0; i<a.length; i++){
			a[i].checked=true;
	}
}
function DeSelectAllSamp(a)
{


	for(var i=0; i<a.length; i++){
			a[i].checked=false;
	}
}

</script>
</head>
<%
        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;
        String state ="";
        String    errorMSG="no";
        SampleIDDS ds=null;
        BySampleIDWrapper wrapper=null;
        //ccColl is the session place holder for the CombinedCriteriaCollaction object
        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
            String type = request.getParameter("type");
            if( type == null)
            {            
              errorMSG="Your Session has EXPIRED. Please go to index.jsp and start over.";
            }
            else //from GeoMapApp
		    {	
        	       /* GeoMapApp pass in sample id. So get sample_num then forward to sample_info page */
			       String srch_val = request.getParameter("srch_value");
			       wrapper = new BySampleIDWrapper(srch_val,type);
			       ds = (SampleIDDS) wrapper.getControlList("0");
			       
			       String sample_num=null;
			       while(ds.next())
			       {
			    	   sample_num = ds.getValue(SampleIDDS.KEY);
			    	   break;
			       }
                   wrapper.closeQueries();
                   String sid = new SimpleQuery("select sample_id from sample where SAMPLE_num ="+sample_num).getSingleResult();
			       String forwardURI="sample_info.jsp?sampleID="+sid;
			       response.sendRedirect(forwardURI);
		     }
        }
        else {
            
	       c_criteria = c_c_collection.getCurrentCCriteria();

	       String type  = SampleIDDCtlQuery.View; 
	
	       if ( (type = request.getParameter("type")) == null)
	       {
	        	type = SampleIDDCtlQuery.View;  
                    
        	    Criteria c = c_criteria.getSampleCriteria( request.getSession().getId());
		       if (c == null) throw new Exception("Your Sample Criteria is outdated!"); 
        	   SummaryWrapper summary_wrapper = (SummaryWrapper)c.getWrapper();
               Criteria criteria;
        	   if ( (criteria = c_criteria.getCriteria(CombinedCriteria.BySampleIDCriteria)) == null)
        	   {
			       int test = c_criteria.addCriteria(CombinedCriteria.BySampleIDCriteria,
			 	      new BySampleIDCriteria(summary_wrapper.getQryStr(false)));
               		   criteria = c_criteria.getCriteria(CombinedCriteria.BySampleIDCriteria);
        	   }

        	   wrapper = (BySampleIDWrapper) criteria.getWrapper();
		       wrapper.update(summary_wrapper.getQryStr(false),type);
	      } else
          {
		      if (type.equals(SampleIDDCtlQuery.View))
		      {
        		Criteria c = c_criteria.getSampleCriteria(request.getSession().getId());
			    if (c == null) throw new Exception("Your Sample Criteria is outdated!"); 
        		SummaryWrapper summary_wrapper = (SummaryWrapper)c.getWrapper();
			    wrapper = new BySampleIDWrapper(summary_wrapper.getQryStr(false),type);
		      }
		      else
		      {	
                   String srch_val = request.getParameter("srch_value");
                   String match = request.getParameter("match");
			   //    wrapper = new BySampleIDWrapper(srch_val,type);
                   wrapper = new BySampleIDWrapper(srch_val,type, match);
		      }
	      }
    	  //session.setAttribute("wrapper",wrapper);
    	  if (wrapper == null)  throw new Exception("Your wrapper = null");
          ds = (SampleIDDS)wrapper.getControlList("0");
    	  if (ds == null) throw new Exception("There is not dataset"); 
    	}
%>
<body class="pop">
<% if( "no".equalsIgnoreCase(errorMSG) == false) { %>
 <div><%= errorMSG %></div>
<% } else { %>
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<div class="popTop">
<input  name="act" type="hidden" value="include">

<span class="emphasis">List of Samples</span><br />
<span class="regTxt">View the selected samples.</span>
</div><!-- end popTop -->
<table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td colspan="2" valign="middle">
	  <table width="100%" CELLPADDING="1" CELLSPACING="0" class="regTxt">
		<tr class="rowLabel2">
		<th ALIGN="left" nowrap class="pad2">Sample ID</th>
        <th ALIGN="left" nowrap class="pad2">IGSN</th>
		<th ALIGN="left" nowrap class="pad2">Station ID</th>
		<th ALIGN="left" nowrap class="pad2">Rock Description</th>
		<th ALIGN="left" nowrap class="pad2">Data Existence</th>
		<th ALIGN="left" nowrap class="pad2">Alteration</th>
		<th ALIGN="left" nowrap class="pad2">Sampling</th>
		<th ALIGN="left" nowrap class="pad2">Latitude</th>
		<th ALIGN="left" nowrap class="pad2">Longitude</th>
		<th ALIGN="left" nowrap class="pad2">Elevation Min</th>
		<th ALIGN="left" nowrap class="pad2">Elevation Max</th>
		</tr>
<!-- these rows repeat for each set of results-->

<%
	int index;
	if (ds == null) return;
	
	String key 	= "";
	String s_id ="";
	String st_id = "";
	String st_num = "";
	String r_dsc ="";
 	String alt ="";
	String samp ="";
	String lat ="";
	String lng ="";
	String e_min ="";
	String e_max ="";
	String data_e ="";
    String igsn ="";
	boolean any_rec = false;
	while (ds.next())
	{
		any_rec= true;
		if (!key.equals(ds.getValue(SampleIDDS.KEY)))
		{
			if (key.length() != 0 )
			{
%>
	<tr valign="top" class="pad" >
	  <td><a href="sample_info.jsp?sampleID=<%= s_id%>"  onClick="openWindow2(this,700,700,'Sample_Info')"><%= s_id%></a></td>
      <td><% if(!"&nbsp;".equals(igsn)) {%><a href="<%= PetDBConstants.igsnSESARURL+igsn%>"  onClick="openWindow2(this,700,700,'IGSN_Info');window.close();"><%= igsn%></a><%}%></td>
	  <td><a href="statn_info.jsp?singlenum=<%= st_num%>"  onClick="openWindow2(this,700,700,'Station_Info')"><%= st_id%></a></td>
	  <td><%= r_dsc%></td>
	  <td><%= data_e%></td>
	  <td><%= alt%></td>
	  <td><%= samp%></td>
	  <td><%= lat%></td>
	  <td><%= lng%></td>
	  <td><%= e_min%></td>
	  <td><%= e_max%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="10"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<%
			}
		 	key  	= ds.getValue(SampleIDDS.KEY);
		 	s_id 	= ds.getValue(SampleIDDS.SAMPLE_ID);
		 	st_id 	= ds.getValue(SampleIDDS.STATION_ID);
			st_num   = ds.getValue(SampleIDDS.STATION_NUM);
		 	r_dsc 	= ds.getValue(SampleIDDS.ROCK_DESC);
		 	alt 	= ds.getValue(SampleIDDS.ALTERATION);
			samp	= ds.getValue(SampleIDDS.SAMPLING);
			lat 	= ds.getValue(SampleIDDS.LATITUDE);
			lng	= ds.getValue(SampleIDDS.LONGITUDE);
	 		e_min 	= ds.getValue(SampleIDDS.ELEV_MIN);
		 	e_max 	= ds.getValue(SampleIDDS.ELEV_MAX);
		 	data_e 	= ds.getValue(SampleIDDS.DATA_E);
            igsn = ds.getValue(SampleIDDS.IGSN);
		}
		else if (alt.indexOf(ds.getValue(SampleIDDS.ALTERATION)) == -1) {
            alt +="; " + ds.getValue(SampleIDDS.ALTERATION);
        }

	}
 
	if (any_rec)
	{
%>
	<tr valign="top" class="pad" >
    <td><a href='<%= "sample_info.jsp?sampleID="+ URLEncoder.encode(s_id,"UTF-8")%>'  onClick="openWindow2(this,700,700,'Sample_Info');window.close();"><%= s_id%></a></td>
 <%--   
	  <td><a href="sample_info.jsp?sampleID=<%= s_id%>"  onClick="openWindow2(this,700,700,'Sample_Info');window.close();"><%= s_id%></a></td>
--%>      
      <td><% if(!"&nbsp;".equals(igsn)) {%><a href="<%= PetDBConstants.igsnSESARURL+igsn%>"  onClick="openWindow2(this,700,700,'IGSN_Info');window.close();"><%= igsn%></a><%}%></td>
      <td><a href="statn_info.jsp?singlenum=<%= st_num%>" onClick="openWindow2(this,700,700,'Station_Info');window.close();"><%= st_id%></a></td>
	  <td><%= r_dsc%></td>
	  <td><%= data_e%></td>
	  <td><%= alt%></td>
	  <td><%= samp%></td>
	  <td><%= lat%></td>
	  <td><%= lng%></td>
	  <td><%= e_min%></td>
	  <td><%= e_max%></td>
	</tr>
	<tr class="rowLabel5"><td colspan="10"><img src="images/shim.gif" width="5" height="1"></td>
	</tr>
<%
	}
    if( wrapper != null )
	wrapper.closeQueries();
%>
<!-- *** -->
	  </table>
  	  </td>
    </tr>
    <% if(!SampleIDDCtlQuery.Srch_Exped.equals(request.getParameter("type")))  {%>
	<tr><td>
	<script>
	if (window.history.length > 1)document.write('<input name="back" type="button" id="back" value="Back" onClick="javascript:history.back();">&nbsp;&nbsp;&nbsp;');
	</script>
	<input type="button" name="close" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();"></td></tr>
    <% } %>
  </table> 
</form>
<% } %>
</body>
<SCRIPT TYPE="text/javascript">
<!--
function formSubmit(a)
{
var count = 0;
var u_count = 0;
var ok = true; 
	document.form1.act.value = "include";
       	for(var i=0; i<a.length; i++){
                if (a[i].checked)
			count++;
		else u_count++
        }
	if (count >1000)
	{
		ok = false;
		if  (u_count <1000)
		{
			document.form1.act.value = "exclude";
			ok = true;
			for(var i=0; i<a.length; i++){
				a[i].checked = !(a[i].checked)
			}
		}
	}

// see how many are checked!!! swich to unchecked if more then 1000!!!
if (!ok) {
var msg = "\nYou have selected/unselected more than 1000 samples"
		+ "\n Please select/unselect less!";
                confirm(msg);
} 
else 
{
document.form1.target = "MAIN_WINDOW";
document.form1.submit();
this.window.close();
}
return false;
}
//-->
</SCRIPT>
</html>
</html>
