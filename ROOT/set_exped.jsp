<%@ include file="headCode2.jspf"%>
<html>
<script>
var chgChk = 0;
</script>
<head>
<title>PETDB query -  set Expedition/Cruise filters</title>
<script  language= "JavaScript" src=js/windows.js></script>
<script  LANGUAGE= "JavaScript" src="./js/set_exped.js"></script>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<META http-equiv="expires" content="0">
</head>
<body class="pop" onLoad="javascript:chgChk = 0;document.frm_queryexpedition.ItemNum.length = 0;">
<!-- took this out of the body tag 
- not sure we need it -
OnLoad="BodyLoadFunction()" -->
<%      String ipAddress = IPAddress.getIpAddr(request);
        new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Cruise or Field',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;
        Criteria criteria;
        String state ="";
        int test =0;

        //ccColl is the session place holder for the CombinedCriteriaCollaction object
        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
                //session.setAttribute("ccColl",new CCriteriaCollection());
                //c_c_collection =  (CCriteriaCollection)session.getAttribute("ccColl");
                throw new Exception("Your Session has EXPIRED. Please start over");
        }

        c_criteria = c_c_collection.getCurrentCCriteria();

        if ( (criteria = c_criteria.getCriteria(CombinedCriteria.ByExpCriteria)) == null)
                {
                        test = c_criteria.addCriteria(CombinedCriteria.ByExpCriteria, new ByExpCriteria(ByExpCriteria.EXPNUMs));
                        criteria = c_criteria.getCriteria(CombinedCriteria.ByExpCriteria);
                }

	criteria = ((CompositeCriteria)criteria).getSubCriteria();
        Wrapper wrapper = criteria.getWrapper();

        DataSet ds;
%>
<div class="popTop">
<span class="emphasis">Expedition/Cruise</span><br />
<span class="regTxt" >Hold CTRL key (Command key on Mac) key for multiple selections</span>&nbsp;<span id="importantText">
Use EITHER the top or bottom selection form, <b>not both</b></span>
</div><!-- end popTop -->
<table border="0" cellspacing="0" class="sub2">
<form method="POST" action="set_exped_scrn2.jsp" name="frm_queryexpedition">
    <tr>
        <td width="95%" align="left">
<table width="100%" class="setLabel2T">
<tr>
        <th><h2>Form 1: By Expedition Name</h2></th>
</tr>
<tr>
  <td align="center" valign="top">
    <table width="100%" CELLPADDING=0 CELLSPACING=0>
    <tr>
      <td align=center><font color=#CB0C41 size=-1>* Alphabetical list;&nbsp;&nbsp;&nbsp;**
      Special groups</font> </td>
    </tr>
    <tr>
      <td width="701" align=center> <font color=#CB0C41 size=-1>*</font> &nbsp;
<%
        ds = wrapper.getControlList(ByExp2Criteria.EXP);
        Vector v0 =  ds.getKeys();
        for (int i =0; i< v0.size(); i++) {
                String key  = (String)v0.elementAt(i);
                Record v = (Record)ds.getValue(key);
		String label = JavaSCS.getExpL((Vector)v.getColumn(ExpRRecord.Label));
		String id = JavaSCS.getExpL((Vector)v.getColumn(ExpRRecord.ID));
                if ((!key.equals("ALVIN"))
                        && (!key.equals("DSDP"))
                        && (!key.equals("ODP"))
                ) {

%>
	<input type=button value=" <%= key%> " onclick="ShowAvailble('<%= id%>','<%= label%>','|','<%= key%>')">
<%
                }
        }
%>

     </td>
    </tr>
    <tr>
      <td align=center> &nbsp; <font color=#CB0C41 size=-1>**</font> &nbsp;
<%	{
	Record v = (Record)ds.getValue("ALVIN");
	String id = JavaSCS.getExpL((Vector)v.getColumn(1));
	String label = JavaSCS.getExpL((Vector)v.getColumn(0));
%>
             <input type=button value=" ALVIN "
             onclick="ShowAvailble('<%= id%>','<%= label%>','|','ALVIN')"/>
<%
	v = (Record)ds.getValue("DSDP");
	id = JavaSCS.getExpL((Vector)v.getColumn(1));
	label = JavaSCS.getExpL((Vector)v.getColumn(0));
%>
             <input type=button value=" DSDP "
             onclick="ShowAvailble('<%= id%>','<%= label%>','|','DSDP')" />
<%
	v = (Record)ds.getValue("ODP");
	id = JavaSCS.getExpL((Vector)v.getColumn(1));
	label = JavaSCS.getExpL((Vector)v.getColumn(0));
%>
             <input type=button value=" ODP "
             onclick="ShowAvailble('<%= id%>','<%= label%>','|','ODP')"/>
        <font color=orange size=-1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
<%
	}
