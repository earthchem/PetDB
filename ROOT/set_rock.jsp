<%@ include file="headCode2.jspf"%>
<html>
<head>
<title>PETDB query - set sample search filters</title>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<META http-equiv="expires" content="0">
<script  language= "JavaScript" src=js/windows.js></script>
<script  LANGUAGE= "JavaScript" src="js/set_rock.js"></script>
<SCRIPT TYPE="text/javascript">
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
//-->
</SCRIPT>
</head>
<body class="pop" OnLoad="BodyLoadFunction()">
<%      String ipAddress = IPAddress.getIpAddr(request);
        new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Sample Type',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
        CombinedCriteria c_criteria;
        Criteria  criteria;
        int test;
        if ((c_criteria = (CombinedCriteria)session.getAttribute("cCriteria")) == null)
        {
                session.setAttribute("cCriteria", new CombinedCriteria());
                c_criteria = (CombinedCriteria)session.getAttribute("cCriteria");
        }
        if ( (criteria = c_criteria.getCriteria(CombinedCriteria.ByRockCriteria)) == null)
        {
                        test = c_criteria.addCriteria(CombinedCriteria.ByRockCriteria, new ByRockCriteria());
                        criteria = c_criteria.getCriteria(CombinedCriteria.ByRockCriteria);
        }
        
        Wrapper wrapper = criteria.getWrapper();
        DataSet ds;
%>
<div class="popTop">
<span class="emphasis">Sample Characteristics</span><br />
        <span class="regTxt">Hold CTRL key ( Command Key on Mac ) for multiple selections</span>
</div><!-- end popTop -->
<form name="frm_main" method="POST" action="pg2.jsp" target="MAIN_WINDOW">
  <table width="90%" border="0" cellspacing="0" class="sub2">
          <tr>
            <td width="152" align="left"><b>Sampling Technique</b><br />
              <select id="samptech" name="samptech"  style="height:100px;width:320px;overflow:auto;" size=6>
                <option value="" <%= ((criteria == null)|| (!criteria.isSet(ByRockCriteria.METHOD)) ) ? "selected" : ""%>>No Preference
                <%
			
					ds = wrapper.getControlList(ByRockCriteria.METHOD);
					Vector v0 =  ds.getKeys();
					for (int i =0; i< v0.size(); i++) {
							String k = (String)v0.elementAt(i);
							String v = (String)ds.getValue(k);
					                if (criteria != null) {
%>
         <option value="<%= k%>" <%= (criteria.isSelected(ByRockCriteria.METHOD,k)) ? "selected" :""%>> <%= v%> </option>
<%
                }  else {
%>
         <option value="<%= k%>" > <%= v%> </option>
<%
                }
        }
%>
              </select>
              </td>
              <td>
              <b>Alteration</b><br />
              <select name="alteration" id="alteration" style="height:100px;width:320px;overflow:auto;" size=6>
                <option value = "" <%= ((criteria == null)|| (!criteria.isSet(ByRockCriteria.ALTERATION)) ) ? "selected" : ""%>> No Preference</option>
                <%

	ds = wrapper.getControlList(ByRockCriteria.ALTERATION);
        Vector v2 =  ds.getKeys();
        for (int i =0; i< v2.size(); i++) {
                String key  = (String)v2.elementAt(i);
                String v = (String)ds.getValue(key);
               if (criteria != null) {
%>
         <option value="<%= key%>" <%= (criteria.isSelected(ByRockCriteria.ALTERATION,key)) ? "selected" :""%>> <%= v%> </option>
<%
                }  else {
%>
         <option value="<%= key%>" > <%= v%> </option>
<%
                }
        }
%>
              </select>
            </td>
          </tr>
          <tr valign="top" >
            <td align="center" colspan="2"><hr></td>
          </tr>
          <tr valign="top">
            <td align="center" colspan="2">
            <table width="100%" align="center">
            <tr>
              <th>Rock type</th>
              <th>Rock Classification</th>
            </tr>
            <tr valign="top">
              <td align="center">
            
              <div style="height:320px;width:320px;overflow:auto;" >
                <!-- the dynamic list can be generated in the table below  
                    without messing up the surrounding table structure -->
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
<%
                       ds = wrapper.getControlList(ByRockCriteria.ROCK_CLASS);
                       Vector v1 =  ds.getKeys();
                       for (int i =0; i< v1.size(); i++) {
                            String key  = (String)v1.elementAt(i);
                            Vector v = (Vector)ds.getValue(key);
                            Vector k4v = (Vector)((UniformKeyedValueDS)ds).getKey4Value(key);
        
%>
                  <tr>
              <td class="pad"><%= i+1%></td>
             <td align=left><input type=button value="<%= key%>"
                                                onclick =
                                                "SelectedListDynamic(<%= i+1%>,'<%= JavaSCSerializer.getGeoList(v)%>','<%= JavaSCSerializer.getGeoList(k4v)%>','@@@','<%= key.replace(' ','_')%>');">
                    </td>
                  </tr>
                  <%
                                        }
                 %>
                </table>
               </div>
              </td>
              <td align="center" valign="top">
              <div style="height:320px;overflow-x: hidden; overflow-y: hidden;" >
                <table border="0" CellSpacing="0" CellPadding="5">
                  <tr>
                    <td align=center class="select"><input type=button name="ButtonAvailable" value="Available" onclick=AlertAvailableHere()>
                    </td>
                    <td class="select">&nbsp;</td>
                    <td align=center valign="middle" class="select"><b>selected</b></td>
                  </tr>
                  <tr>
                    <td class="select">
                      <select Name="ItemsAvailable" Multiple Size=17  style="width:200px;" >
                        <option Value="#"> </option>
                      </select>
                    </td>
                    <td class="select">
                      <table border="0">
                        <tr>
                          <td align="center"><input type="button" name="AddAll" value="Add All &gt;&gt;" onClick="SelectAllOptions(ItemsAvailable);SelectA2SelectB(ItemsAvailable,ItemNum)"></td>
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
                          <td align="center"><input type="button" name="RemoveAll" value="&lt;&lt; Remove All" onClick="AllSelectA2SelectB(ItemNum,ItemsAvailable);"></td>
                        </tr>
                      </table>
                    </td>
                    <td class="select">
                      <select Name="ItemNum" Multiple Size="17" style="width:200px;" >
                        <option Value="#"> </option>
                      </select>
                    </td>
                  </tr>
                </table>
                </div>
              </td>
            </tr>
          </table>
      </td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle" colspan="2"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td colspan="2" valign="middle">&nbsp;</td>
    </tr>
    <tr align="right">
      <td colspan="2" align="center">
        <input type="button" name="query"  class="importantButton"  value="Apply"  onClick="SelectAllOptions(document.frm_main.ItemNum); formSubmit();">
        <input type=reset value="reset" onClick=BodyLoadFunction();>
      </td>
    </tr>
  </table>
</form>
</body>
<SCRIPT TYPE="text/javascript">
function formSubmit()
{
//if (!isMacwIE)alert(isMacwIE);
document.frm_main.target = "MAIN_WINDOW";
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
</html>
