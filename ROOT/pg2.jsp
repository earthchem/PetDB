<%@ include file="headCode.jspf"%>
<%@ include file="pg2_parameters.jspf" %>
<%
boolean search = c_criteria.isSet();
Vector savedCCriterias =(Vector)c_c_collection.getSavedCCriterias();
boolean savedQ = false;
if (savedCCriterias.size() > 1) savedQ = true;
int counter = -1;
String pgTitle = "PETDB: Petrological Database of the Ocean Floor - Select Criteria";
String pgType = "";
//String ipAddress = IPAddress.getIpAddr(request);
//new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('PetDB First Page',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");

%>

<%@ include file="head.jsp" %>
<%
   if(search)
   {
	state += " count = " + wrapper;
	session.setAttribute("state",state);
	DataSet sDS = wrapper.getControlList("0");
	if (sDS != null) {
		counter =((CriteriaDS)sDS).getCount();
	}
   }
%>
<script type="text/javascript">
window.name = "MAIN_WINDOW";
document.title = "<%=pgTitle%>"; 
</script>
<div class="pad" align="left">
<br />
<!-- 
<h1>PetDB Search</h1>
 -->
<form name="form1" id="form1" method="POST" action="">

		<table style="width:98%;align:left;border:0;cellspacing:10;cellpadding:0;">
          <tr > 
					<td >
                    <table class="crit" styl="width:100%;align:left;cellspacing:10;cellpadding:0;">
                    <tr><td class="new" nowrap>Quick Search:&nbsp;&nbsp;&nbsp;&nbsp;</td><td><input name="Submit5" type="button" value="Diamond data" class="importantButton setButton" onClick="window.location = 'pg3.jsp?diamond=true&mineral_type=151&DoOnSubmit=submit'"></td><td>Get data for diamonds.</td></tr>
                    <tr><td class="new"></td><td><input name="Submit6" type="button" value="Mantle Xenolith data" class="importantButton setButton" onClick="window.location = 'pg3.jsp?xenolith=true';"></td><td>Get data for mantle xenolith samples, including: peridotite, eclogite, dunite, websterite, wehrlite, lherzolite, harzburgite, pyroxenite. (For other xenolith types, please use the PetDB search by 'Sample Type' below.)</td></tr>
                    </table>
					</td>
		  </tr>        
          
		  <tr>
		  <td>
		  <table class="crit" style="width:100%;align:left;cellspacing:10;cellpadding:0;">
          <tr><td class="new">Advanced Search:</td></tr>
           <tr > 
					<td >
                    <div align="left" class="emphasis">The PetDB Search allows you to retrieve any combination of chemical measurements for a group of samples that you can select by the criteria provided below.<i></i></div>
					</td>
		  </tr>
		  <tr><td class="new">
				  Step 1. Select Samples (You can combine different criteria.)
				  </td>
		  </tr>
		  <tr> 
		  <td align="left"> 
			<table  style="cellspacing:2;border:0;width:100%;" class="crit">
			<tr>
<%

	
	Criteria ge_criteria = c_criteria.getCriteria(CombinedCriteria.ByLongLatCriteria); 
	String filter_url = "?";
        //NB, SB, WB, EB, Top, Bottom

        filter_url += (ge_criteria != null ?  "NB="+ge_criteria.getValueAsStr(ByLongLatCriteria.L_NORTH) : "");
        filter_url += (ge_criteria != null ? "&SB="+ge_criteria.getValueAsStr(ByLongLatCriteria.L_SOUTH) : "");
        filter_url += (ge_criteria != null ?  "&WB="+ge_criteria.getValueAsStr(ByLongLatCriteria.L_WEST) : "");
        filter_url += (ge_criteria != null ?  "&EB="+ge_criteria.getValueAsStr(ByLongLatCriteria.L_EAST) : "");
        filter_url += (ge_criteria != null ?  "&Top="+ge_criteria.getValueAsStr(ByLongLatCriteria.D_TOP) : "");
        filter_url += (ge_criteria != null ?  "&Bottom="+ge_criteria.getValueAsStr(ByLongLatCriteria.D_BOTTOM) : "");
        if (filter_url.indexOf("?&") > -1)
                filter_url = filter_url.replaceAll("?&","?");

	filter_url = "/set_latlong.jsp"+filter_url;
    
    String mapURL = "http://" + new URL(request.getRequestURL().toString()).getHost() + "/mapviewer" + filter_url;