%>

</td>
</tr>
</table>
  </td>
</tr>
<tr align="right">
  <td align="center" align="right">
    <table CellSpacing="0">
      <tr>
        <td align=center class="select" width="200"><input type=button name="ButtonAvailable" value="Available" onclick=AlertAvailable()>
        </td>
        <td class="select">&nbsp;</td>
        <td align=center class="select" width="200"><strong>selected</strong></td>
      </tr>
      <tr>
        <td class="select" width="200" style="height:320px;width:320px;overflow:auto;">
          <select Name="ItemsAvailable" Multiple Size=7 style="height:320px;width:320px;overflow:auto;">
            <option Value="">&nbsp;&nbsp;&nbsp;&nbsp;                                   
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                                    
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
          </select>
        </td>
        <td class="select">
          <table Border="0">
            <tr>
              <td align="center"><input type="button" name="AddAll" value="Add All &gt;&gt;" onClick="SelectAllOptions(document.frm_queryexpedition.ItemsAvailable);SelectA2SelectB(ItemsAvailable,ItemNum)"></td>
            </tr>
            <tr>
              <td align=center>
        <input Type="button" Name="AddFromAvailable" Value="Add &gt;" onclick=SelectA2SelectB(ItemsAvailable,ItemNum)>
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td align=center>
        <input Type="button" Name="RemoveFromChosen" Value="&lt; Remove" onclick=SelectA2SelectB(ItemNum,ItemsAvailable)>
              </td>
            </tr>
            <tr>
              <td align="center"><input type="button" name="RemoveAll" value="&lt;&lt; Remove All" onClick="AllSelectA2SelectB(document.frm_queryexpedition.ItemNum,document.frm_queryexpedition.ItemsAvailable);"></td>
            </tr>
          </table>
        </td>
        <td class="select" width="200" >
          <select Name="ItemNum" Multiple Size="7" style="height:320px;width:320px;overflow:auto;">
          <option value="">&nbsp;&nbsp;&nbsp;&nbsp;                                               
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                                            
		  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
		  </select>
        </td>
      </tr>
    </table>
  </td> 
</tr>
<tr align="right">
    <td align="right">
<input type="submit" name="Submit" value="Form 1 Continue"  class="importantButton" onClick="SelectAllOptions(document.frm_queryexpedition.ItemNum);verifyIt2(this.form);">
</td>
</tr>
</table>
</td></tr></form>
<tr><td width="95%" align="left">
<!-- here -->
<form name="form2" method="POST" action="set_exped_scrn2.jsp">
<table width="100%" class="upcoming">
     <tr align="right">
       <td align="center" valign="middle" colspan=2 ><img src="images/shim.gif" width="5" height="1"></td>
     </tr>
     <tr align="right" >
      <th align="left" valign="middle" colspan=2><h2>Form 2: By Other Expedition Details</h2></th>
    </tr>
    <tr align="right"  >
      <td valign="middle" align="center" colspan=2>
          <table width="95%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="36%" align="left" valign="top">Ship:<br />
            <select id="ship" name="ship"  size=5  onChange="addTo()" style="height:100px;width:320px;overflow:auto;">
              <option value="" selected>No Preference
<%
        ds = wrapper.getControlList(ByExp2Criteria.SHIP);
        Vector v1 = ds.getKeys();
        for (int i=0; i < v1.size(); i++)
        {
                String key  = (String)v1.elementAt(i);
                String v = (String)ds.getValue(key);
		if (criteria != null) 
		{ 
%>
 <option value="<%= key%>" <%= (criteria.isSelected(ByExp2Criteria.SHIP,key)) ? "selected" : ""%>><%= v%>&nbsp;&nbsp;
<%
		} else {
%>
 <option value="<%= key%>"><%= v%>&nbsp;&nbsp;
<%
		}
        }
%>
	 	</select>
