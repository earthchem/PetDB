<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query -  set Data Availability filters</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="expires" content="0">
<link href="css/petdb.css" rel="stylesheet" type="text/css">
</head>
<SCRIPT TYPE="text/javascript">
<!--
window.focus();
window.name = "myWin";
var browsr = navigator.appName;
var plat = navigator.platform.toLowerCase();
var isMacwIE = false;
if((plat.indexOf("mac") > -1)&&(browsr.indexOf("Explorer") > -1)){
isMacwIE = true;
document.write('<font color="red">You are using IE on a Mac. If the server response time is very slow you may experience problems with these popout window settings being properly recorded.</font>');
document.close();
}
//-->
</SCRIPT>
<%      String ipAddress = IPAddress.getIpAddr(request);
        new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Data Availability',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
        CCriteriaCollection c_c_collection;
        CombinedCriteria c_criteria;
        Criteria criteria;
        String state ="";
        Map map = new HashMap();
        int test =0;

        if ((c_c_collection = (CCriteriaCollection)session.getAttribute("ccColl")) == null)
        {
                throw new Exception("Your Session has EXPIRED. Please go to index.jsp and start over");
        }

        c_criteria = c_c_collection.getCurrentCCriteria();

        if ( (criteria = c_criteria.getCriteria(CombinedCriteria.ByDataAvailCriteria)) == null)
        {
               test = c_criteria.addCriteria(CombinedCriteria.ByDataAvailCriteria, new ByDataAvailCriteria());
               criteria = c_criteria.getCriteria(CombinedCriteria.ByDataAvailCriteria);
        }


        Wrapper wrapper = criteria.getWrapper();
	DataSet ds0 = wrapper.getControlList("0");
	DataSet ds1 = wrapper.getControlList("1");

%>


<body class="pop">
  <div class="popTop">
    <p class="emphasis">
    <font size="+1">Search for Samples for which the following types of data are available</font>
    <br>
    </p>
    <div class="clearer"></div>
  </div><!-- end popTop -->

<form method="POST"  action="pg2.jsp" target="MAIN_WINDOW" name="form1">

<table class="sub2a" style="width:80%;">
  <tr><td align="left">Chemical composition of samples</td></tr>
    <tr><td align="left"><td><img src="images/shim.gif" width="5" height="10"></td></tr>
    <tr align="right">
      <td align="left" valign="middle">
	    <table id="#IDBlock" style="font-size:100%;">
                <tr align="left" valign="top"> 
                  <td width="15%" nowrap><span  class="keyword">Rock Analyses:</span></td>
                </tr>
                <tr>
                  <td nowrap colspan="7" align="left"><input type="checkbox" name="<%= ByDataAvailCriteria.Any_Material%>" value="<%= ByDataAvailCriteria.Rock%>" <%=  criteria.isSelected(ByDataAvailCriteria.Any_Material,ByDataAvailCriteria.Rock) ? "checked" : ""%>>
                    whole rock analyses&nbsp;&nbsp;<input type="checkbox" name="<%= ByDataAvailCriteria.Any_Material%>" value="<%= ByDataAvailCriteria.Glass%>" <%=  criteria.isSelected(ByDataAvailCriteria.Any_Material,ByDataAvailCriteria.Glass) ? "checked" : ""%>>
                    glass&nbsp;analyses&nbsp;&nbsp;
                  </td>
                </tr>
                <tr align="left">
<%
		for (int i=0; i<ByDataAvailConfigurator.Materials.length; i++)
		{
             
			String v =  ByDataAvailConfigurator.getNumIDIn(ds0,ByDataAvailConfigurator.Materials_Code[i]);
			boolean  selected =  criteria.isSelected(ByDataAvailCriteria.Material_Analysis,v);
            map.put("rock:"+v,ByDataAvailConfigurator.Materials[i]); 
%>
                  <td nowrap><input type="checkbox" name="<%= ByDataAvailCriteria.Material_Analysis%>" <%= selected ? "checked" :""%> value="<%= v%>"><%= ByDataAvailConfigurator.Materials[i]%>&nbsp;</td>
<%
		}
