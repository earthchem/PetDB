<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="javax.naming.NamingException"%>
<%@ page import="petdb.data.Expedition"%>
<%@ page import="petdb.data.ExpeditionWS"%>
<%@ page import="petdb.vocabulary.MeasuredParameterVoc"%>
<%@ page import="petdb.vocabulary.MethodVoc"%>
<%@ page import="petdb.vocabulary.MineralVoc"%>
<%@ page import="petdb.vocabulary.TectonicSettingVoc"%>
<%@ page import="petdb.vocabulary.UnitVoc"%>
<%@ page import="petdb.vocabulary.Vocabulary"%>
<%@ include file="headCode2.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>PETDB query - Expedition Information</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">

<link href="js/JQuery/css/overcast/jquery-ui-1.8.16.custom.css" rel="stylesheet" type="text/css"/> 
<link href="css/petdb.css" rel="stylesheet" type="text/css"/>
<script  language= "JavaScript" src=js/windows.js></script>
<script language="JavaScript" src="js/JQuery/js/jquery-1.6.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="js/JQuery/js/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script>



</head>
<body class="pop">
<div class="popTop">


<%
    String category = request.getParameter("category");
    Vocabulary voc = null;
    String[] columnHeads = null;
    Iterator it = null;
    int size = 0;
    List list = null;
	if(category != null) {
        if(category.equals(Vocabulary.MeasuredParameter.replaceAll(" ",""))) {
            voc = new MeasuredParameterVoc();
            category = Vocabulary.MeasuredParameter;
        } else if (category.equals(Vocabulary.Method)) {
             voc = new MethodVoc();
        } else if (category.equals(Vocabulary.Mineral)) {
             voc = new MineralVoc();
        } else if (category.equals(Vocabulary.TectonicSetting.replaceAll(" ",""))) {
             voc = new TectonicSettingVoc();
             category = Vocabulary.TectonicSetting;
        } else if (category.equals(Vocabulary.Unit)) {
             voc = new UnitVoc();
        }
  
        columnHeads = voc.getColumnHeads();
        list = voc.getList();
        it = list.iterator();
        size = columnHeads.length;
	}
%>
<span class="emphasis"><%= category %></span>
<br />
</div><!-- end popTop -->
 <table class="sub2a">
     <thead>
     <tr class="rowLabel2">
     <% for(int i=0; i < size; i++) {%>
     <td class="pad2"><%= columnHeads[i] %></td>
     <%}%>
     </tr>
     </thead>
     <tbody>
     <%while(it.hasNext()) { String arr[] = (String[])it.next();%>
        <tr class="regTxt">
                   <% for(int i=0; i < size; i++) {%>
                    <td class="pad2"><%= arr[i] %></td>
     <%}%>
           
         </tr>
    <%}%>
        <tbody-->
        </table>         

</body>
</html>