%>
 
						  <td align="left"  nowrap width="155"><button class="setButton" onClick="openWindow2('set_latlong.jsp',750,900,'set_win')">By Latitude/Longitude</button></td>
						  <td align="left"   width="40" ><span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="setLatLonHelp"></span>
						  <% if (c_criteria.isSet(CombinedCriteria.ByLongLatCriteria) ){ %>
						  <a onclick="submitForm('form1','pg2.jsp?clear=ByLongLatCriteria')"> <button class="clearButton ui-state-default ui-corner-all ui-icon ui-icon-trash" title="clear" ></button></a>
                          <% } %></td>
						  
<% 
     if(c_criteria.isSet(CombinedCriteria.ByLongLatCriteria)){ 
       if((c_criteria.getSDsc(CombinedCriteria.ByLongLatCriteria).length())< (c_criteria.getDsc(CombinedCriteria.ByLongLatCriteria).length())){%>          
<td   class="regTxt">
<button class="infoButton ui-state-default ui-corner-all ui-icon ui-icon-info" onMouseOver="MM_showHideLayers('latlongcrit','show')" 
        onMouseOut="MM_showHideLayers('latlongcrit','hide')" onClick="alert('<%= c_criteria.getDsc(CombinedCriteria.ByLongLatCriteria)%>');"></button>
<div id="latlongcrit" class="criteria">
		     <%= c_criteria.getDsc(CombinedCriteria.ByLongLatCriteria)%></div>(<span id="importantText"><%= counter%> samples</span>)
</td>		     
<% } else { %>
<td   class="regTxt" nowrap>
    <%= c_criteria.getDsc(CombinedCriteria.ByLongLatCriteria)%>(<span id="importantText"><%= counter%> samples</span>)
</td>    
<%

   }
 } %>
							

							
						  
						</tr>
						<tr> 
						  <td align="left"  width="155" nowrap>
						  <button  class="setButton"  onClick="openWindow2('set_geo_name.jsp',500,950,'set_win');" style="display:inline;" >By Feature Name</button>
						  </td>
						  <td width="40">
						  <span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="setGeoSampHelp"></span>
						  <% if(c_criteria.isSet(CombinedCriteria.ByGeoCriteria)){ %>
						  <a  onclick="submitForm('form1','pg2.jsp?clear=ByGeoCriteria')"><button class="clearButton ui-state-default ui-corner-all ui-icon ui-icon-trash" title="clear"></button></a>
                          <% } %></td>
						  
<%  
     if(c_criteria.isSet(CombinedCriteria.ByGeoCriteria)){       
       if((c_criteria.getSDsc(CombinedCriteria.ByGeoCriteria).length())< (c_criteria.getDsc(CombinedCriteria.ByGeoCriteria).length())){%> 
 <td  class="regTxt">               
      <button class="infoButton ui-state-default ui-corner-all ui-icon ui-icon-info" onMouseOver="MM_showHideLayers('geocrit','show')" 
                                onMouseOut="MM_showHideLayers('geocrit','hide')" 
                                onClick="alert('<%= c_criteria.getDsc(CombinedCriteria.ByGeoCriteria)%>');"></button>
                                <div id="geocrit" class="criteria"><%= c_criteria.getDsc(CombinedCriteria.ByGeoCriteria)%></div> 
                                <%= c_criteria.getSDsc(CombinedCriteria.ByGeoCriteria)%>(<span id="importantText"><%= counter%> samples</span>)
</td>
<% } else { %>
<td  class="regTxt" nowrap>
<%= c_criteria.getDsc(CombinedCriteria.ByGeoCriteria)%> (<span id="importantText"><%= counter%> samples</span>)
</td>
<%
   }
 } %>
					

		
             
						</tr>
						<tr> 
						  <td align="left"  width="155" nowrap><button  class="setButton" onClick="openWindow3('set_tect.jsp',400,450,'set_win')">Tectonic Setting</button>  
                          </td>
						  <td align="left"  width="40"><span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="setTecHelp"></span>
						  <%if(c_criteria.isSet(CombinedCriteria.ByTectCriteria)){%>
						  <a  onclick="submitForm('form1','pg2.jsp?clear=ByTectCriteria')"><button class="clearButton ui-state-default ui-corner-all ui-icon ui-icon-trash" title="clear"></button></a>
                          <%  } %></td>
						 
