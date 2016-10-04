<%@ page import="java.net.URLEncoder"%>
<%@ include file="headCode.jspf"%>
<%@ include file="pg4_parameters.jspf" %>
<%
Vector savedCCriterias =(Vector)c_c_collection.getSavedCCriterias();
boolean savedQ = false;
boolean hasComment=false;

if (savedCCriterias.size() > 1) savedQ = true;
int counter = -1;
String pgTitle = "PETDB: Petrological Database of The Ocean Floor - Data Table";
String pgType = "";
AValuePerKeyDS itemds = (AValuePerKeyDS) request.getSession().getAttribute("ItemMeasured");
int itemNum=0;
Vector itemDescs=new Vector();
AValuePerKeyDS methodds = (AValuePerKeyDS) request.getSession().getAttribute("Method");
Vector mineralDesc = new Vector();
Vector crystalDesc = new Vector();
Vector methodDesc = new Vector();
Vector materialDesc = new Vector();
Vector commentDesc = new Vector();
HashSet ref_num_set= new HashSet();
HashSet sample_num_set = new HashSet();
int methodNum=1;
%>
<%@ include file="head.jsp" %>
<script type="text/javascript">
document.title = "<%=pgTitle%>";
</script>

<!-- error block -->
<% if( errorMsg != null ){ %>
	<div class="pad"  align="left"><h3><%= errorMsg %></h3></div>
<% } else { %>
<div class="pad"  align="left">
<br />
<table><tr><td>
<div style="border:0px; padding:10px; ">
<form action="pg3.jsp" method="post" id="mybackform">
                                <input type=hidden name=criteria_type value="<%= criteria_type%>">
                                <input type="submit" value="Back" class="importantButton">
                                <input type="button" value="Start New Search" class="importantButton" onclick="submitForm('mybackform','start')">
</form>
<h1 style="clear:both;">PetDB Search</h1>
</div>
</td><td width="10px"></td><td width="450px"><div style="border:1px solid black; padding: 10px;">
<b>NEW:</b> Provide feedback about a dataset by clicking on the icon <img style="cursor:none" src="images/feedback3.png" width="15" height="15"/>
 in the Reference column.
</div></td></tr></table>
<form action ="ExcelDownload" name="tagAlongForm" id="tagAlongForm">
 <input name="criteria_type" type="hidden" value="<%= criteria_type %>">
<div style="border:0px solid black;padding:8px;"> <b><u>Download Options</u></b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>
<input type="checkbox" name="RESULT" checked/>&nbsp;Search Results (see table below)<br><br>
Add tag-along data<br>

<%  String isAllMAJ = request.getParameter("allMAJ");
    String isAllTE = request.getParameter("allTE");
    String isAllIR = request.getParameter("allIR");
    if(isAllMAJ == null || "".equals(isAllMAJ)) isAllMAJ = (String) session.getAttribute("allMAJ");
    else session.setAttribute("allMAJ", isAllMAJ);
    if(isAllTE == null || "".equals(isAllTE)) isAllTE = (String) session.getAttribute("allTE");
    else session.setAttribute("allTE", isAllTE);
    if(isAllIR == null || "".equals(isAllIR)) isAllIR = (String) session.getAttribute("allIR");
    else session.setAttribute("allIR", isAllIR);  
    String majChecked = "";
    String teChecked = "";
    String irChecked = "";
    if("true".equals(request.getParameter("MAJ"))) majChecked =" checked";
    if("true".equals(request.getParameter("TE"))) teChecked =" checked";
    if("true".equals(request.getParameter("IR"))) irChecked =" checked";
%>

<% if(tagAlong.getAvailableItemType("MAJ") && "false".equals(isAllMAJ)) {%>
<input type="checkbox" id="MAJ" name="MAJ" <%= majChecked %>/>&nbsp;include <b>Major Oxides for these samples</b><br>
<% } else { %>
<input type="checkbox" id="MAJ" name="MAJ" disabled="disabled"/>&nbsp;include <b>Major Oxides for these samples</b><% if(!tagAlong.getAvailableItemType("MAJ")) {%>&nbsp;(No data available)<%}%><br>

<% } if (tagAlong.getAvailableItemType("TE") && "false".equals(isAllTE)) { %>
<input type="checkbox" id="TE" name="TE" <%= teChecked %>/>&nbsp;include <b>Trace Elements for these samples</b><br>
<% } else { %>
<input type="checkbox" id="TE" name="TE" disabled="disabled"/>&nbsp;include <b>Trace Elements for these samples</b><% if(!tagAlong.getAvailableItemType("TE")) {%>&nbsp;(No data available)<%}%><br>

<% } if (tagAlong.getAvailableItemType("IR") && "false".equals(isAllIR)) { %>
<input type="checkbox" id="IR" name="IR" <%= irChecked %>/>&nbsp;include <b>Isotope Ratios for these samples</b><br>
<% } else { %>
<input type="checkbox" id="IR" name="IR" disabled="disabled"/>&nbsp;include <b>Isotope Ratios for these samples</b><% if(!tagAlong.getAvailableItemType("IR")) {%>&nbsp;(No data available)<%}%><br>
<% } %>



<% if((null != sub) && (!sub.equals( "" )) && (sub.equals("y"))) { %>
<input type="button" value="Download" class="importantButton" onClick="tagAlongForm.submit()" />
<% } else { 
   String feedbackStr =(String) session.getAttribute("feedback");
%>
<input type="button" value="Download" class="importantButton" onClick="feedbacksurvey('<%=feedbackStr %>');" />
<% } %>



</div>
</form>
<br/>
<table >
<tr class="smTxt" valign="top">
  <td align="center" valign="top"><form action="expedition_list.jsp" target="list_win" method="post">
<input type=hidden name=source value="final"><input type="submit" value="Get Expedition Info" onClick="openWindow1('expedition_list.jsp','list_win','450','900')"></form></td>
	<td align="center" valign="top"><form action="reference_list.jsp" target="list_win" method="post">
<input type=hidden name=source value="final"><input type="submit" value="Get Reference Info " onClick="openWindow1('reference_list.jsp','list_win','450','900')">
</form></td>

<%
if (!(criteria instanceof ByRockModeCriteria)) {
%>
	<td align="center" valign="top">
		<form action="method_list.jsp" target="list_win" method="post">
			<input type=hidden name=source value="<%= final_data.getAnalysisStr()%>"><input type="submit" value="  Get Method Info  "  onClick="openWindow1('method_list.jsp','list_win','700','900')">
			<!-- <a href="method_info.jsp?singlenum=dt_quality_num"> method</a> -->
		</form>
	</td>
<%
}
%>
	<td>
		<form action="sample_map.jsp" target="mapresults_win" method="post">
        <input type=hidden name="andVariable" value="<%= ((ByChemistryCriteria )criteria).isANDQuery()%>">
        <input type=hidden name="itemCode" value="<%= ((ByChemistryCriteria )criteria).getItemCodes()%>">
       
			<input type="submit" value="      View Map      " onClick="openWindow2('sample_map.jsp', 630, 840, 'mapresults_win')">
		</form>
	</td>
	</tr>
</table>
<!-- Summary of the selected criteria -->
<h3 class="flip" style="display:inline;">Your selection criteria are:
<span class="ui-state-default ui-corner-all ui-icon ui-icon-circle-plus" style="position:relative;display:inline-block;" title="Click to see the detail"></span></h3>

<div class="results" id="pg3summary" style="overflow:auto;display:none;margin:3px;min-height:30px">
			   <%@ include file="reduced_criteria.jspf"%>
</div>
<%
	if ((currentRow >= page_size)||(currentRow + page_size < totalCount)){
%>
<form name="form2" method="post" action="pg4.jsp">
<input  name="browsing" type="hidden" value="">
<input  name="MAJ" type="hidden" value="">
<input  name="TE" type="hidden" value="">
<input  name="IR" type="hidden" value="">
	<table style="align:left">
		<tr>
<%
	if (currentRow >= page_size)
	{
%>
              <td align="left" class="notices"><a href="#" onClick="SubmitWithBrowsing('first');"><img name="arrow_1" src="images/bkwd_double_arrow.gif" width="25" height="21" border="0"  alt="first page" title="first page" align="middle">first page </a></td>
              <td align="left" class="notices" nowrap><a href="#" onClick="SubmitWithBrowsing('previous');"><img name="arrow_2" src="images/bkwd_single_arrow.gif" width="20" height="21" border="0" alt="previous page" title="previous page" align="middle">previous page </a></td>
              <td >&nbsp;</td>
<%
	}
	if ( (currentRow + page_size < totalCount))
	{
%>
              <td class="notices" align="left"><a href="#" onClick="SubmitWithBrowsing('next');"> next page<img name="arrow_3" src="images/fwd_single_arrow.gif" width="20" height="21" border="0" alt="next page" title="next page" align="middle"></a></td>
              <td class="notices"><a href="#" onClick="SubmitWithBrowsing('last');"> last page<img name="arrow_4" src="images/fwd_double_arrow.gif" width="25" height="21" border="0" alt="last page" title="last page" align="middle"></a></td>

<%
  }
%>
        <td width="70%"><p><%= (totalCount != 0 ? "Summary: " + (currentRow+1) +" - " + Math.min(currentRow + page_size,totalCount) + " of " : "")%><%= totalCount%> <%= analysis_summary%> </p>
        </td>
      </tr>
 </table></form>
 <% } %>
<div class="clearer"></div>
<span>( Mouseover the acronym for more information;&nbsp;&nbsp; click&nbsp;<input style="cursor:none" type="image" src="images/feedback3.png" width="15" height="15"/>&nbsp;to send feedback)</span>

<div style="display:none">

<!--  DEGUG Block -->
<% 
//try{
//	out.println(final_wrapper.toString());
      	  // Create file 
//      	  FileWriter fstream = new FileWriter("C:\\Users\\Lulin Song\\Downloads\\resultqry.txt");
//      	  BufferedWriter outFile = new BufferedWriter(fstream);
//      	  outFile.write( final_wrapper.toString() );
      	  //Close the output stream
//      	  outFile.close();
//      	  }catch (Exception e){//Catch exception if any
//      	  System.err.println("Error: " + e.getMessage());
//      	  }
%>

</div>

<div>
<table class="basic" style="cellpadding:3; align:left">
            <tr valign="top" class="setLabel1T">
              <th>Sample ID</th>
              <th>IGSN</th>
              <th> Reference</th>
              <th> Expedition</th>
<%
	if (!(criteria instanceof ByRockModeCriteria))
	{
%>
              <th> Method</th>
<%
	}
	if (criteria instanceof ByMineralCriteria)
	{
%>
          <th>Mineral</th>
          <th>Crystal</th>
          <th>Rim/Core</th>     
             <th id="commentCol0">Comment</th>
	  
<%
	} else if (criteria instanceof ByInclusionCriteria)
	{
%>
          <th>Host Mineral</th>
          <th>Heating</th>
          <th>Rim/Core</th>
<%
	} else if (criteria instanceof ByDataCriteria) {
 %>
	
	  <th>Material</th>
	  	<% if ( !compiled ) { %>
	       <th id="commentCol0">Comment</th>
	    <% } %>
<%
	}
	int field_count = 0;
	for (int j=0; j<s_keys.length; j++)
	{
		String[]  fields = ((ByChemistryCriteria)criteria).getParam(s_keys[j]);
		if (fields == null ) continue;
		for (int i=0; i< fields.length; i++)
		{
			field_count ++;
			String val = (String)itemds.getValue(fields[i]);
			itemNum++;
			itemDescs.add(val);
%>
          <th>          
          <span id="field<%=itemNum %>" onClick="alert('<%= val %>');"><%= fields[i]%></span>         
          </th>
  <%
		}
	}
%>
          <th> Latitude</th>
          <th> Longitude</th>
          <th> Elevation</th>
          <th> Tectonic</th>
          <th> Rock Type</th>
        </tr>
<%
	int inclusion_type 	= 0;
	int heating 		= 0;
	int material		= 0;
	int material_desc	= 0;
	int mineral 		= 0;
	int mineral_desc 	= 0;
	int crystal 		= 0;
	int crystal_desc 	= 0;
	int rim_core 		= 0;
	int analysis_num=-1, sample_id=-1, sample_igsn=1,lat=-1, log=-1, elev=-1, tect=-1, rock=-1, ref=-1, ref_num=-1, exp=-1, exp_num=-1, mth=-1, sample_num=-1,comment=-1;
	if (criteria instanceof ByMineralCriteria)
	{
	        analysis_num 	= field_count + MineralFSDS.Analysis_Key; 
        	sample_id 	= field_count + MineralFSDS.Sample_ID ;
        	sample_igsn = field_count + MineralFSDS.Sample_IGSN;
        	mineral		= field_count + MineralFSDS.Mineral;
        	mineral_desc 	= field_count + MineralFSDS.Mineral_Desc;
        	crystal		= field_count + MineralFSDS.Crystal;
        	crystal_desc	= field_count + MineralFSDS.Crystal_Desc;
        	rim_core 	= field_count + MineralFSDS.Rim_or_Core;
        	lat 		= field_count + MineralFSDS.Latitude;
        	log 		= field_count + MineralFSDS.Longitute;
        	elev 		= field_count + MineralFSDS.Elevation;
        	tect 		= field_count + MineralFSDS.Tectonic;
        	rock 		= field_count + MineralFSDS.Rock;
        	ref 		= field_count + MineralFSDS.Reference;
        	ref_num		= field_count + MineralFSDS.Reference_Num;
        	exp		= field_count + MineralFSDS.Expedition;
        	exp_num		= field_count + MineralFSDS.Expedition_Num;
        	mth 		= field_count + MineralFSDS.Method;
        	sample_num 	= field_count + MineralFSDS.Sample_Num;
        	comment     = field_count + MineralFSDS.Analysis_Comment;
	} else if (criteria instanceof ByInclusionCriteria)
	{
	        analysis_num 	= field_count + InclusionFSDS.Analysis_Key; 
        	sample_id 	= field_count + InclusionFSDS.Sample_ID ;
        	sample_igsn = field_count + InclusionFSDS.Sample_IGSN;
            inclusion_type 	= field_count + InclusionFSDS.Inclusion_Type;
        	mineral		= field_count + InclusionFSDS.Mineral;
        	mineral_desc 	= field_count + InclusionFSDS.Mineral_Desc;
        	heating 	= field_count + InclusionFSDS.Heating;
        	rim_core 	= field_count + InclusionFSDS.Rim_or_Core;
        	lat 		= field_count + InclusionFSDS.Latitude;
        	log 		= field_count + InclusionFSDS.Longitude;
        	elev 		= field_count + InclusionFSDS.Elevation;
        	tect 		= field_count + InclusionFSDS.Tectonic;
        	rock 		= field_count + InclusionFSDS.Rock;
        	ref 		= field_count + InclusionFSDS.Reference;
        	ref_num		= field_count + InclusionFSDS.Reference_Num;
        	exp		= field_count + InclusionFSDS.Expedition;
        	exp_num		= field_count + InclusionFSDS.Expedition_Num;
        	mth 		= field_count + InclusionFSDS.Method;
        	sample_num 	= field_count + InclusionFSDS.Sample_Num;
	} else if (criteria instanceof ByDataCriteria) 
	{
	        analysis_num 	= field_count + DataFSDS.Analysis_Key; 
        	sample_id 	= field_count + DataFSDS.Sample_ID ;
        	sample_igsn = field_count + DataFSDS.Sample_IGSN;
        	material 	= field_count + DataFSDS.Material ;
        	material_desc 	= field_count + DataFSDS.Material_Desc ;
        	lat 		= field_count + DataFSDS.Latitude;
        	log 		= field_count + DataFSDS.Longitute;
        	elev 		= field_count + DataFSDS.Elevation;
        	tect 		= field_count + DataFSDS.Tectonic;
        	rock 		= field_count + DataFSDS.Rock;
        	ref 		= field_count + DataFSDS.Reference;
        	ref_num 	= field_count + DataFSDS.Reference_Num;
        	exp		    = field_count + DataFSDS.Expedition;
        	exp_num		= field_count + DataFSDS.Expedition_Num;
        	mth 		= field_count + DataFSDS.Method;
        	sample_num 	= field_count + DataFSDS.Sample_Num;
        	comment     = field_count + DataFSDS.Analysis_Comment;
	}
	if (final_data != null)
	{
    int layerNum = 1;
	while ( (rows_written-- > 0) && (final_data.next()) )
	{
        if( !((null != sub) && (!sub.equals( "" )) && (sub.equals("y")) ) ) 
        {
		  sample_num_set.add(new Integer(final_data.getValue(sample_num)));
		  //System.out.println("SampleNum="+final_data.getValue(sample_num));
        }
%>
        <tr valign="top" class="rowCream">
          <td valign="middle"><a href='<%= "sample_info.jsp?sampleID="+ URLEncoder.encode(final_data.getValue(sample_id),"UTF-8")%>'   target="set_win" onClick="openWindow2(this,700,900)"><%= final_data.getValue(sample_id)%></a></td>
          <td valign="middle">
          <% String igsnStr= final_data.getValue(sample_igsn); 
                 if("&nbsp;".	equalsIgnoreCase(igsnStr)==true) 
                	 out.print("N/A"); 
                 else 
                 { 
                	 String igsnURL= "<a href='#' onclick=\"openwindow('"+PetDBConstants.igsnSESARURL+igsnStr+"','"+igsnStr+"','600','400')\" >"+igsnStr+"</a>";
                	 out.print(igsnURL);
                 }
          
          %></td>
          <td valign="middle">
	<%
             StringTokenizer refs = new StringTokenizer(final_data.getValue(ref),";");
             StringTokenizer ref_nums = new StringTokenizer(final_data.getValue(ref_num),";");
             if( !((null != sub) && (!sub.equals( "" )) && (sub.equals("y")) ) ) 
             {
               ref_num_set.add(new Integer(final_data.getValue(ref_num)));
         	   //System.out.println("ReferenceNum"+final_data.getValue(ref_num));
             }
        //     String ref_list ="";
             	int ref_counter = 0;

		while (ref_nums.hasMoreTokens()) {
				ref_counter++; 
                                String r = refs.nextToken();
                                String r_n = ref_nums.nextToken();
                         //       if(ref_counter==1) ref_list = r_n;
                          //      else ref_list += ", "+r_n; 
	%>
<%= (ref_counter>1 ? "; " : "")%><a href="ref_info.jsp?singlenum=<%= r_n%>&fb=true" target="set_win" onClick="openWindow2(this,450,900)"><%= r%></a>
<input type="image" src="images/feedback3.png" title="Feedback" width="15" height="15" onClick="openWindow2('ref_feedback.jsp?refNum=<%= r_n %>',450,900)">
	<%   }  %>
	</td>
          <td valign="middle"><a href="exped_info.jsp?singlenum=<%= final_data.getValue(exp_num)%>" target="set_win" onClick="openWindow2(this,450,900)"><%= final_data.getValue(exp)%></a></td>
<%
	if (!(criteria instanceof ByRockModeCriteria))
	{
		String methodStr = final_data.getValue(mth);
		int mt = 1;
		if( methodStr.indexOf(';') == -1 ) //not found
		{
			String descStr =(String) methodds.getValue(final_data.getValue(mth));
		    descStr = descStr.replace('\n',' ');
			methodDesc.add(descStr);
			
		}
		else //precompiled
		{
			String[] methodStrs = methodStr.split(";");
			mt = methodStrs.length;
			for (int i=0;i<mt;i++)
			{
			   String descStr =(String) methodds.getValue(methodStrs[i].trim());
			   descStr = descStr.replace('\n',' ');
			   methodDesc.add(descStr);
			}

		}
		
%>
          <td valign="middle">
          <% 
          if( mt == 1 ) {
        	  %>
          <span id="method<%= methodNum %>" ><%= final_data.getValue(mth)%></span>
          <%
           methodNum++; } else {
        	   String[] methodStrs = methodStr.split(";");
   			mt = methodStrs.length;   
            for (int i=0;i<mt;i++) {
            	 %> 
            <span id="method<%= methodNum %>" ><%= methodStrs[i]%></span>
            <%
            methodNum++;
            }
          }
          %>
          
          </td>
<%
	}
	if (criteria instanceof ByMineralCriteria)
	{
		mineralDesc.add((String) final_data.getValue(mineral_desc) );
		crystalDesc.add((String) final_data.getValue(crystal_desc) );
		
		
			String commentStr=null;
			boolean showicon=false;
			{
			  commentStr = (String) final_data.getValue(comment);
			  commentDesc.add( commentStr );
			  if( commentStr != null ) 
				if(commentStr.equals("&nbsp;") == false ) 
				{   hasComment=true;
					showicon=true;
				}
			}
		
%>
          <td width="20" valign="middle"><span id="mineral<%= layerNum %>" ><%= final_data.getValue(mineral)%></span>
          </td>
          <td valign="middle"><span id="crystal<%=layerNum %>" ><%= final_data.getValue(crystal)%></span>
          </td>
          <td valign="middle"><%= final_data.getValue(rim_core)%></td>
          
          
             <td align="center" valign="middle" id="commentCol<%= layerNum %>">
              <% if (showicon==true) { %>
              <span id="comment<%= layerNum %>" style="border-radius: 4px 4px 4px 4px;" class="ui-state-default ui-corner-all ui-icon ui-icon-comment"></span>
              <% } %>
          </td>
     
<%
	} else if (criteria instanceof ByInclusionCriteria)
	{
		mineralDesc.add(final_data.getValue(mineral_desc) );
%>
          <td valign="middle"><span class="plain"  id="mineral<%= layerNum %>" ><%= final_data.getValue(mineral)%></span>
          </td>
          <td  valign="middle"><%= final_data.getValue(heating)%></td>
          <td  valign="middle"><%= final_data.getValue(rim_core)%></td>
        
<%
	} else  if (criteria instanceof ByDataCriteria) {
		materialDesc.add((String) final_data.getValue(material_desc) );
		String commentStr=null;
		boolean showicon=false;
		if ( !compiled )
		{
		  commentStr = (String) final_data.getValue(comment);
		  commentDesc.add( commentStr );
		  if( commentStr != null ) 
			if(commentStr.equals("&nbsp;") == false ) 
			{   hasComment=true;
				showicon=true;
			}
		}
%>
          <td valign="middle"><span id="material<%= layerNum %>" ><%= final_data.getValue(material)%></span>
          </td>
          
          <% if ( !compiled ) {%>
          <td align="center" valign="middle" id="commentCol<%= layerNum %>">
              <% if (showicon==true) { %>
              <span id="comment<%= layerNum %>" style="border-radius: 4px 4px 4px 4px;" class="ui-state-default ui-corner-all ui-icon ui-icon-comment"></span>
              <% } %>
          </td>
          <% }%>
<%
	}
	int abs_index = 0;

		for (int j=0; j<s_keys.length; j++)
		{

	String[]  fields = ((ByChemistryCriteria)criteria).getParam(s_keys[j]);
	if (fields == null ) continue;
		for (int i=0; i< fields.length; i++)
		{
			String val =  final_data.getValue(i+1+abs_index);
			if (val.equals("&nbsp;")) {
%>
          <td valign="middle"><%= final_data.getValue(i+1+abs_index)%></td>
<%
			} else {
				if (compiled)
				{
%>
          <td valign="middle"><a href="<%= info_page%>?singlenum=<%= ((VectorFSDS)final_data).getAnalysis(i+abs_index)%>" target="set_win" onClick="openWindow2(this,450,900)"><%= final_data.getValue(i+1+abs_index)%></a></td>
<%
	
				}
				else
				{ 
%>
          <td valign="middle"><a href="<%= info_page%>?singlenum=<%= final_data.getValue(analysis_num)%>" target="set_win" onClick="openWindow2(this,450,900)"><%= final_data.getValue(i+1+abs_index)%></a></td>
<%
				}
				
			}
		}
	abs_index += fields.length;
	}
%>
          <td valign="middle"><% String latstr=final_data.getValue(lat); 
                 int latlen=latstr.length(); 
                 latstr = latstr.substring(0,latlen-2)+"&deg;"+latstr.substring(latlen-1,latlen); 
               %>
          <%= latstr%></td>
          <td valign="middle">
          <% String logstr=final_data.getValue(log); 
                 int loglen=logstr.length(); 
                 logstr = logstr.substring(0,loglen-2)+"&deg;"+logstr.substring(loglen-1,loglen); 
               %>
          <%= logstr %></td>
          <td valign="middle"><%= final_data.getValue(elev)%></td>
          <td valign="middle"><%= final_data.getValue(tect)%></td>
          <td valign="middle"><%= final_data.getValue(rock)%></td>
        </tr>
<%
        layerNum++;
	}  // end of while
		
    if( !((null != sub) && (!sub.equals( "" )) && (sub.equals("y")) ) ) //download button is clicked
    {
    	if (compiled)
		{
    		VectorFSDS vsds = (VectorFSDS) final_data;
    		session.setAttribute("searched_refs",vsds.getReferenceNumberSet());
    	    session.setAttribute("searched_samples",vsds.getSampleNumberSet());
		}
    	else
    	{
    	  int forwardCnt=0;
	      while ( final_data.next() )
	      {
		    sample_num_set.add(new Integer(final_data.getValue(sample_num)));
		    //System.out.println("SampleNum="+final_data.getValue(sample_num));		
            ref_num_set.add(new Integer(final_data.getValue(ref_num)));
    	    //System.out.println("ReferenceNum"+final_data.getValue(ref_num));    	    
            forwardCnt++;
	      }
          while( forwardCnt-- != 0 ) final_data.previous(); final_data.previous();//rewind to previous data point.    	
	      session.setAttribute("searched_refs",ref_num_set);
	      session.setAttribute("searched_samples",sample_num_set);
    	}
    }
  }
%>
</table>
</div>
<div class="clearer"></div>
<%
	if ((currentRow >= page_size)||(currentRow + page_size < totalCount)){
%>
<form name="form3" method="post" action="pg4.jsp">
<table style="align:left;cellspacing:0; cellpadding:0">

        <tr>
<%
	if (currentRow >= page_size)
	{
%>
              <td align="left"  valign="middle" class="notices"><a href="#" onClick="SubmitWithBrowsing('first');"><img name="arrow_1" src="images/bkwd_double_arrow.gif" width="25" height="21" border="0"  alt="first page" title="first page" align="middle">first page </a></td>
              <td align="left"  valign="middle" class="notices" nowrap><a href="#" onClick="SubmitWithBrowsing('previous');"><img name="arrow_2" src="images/bkwd_single_arrow.gif" width="20" height="21" border="0" alt="previous page" title="previous page" align="middle">previous page </a></td>
              <td >&nbsp;</td>
<%
	}
	if ( (currentRow + page_size < totalCount))
	{
%>
              <td class="notices" align="left" valign="middle"><a href="#" onClick="SubmitWithBrowsing('next');"> next page<img name="arrow_3" src="images/fwd_single_arrow.gif" width="20" height="21" border="0" alt="next page" title="next page" align="middle"></a></td>
              <td class="notices"  valign="middle"><a href="#" onClick="SubmitWithBrowsing('last');"> last page<img name="arrow_4" src="images/fwd_double_arrow.gif" width="25" height="21" border="0" alt="last page" title="last page" align="middle"></a></td>

<%
  }
%>
         <td width="50%">(Mouseover the acronym for more information;)</td>
        </tr>

</table>
  </form>
<% } %>
</div><!-- end div -->
<% } %>
<%@ include file="footer.jsp" %>

