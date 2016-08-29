<%@ include file="headCode.jspf"%>
<%@ include file="pg3_parameters.jspf" %>
<% String pgTitle = "PETDB: Petrological Database of he Ocean Floor - Get Data"; 
   String pgType = "";
session.setAttribute("state",state);
DataSet ds =summary_wrapper.getControlList("0");
int totalcount = ((CriteriaDS)ds).getCount();/* Used for the criteria summary */
String step3DisplayStr = (String) session.getAttribute("step3Display");
if( step3DisplayStr == null && !"true".equals(request.getParameter("xenolith"))&& !"true".equals(request.getParameter("diamond")) ) step3DisplayStr ="none";
%>
<%@ include file="head.jsp" %>
<script LANGUAGE="JavaScript">
window.name = "pg3_win";
document.title = "<%=pgTitle%>";
var chkdVal = 0;
function Sel_DeSelAllItems(ObjName)
{
	var a=document.form1.elements[ObjName.value];
        for(var i=0; i<a.length; i++){
                        a[i].checked=ObjName.checked;
        }
  if (!a.length) document.form1.elements[ObjName.value].checked=ObjName.checked;      
}

function noWrite(){
  for (i=0; i < document.form1.elements.length; i++){
    if (document.form1.elements[i].name == 'textarea2'){
    alert('You can not edit these values here. Hit "Clear Criteria" to clear all, or use the links next to checkboxes below to add to, or change, these constraints.');
    document.form1.elements[i].blur();
    document.form1.elements[i-1].focus();
    }
  }
  return;
}

function checkForSelectedParameters() {
    chkdVal = 0;
    
    var elements = document.form1.elements;
    for (var i=0; i < elements.length; i++) {
    	//alert(elements[i].name);
        if ((elements[i].name == "MAJ" ||
            elements[i].name == "IR" ||
            elements[i].name == "NGAS" ||
            elements[i].name == "REE" ||
            elements[i].name == "US" ||
            elements[i].name == "VO" ||
            elements[i].name == "TE" ||
            elements[i].name == "IS" ||
            elements[i].name == "AGE" ||
            elements[i].name == "EM" ||
            elements[i].name == "MIN" ||
            elements[i].name == "MODE" ||
            elements[i].name == "MD" ||
            elements[i].name == "RT" ||
            elements[i].name == "SPEC"
            ) &&
            elements[i].checked === true) {
                chkdVal = 1;
        }
    }
}

</script>

<div class="pad" align="left">
<br />
<div>
<!--  Back Button -->
<form id="backform">
<input type="button" value="Back" class="importantButton"title="Use the 'Back' button to return to the previous page" onclick="submitForm('backform','pg2.jsp')"/>
<input value=" " type="hidden" id="placeholder"/>
<input type="button" value="Start New Search" class="importantButton"title="Start New Search" onclick="submitForm('backform','start')"/>
</form>
<h1>PetDB Search</h1>
</div>
<br/>
<!--  Debug block
<div  style="display:none"><!!!!  %= summary_wrapper.getQryStr(true)  %></div>
 -->

<div style="margin:10px;width:880px">

<!-- Summary of the selected criteria -->
<div><h3 class="flip" style="display:inline;">Summary of Step 1 Search Criteria...
<span class="ui-state-default ui-corner-all ui-icon ui-icon-circle-plus" style="position:relative;display:inline-block;" title="Click to see the detail or view samples in map"></span></h3>
<input type="button" name="Submit" value="Submit and View Results" class="importantButton"  style="float:right;"  onClick="ChangeSubmit()"/></div><br/>
<div class="results" id="pg3summary" style="display:none">
			   <%@ include file="reduced_criteria.jspf"%>
			   <p>Search Result: <a href="view_samples2.jsp?type=<%= SampleIDDCtlQuery.View%>" 
					onClick="openWindow2(this,700,900)"><%= totalcount%> samples </a>(<a href="sample_map.jsp" onClick="openWindow2(this,650,850, 'mapresults_win')">view in map</a>)
			   </p>
</div>
</div>
<form name="form1" method="POST" action="pg3.jsp">
<input value="false" type="hidden" name="allMAJ"/>
<input value="false" type="hidden" name="allTE"/>
<input value="false" type="hidden" name="allIR"/>
<input name="DoOnSubmit" type="hidden" value="submit" >
<input name="chem_elements" type="hidden" value="no">

<table style="align:left;border:0;cellspacing:0;cellpadding:0;">

  <tr><th class="emphasis"  style="clear:both;font-size:15px;" align="left" id="pg3_plus">Step 2: Choose type of material (rock analyses, mineral analyses and inclusion analyses). </th></tr>
  <tr ><td><%@ include file="pg3_criteria.jspf"%>
          <% state += "pg3_criteria DONE";
			session.setAttribute("state",state);
		  %> <br/>
					  
       </td>
   </tr>
 <!--  
  <tr><td><div id="busy-box"></div></td></tr>
