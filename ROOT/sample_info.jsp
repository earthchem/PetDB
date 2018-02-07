<%@ include file="headCode2.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>PETDB: Single sample info</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="js/JQuery/css/overcast/jquery-ui-1.7.3.custom.css" rel="stylesheet" type="text/css"/>
<link href="css/petdb.css" rel="stylesheet" type="text/css">
<script  language= "JavaScript" type="text/javascript" src="js/OpenLayers-2.12/OpenLayers.js"></script>
<script  language= "JavaScript" type="text/javascript" src=js/openlayermap.js></script>
<script type="text/javascript" src=js/windows.js></script>
<script type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
</head>

<%
        String sample_num = request.getParameter("singlenum");
        String sampleName = request.getParameter("sampleID");
        if(sample_num == null) 
           sample_num = new SimpleQuery("select sample_num from sample where SAMPLE_ID = '"+sampleName+"'").getSingleResult();
              
        if (sample_num == null || !PetdbUtil.isInteger(sample_num)) throw new Exception("You are trying to go to Sample Info page, with NO sample specified");
        String ipAddress = new IPAddress().getIpAddrWithFilter(request);
       // if(ipAddress != null)
       //     new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Sample Info',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
               
        SampleInfoWrapper wrapper 		    = new SampleInfoWrapper(sample_num);
        SampleInfo1DS ds1 		    = (SampleInfo1DS)wrapper.getControlList("0");
	    OrderedChemicals ds3 		= (OrderedChemicals)wrapper.getControlList("1");
        SampleInfoAnalysisDS rockDS 	= (SampleInfoAnalysisDS)wrapper.getControlList("2");
	    SampleInfoAnalysisDS minDS 	= (SampleInfoAnalysisDS)wrapper.getControlList("3");
	    SampleInfoAnalysisDS incDS 	= (SampleInfoAnalysisDS)wrapper.getControlList("4");
	    IGSNInfo1DS igsnDS = (IGSNInfo1DS) wrapper.getControlList("5"); /* 6th DataSet stored */
	    SampleInfoAnalysisDS allDS;

        String s_id	="Not Available";
        String igsn="Not Available";
        String igsnURL="Not Available";
        String aliasesRefStr=" ";
        String lat	="Not Available";
        String lon	="Not Available";
        String depth	="Not Available";
        String tect	="Not Available";
        String l_N	="Not Available";
        String l_C	="Not Available";
        String r_type	="Not Available";
        String desc 	="Not Available";
        String alt 	="Not Available";
        String g_age	="Not Available";
        String age	="Not Available";
        String e_name	="Not Available";
        String e_num	="Not Available";
        String chief	="Not Available";
        String s_date	="Not Available";
        String s_tech	="Not Available";
        String st	="Not Available";
        String st_id	="Not Available";
        String inst	=" ";
        String dept	=" ";
	    String loc 	="Not Available";
        String prev_s_id ="";
        Vector commentDesc = new Vector();
        Vector rmcommentDesc = new Vector();
        Vector mincommentDesc = new Vector();
        Set r_typeSet = new HashSet();
        Map rockClassMap = new HashMap();
        Map authorMap = new HashMap();
        StringBuffer rcSB = new StringBuffer();
        String rc="";
        
		int commentNum=1;
		int rmcommentNum=1;
		int mincommentNum=1;
        if (ds1 != null) {
                while (ds1.next())
                {
                        prev_s_id =  ds1.getValue(SampleInfo1DS.SAMPLE_ID);
                        if (prev_s_id.equals(s_id)){
                                l_N = ds1.getValue(SampleInfo1DS.LOC_Name);
                                l_C = ds1.getValue(SampleInfo1DS.LOC_Com);
				                loc = l_N;
                                r_typeSet.add(ds1.getValue(SampleInfo1DS.ROCKTYPE));
                                rc  = ds1.getValue(SampleInfo1DS.ROCKCLASS);
                                if(rockClassMap.containsKey(rc)) {
                                    ((Set)rockClassMap.get(rc)).add(ds1.getValue(SampleInfo1DS.REF_NUM));
                                } else {
                                    Set rockClassSet = new HashSet();
                                    rockClassSet.add(ds1.getValue(SampleInfo1DS.REF_NUM));
                                    rockClassMap.put(rc,rockClassSet);                                   
                                }                               
                                
                        } else {
                                s_id	=  ds1.getValue(SampleInfo1DS.SAMPLE_ID);
                                lat	=  ds1.getValue(SampleInfo1DS.LATITUDE);
                                lon	=  ds1.getValue(SampleInfo1DS.LONGITUDE);
                                depth	=  ds1.getValue(SampleInfo1DS.DEPTH);
                                tect	=  ds1.getValue(SampleInfo1DS.TECT);
                                l_N	=  ds1.getValue(SampleInfo1DS.LOC_Name);
                                if("Not Available".equalsIgnoreCase(loc) == true)
                                	loc =l_N;
                                else
				                    loc += "; " + l_N; 
                                l_C	=  ds1.getValue(SampleInfo1DS.LOC_Com);
                                r_typeSet.add(ds1.getValue(SampleInfo1DS.ROCKTYPE));
                                rc  = ds1.getValue(SampleInfo1DS.ROCKCLASS);
                                if(rockClassMap.containsKey(rc)) {
                                    ((Set)rockClassMap.get(rc)).add(ds1.getValue(SampleInfo1DS.REF_NUM));
                                } else {
                                    Set rockClassSet = new HashSet();
                                    rockClassSet.add(ds1.getValue(SampleInfo1DS.REF_NUM));
                                    rockClassMap.put(rc,rockClassSet);                                   
                                }
                                desc 	=  ds1.getValue(SampleInfo1DS.DESC); /* FIXME: It has multiple entries. It should include all */
                                alt 	=  ds1.getValue(SampleInfo1DS.ALTERATION);
                                g_age 	=  ds1.getValue(SampleInfo1DS.GEOL_AGE);
                                age 	=  ds1.getValue(SampleInfo1DS.AGE);
                                e_name 	=  ds1.getValue(SampleInfo1DS.EXP_Name).trim();
                                e_num 	=  ds1.getValue(SampleInfo1DS.EXP_Num);
                                if(!"NOT PROVIDED".equals(e_name)) {
                                    e_name = "<a href=\"exped_info.jsp?singlenum="+e_num+"\" target=\"_self\">"+e_name+"</a>";
                                }
                                
                                chief 	=  ds1.getValue(SampleInfo1DS.CHIEF);
                                s_date 	=  ds1.getValue(SampleInfo1DS.SAMP_DATE);
                                s_tech 	=  ds1.getValue(SampleInfo1DS.SAMP_TECH);
                                st 	=  ds1.getValue(SampleInfo1DS.STATION);
                                st_id	 =  ds1.getValue(SampleInfo1DS.STATION_Num);
                        }

                }
                r_typeSet.remove("&nbsp;");
                r_type = r_typeSet.toString();
                r_type = r_type.substring(1, r_type.length()-1);
                if(rockClassMap.containsKey("&nbsp;")) rockClassMap.remove("&nbsp;");
        }
      if(igsnDS != null)
    	  while ( igsnDS.next() ) igsn=igsnDS.getValue(IGSNInfo1DS.IGSN);
  	  if( "Not Available".equalsIgnoreCase(igsn) == false ) 
		igsnURL = "<a href='#IDBlock' onclick=\"openwindow('"+PetDBConstants.igsnSESARURL+igsn+"','"+igsn+"','600','400')\" >"+igsn+"</a>";
	  else
		igsnURL="N/A";

      String archive ="Not Available";
      if(" ".equalsIgnoreCase(inst)==false || " ".equalsIgnoreCase(dept) == false) archive = inst+" "+dept;
      if( "&nbsp;".equalsIgnoreCase(lat) )  lat    ="Not Available";
      if( "&nbsp;".equalsIgnoreCase(lon) )  lon    ="Not Available";
      if( "&nbsp;".equalsIgnoreCase(s_date) )  s_date    ="Not Available";
      if( "&nbsp;".equalsIgnoreCase(chief) )  chief    ="Not Available";
      if( "&nbsp;".equalsIgnoreCase(tect) ) tect   ="Not Available";
      if( "&nbsp;".equalsIgnoreCase(l_C) ) l_C   ="Not Available";      
      if( "&nbsp;".equalsIgnoreCase(depth) )  depth    ="Not Available";
      if( "&nbsp;".equalsIgnoreCase(alt) )  alt    ="Not Available";
      desc=desc.trim();
      if( "&nbsp;".equalsIgnoreCase(desc) ) 
    	  desc   ="Not Available";
      else if(desc.charAt(0)==':') 
    	  desc=desc.replaceFirst(":","");
      else
      {
    	  int len = desc.length();
    	  if(desc.charAt(len-1)==':') desc=desc.substring(0,len-1);
      }
      
      String ageStr = "Not Available";
      if("&nbsp;".equalsIgnoreCase(g_age) == false || "&nbsp;".equalsIgnoreCase(age) == false) 
    	  ageStr = g_age + ( ((!g_age.equals("&nbsp;")) && (!age.equals("&nbsp;"))) ? (", " + age) : age);
      
      String rockmodedataTableStr="";//rockmode data will extract from rock data. rock mode analysis and rock mode table will not exist in the future.
