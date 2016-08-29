<%@ include file="headCode2.jspf"%>
<%  String ipAddress = IPAddress.getIpAddr(request);
    new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Sample Look-up',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')"); %>
<html>
<head>
<title>PETDB - Sample Look-up</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<script  language= "JavaScript" src=js/set_pub.js></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
</head>
<body class="pop">
<div class="popTop">
<span class="emphasis">Find a Sample</span><br />
</div><!-- end popTop -->
  <table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td valign="middle">
        <table width="100%" border="0" cellspacing="0" cellpadding="2">
          <tr>
            <td align="left" valign="top" nowrap>
            <form method="POST" action="view_samples2.jsp" name="form1">
                <b><font color="#800000"> Enter Sample Name (full or partial, as given by author/investigator)</font></b><br />
                <select id="match" name="match" size=1>
                     <option value="<%=SampleIDDCtlQuery.equal %>">Equals</option>
                     <option value="<%=SampleIDDCtlQuery.begin %>">Begins with</option>
                     <option value="<%=SampleIDDCtlQuery.end %>">Ends with</option>
                     <option value="contain">Contains</option>
                </select>
                <input name="srch_value">&nbsp;&nbsp;
                <input name="type" type="hidden" value="<%= SampleIDDCtlQuery.Srch_Alias%>">
                <input id="submit1" name="submit1" type="submit" value="Search Sample by Name" class="importantButton">
              </form>
            </td>
          </tr>
          <tr><td>Example: DR3-1</td></tr> 
		  <tr align="right" class="sub2">
            <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="10"></td>
	      </tr>
		  <tr>
            <td width="24%" align="left" valign="top" nowrap>
             <form method="POST" action="view_samples2.jsp" name="form2">
                <b><font color="#800000"> Or Enter PetDB Sample Identifier (full or partial)</font></b><br />
                 <select id="match" name="match" size=1>
                     <option value="<%=SampleIDDCtlQuery.equal %>">Equals</option>
                     <option value="<%=SampleIDDCtlQuery.begin %>">Begins with</option>
                     <option value="<%=SampleIDDCtlQuery.end %>">Ends with</option>
                     <option value="contain">Contains</option>
                </select>
                <input name="srch_value">&nbsp;&nbsp;
                <input name="type" type="hidden" value="<%= SampleIDDCtlQuery.Srch_ID%>">
                <input id="submit1" name="submit1" type="submit" value="Search Sample by ID" class="importantButton">
              </form>
            </td>
          </tr>
           <tr><td>Example: ODP0135-0834B-003R-002/105</td></tr> 
           <tr align="right" class="sub2">
            <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="10"></td>
	      </tr>
            <tr>
            <td width="24%" align="left" valign="top" nowrap>
             <form method="POST" action="view_samples2.jsp" name="form3">
                <b><font color="#800000"> Enter IGSN Identifier (full or partial)</font></b><br />
                 <select id="match" name="match" size=1>
                     <option value="<%=SampleIDDCtlQuery.equal %>">Equals</option>
                     <option value="<%=SampleIDDCtlQuery.begin %>">Begins with</option>
                     <option value="<%=SampleIDDCtlQuery.end %>">Ends with</option>
                     <option value="contain">Contains</option>
                </select>
                <input name="srch_value">&nbsp;&nbsp;
                <input name="type" type="hidden" value="<%= SampleIDDCtlQuery.Srch_IGSN %>">
                <input id="submit1" name="submit1" type="submit" value="Search Sample by IGSN ID" class="importantButton">
              </form>
            </td>
          </tr>
           <tr><td>Example: 55500006A</td></tr> 
            <tr align="right" class="sub2">
            <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="10"></td>
	      </tr>
        </table>
      </td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td align="left">
        <input type="button" name="back" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();">
      </td>
    </tr>
  </table>
</body>
</html>
