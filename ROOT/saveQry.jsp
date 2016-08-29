<%@ include file="headCode2.jspf"%>
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
  String savedNames = "";


%>
<html>
<head>
<title>PETDB query - Save Session Query</title>
<script  language= "JavaScript" src=js/windows.js></script>
<script  language= "JavaScript" src=js/set_pub.js></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">

</head>
<body class="pop" style="width:550px">
<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<div class="popTop" >
<span class="emphasis">Save Query</span><br />
        <span class="smTxt">This window allows users to save queries created <i>in this session</i>. A session expires after 30 minutes of inactivity; saved queries will be lost when a user closes their browser or the session expires.</span></td>
</div><!-- end popTop -->
  <table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td align="left" valign="middle" class="smTxt"><input name="session" type="checkbox" value="" checked onClick="this.blur(); return false;">&nbsp;
      Session Query &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <input name="user" type="checkbox" value="" onClick="this.blur(); return false;">&nbsp;
<% // this should ultimately be conditional - no checkbox should appear unless a user is logged in, only the link. (not logged in? Click <a href="#">here</a> to register) %>
      User Query (not yet available)</td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle" class="regTxt">
	    Name your query (max 100 characters)<br />
	    <input name="saveQry" type="text" size="55" maxlength="100">
	  </td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle">      </td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle" class="regTxt">Please create a unique
        name. Currently you have the following saved queries:<br />
        <font color="#666600">Session Queries</font>:<br /><blockquote>
<%
	if (savedCCriterias != null)
	{
		for (int i=0; i<savedCCriterias.size(); i++)
		{
			if (!savedCCriterias.elementAt(i).equals(CCriteriaCollection.CurrentCCriteria))
			{
      savedNames += savedCCriterias.elementAt(i);
%>
          <%= savedCCriterias.elementAt(i)%><br />
<%
			}
		}
		if (savedCCriterias.size() == 1)
		{
%>
	No Query Saved so far.
<%
		}
	}
%>
          </blockquote>
</td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td valign="middle" align="left">
	    <input name="Save" type="button" value="Save" onClick="formSubmit();" class="importantButton">&nbsp;&nbsp;
	    <input type="button" name="back" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();">
      </td>
    </tr>
  </table>
</form>
</body>
<SCRIPT TYPE="text/javascript">
function formSubmit()
{
  if (chkName()){
  document.form1.target = "MAIN_WINDOW";
  document.form1.submit();
  this.window.close();
  return false;
  }
}

function chkName(){
var savedString = "<%=savedNames%>";
savedString = savedString.toLowerCase();
var newSave = document.form1.saveQry.value;
var idx = savedString.indexOf(newSave.toLowerCase());
var msg = "The name you have chosen to save your query under is already in use.\n" +
          "Do you wish to overwrite the saved query? \nIf not, choose CANCEL and enter another name.";
  if (idx != -1){
    if(confirm(msg)){
    return true;
    }
    else{ 
    return false;
    }
  }
 else {return true;}
}
</SCRIPT>

</html>