-->   
   <tr><th class="emphasis" align="left">
           <span id="Step3Title" style="font-size:15px;display:<%=step3DisplayStr%>">Step 3: <% if (criteria_type.equals(DataWrapper.ByDataCriteria)){%>Configure your search result. <%}%>
                                          <% if (criteria_type.equals(DataWrapper.ByMineralCriteria)){%>Select mineral type. <% } %>
           </span>
   </th></tr>
   <tr><td align="left"> 
          <div id="Step3Block" style="display:<%=step3DisplayStr%>"> <!--  Step 3 block not show by default. Will show after analyses is clicked. -->
          <span id="critloading" style="display:none;color:red;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Loading ...</span>
       <!-- Step 3 top selection Table -->
         
          <table class="crit" border="0" cellspacing="0">
            <tr align="left" class="notices">
             <td valign="top"><%@ include file="pg3_c_filter.jspf" %>
		                      <%  state += "pg3_c_filter DONE";
			                  session.setAttribute("state",state);
		                      %>
             </td>
            </tr>
   		    <tr valign="top">
              <td align="left">
                 <table width="100%" > <!-- Chemical Constrains Table -->
                   <tr valign="top">
                    	<%@ include file="pg3_e_filter.jspf"	%>
		                <% state += "pg3_e_filter DONE";
			               session.setAttribute("state",state);
		                %>
                   </tr>
                 </table>
               </td>
              </tr>
           </table>
           </div>
        </td>
   </tr>
   <tr align="left">
                <td valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
   </tr>

</table>
</form>
<div style="margin:10px;width:880px">
<form id="backform" style="display:inline">

<input type="button" name="Submit" value="Submit and View Results" class="importantButton"  style="float:right;"  onClick="ChangeSubmit()"/>
</form>
</div>
</div><!-- end pad -->
<% 
summary_wrapper.closeQueries(); 
wrapper.closeQueries();
%>
 <%@ include file="footer.jsp" %>

<script>
$(".flip .ui-icon-circle-plus").toggle(function() {
	$(this).removeClass("ui-icon-circle-plus");
	$(this).addClass("ui-icon-circle-minus");
	$("#pg3summary").slideToggle("fast");
	}, function() {
	$(this).removeClass("ui-icon-circle-minus");
	$(this).addClass("ui-icon-circle-plus");
	$("#pg3summary").slideToggle("fast");
	});

$("#rock").click(function(){ 
  //turn off crit block
  $(".crit").hide();
  $("#critloading").show();
  console.log("I AM CLICKED.");
});

$("#inclusion").click(function(){ 
	  //turn off crit block
	  $(".crit").hide();
	  $("#critloading").show();
	  console.log("I AM CLICKED.");
	});
	
$("#mineral").click(function(){ 
	  //turn off crit block
	  $(".crit").hide();
	  $("#critloading").show();
	  console.log("I AM CLICKED.");
	});
function SubmitClear()
{
	document.form1.action = "pg3.jsp";
	document.form1.DoOnSubmit.value = "clear";
	/* document.form1.DoOnSubmit.class="importantButton"; */
	document.form1.submit();
	return;
}

function ChangeSubmit()
{
  
    if (!$('input[name="MAJ"][type=checkbox]:not(:checked)').length) 
        $('input[name="allMAJ"]').val("true");

    if (!$('input[name="TE"][type=checkbox]:not(:checked)').length) 
        $('input[name="allTE"]').val("true");
        
    if (!$('input[name="IR"][type=checkbox]:not(:checked)').length) 
        $('input[name="allIR"]').val("true");

	if( !checkCompiledOrIndividual() ) return ;
    checkForSelectedParameters();
    
	if (/* (<%= ((ByChemistryCriteria)criteria).isSet()%>) || */ (chkdVal ==1))
	{
		document.form1.chem_elements.value ="yes";
		document.form1.action = "pg4.jsp";
		document.form1.DoOnSubmit.value = "submit";
		document.form1.submit();
	}
	else 
	{
		msg ="\nNo chemical elements chosen."
			+ "\nPlease, do so, then SUBMIT";
		alert(msg);
	}
	return;
}

/* Check if pre-compiled or individual radio button is checked. If not, ask user to go back and select one */
function checkCompiledOrIndividual()
{   
	var analysesRadioObjs=document.getElementsByName("criteria_type");
	var rlen = analysesRadioObjs.length;
	if(rlen == undefined)
	{  
		var msg="You must choose a analyses criteria.";
		confirm(msg);
		return false;
	}
	var analysesType="";
	for(var i = 0; i < rlen; i++) {
		if(analysesRadioObjs[i].checked) {
		   analysesType = analysesRadioObjs[i].value
		}
	}
    if(analysesType =="")
    {
		var msg="You must choose a analyses criteria.";
		confirm(msg);
		return false;
    }
    if(analysesType=="ByDataCriteria")
    {
	  var radioObj = document.getElementsByName("datatype");
	  var radioLength = radioObj.length;
	  if(radioLength == undefined)
		return false;
	  for(var i = 0; i < radioLength; i++) {
		if(radioObj[i].checked) {
			return true;
		}
	  }
	  var msg ="You must select 'multiple rows per sample' or 'one row per sample' for 'Data Display for sample'."
	  confirm(msg);
	  return false;
    }
    return true;
}