%>
<body class="pop">
<div class="popTop">
<p class="emphasis" style="float:left;">
<font size="+1">Sample information</font><br />
</p>
<div class="clearer"></div>
</div><!-- end popTop -->

<table border="0" class="sub2a">
  <tr><td>
      <!-- Identification block -->
      <table  border="0" id="#IDBlock">
        <tr><td colspan="2"><span class="keyword">Identification:</span></td></tr>
        <tr><th width="10%" align="left" nowrap scope="row">PetDB Identifier:</th><td width="90%" align="left" class="regTxt"><%= s_id%></td></tr>
        <tr><th width="10%" align="left" nowrap scope="row">IGSN:</th><td width="90%" align="left" class="regTxt"><%= igsnURL%></td></tr>
        <tr><th width="10%" align="left" nowrap scope="row">Other Names:</th><td width="90%" align="left" id="aliases" class="regTxt">Not Available</td></tr>
      </table>
      </td>
      <!--  Google Map -->
      <td rowspan=2>

      <div>
          <br/>
          <table border="0" cellspacing="5">
            <tr align="center" valign="bottom"> 
              <td>
                <div id="map_canvas" style="width: 350px; height: 200px"></div>
              </td>
            </tr>
            <tr> 
              <td align="center" valign="middle" colspan="4"><img src="images/shim.gif" width="5" height="5"></td>
            </tr>
         </table>
      </div>
      <!-- 
      <img src="images/samplelocation.gif" width="200" height="117" align="middle" alt="map" class="imgColR">
       -->
       </td>
  </tr>
  <tr>
     <!-- Sample Descrption block -->
     <td>
         <table width="100%" align="left" summary="Sample description">
            <tr>
              <td colspan="3" valign=top><span class="keyword">Sample Description:</span></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Rock type: </th>
              <td width="80%" align="left" class="regTxt"><%= r_type%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Classification: </th>
              <td width="80%" align="left" class="regTxt" id="rockClass">Not Available</td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Description: </th>
              <td width="80%" align="left" class="regTxt"><%= desc%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Alteration: </th>
              <td width="80%" align="left" class="regTxt"><%= alt%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Age: </th>
              <td width="80%" align="left" class="regTxt"><%= ageStr%> </td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Archived at: </th>
              <td width="80%" align="left" class="regTxt"> <%= archive %> </td>
            </tr>
         </table>
     </td>

 </tr>
 <tr>
 <!--  Sampling Information -->
 <td>		  
     <table border="0" width="100%" >
            <tr>
              <td colspan="2"><span class="keyword">Sampling Information</span></td>
            </tr>
            <tr class="pad">
              <th width="10%" align="left" nowrap scope="row">Field Program/Cruise:</th> 
              <td width="90%" align="left" class="regTxt"> <%= e_name%></td>
            </tr>
            <tr> 
              <th width="10%" align="left" nowrap scope="row">Date:</th>  
              <td width="90%" align="left" class="regTxt"> <%= s_date%></td> 
            <tr>
              <th width="10%" align="left" nowrap scope="row">Chief Scientist:</th>
              <td width="90%" align="left" class="regTxt"><%= chief%></td>
            </tr>
            <tr>
              <th width="10%" align="left" nowrap scope="row">Technique:</th>
              <td width="90%" align="left" class="regTxt"><%= s_tech%></td>
            </tr>
            <tr>
              <th width="10%" align="left" nowrap scope="row">Station: </th>
              <td width="90%" align="left" class="regTxt"><a href="statn_info.jsp?singlenum=<%= st_id%>" target="_self"><%= st%></a></td>
            </tr> 
      </table>
  </td>
       <!-- Location block -->
     <td>
         <table width="100%" align="left" summary="Location information">
            <tr>
              <td colspan="2" valign=top><span class="keyword">Location:</span></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Latitude:</th>
              <td width="90%" align="left" class="regTxt"><%= lat%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Longitude:</th>
              <td width="90%" align="left" class="regTxt"><%= lon%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Elevation:</th>
              <td width="90%" align="left" class="regTxt"><%= depth%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Tectonic setting:</th>
              <td width="90%" align="left" class="regTxt"><%= tect%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Location:</th>
              <td width="90%" align="left" class="regTxt"><%= loc%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Location Comment:</th>
              <td width="90%" align="left" class="regTxt"><%= l_C%></td>
            </tr>
          </table>
     </td>
 </tr>