<% if(c_criteria.isSet(CombinedCriteria.ByTectCriteria)){       
       if((c_criteria.getSDsc(CombinedCriteria.ByTectCriteria).length())< (c_criteria.getDsc(CombinedCriteria.ByTectCriteria).length())){%> 
 <td  class="regTxt">                
            <button class="infoButton ui-state-default ui-corner-all ui-icon ui-icon-info"  onMouseOver="MM_showHideLayers('tectcrit','show')" 
                      onMouseOut="MM_showHideLayers('tectcrit','hide')" 
                      onClick="alert('<%= c_criteria.getDsc(CombinedCriteria.ByTectCriteria)%>');"></button>
            <div id="tectcrit" class="criteria">
		       <%= c_criteria.getDsc(CombinedCriteria.ByTectCriteria)%></div>
		       <%= c_criteria.getSDsc(CombinedCriteria.ByTectCriteria)%> 
		       <span id="importantText">(<%= counter%> samples)</span> 
</td>
<% } else { %>
 <td  class="regTxt" nowrap>
<%= c_criteria.getDsc(CombinedCriteria.ByTectCriteria)%>  <span id="importantText">(<%= counter%> samples)</span>
</td> 
<%
   }
 }%>

						
						</tr>
						<tr> 
						  <td align="left"  width="155" nowrap><button  class="setButton" onClick="openWindow2('set_rock.jsp',720,950,'set_win')">Sample Type</button></td>
						  <td align="left"  width="40"><span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="setSampCharHelp"></span>
						  <% if(c_criteria.isSet(CombinedCriteria.ByRockCriteria)){%>
                          <a onclick="submitForm('form1','pg2.jsp?clear=ByRockCriteria')">
                          <button class="clearButton ui-state-default ui-corner-all ui-icon ui-icon-trash" title="clear" ></button></a>
                          <% } %></td>
						  
<% 
  if(c_criteria.isSet(CombinedCriteria.ByRockCriteria))
  {
    if((c_criteria.getSDsc(CombinedCriteria.ByRockCriteria).length())< (c_criteria.getDsc(CombinedCriteria.ByRockCriteria).length()))
    {
%>          
    <td class="regTxt">
    <button class="infoButton ui-state-default ui-corner-all ui-icon ui-icon-info" onMouseOver="MM_showHideLayers('rockcrit','show');" 
    onMouseOut="MM_showHideLayers('rockcrit','hide');" onClick="alert('<%= c_criteria.getDsc(CombinedCriteria.ByRockCriteria)%>');"></button>
    <div id="rockcrit" class="criteria"><%= c_criteria.getDsc(CombinedCriteria.ByRockCriteria)%></div> <%= c_criteria.getSDsc(CombinedCriteria.ByRockCriteria)%><span id="importantText">(<%= counter%> samples)</span>
 </td>
 <%  }
    else 
    { %>
    <td  class="regTxt" nowrap>
    <%= c_criteria.getDsc(CombinedCriteria.ByRockCriteria)%><span id="importantText">(<%= counter%> samples)</span>
 </td>
 <%
    }
  } 
 %>			 
						</tr>
						<tr> 
						  <td align="left"  width="155" nowrap><button  class="setButton"  onClick="openWindow2('set_exped.jsp',800,950,'set_win')">Cruise or Field Program</button></td>
						  <td align="left"   width="40"><span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="setCruiseHelp"></span>
						  <% if(c_criteria.isSet(CombinedCriteria.ByExpCriteria)){%>
						  <a onclick="submitForm('form1','pg2.jsp?clear=ByExpCriteria')">
						  <button class="clearButton ui-state-default ui-corner-all ui-icon ui-icon-trash" title="clear" ></button></a>
                          <% } %>
                          </td>						 
