<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - select Reference list data</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" src=js/windows.js></script>
<script LANGUAGE="JavaScript" TYPE="text/javascript">
<!--
window.focus();
var browsr = navigator.appName;
var plat = navigator.platform.toLowerCase();
var isMacwIE = false;
if((plat.indexOf("mac") > -1)&&(browsr.indexOf("Explorer") > -1)){
isMacwIE = true;
document.write('<font color="red">You are using IE on a Mac. If the server response time is very slow you may experience problems with these popout window settings being properly recorded.</font>');
document.close();
}


function SelectAllSamp(a)
{

	for(var i=0; i<a.length; i++){
			a[i].checked=true;
	}
  if (!a.length) a.checked=true;  
}
function DeSelectAllSamp(a)
{

	for(var i=0; i<a.length; i++){
			a[i].checked=false;
	}
  if (!a.length) a.checked=false;
}

//-->
</SCRIPT>

</head>

<%
        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;
        Criteria criteria;
        String state ="";
        int test =0;
	DataSet ds = null;
    DataSet ds2 = null;
    DataSet ds3 = null;
	Wrapper wrapper = null;

	String href_str = "set_ref_scrn2.jsp";
	String ind = "scr2"; 
        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");

        c_criteria = c_c_collection.getCurrentCCriteria();

        criteria = c_criteria.getCriteria(CombinedCriteria.ByPubCriteria);
	if (request.getParameter("orderBy") != null || request.getParameter("orderBy2")!= null)
	{
		ds = (DataSet)session.getAttribute("srs_ds");
        ds2 = (DataSet)session.getAttribute("srs_ds2");
        ds3 = (DataSet)session.getAttribute("srs_ds3");
		if (ds == null && ds2 == null && ds3 == null) throw new Exception("Your session has expired"); 
	} else 
	{	
		Criteria i_criteria = ((CompositeCriteria)criteria).getSubCriteria();
		i_criteria.setValues(ByPub2Criteria.AUTH,request.getParameterValues("author"));
		i_criteria.setValues(ByPub2Criteria.YEAR,request.getParameterValues("pubyear"));
        	i_criteria.setValues(ByPub2Criteria.JOUR,request.getParameterValues("journal"));
		i_criteria.setValues(ByPub2Criteria.A_ORDER, request.getParameterValues("authororder"));
        	i_criteria.setValues(ByPub2Criteria.KEYWORD, request.getParameterValues("title"));
        	wrapper = criteria.getWrapper();
        	ds      = wrapper.getControlList(ByPubCriteria.REFIDs);
            ds2 = wrapper.getControlList(ByPubCriteria.REFIDs2);
            ds3 = wrapper.getControlList(ByPubCriteria.REFIDs3);
            
        	session.setAttribute("srs_ds",ds);
            session.setAttribute("srs_ds2",ds2);
            session.setAttribute("srs_ds3",ds3);
	}
	state += " DS " + ds;
	String TheQuery = "spec_ref";
	String qr_keys = "";

	Vector keys = ds.getKeys();
        for (int index = 0; index < keys.size(); index++)
        	if (index == 0)	
			qr_keys += keys.elementAt(index);
		else 
			qr_keys += ", " + keys.elementAt(index);
  
        session.removeAttribute(TheQuery);
        session.setAttribute(TheQuery,qr_keys); 
        
        //original the following code is from reference_list_details.jspf 
        int orderBy;
        String order2Status = request.getParameter("orderBy2")==null? "":"orderBy2="+request.getParameter("orderBy2")+"&";
        String orderDesc =order2Status+"orderBy";
        
        if ((""+PubRecord.YEAR).equals(request.getParameter("orderBy")))
                orderBy = PubRecord.YEAR;
        else  orderBy = PubRecord.AUTH;

        if (ds == null) return;
        int index;
        String r_keys= "";
        String c_title= "";
        PubRecordDS pr_ds = (PubRecordDS)ds;
        pr_ds.goFirst();
        int kk = pr_ds.orderBy(orderBy);
        boolean isCompleted = true;	
%>
<body class="pop">
<div class="popTop">
<p class="emphasis"><font size="+1">Select Reference list data</font></p>
<span class="regTxt"><i>If you want to see chemical analyses from other publications on the same
samples in a particular reference, set the reference as a criteria. The checkbox on the left is used to set the particular reference as a query criteria. Click the &quot;Apply&quot; button to finish reference query.</i></span>
</div><!-- end popTop -->