</table>
<!--  Rock Data block -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="sub2a">
      <%
	Vector columns = new Vector();
    Vector rmcolumns = new Vector();
	String title	="";
	String c_title	="";
	double[] columns_value;
	String author	="";
	String ref_num	="";
	String alias 	="";
	String analysis_comment="";
	String method 	="";
	String method_num ="";
	String material ="";
	String comment ="";
	String prev_an 	="";	
	String type 	="";	
	String heated 	="";	
	boolean any 	=false;
	if (ds3 != null)
	{
		String[] types = new String[4];
		types[0] = "3";
		types[1] = "6";
		types[2] = "5";
		types[3] = "4";
	for (int g = 0; g<types.length; g++)
	{
	
		if ( types[g].equals("3"))
		{
			c_title = "Material";
			title = "Rock Data";
			rmcolumns.clear();
			int rm = ds3.getRockModeItemIndex(rmcolumns);
			allDS = rockDS;
		}else if (types[g].equals("4"))
		{
			c_title = "Groundmass";
			title = "Groundmass Data";
			allDS = rockDS;
		} 
		else if (types[g].equals("6"))
		{
			c_title = "Mineral";
			title = "Mineral Data";
			allDS = minDS;
		} else 
		{
			c_title = "Host Mineral";
			title = "Inclusion Data";
			allDS = incDS;
		}
		columns.clear();
		int r = ds3.getOrderedChemicals(types[g], columns);
		
		if (columns.size() != 0) 
		{
%>
<%@ include file="sample_info_a_table.jspf"%>
<%
		} //end of: if (columns.size() != 0)
	} //end of: for (int g = 0; g<types.length; g++)
	} //end of: if (ds3 != null)

	if (rmcolumns.size() > 0)
	{
		title = "Rock Mode Analyses";
%>
	  <tr>
        <td valign=top class="keyword"><br />Rock Mode Data</td>
      </tr>
      <tr>
        <td valign=top>
          <table border="1" cellpadding="1" cellspacing="0" summary="<%= title%>">
            <!-- needs new java here to load in headings and table contents -->      
           <%=  rockmodedataTableStr %>
          </table>
        </td>
     </tr> 
<%
	}
	int r = wrapper.closeQueries();
  
    // Classification for Sample Description    
 Iterator it = rockClassMap.entrySet().iterator();
 while (it.hasNext()) {
  Map.Entry entry = (Map.Entry) it.next();
  String rkey = (String)entry.getKey();
  Set rValue = (Set)entry.getValue();
  Iterator ir = rValue.iterator();
  while(ir.hasNext()) { 
    String rn = (String) ir.next();
    if(authorMap.containsKey(rn))
        rcSB.append(rkey+authorMap.get(rn));
  }
}
       
