<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query -  Data Quality Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<script  language= "JavaScript" src=js/set_pub.js></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
</head>
<%
       String method_id = request.getParameter("singlenum");
        if(!PetdbUtil.isInteger(method_id)) method_id = null;
        if (method_id == null) throw new Exception(method_id + " You are trying to go to Method Info page, with NO Method specified");
        Wrapper wrapper = new MethodInfoWrapper(method_id);
        MethodInfo1DS  ds1 = (MethodInfo1DS)wrapper.getControlList("0");
        MethodInfo2DS  ds2 = (MethodInfo2DS)wrapper.getControlList("1");
        MethodInfo3DS  ds3 = (MethodInfo3DS)wrapper.getControlList("2");
        MethodInfo4DS  ds4 = (MethodInfo4DS)wrapper.getControlList("3");
        OrderedChemicals  ds5 = (OrderedChemicals)wrapper.getControlList("4");
        MethodInfo6DS  ds6 = (MethodInfo6DS)wrapper.getControlList("5");
   
	String items = "";
	if (ds5 != null)
	{
		Vector elems = new Vector();
		ds5.getOrderedChemicals(elems);
		for (int i = 0; i< elems.size(); i++)
			if (items.length() == 0)
				items +=elems.elementAt(i);
			else 
				items +="," +elems.elementAt(i);
	}
%>

<body class="pop">
<div class="popTop">
<span class="emphasis">Data Quality Information</span><br />
</div><!-- end popTop -->
<form method="POST" action="" name="">
  <table border="0" cellspacing="0" class="sub2">
<%
	if (ds1.next())
	{
%>
    <tr align="right">
      <td align="left" valign="middle"><b>Method:</b>
	   <table cellspacing="0" cellpadding="2" border="1">
          <tr class="regTxt">
            <td>Code:</td>
            <td><%= ds1.getValue(MethodInfo1DS.Method_code)%>(<%= method_id%>)</td>
          </tr>
          <tr class="regTxt">
            <td>Name:</td>
            <td><%= ds1.getValue(MethodInfo1DS.Method_Name)%></td>
          </tr>
          <tr class="regTxt">
            <td>Location:</td>
            <td><%= ds1.getValue(MethodInfo1DS.Inst)%></td>
          </tr>
          <tr class="regTxt">
            <td>Provided by:</td>
            <td><a href="ref_info.jsp?singlenum=<%=  ds1.getValue(MethodInfo1DS.Reference_Num)%>" target="ref_win" onClick="openWindow2(this,350,750,this.target)"><%= ds1.getValue(MethodInfo1DS.Reference)%> </a></td>
          </tr>
          <tr class="regTxt">
            <td>Comment:</td>
            <td><%= ds1.getValue(MethodInfo1DS.Comment)%></td>
          </tr>
          <tr class="regTxt">
            <td>Items measured:</td>
            <td><%= items%></td>
          </tr>
        </table></td>
    </tr>
<%
	}
%>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
	<tr align="right">
      <td align="left" valign="middle"><b>Precision:</b>
        <table cellspacing="0" cellpadding="2" border="1" >
          <tr class="regTxt">
            <th width="60" align="left">Item</th>
            <th width="100" align="left">Precision type</th>
            <th width="80" align="left">Minimum</th>
            <th width="80" align="left">Maximum</th>
          </tr>
<%
	while (ds2.next())
	{
%>
          <tr valign="top" class="regTxt">
            <td> <%= ds2.getValue(MethodInfo2DS.Item)%>&nbsp;</td>
            <td> <%= ds2.getValue(MethodInfo2DS.Type)%>&nbsp;</td>
            <td> <%= ds2.getDoubleValue(MethodInfo2DS.Min)%>&nbsp;</td>
            <td> <%= ds2.getDoubleValue(MethodInfo2DS.Max)%>&nbsp;</td>
          </tr>
<%
	}
%>
        </table>
      </td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle"><b>Standard sample measurement:</b>
        <table cellspacing="0" cellpadding="2" border="1" >
          <tr  class="regTxt">
            <th width="60" align="left">Item</th>
            <th width="100" align="left">sample name</th>
            <th width="80" align="left">value</th>
            <th width="50" align="left">stdev</th>
            <th width="50" align="left">stdev type</th>
            <th width="40" align="left">Unit</th>
          </tr>
<%
	while (ds3.next()) {
%>
          <tr valign="top" class="regTxt">
            <td> <%= ds3.getValue(MethodInfo3DS.Code)%>&nbsp;</td>
            <td> <%= ds3.getValue(MethodInfo3DS.Name)%>&nbsp;</td>
            <td> <%= ds3.getValue(MethodInfo3DS.Val)%>&nbsp;</td>
            <td> <%= ds3.getValue(MethodInfo3DS.Stdev)%>&nbsp;</td>
            <td> <%= ds3.getValue(MethodInfo3DS.Type)%>&nbsp;</td>
            <td> <%= ds3.getValue(MethodInfo3DS.Unit)%>&nbsp;</td>
          </tr>
<%
	}
%>
        </table></td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle"><b>Measured values have been normalized
          to:</b>        <table cellspacing="0" cellpadding="2" border="1" >
          <tr  class="regTxt">
            <th width="60" align="left">Item</th>
            <th width="100" align="left">Standard</th>
            <th width="80" align="left">value</th>
          </tr>
<%
	while (ds4.next()) {
%>
          <tr valign="top" class="regTxt">
            <td> <%= ds4.getValue(MethodInfo4DS.Code)%>&nbsp;</td>
            <td> <%= ds4.getValue(MethodInfo4DS.Name)%>&nbsp;</td>
            <td> <%= ds4.getValue(MethodInfo4DS.Value)%>&nbsp;</td>
          </tr>
<%
	}

%>
        </table></td>
    </tr>
  
   <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
  <tr align="right">
      <td align="left" valign="middle"><b>Fractionation correction (Isotopes):</b>
        <table cellspacing="0" cellpadding="2" border="1" >
          <tr  class="regTxt">
            <th width="60" align="left">Item</th>
            <th width="60" align="left">Fcorr Ratio</th>
            <th width="60" align="left">Standard</th>
            <th width="60" align="left">Value</th>
          </tr>
 <%
	while (ds6.next()) {
%>
          <tr valign="top" class="regTxt">
            <td> <%= ds6.getValue(MethodInfo6DS.Code)%>&nbsp;</td>
            <td> <%= ds6.getValue(MethodInfo6DS.Ratio)%>&nbsp;</td>
            <td> <%= ds6.getValue(MethodInfo6DS.Standard)%>&nbsp;</td>
            <td> <%= ds6.getValue(MethodInfo6DS.Value)%>&nbsp;</td>
          </tr>
<%
	}
 	int r =wrapper.closeQueries();
%>
        </table></td>
    </tr>
      
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td valign="middle" align="left">
        <input type="button" name="back" value="close window" onClick="if(window.opener)window.opener.focus(); window.close();">
      </td>
    </tr>
  </table>
</form>
</body>
</html>