</td>
          <td width="5%" align="left">&nbsp;</td>
          <td width="24%" align="center" valign="top">Year:<br />
  <select name="year" size=5 multiple onChange="addTo()" style="height:100px;width:100px;overflow:auto;">
   	<option <%= ((criteria == null) || (!criteria.isSet(ByExp2Criteria.YEAR))) ? "selected" : ""%>  value="">No Preference
<%
        ds = wrapper.getControlList(ByExp2Criteria.YEAR);
        Vector v2 = ds.getValues();
        for (int i=0; i < v2.size(); i++)
        {
                String key  = (String)v2.elementAt(i);
                if (criteria != null)
                {
%>
 <option value="<%= key%>" <%= (criteria.isSelected(ByExp2Criteria.YEAR,key)) ? "selected" : ""%>><%= key%>&nbsp;&nbsp;
<%
                } else {
%>
 <option value="<%= key%>"><%= key%>&nbsp;&nbsp;
<%
                }
        }
%>
  </select></td>
          <td width="5%" align="left" valign="top">&nbsp;</td>
          <td width="28%" align="right" valign="top">Chief Scientist:<br />
  <select id="chief" name="chief"  size=5 multiple  onChange="addTo()" style="height:100px;width:320px;overflow:auto;">
    <option  <%= ((criteria == null) || (!criteria.isSet(ByExp2Criteria.CHIEF))) ? "selected" : ""%> value="" >No Preference
<%
        ds = wrapper.getControlList(ByExp2Criteria.CHIEF);
        Vector v3 = ds.getKeys();
        for (int i = 0; i < v3.size(); i++)
        {
                String key      = (String)v3.elementAt(i);
                String v        = (String)ds.getValue(key);
		if (criteria != null) 
                {
%>
    <option value="<%= key%>"  <%= (criteria.isSelected(ByExp2Criteria.CHIEF,key)) ? "selected" : ""%>><%= v%>&nbsp;&nbsp;
<%
		} else {
%>
    <option value="<%= key%>"><%= v%>&nbsp;&nbsp;
<%
		}
        }
%>



   </select></td>
        </tr>
      </table></td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle">&nbsp;</td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle"> Institution:<br />
        <select id="institution" name="institution"  size=5  onChange="addTo()" style="height:100px;width:740px;overflow:auto;">
        <option value="" <%= ((criteria == null) || (!criteria.isSet(ByExp2Criteria.INST))) ? "selected" : ""%>>No Preference
 <%
        ds = wrapper.getControlList(ByExp2Criteria.INST);
        Vector v4 = ds.getKeys();
        for (int i = 0; i < v4.size(); i++)
        {
                String key      = (String)v4.elementAt(i);
                String v        = (String)ds.getValue(key);
		if (criteria != null)
                {
%>
    <option value="<%= key%>"  <%= (criteria.isSelected(ByExp2Criteria.INST,key)) ? "selected" : ""%>><%= v%>&nbsp;&nbsp;
<%
		} else {
%>
    <option value="<%= key%>"><%= v%>&nbsp;&nbsp;
<%
		}
        }
%>

        </select> 
        </td>
        <td align="left" valign="middle">maximum results:<br />
              <select name="maxreturn" size=5 onChange="addTo()" style="height:100px;width:100px;overflow:auto;">
        <option selected value="100">100
        <option value="200">200
        <option value="500">500
    </select>
</td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td valign="middle" colspan=2 align='right'> <input type="submit" name="Submit" value="Form 2 Continue" class="importantButton" onClick="return verifyIt(this.form);"> </td>
  </tr></table>
</form>
</td></tr></table>
</body>
</html>
<script>
function verifyIt(formName){
var ln =  document.frm_queryexpedition.ItemNum.length;
var msg = "You changed some values on the upper form:"
		 +"\n - Other Expedition Details - "
		 +"\nbut this button only submits values for"
		 +"\nthis form. Do you want to continue?";
 if (ln > 0){
 if(confirm(msg))
   {
    document.formname.submit();
	return false;
   }
 return false;
 }
}

function addTo(){
chgChk = 1;
}

function verifyIt2(formName){
var msg = "You changed some values on the lower form:"
		 +"\n - By Expedition Name - "
		 +"\nbut this button only submits values for"
		 +"\nthis form. Do you want to continue?";
;
 if (chgChk > 0){
 if(confirm(msg))
   {
    document.formname.submit();
	return false;
   }
 return false;
 }
}
</script>