%>
    <tr >
      <td valign="middle" align="left">
        <form name="close">
<script language= "JavaScript" type="text/JavaScript">
	if (window.history.length > 1)
	{	document.write('<input name="back" type="button" id="back" value="Back" onClick="javascript:history.back();">&nbsp;&nbsp;&nbsp;'); }
</script>
	<input type="button" name="close" value="close window" onclick="if(window.opener)window.opener.focus(); window.close();"/></form>
      </td>
    </tr>
</table>

<script type="text/javascript" >
function loadOpenLayerMap()
{

var markers = [];
var map=null; // will be initialize in Gload

<%
HashMap found = new HashMap();
        found.put(st, st_id);
        String clat = lat;
        String clong = lon;        
        float clat_num = new Float(clat.substring(0, clat.indexOf("\260"))).floatValue();
        float clong_num = new Float(clong.substring(0, clong.indexOf("\260"))).floatValue();
                
        String clat_hemi = clat.substring(clat.indexOf("\260") + 1, clat.indexOf("\260") + 2);
        String clong_hemi = clong.substring(clong.indexOf("\260") + 1, clong.indexOf("\260") + 2);
                
        if (clat_hemi.equals("S")) {
            clat_num = - clat_num;
        }
                
        if (clong_hemi.equals("W")) {
            clong_num = - clong_num;
        }
%>
        if(map==null) { map=loadGMRTMap('map_canvas');}
      //  console.log(map.getProjection()); -->EPSG:3395
        
        var lonLat = new OpenLayers.LonLat('<%= clong_num %>', '<%=  clat_num%>');
        lonLat = lonLat.transform(
        		new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
                new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator
        );
            var markers = new OpenLayers.Layer.Markers( "Markers",{displayInLayerSwitcher:false} );
            map.addLayer(markers);
            
            var popupContentHTML = "Station ID: <a href=\"<%= request.getRequestURI().substring(0, request.getRequestURI().indexOf("sample_info")) %>statn_info.jsp?singlenum=<%=st_id%>\"><%= st %></a>";
            addClickableMarker(lonLat, popupContentHTML,markers,map);            
            map.setCenter(lonLat,6);
 
}
</script>

