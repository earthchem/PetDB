<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query -  set Publication Info filters</title>
<script  language= "JavaScript" src=js/windows.js></script>
<script  LANGUAGE= "JavaScript" src=./js/set_pub.js></script>
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">

</head>
<body class="pop" OnLoad="BodyLoadFunction()" style="width:800px">

<%      String ipAddress = IPAddress.getIpAddr(request);
        new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Reference, Author',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;
        Criteria criteria;
        String state ="";
        int test =0;

        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");

        c_criteria = c_c_collection.getCurrentCCriteria();

        if ( (criteria = c_criteria.getCriteria(CombinedCriteria.ByPubCriteria)) == null)
                {
                        test = c_criteria.addCriteria(CombinedCriteria.ByPubCriteria, new ByPubCriteria());
                        criteria = c_criteria.getCriteria(CombinedCriteria.ByPubCriteria);
                }

        criteria = ((CompositeCriteria)criteria).getSubCriteria();
        Wrapper wrapper = criteria.getWrapper();

        DataSet ds;


%>
<div class="popTop">
<span class="emphasis">Publication Look-up</span><br />
        <span class="regTxt">Hold CTRL key ( Command Key on Mac ) for multiple selections</span>
    </div><!-- end popTop -->
<form method="POST" action="set_ref_scrn2.jsp" name="">
<table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td valign="middle">
	  <table width="100%" border="0" cellspacing="0" cellpadding="2">
        <tr>
          <td width="71%" align="left" valign="top" nowrap><b>Author:</b><br /> <span class="regTxt">LastName, FirstName</span><br />

<% 	
	// author
	ds = wrapper.getControlList(ByPub2Criteria.AUTH);
%>
<select id="author" name="author"  size=7 multiple style="width:630px">
<option value="" <%= ((criteria == null) || (!criteria.isSet(ByPub2Criteria.AUTH)) ) ? "selected" : ""%> >No Preference
<%
        Vector v1 =  ds.getKeys();
        for (int i =0; i< v1.size(); i++) {
                String key  = (String)v1.elementAt(i);
                String v = (String)ds.getValue(key);
		if (criteria != null) {
%>
<option value="<%= key%>" <%= (criteria.isSelected(ByPub2Criteria.AUTH,key)) ? "selected" : ""%> ><%= v%></option>
<%
		} else {
%>
	<option value="<%= key%>"><%= v%></option>
<%

		}
	}
%>
</select><br />
<span class="regTxt">First Author&nbsp
<input type="radio" value="1" name="authororder" <%= ( (criteria != null) && (criteria.getValueAsStr(ByPub2Criteria.A_ORDER).equals("1")) ) ? "checked" : "" %> >
YES&nbsp;&nbsp
<input type="radio" value="2" name="authororder" <%= ( (criteria == null) || (criteria.getValueAsStr(ByPub2Criteria.A_ORDER).equals("2")) ) ? "checked" : "" %>>
Don't Care

</span></td>
          <td width="5%" align="left">&nbsp;</td>
          <td width="24%" align="right" valign="top" nowrap><b>Publication Year:</b><br />            <br />
            <b><b>
<%
        // year 
        ds = wrapper.getControlList(ByPub2Criteria.YEAR);
%>

            <select name="pubyear" size=7 multiple>
             <option value="" <%= ((criteria == null)|| (!criteria.isSet(ByPub2Criteria.YEAR)) ) ? "selected" : ""%>>No Preference
              
<%
	Vector vals = (Vector)ds.getValues();
	for (int i =0; i <vals.size(); i++) 
	{
		String key = (String)vals.elementAt(i);
		if (criteria != null)
		{
%>
        <option value="<%= key%>" <%= (criteria.isSelected(ByPub2Criteria.YEAR,key))? "selected" :""%>><%= key%></option>
<%
		} else {
%>
        <option value="<%= key%>"><%= key%></option>
<%
		}
	}
%>
	    </select>
            </b></b></td>
          </tr>
      </table></td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle">&nbsp;</td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle"> <b>Keyword in Title:</b><nobr>

<input name="title" value ="<%= (criteria != null) ? criteria.getValueAsStr(ByPub2Criteria.KEYWORD): ""%>">  <span class="regTxt">(can use word fragment)<br />
</span>  <br />

<%
        // journal 
        ds = wrapper.getControlList(ByPub2Criteria.JOUR);
%>
<b>Journal:</b><nobr>


<select id="journal" name="journal" size=1>
<option value="" <%= ((criteria == null)|| (!criteria.isSet(ByPub2Criteria.JOUR)) ) ? "selected" : ""%>>No Preference</option>
<%
        vals = (Vector)ds.getValues();
        for (int i =0; i <vals.size(); i++)
        {
                String key = (String)vals.elementAt(i);
		if (criteria != null)
		{
%>
        <option value="<%= key%>" <%= (criteria.isSelected(ByPub2Criteria.JOUR,key)) ? "selected" : ""%>><%= key%></option>
<%
		} else { 
%>
        <option value="<%= key%>"><%= key%></option>
<%
        	}
	}
%>

</select>

<br />
<input  name="sendquery" type="hidden" value="T">
<input  name="fromwhere" type="hidden" value="queryreference">
</nobr></td>
    </tr>
<tr align="right">
 <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
 <tr>
      <td align="center" valign="middle" colspan="2"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr>
      <td align="left"><input type="submit" name="Submit" value="continue" class="importantButton">
      </td>
      <td valign="middle" align="right"> <input type="button" name="close" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();">
        <input id="reset1" name="reset1" type="reset" value="Reset"> </td></tr></table>
      </td></tr>

</table>
</form>

</body>
</html>