<script>
//Wait for document is ready
$(document).ready(function(){

$(".flip .ui-icon-circle-plus").toggle(function() {
	$(this).removeClass("ui-icon-circle-plus");
	$(this).addClass("ui-icon-circle-minus");
	$("#pg3summary").slideToggle("fast");
	}, function() {
	$(this).removeClass("ui-icon-circle-minus");
	$(this).addClass("ui-icon-circle-plus");
	$("#pg3summary").slideToggle("fast");
	});

    <% if((null != sub) && (!sub.equals( "" )) && (sub.equals("y"))) 
    { //Come back from Feedback Survey.
    	String majflag = (String)session.getAttribute("MAJ");
    	String teflag = (String)session.getAttribute("TE");
    	String irflag = (String)session.getAttribute("IR");
    	String email = (String) request.getParameter("email");
    	String purpose = (String) request.getParameter("purpose");
        session.setAttribute("email",email);
        session.setAttribute("purpose",purpose);        
        
        if(null != majflag && majflag.equals("on"))
        {
        %>
          $('#tagAlongForm').find('input[name="MAJ"]').attr('checked',true);
        <%	  
        }
        if(null != teflag && teflag.equals("on"))
        {
        %>
          $('#tagAlongForm').find('input[name="TE"]').attr('checked',true);
        <%
        }
        if(null != irflag && irflag.equals("on"))
        {
        %>
          $('#tagAlongForm').find('input[name="IR"]').attr('checked',true);
        <%
        } 
        %>
        document.tagAlongForm.submit();
    <% 
    }
    %>
    
  //create qtip for acronyms on the table heading
    <%  int total=itemDescs.size();
    for (int i=1;i<=total;i++) { 
       String idNum = "'field"+i+"'";
       String contentStr = (String) itemDescs.get(i-1);
    %>
    creatAndShowQtip(<%=idNum%>,'<%=contentStr%>')
	  <% } %>
	  
	  <% total=mineralDesc.size();
         for (int i=1;i<=total;i++) { 
         String idNum = "'mineral"+i+"'";
         String contentStr = (String) mineralDesc.get(i-1);
	  %>
	  creatAndShowQtip(<%=idNum%>,'<%=contentStr%>')	
	  
	  <% }%>
	  
	  <% total=crystalDesc.size();
      for (int i=1;i<=total;i++) { 
      String idNum = "'crystal"+i+"'";
      String contentStr = (String) crystalDesc.get(i-1);
	  %>
	  creatAndShowQtip(<%=idNum%>,'<%=contentStr%>')	
	  
	  <% }%>
	  
	  <% total=methodDesc.size();
      for (int i=1;i<=total;i++) { 
      String idNum = "'method"+i+"'";
      String contentStr = (String) methodDesc.get(i-1);
	  %>
	  creatAndShowQtip(<%=idNum%>,'<%=contentStr%>')	 
	  <% }%>	
	  
	  <% total=materialDesc.size();
      for (int i=1;i<=total;i++) { 
      String idNum = "'material"+i+"'";
      String contentStr = (String) materialDesc.get(i-1);
	  %>
	    creatAndShowQtip(<%=idNum%>,'<%=contentStr%>')	   
	 <% }%>

	 var jsCommentDesc = new Array();
	 <% total=commentDesc.size();
     for (int i=0;i<total;i++) 
     {
     %>
       jsCommentDesc.push( '<%= commentDesc.get(i) %>' ) 
     <% 
     } 
     %>
	 createAnalysisCommentQtip(jsCommentDesc,'comment');
	 removeEmptyAnalysisColumn('comment',1,<%=page_size%>);
});

function SubmitWithBrowsing(a) {
    document.form2.MAJ.value = $('#MAJ').is(':checked');
    document.form2.TE.value = $('#TE').is(':checked');
    document.form2.IR.value = $('#IR').is(':checked');
	document.form2.browsing.value =a;
	document.form2.submit();
	return;
}

function feedbacksurvey(feedbackStr)
{
	var str = $('#tagAlongForm input').serialize();

	 //alert( "Data Saved: " + msg );
	// openWindow2("download_feedback.jsp?"+str, '450','900','_self');	 
	openWindow1("download_feedback.jsp?"+str, '_self', '450','900');	
}
	 
</script>