<script language="JavaScript" src="js/JQuery/js/jquery-1.3.2.min.js" type="text/javascript"></script> 
<script language="JavaScript" src="js/JQuery/jquery.qtip-1.0.0-rc3.min.js" type="text/javascript"></script>
<script language="JavaScript" src="js/JQuery/js/jquery-ui-1.7.3.custom.min.js" type="text/javascript"></script>
<script language="JavaScript" src="js/loadHelpContent.js" type="text/javascript"></script>

<script language="JavaScript" type="text/JavaScript">
<!--
replaceTDText('aliases', "<%= aliasesRefStr %>");
replaceTDText('rockClass', "<%= rcSB.toString() %>");
//loadGoogleMap();
loadOpenLayerMap();
//Create analysis comment toot tips or remove empty columns for Rock Data
var jsRMCommentDesc = new Array();
<% int total=rmcommentDesc.size();
     for (int i=0;i<total;i++) 
     {
%>
       jsRMCommentDesc.push( '<%= rmcommentDesc.get(i) %>' ) 
<% 
     } 
%>
createAnalysisCommentQtip(jsRMCommentDesc,'rmcomment');
removeEmptyAnalysisColumn('rmcomment', 1, <%= rmcommentNum %>)

var jsCommentDesc = new Array();
<%  total=commentDesc.size();
     for (int i=0;i<total;i++) 
     {
%>
       jsCommentDesc.push( '<%= commentDesc.get(i) %>' ) 
<% 
     } 
%>

createAnalysisCommentQtip(jsCommentDesc,'comment');
removeEmptyAnalysisColumn('comment',1,<%= commentNum %>);

var jsMinCommentDesc = new Array();
<%  total=mincommentDesc.size();
     for (int i=0;i<total;i++) 
     {
%>
       jsMinCommentDesc.push( '<%= mincommentDesc.get(i) %>' ) 
<% 
     } 
%>
createAnalysisCommentQtip(jsMinCommentDesc,'mincomment');
removeEmptyAnalysisColumn('mincomment',1,<%= mincommentNum %>);
//-->
</script>

</body>
</html>