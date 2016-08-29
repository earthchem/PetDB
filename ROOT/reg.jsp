<%@ include file="headCode.jspf"%>
<% 
String pgTitle = "PETDB: Petrological Database of the Ocean Floor - Registration";
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
  
  String state = null; //(String)session.getAttribute("user_reg_stat");
%>
<%@ include file="head.jsp" %>
<script  language= "JavaScript" src=js/windows.js></script>
<script LANGUAGE="JavaScript">
window.name = "MAIN_WINDOW";
document.title = "<%=pgTitle%>";
</script>
<div class="pad" align="left">
<br />
<h1>Register for PETDB Updates</h1>
<p class="regTxt">Please, register with PetDB to receive updates about new data and features as they become available.</p>
<form name="form1" method="POST" action="submit_reg.jsp" onsubmit="return FormValidator(this)">
    <table width="98%" valign="top" cellpadding="3" cellspacing="0" class="content">
			<%
				if (state != null) 
				{
			%>
			    <tr>
				<%= state%>
			    </tr>
			    <tr>
			    </tr>
			<% 	
				}
			%>
<tr>
          <td colspan="3" valign="top" class="keyword"></td>
			  </tr>
			  <tr align="left" valign="top" class="regTxt">
			    <td colspan="3" valign="top" class="keyword">First Name:<br />
			      <input name="firstname" type="text" id="firstname" size="30" maxlength="40"></td>
		    </tr>
			  <tr align="left" valign="top">
			    <td colspan="3" valign="top" class="keyword" height="4"></td>
			    </tr>
			  <tr align="left" valign="top" class="regTxt">
			    <td colspan="3" valign="top" class="keyword">Last Name:<br /><input name="lastname" type="text" id="lastname" size="50" maxlength="60"></td>
			    </tr>
			  <tr align="left" valign="top">
			    <td colspan="3" valign="top" class="keyword">&nbsp;</td>
			    </tr>
			  <tr align="left" valign="top">
			    <td colspan="3" valign="top" class="keyword">Organization:</td>
			    </tr>
			  <tr align="left" valign="top">
			    <td colspan="3" valign="top" class="keyword"><input name="organizatn" type="text" id="organizatn" size="95" maxlength="200"></td>
			    </tr>
			  <tr align="left" valign="top">
			    <td valign="top" class="keyword">&nbsp;</td>
			    <td colspan="2" valign="top" align="right" class="keyword">&nbsp;</td>
			    </tr>
			  <tr align="left" valign="top">
			    <td valign="top" class="keyword">Email:<br />
			      <input name="email" type="text" id="email" size="50" maxlength="100">
			      </td>
			    <td colspan="2" valign="bottom" align="right" class="keyword"><input type="submit" name="Submit" value="Submit"></td>
			  </tr>
            </table>
		</form>
        </div><!-- end pad -->
 <%@ include file="footer.jsp" %>

<script Language="JavaScript">
function isEmailAddr(email)
{
  var result = false
  var theStr = new String(email)
  var index = theStr.indexOf("@");
  if (index > 0)
  {
    var pindex = theStr.indexOf(".",index);
    if ((pindex > index+1) && (theStr.length > pindex+1))
	result = true;
  }
  return result;
}

function FormValidator(theForm)
{

  if (theForm.email.value == "")
  {
    alert("Please enter a value for the \"email\" field.");
    theForm.email.focus();
    return (false);
  }

  if (!isEmailAddr(theForm.email.value))
  {
    alert("Please enter a complete email address in the form: yourname@yourdomain.com");
    theForm.email.focus();
    return (false);
  }
   
  if (theForm.email.value.length < 3)
  {
    alert("Please enter at least 3 characters in the \"email\" field.");
    theForm.email.focus();
    return (false);
  }
  return (true);
}
</script>