function changeCriteriaType(a, counter){
var ok = false;
var msg ="";
if (!(counter > 0))
{
	msg = "\nNo Analysis Available."
		+ "\n This Radio Button is not Available!" + "<%= criteria_type%>" + "<%= c_type%>";
	alert(msg);
} else
{ 
	//Indicate Analyses type is set
    <% session.setAttribute("analysesTypeSet","Y"); %> 
    //Turn on Step 3 block
    <% session.setAttribute("step3Display","block"); %> 
    
	if (<%= ((ByChemistryCriteria)criteria).isSet()%>)
	{
		msg = "\nYou have changed some checkboxes."
        	+ "\nIf you clicked one of these radio buttons"
		+ "\nand you choose to continue, your selections"
		+ "\nwill be lost and you will get a new list of"
		+ "\nanalyses to choose from. Is this ok?";
	}

	if (<%= criteria.isSet()%>) 
	{
		if (confirm(msg)){
			ok = true;
		}
	} else ok = true;
	
}
	if (ok)
	{
		updateChoice( a.value);
		return ok;
	} else {
		document.form1.criteria_type[<%= c_type%>].checked = true;
		return ok;
	}
}

function setInclusionType(incType){
    var hiddenField = document.createElement("input");
            hiddenField.setAttribute("type", "hidden");
            hiddenField.setAttribute("name", "incl_type");
            hiddenField.setAttribute("value", incType);
           document.form1.appendChild(hiddenField);
}

function changeInclusionType(a){
var ok = true;
var msg = "\nYou have changed some checkboxes."
        + "\nIf you clicked one of these radio buttons"
		+ "\nand you choose to continue, your selections"
		+ "\nwill be lost and you will get a new list of"
		+ "\nanalyses to choose from. Is this ok?";
	if ((<%= criteria.isSet()%>)||(chkdVal == 1)){
		if (!confirm(msg)){
			ok = false;
		}
	}
	if (ok){thisRefresh();}
document.form1.incl_type[a.name].checked = true;
return;
}

function chkVal(){
var ok = true;
var msg = "\nYou have changed some checkboxes."
        + "\nIf you clicked one of these radio buttons"
		+ "\nand you choose to continue, your selections"
		+ "\nwill be lost and you will get a new list of"
		+ "\nanalyses to choose from. Is this ok?";
	if ((<%= criteria.isSet()%>)||(chkdVal == 1)){
		if (!confirm(msg)){
			ok = false;
		}
	}
	if (ok){
		thisRefresh();
    }
document.form1.analysis_type[<%= a_type%>].checked.value = true;
return;
}
/*
function thisRefresh(){
	document.form1.chem_elements.value ="yes";
	document.form1.action = "pg3.jsp";
	document.form1.submit();
	return;
	}
*/

function thisRefresh(){
	document.form1.action = "pg3.jsp";
	document.form1.submit();
	return;
	}
	
function updateChoice( type){
	document.form1.action = "pg3.jsp?criteria_type="+type;
	document.form1.submit();
	return;
	}
//Tooltips for each chemicals

<% int total=itemDesc.size();
  for (int i=1;i<=total;i++) { 
	String idNum =(String) itemDesc.get(i-1);
    String contentStr = null;
    if( itemds != null) contentStr =(String) itemds.getValue(idNum);
    if ( contentStr == null )
    {
    	if (mineralistds != null ) 
    		contentStr =(String) mineralistds.getValue(idNum);
    }
    idNum = "'#"+idNum+"'";
    if( contentStr !=null ) {
        idNum = idNum.replace('(','_');
	    idNum = idNum.replace(')','_');
	    idNum = idNum.replace('/','_');  
	    contentStr = contentStr.replace('\'',' ');
	    contentStr = contentStr.replace(',',' ');
	    contentStr = contentStr.replace('"',' ');
	    contentStr ="'"+contentStr.trim()+"'";
%>
  $(<%=idNum%>).qtip({
   content:<%= contentStr%>,
   style: 'helpstyle', // The name of the newly created custom style above
   show: 'mouseover',
   hide: 'mouseout',
   position: {
         target: 'mouse',
         corner: {
                target: 'topRight',
                tooltip: 'bottomLeft'
         }
        }
   }); 	  

	  <% } }%>
</script>