%>
                </tr>
      </table>
      </td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="8"></td>
    </tr>
    
    <!--------------------  Mineral Ananlyses -->    
    <tr align="right">
      <td align="left" valign="middle">
	    <table id="#IDBlock"  style="font-size:100%;">
                <tr align="left" valign="top"> 
                  <td width="15%" nowrap><span  class="keyword">Mineral Analyses:</span></td>
                </tr>
                <tr>
                  <td nowrap align="left" colspan="7"><input type="checkbox" onClick ="Sel_DeSelAllItems(this,'<%=ByDataAvailCriteria.Mineral_Analysis%>');" name="<%= ByDataAvailCriteria.Any_Mineral%>" value="yes" <%= ((ByDataAvailCriteria)criteria).isAnyMineral()?"checked":""%> >
                    any mineral analyses</td>
                </tr>
                <tr align="left" valign="top"> 
<%
		for (int i=0; i<ByDataAvailConfigurator.Minerals.length; i++)
		{
			
			String v =  ByDataAvailConfigurator.getNumIDIn(ds1,ByDataAvailConfigurator.Minerals_Code[i]);
			boolean selected = criteria.isSelected(ByDataAvailCriteria.Mineral_Analysis,v);
            map.put("mineral:"+v,ByDataAvailConfigurator.Minerals[i]); 
%>
                  <td nowrap><input type="checkbox" onClick ="Sel_DeSelAllItems(this,'<%=ByDataAvailCriteria.Any_Mineral%>');"  name="<%= ByDataAvailCriteria.Mineral_Analysis%>" <%= selected ? "checked" : ""%> value="<%= v%>"><%= ByDataAvailConfigurator.Minerals[i]%>&nbsp;</td>
<%
		}
%>
                </tr>
      </table>
      </td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="8"></td>
    </tr>
    
    <!--------------- Melt Inclusions: -->
    <tr align="right">
      <td align="left" valign="middle">	    
         <table id="#IDBlock"  style="font-size:100%;">
                <tr align="left" valign="top"> 
                  <td width="15%" nowrap><span  class="keyword">Inclusions Analyses:</span></td>
                </tr>
                <!-- 
                <tr>  
                  <td><a href="<%= request.getContextPath() %>/readydata/Inclusions.xls">Click here for complete inclusion data</a></td>
                </tr>
                -->
                <tr>
                   <td nowrap align="left" colspan="4">
                   <input type="checkbox" onClick ="Sel_DeSelAllItems(this,'<%=ByDataAvailCriteria.Inclusion_Analysis%>');" name="<%= ByDataAvailCriteria.Any_Inclusion%>" value="yes" <%= ((ByDataAvailCriteria)criteria).isAnyInclusion()?"checked":""%>/>
                    any melt inclusion analyses</td>
                </tr>
                <tr align="left" valign="top">
<%
                for (int i=0; i<ByDataAvailConfigurator.Inclusion.length; i++)
                {
			String v =  ByDataAvailConfigurator.getNumIDIn(ds1,ByDataAvailConfigurator.Inclusion_Code[i]);
			boolean selected = criteria.isSelected(ByDataAvailCriteria.Inclusion_Analysis,v);
            map.put("inclusion:"+v,ByDataAvailConfigurator.Inclusion[i]); 
%>
                  <td nowrap><input type="checkbox" onClick ="Sel_DeSelAllItems(this,'<%=ByDataAvailCriteria.Any_Inclusion%>');" name="<%= ByDataAvailCriteria.Inclusion_Analysis%>" value="<%= v%>" <%= selected ? "checked" : ""%>/><%= ByDataAvailConfigurator.Inclusion[i]%>&nbsp;</td>
                
<%
                session.setAttribute("availMap",map);
		}
%>
		</tr>
      </table>
      </td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="8"></td>
    </tr>
    <tr align="right">
      <td align="center"> <input type="submit" name="Submit"  class="importantButton"  value="Apply" onClick="formSubmit();">
        <input id="reset1" name="reset1" type="reset" value="Reset"> </td></tr>
</table>
</form>

</body>
<SCRIPT TYPE="text/javascript">
<!--

function formSubmit()
{
document.form1.target = "MAIN_WINDOW";
//alert('got here');
document.form1.submit();
window.opener.focus();
if(!isMacwIE){
	window.close();
setTimeout("winClose()", 20000); // 60000 ms is 1 minute.
return;
  }
else {
	window.close();
setTimeout("winClose()", 20000); // 60000 ms is 1 minute.
return;
  }
}

function winClose(){
//alert('finished!');
window.close();
return;
}


function Sel_DeSelAllItems(ObjName, Obj)
{
        var a=document.form1.elements[Obj];
	if (ObjName.checked)
	{
        	for(var i=0; i<a.length; i++){
                        a[i].checked=false;
        	}
  	if (!a.length) document.form1.elements[Obj].checked=false;
	}
}
//-->
</SCRIPT>
</html>