<% 
     if(c_criteria.isSet(CombinedCriteria.ByExpCriteria)){ 
       if((c_criteria.getSDsc(CombinedCriteria.ByExpCriteria).length())< (c_criteria.getDsc(CombinedCriteria.ByExpCriteria).length())){%>          
 <td   class="regTxt">
<button class="infoButton ui-state-default ui-corner-all ui-icon ui-icon-info" class="buttonLink"onMouseOver="MM_showHideLayers('expcrit','show')" 
onMouseOut="MM_showHideLayers('expcrit','hide')" onClick="alert('<%= c_criteria.getDsc(CombinedCriteria.ByExpCriteria)%>');"></button>
<div id="expcrit" class="criteria">
		     <%= c_criteria.getDsc(CombinedCriteria.ByExpCriteria)%></div> <span><%= c_criteria.getSDsc(CombinedCriteria.ByExpCriteria)%></span>(<span id="importantText"><%= counter%> samples</span>)
  </td>
<% } else { %>
 <td   class="regTxt" nowrap>
 <%= c_criteria.getDsc(CombinedCriteria.ByExpCriteria)%>(<span id="importantText"><%= counter%> samples</span>)
  </td>
  <%
   }
 }%>
							

						
						</tr>
						<tr> 
						  <td align="left"  width="155" nowrap><button class="setButton" onClick="openWindow2('set_pub.jsp',500,950,'set_win')">Reference, Author</button></td>
						  <td align="left"   width="40"><span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="setPubInfoHelp"></span>
						  <%      if(c_criteria.isSet(CombinedCriteria.ByPubCriteria)){%>
						  <a onclick="submitForm('form1','pg2.jsp?clear=ByPubCriteria')">
						  <button class="clearButton ui-state-default ui-corner-all ui-icon ui-icon-trash" title="clear"></button></a>
                          <% } %></td>
						 
<% 
     if(c_criteria.isSet(CombinedCriteria.ByPubCriteria)){ 
       if((c_criteria.getSDsc(CombinedCriteria.ByPubCriteria).length())< (c_criteria.getDsc(CombinedCriteria.ByPubCriteria).length())){%> 
        <td   class="regTxt">         
<button class="infoButton ui-state-default ui-corner-all ui-icon ui-icon-info" onMouseOver="MM_showHideLayers('pubcrit','show')" 
onMouseOut="MM_showHideLayers('pubcrit','hide')" onClick="alert('<%= c_criteria.getDsc(CombinedCriteria.ByPubCriteria)%>');"></button>
<div id="pubcrit" class="criteria"><%= c_criteria.getDsc(CombinedCriteria.ByPubCriteria)%></div><%= c_criteria.getSDsc(CombinedCriteria.ByPubCriteria)%>
(<span id="importantText"><%= counter%> samples</span>)
 </td>
<% } else { %>
<td   class="regTxt" nowrap>
<%= c_criteria.getDsc(CombinedCriteria.ByPubCriteria)%>(<span id="importantText"><%= counter%> samples</span>)
 </td>
<%
   }
 }%>
						</tr>
						<tr>
						  <td align="left"  width="155" nowrap><button  class="setButton" onClick="openWindow2('set_avail.jsp',400,950,'set_win')">Data Availability</button></td>
						  <td align="left"   width="40"><span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="setDataAvailHelp"></span>
						  <% if(c_criteria.isSet(CombinedCriteria.ByDataAvailCriteria)){ %>
						  <a  onclick="submitForm('form1','pg2.jsp?clear=ByDataAvailCriteria')">
                          <button class="ui-state-default ui-corner-all ui-icon ui-icon-trash clearButton " title="clear"></button></a>
                          <% } %>                                 
						  </td>
						  
						  
