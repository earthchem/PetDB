<%@ include file="headCode2.jspf"%>
<html>
<head>
<%

        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;

        //ccColl is the session place holder for the CombinedCriteriaCollaction object
        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");
        }
        String ipAddress = IPAddress.getIpAddr(request);
        new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Save Query',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
        Vector savedCCriterias = (Vector)c_c_collection.getSavedCCriterias();
        c_criteria = c_c_collection.getCurrentCCriteria();


%>
<title>PETDB query - retreive your saved Query</title>
<script  language= "JavaScript" src=js/windows.js></script>
<script  language= "JavaScript" src=js/set_pub.js></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
</head>
<body class="pop" style="width:600px">
<div class="popTop">
<span class="emphasis">Retrieve Query</span><br />
        <p class="regTxt">This window allows users to retrieve queries created<i> in this session</i> (<b><font color="#666600">Session
        Queries</font></b>) or, once user registration and login services have been set up, queries saved
        in previous visits to PETDB (<b><font color="#666600">User Queries</font></b>).
        Currently sessions are set to expire after 30 minutes of inactivity;
        any saved <b><font color="#666600">session
        queries</font></b> will be lost when a user closes their
        browser or allows a session to expire.</p>
        </div>
<form method="POST" action="pg2.jsp" target="MAIN_WINDOW"  name="form1">
  <table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td align="left" valign="middle" class="regTxt">
	    Select query<br />
	    <select name="retriveQry" style="width:320px;overflow:auto;">
<%
		if (savedCCriterias != null)
		{
			for (int i=0; i< savedCCriterias.size(); i++)
			{
				if (!((String)savedCCriterias.elementAt(i)).equals(CCriteriaCollection.CurrentCCriteria))
				{
%>
	    <option name="<%=savedCCriterias.elementAt(i)%>"><%= savedCCriterias.elementAt(i)%></option>
 <%
				}
			}
		}
%>  
        </select>
	  </td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle">      </td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr>
      <td valign="middle" align="left">
	    <input name="Retrieve" type="button" value="Retrieve" onClick="formSubmit();">&nbsp;&nbsp;
	    <input type="button" name="back" value="close window" onClick="window.opener.focus(); window.close();">
      </td>
    </tr>
  </table>
</form>
</body>
<SCRIPT TYPE="text/javascript">
function formSubmit()
{
document.form1.target = "MAIN_WINDOW";
document.form1.submit();
this.window.close();
return false;
}
</SCRIPT>
</html>
