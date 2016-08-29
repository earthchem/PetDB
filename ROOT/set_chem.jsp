<%@ include file="headCode2.jspf"%>
<html>
<%@ include file="chem_parameters.jspf" %>
<head>
<title>Set search filters</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>
<script language="JavaScript">
<!--

window.focus();
var browsr = navigator.appName.toLowerCase();
var plat = navigator.platform.toLowerCase();
var isMacwIE = false;
if((plat.indexOf("mac") > -1)&&(browsr.indexOf("explorer") > -1)){
isMacwIE = true;
document.write('<font color="red">You are using IE on a Mac. If the server response time is very slow you may experience problems with these popout window settings being properly recorded.</font>');
document.close();
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
 var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
   var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
   if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function SelectAllItems()
{

	var a=document.frm_main.ItemNum1;
	for(var i=0; i<a.length; i++){
			a[i].checked=true;
	}
  if (!a.length) document.frm_main.ItemNum1.checked=true;
}
function DeSelectAllItems()
{

	var a=document.frm_main.ItemNum1;
	for(var i=0; i<a.length; i++){
			a[i].checked=false;
	}
	if (!a.length) document.frm_main.ItemNum1.checked=false;
}

//-->
</script>
</head>
<body class="pop">
<div class="popTop">
<span class="emphasis"><font size="+1"><%= title%></font></span><span class="keyword"><%=type_desc%>: <%= item%></span>
</div><!-- end popTop -->
<form method="POST" action="pg3.jsp" target="pg3_win" name="frm_main">
          <table width="90%" border="0" cellspacing="0" class="sub2a" align="left">
                  <input name="criteria_type" type=hidden value="<%= criteria_type%>">
                  <input name="Item" type=hidden value="<%= item%>">
                  <input name="Type" type=hidden value="<%= type%>">
            <tr align="right"> 
              <td colspan="6" align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
            </tr>
                  <tr valign="top"><td colspan="6"><span  class="new">Specify compositional range</span></td></tr>
                  <tr valign="top">
                    <td>
                    <%= measure%>&nbsp;&nbsp;</td>
                    <td> 
                      <select name="calca0">
                        <option value=">=" selected>greater than or equal to 
                        <option value=">">greater than 
                        <option value="=">equal to 
                        <option value="<">less than 
                        <option value="<=">less than or equal to 
                      </select>
                    </td>
                    <td> 
                      <input name="valuea0" size=10 onBlur="checkNumeric(this,'.','-')">
                    </td>
                    <td> &nbsp;and &nbsp;&nbsp;&nbsp;</td>
                    <td> 
                      <select name="calcb0">
                        <option value="<=" selected>less than or equal to 
                        <option value="<">less than 
                        <option value="=">equal to 
                        <option value=">">greater than 
                        <option value=">=">greater than or equal to 
                      </select>
                    </td>
                    <td> 
                      <input name="valueb0" size=10 onBlur="checkNumeric(this,'.','-')">
                    </td>
                  </tr>

<%
	if (mth_flag)
	{
%>
            <tr> 
              <td colspan="6" valign="middle"><br /><span class="new">Select analytical method</span><br />
            </tr>
            <tr> 
              <td colspan="6"> 
                <input type="button" onClick="SelectAllItems()" name="methods" value="Select All" checked>
                Select all methods&nbsp;&nbsp;&nbsp; 
                <input type="button" onClick="DeSelectAllItems()" name="methods" value="Clear All">
                Select none </td>
            </tr>
            <tr width="100%"> 
              <td colspan="6" align="left" width="100%"> 
                <table border="0" class="regTxt">
                  <tr>
 <%
	if (ds != null)
	{
		Vector methods = ds.getKeys();
		if (methods != null)
		for (int i=0; i < methods.size(); i++)
		{
			String key = (String)methods.elementAt(i);
			String val = (String)ds.getValue(key);
%>
                    <td valign=top> 
                      <input type="checkbox"  id="itemcheckbox1" name="ItemNum1" checked value="<%= val%>"> <%= val%></td>
<%
		}
%>
		    </tr>
		    <% if ( methodds !=null ) { 
		           if (methods != null)
		           for (int i=0; i < methods.size(); i++)
		           {
			           String key = (String)methods.elementAt(i);
			           String val = (String)ds.getValue(key);
			           String val2= (String)methodds.getValue(val);
            %>
		    
		    <tr>
		      <td><span style="font-size:12px;"><%= val %>:</span></td>
		      <td><span style="font-size:12px;"><%= val2 %></span></td>
		    </tr>
		    <% } } %>
         </table>
<%	
	}
%>
      </td>
  </tr>
<%
	}// end of: if (mth_flag)
%>
            <tr align="right"> 
              <td colspan="6" align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
            </tr>
            <tr align="right"> 
              <td colspan="6" valign="middle">
 		 <input type="button" name="Submit"  class="importantButton"   value="Apply" onClick="formSubmit()">
              </td>
            </tr>
          </table>
</form>
</body>
</html>
<script>
<!-- Original:  Nannette Thacker -->
<!-- http://www.shiningstar.net -->
<!-- Begin
function checkNumeric(objName,period,hyphen)
{
	var numberfield = objName;
	if (chkNumeric(objName,period,hyphen) == false)
	{
		numberfield.select();
		numberfield.focus();
		return false;
	}
	else
	{
		return true;
	}
}

function chkNumeric(objName,period,hyphen)
{
// only allow 0-9 be entered, plus . and -
var checkOK = "0123456789" + period + hyphen;
var checkStr = objName;
var allValid = true;
var decPoints = 0;
var allNum = "";

for (i = 0;  i < checkStr.value.length;  i++)
{
ch = checkStr.value.charAt(i);
for (j = 0;  j < checkOK.length;  j++)
if (ch == checkOK.charAt(j))
break;
if (j == checkOK.length)
{
allValid = false;
break;
}
if (ch != ",")
allNum += ch;
}
if (!allValid)
{
alertsay = "Please enter only these values \""
alertsay = alertsay + checkOK + "\" in the \"" + checkStr.name + "\" field."
alert(alertsay);
return (false);
}
}

function formSubmit()
{
//if (!isMacwIE)alert(isMacwIE);
document.frm_main.target = "pg3_win";
document.frm_main.submit();
window.focus();
if(!isMacwIE){
window.close();
return;
}
else {
setTimeout("winClose()", 20000); // 60000 ms is 1 minute.
return;
}}

function winClose(){
//alert('finished!'); 
window.close();
return;
}

//-->
</SCRIPT>