<% 
     if(c_criteria.isSet(CombinedCriteria.ByDataAvailCriteria)){ 
        if((c_criteria.getSDsc(CombinedCriteria.ByDataAvailCriteria).length())< (c_criteria.getDsc(CombinedCriteria.ByDataAvailCriteria).length())){%> 
<td   class="regTxt" align="left">                
<button class="infoButton ui-state-default ui-corner-all ui-icon ui-icon-info" onMouseOver="MM_showHideLayers('dataAvcrit','show')" 
onMouseOut="MM_showHideLayers('dataAvcrit','hide')" onClick="alert('<%= c_criteria.getDsc(CombinedCriteria.ByDataAvailCriteria)%>');"></button>
<span id="dataAvcrit" class="criteria"><%= c_criteria.getDsc(CombinedCriteria.ByDataAvailCriteria)%></span> <span><%= c_criteria.getSDsc(CombinedCriteria.ByDataAvailCriteria)%></span>
(<span id="importantText"><%= counter%> samples</span>)
 </td>
<% } else { %>
<td   class="regTxt" nowrap>
<%= c_criteria.getDsc(CombinedCriteria.ByDataAvailCriteria)%>(<span id="importantText"><%= counter%> samples</span>)
 </td>
<%
   }
 } %>
						</tr>
						 
<!-- 						<tr>
						  <td align="left"  width="155" nowrap><button onClick="openWindow2('set_version.jsp',300,550,'set_win')">Data Version</button></td>
						  <td align="left"  nowrap class="topAlign"  width="40"><span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="setDataVersionHelp"/>
						  <% if(c_criteria.isSet(CombinedCriteria.ByDataVersionCriteria)){ %>
						  <button class="clearButton ui-state-default ui-corner-all ui-icon ui-icon-trash" title="clear" style="float:left;position:relative;left:5px;"  onclick="submitForm('form1','pg2.jsp?clear=ByDataVersionCriteria')"></button>
                          <% } %></td>
						  <td   class="regTxt" nowrap>
