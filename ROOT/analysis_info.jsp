<%@ include file="headCode.jspf"%>
<%@ page errorPage="error.jsp" %>

<html><!-- InstanceBegin template="/Templates/set_pg.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<!-- InstanceBeginEditable name="doctitle" -->
<title>PETDB query - Analyses info</title>
<!-- InstanceEndEditable --><!-- InstanceBeginEditable name="head" -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<!-- InstanceEndEditable -->
<script  language= "JavaScript" src="js/windows.js"></script>
</head>
<body>
<%
       String analysis_id = request.getParameter("singlenum");
        if (analysis_id == null) throw new Exception(analysis_id + " You are trying to go to Analysis Info page, with NO Analysis specified");
        Wrapper wrapper = new AnalysisInfoWrapper(analysis_id);
        AnalysisInfo1DS  ds1 = (AnalysisInfo1DS)wrapper.getControlList("0");
        AnalysisInfo2DS  ds2 = (AnalysisInfo2DS)wrapper.getControlList("1");
	
	String a_num ="";
	String sample_id = "";
	String sample_num = "";
	String ref = "";
	String ref_num = "";
	String method = "";
	String dt_quality_num = "";
	String material ="";
	while (ds1.next())
	{
		a_num =ds1.getValue(AnalysisInfo1DS.Analysis_Num);
	 	sample_id = ds1.getValue(AnalysisInfo1DS.Sample_ID);
	 	sample_num = ds1.getValue(AnalysisInfo1DS.Sample_Num);
	 	ref = ds1.getValue(AnalysisInfo1DS.Reference);
		ref_num = ds1.getValue(AnalysisInfo1DS.Reference_Num);
	 	method = ds1.getValue(AnalysisInfo1DS.Method_Code);
	 	dt_quality_num = ds1.getValue(AnalysisInfo1DS.Data_Quality_Num);
	 	material =ds1.getValue(AnalysisInfo1DS.Material);
	}	
	
%>
<!-- InstanceBeginEditable name="content" -->
  <table width="80%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td><img src="images/shim.gif" width="5" height="5"></td>
      <td><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr>
      <td align="left"><img src="images/shim.gif" width="10" height="5"></td>
      <td width="100%" align="left" class="emphasis"><i> Analyses Information<br /></i></td>
    </tr>
  </table>
  <table border="0" cellspacing="0" class="sub2">
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr>
      <td> </td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle"><b>Analyses:</b>
        <table class="regTxt">
          <tr>
            <td>Analyses_num:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
            <td><%= a_num%> </td>
          </tr>
          <tr>
            <td>Sample:</td>
            <td> <a href="sample_info.jsp?sampleID=<%= sample_id%>" target="_self"> <%= sample_id%></a> </td>
          </tr>
          <tr>
            <td>Reference:</td>
            <td> <a href="ref_info.jsp?singlenum=<%=ref_num%>" target="ref_win" onClick="openWindow2(this,350,750,this.target)"> <%=ref%> </a> </td>
          </tr>
          <tr>
            <td>Method:</td>
            <td> <a href="method_info.jsp?singlenum=<%= dt_quality_num%>" target="_self"> <%= method%></a> </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle"><b>Chemistry:</b>
         <table cellspacing="0" cellpadding="0" border="1" frame="box">
          <tr>
            <th width="50" align="left"><font size="2"> Sample </font></th>
            <th width="100" align="left"><font size="2"> Material </font></th>
<%	
	Vector values = new Vector();
	while (ds2.next())
	{
%>
            <th width="20" align="left"><font size="2"> <%=ds2.getValue(AnalysisInfo2DS.Item_Code)%> </font></th>
<%
		values.add(ds2.getValue(AnalysisInfo2DS.Value));
	}

	int r = wrapper.closeQueries();
%> 
          </tr>
          <tr >
            <td><font size="-1"> <%= sample_id%> </font></td>
            <td><font size="-1"> <%= material%> </font></td>
<%
	for (int i=0; i< values.size(); i++)
	{
%>
            <td><font size="-1"><%= values.elementAt(i)%> </font></td>
<%
	}
%>
          </tr>
        </table>
      <p></td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td valign="middle">
        <input type="button" name="back" value="close window" onClick="window.opener.focus(); window.close();">
      </td>
    </tr>
  </table>
<!-- InstanceEndEditable -->
</body>
<!-- InstanceEnd -->
</html>