<form name="form1" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
<table class="sub2">
   
    <tr>
          <td width="29%" nowrap><input type=button name=selectall onclick="SelectAllSamp(document.form1.checkboxref);" value="Select All">
          <input type=button name=clearall onclick="DeSelectAllSamp(document.form1.checkboxref);" value="Clear All" ></td>
          <td width="61%">&nbsp;
            <input type="button" name="Submit"  class="importantButton"  value="Apply" onClick="formSubmit();">
            </td>
            <td>
               <input name="download" type="button"  value="Download" title="Download this list of references" onclick="downloadRef('<%= TheQuery%>');">
           </td>
          <td width="10%" align="right"><input name="back" type="button" id="back" value="Back" onClick="javascript:location='set_pub.jsp';"></td>
    </tr>
</table>
   <table class="sub2a">
     <thead>
     <tr class="rowLabel2"><td class="pad2" colspan=5 rowspan=2>Available 
     <% if(pr_ds.getKeys().size()== 0) {%> &nbsp; (No reference found for your selection. Please redefine your selection) <%}%>  
     </td></tr>
     </thead>
     <tbody>
      <% if(pr_ds.getKeys().size() > 0) { %>
        <%@ include file="set_ref_scrn2_details.jspf" %>
      <%}%>
     </tbody>
  
   <%   session.removeAttribute(TheQuery);
        session.setAttribute(TheQuery,r_keys);

        int orderBy2;
        String orderStatus = request.getParameter("orderBy")==null? "":"orderBy="+request.getParameter("orderBy")+"&";
        orderDesc =orderStatus+"orderBy2";
        if ((""+PubRecord.YEAR).equals(request.getParameter("orderBy2")))
                orderBy2 = PubRecord.YEAR;
        else  orderBy2 = PubRecord.AUTH;
        pr_ds = (PubRecordDS)ds2;
        pr_ds.goFirst();
        int kk2 = pr_ds.orderBy(orderBy2);
        isCompleted = false;	
 
   if(pr_ds.getKeys().size()>0) { %>  
    <thead>
    <tr class="rowLabel2"><td class="pad2" colspan=5> In Progress</td></tr>
    </thead>
    <tbody>  
   <%@ include file="set_ref_scrn2_details.jspf" %>    
  <%} %>
   </tbody>  

  <%
        int orderBy3;
         orderStatus = request.getParameter("orderBy")==null? "":"orderBy="+request.getParameter("orderBy")+"&";
        orderDesc =orderStatus+"orderBy3";
        if ((""+PubRecord.YEAR).equals(request.getParameter("orderBy3")))
                orderBy3 = PubRecord.YEAR;
        else  orderBy3 = PubRecord.AUTH;
    
        pr_ds = (PubRecordDS)ds3;
        pr_ds.goFirst();
        int kk3 = pr_ds.orderBy(orderBy3);
        isCompleted = false;	
  if(pr_ds.getKeys().size()>0) { %>  
    <thead>
    <tr class="rowLabel2"><td class="pad2" colspan=5>Alert</td></tr>
    </thead>
    <tbody>  
   <%@ include file="set_ref_scrn2_details.jspf" %>  
   </tbody> 
    <%}%>
   </table>  
</form>
 <% if (wrapper != null) wrapper.closeQueries(); %> 
 
<script src="js/JQuery/js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
<!--
function formSubmit()
{
//if (!isMacwIE)alert(isMacwIE);
document.form1.target = "MAIN_WINDOW";
document.form1.submit();
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

function downloadRef(thequery)
{	
	my_form=document.createElement('FORM');
	my_form.name='download_frm';
	my_form.method='POST';
	my_form.action='ReferenceDownload';

	my_input1=document.createElement('INPUT');
	my_input1.type='hidden';
	my_input1.name='filtered';
	my_input1.value=thequery;
	
	my_form.appendChild(my_input1);
	document.body.appendChild(my_form);
	my_form.submit();

}
//Wait for document is ready
$(document).ready(function(){
	$('.sub2a tbody tr:odd td').css('background-color','#FFFFFF');
});

//-->
</script>
</body>
</html>