<% 
     if(c_criteria.isSet(CombinedCriteria.ByDataVersionCriteria)){ 
       if((c_criteria.getSDsc(CombinedCriteria.ByDataVersionCriteria).length())< (c_criteria.getDsc(CombinedCriteria.ByDataVersionCriteria).length())){%>          
<button class="infoButton ui-state-default ui-corner-all ui-icon ui-icon-info" onMouseOver="MM_showHideLayers('dataVrcrit','show')" 
onMouseOut="MM_showHideLayers('dataVrcrit','hide')" onClick="alert('<%= c_criteria.getDsc(CombinedCriteria.ByDataVersionCriteria)%>');"></button>
<div id="dataVrcrit" class="criteria"><%= c_criteria.getDsc(CombinedCriteria.ByDataVersionCriteria)%></div> <%= c_criteria.getSDsc(CombinedCriteria.ByDataVersionCriteria)%>
(<span id="importantText"><%= counter%> samples</span>)<% } else { %>
<%= c_criteria.getDsc(CombinedCriteria.ByDataVersionCriteria)%>(<span id="importantText"><%= counter%> samples</span>)
<%
   }
 } %>

						  </td>
						  </tr>


						<tr> 
						  <td colspan="3" align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
						</tr>
						-->
						
					  </table>
					</td>
				  </tr>
				 <!--  Sample Search block --> 
				 <tr><td class="new">Or you can search by specific sample only (not using the search criteria above)</td></tr>
				  <tr>					
					<td align="left" >
					<table style="border:0;cellspacing:2; cellpadding:2; width:100%;background: #F4F9FB;">
					<tr><td align="left"  width="155" nowrap>
                        <button id="button" class="setButton" onclick="openWindow2('sample_srch.jsp',400,700);return false();">Sample Look-up</button>
                    </td>
                    <td align="left"><span class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="specSampleHelp"></span></td>
                    </tr>
                    </table>				
 </td>
				  </tr>
				  
				  </table>
				  </td>
				  </tr>
				  
				  <tr align="left"><td colspan="2">
                    <table  class="crit" style="width:100%;cellspacing:10;cellpadding:0">
                        <tr><td align="left" colspan="6" class="new">Search Results
                        <%  if (counter > 0 || savedQ) {%>
                          <button class="helpButton ui-state-default ui-corner-all ui-icon ui-icon-help" id="step1ResultHelp"  style="left:160px"></button>
                        <%} %>
                        </td></tr>
                        <tr><td colspan="6" height="50">
                        <%  if (counter > 0) {%>
						  <div>The criteria so far selected give <b> <%= counter%></b> samples. Use the <button class="ui-state-default ui-corner-all ui-icon ui-icon-trash" title="clear" style="display:inline;width:18px;height:20px;"></button>
                           button to remove particular criteria without affecting the remaining settings.</div>
                          
						<%} else {%>
						  <p>No criteria are set.</p>
						<%} %>
						</td></tr>
						<tr align="center">
						<%  if (counter > 0) {%>
						<td><input name="Submit2" type="button" id="Submit2" class="importantButton" onClick="confrmSub();" value="Continue to Data Selection"></td>
						<td  valign="middle"> <input name="Submit1" type="button" id="Submit1"  value="View References" onClick="confPopup('reference_list.jsp')"></td>
                        <td><input name="Submit3" type="button" id="Submit3" value="View Expeditions" onClick="confPopup('expedition_list.jsp')"></td>
                        <td><input name="Submit1" type="button" id="Submit1"  value="View/Pick Samples " onClick="confPopup('view_pick_samples.jsp','900')"></td>
                        <td><input name="saveSQuery" type="button" id="saveSQuery"  value="Save Query" onClick="openWindow1('saveQry.jsp','save_win')"/></td>

						<% } else {%>
						<td width="16%" height="25"></td>
						<td width="16%"> </td>
                        <td width="16%"></td>
                        <td width="16%"></td>
                        <td width="16%"></td>
                        
						<%} %>
							<%  if (savedQ) { %> <td valign="middle" width="16%"><input name="retrieveSQuery" type="button" id="retrieveSQuery" value="Retrieve Query" onClick="openWindow1('retrvQry.jsp','save_win')">
						                     <input  name="criteria_type" type="hidden" value="<%= CombinedCriteria.ByDataCriteria%>">				  
                                            </td>
                            <% } else {%> <td valign="middle" width="16%">
                                          <%  if (counter > 0) {%>
                                          <input name="retrieveSQuery" type="button" id="retrieveSQuery" value="Retrieve Query" onClick="openWindow1('retrvQry.jsp','save_win')" disabled>
						                  <%} %>
						                  <input  name="criteria_type" type="hidden" value="<%= CombinedCriteria.ByDataCriteria%>">				  
                                          </td>
                            <%} %>
						 
                         </tr>
                  </table>
				  </td>
				  </tr>
	</table>
</form>
</div><!-- end pad -->
<div>Version 2.9.2 (Feb 7, 2018)</div>
<script LANGUAGE="JavaScript">
var counter = <%= counter%>;
var msg = "there are " + counter + " samples. This means\n"
		+ "the query will take a significant amount of time.\n"
		+ "You can choose to continue, or hit CANCEL to refine\n"
		+ "your query and reduce the result set.";

</script>
 <%@ include file="footer.jsp" %>
