<%@ page import="java.net.URLEncoder"%>
<%@ include file="headCode2.jspf"%>

<%
	String station_id = "";
	boolean ext_app = false;
	String type =StationInfo1DCtlQuery.NUM;

	if (request.getParameter("station_id") != null)
	{
		station_id = request.getParameter("station_id");
        String qStr = "select station_num from station where STATION_ID = '"+station_id+"'";
        //Convert station_id into station_num
        station_id = new SimpleQuery(qStr).getSingleResult();
    }
	else
		station_id = request.getParameter("singlenum");
    if(!PetdbUtil.isInteger(station_id)) 
      throw new Exception("You are trying to go to Station Info page, without correct station specified");

	if (station_id == null) return;

	StationInfoWrapper wrapper = new StationInfoWrapper(station_id, type);
	session.setAttribute("wrapper",wrapper); 
	StationInfo1DS  ds1 = (StationInfo1DS)wrapper.getControlList("0");
	StationExpedInfo1DS  expedInfo = (StationExpedInfo1DS)wrapper.getControlList("3");

	String st_id ="";
	String st_igsn="Not Available";
	String expedition = "";
	String expedition_id = "";
	String samp_tec = "";
	String loc = "";
	String prev_st_id = "";
	String yearStr="";
    String exped_nameStr="";
    String scientist_nameStr="";
    String station_commentStr="";
    String navigationStr="";
    String station_nameStr="";
    
	if (ds1 == null) throw new Exception("ds1 equals null");
    
  //  String ipAddress = new IPAddress().getIpAddrWithFilter(request);
  //  if(ipAddress != null)
   //     new SimpleQuery("insert into QUICK_SEARCH (SEARCH_NAME,SEARCH_DATE,DATASOURCE_NAME,SEARCH_GROUP,IP_ADDRESS) values ('Station Info',SYSDATE,'"+application.getInitParameter("datasource")+"',null,'"+ipAddress+"')");
	
    while (ds1.next())
	{
		prev_st_id =  ds1.getValue(StationInfo1DS.Station_ID);
		if (prev_st_id.equals(st_id))
		{
			String curLoc = ds1.getValue(StationInfo1DS.Location_Name);
			if(loc.indexOf(curLoc) == -1)
			  loc += "</br>" + curLoc;
		}
		else {
			st_id =  ds1.getValue(StationInfo1DS.Station_ID);
			expedition =  ds1.getValue(StationInfo1DS.Expedition);
			expedition_id = ds1.getValue(StationInfo1DS.Expedition_id);
			samp_tec =  ds1.getValue(StationInfo1DS.Samp_Tech);
			loc =  ds1.getValue(StationInfo1DS.Location_Name);
			st_igsn = ds1.getValue(StationInfo1DS.Station_IGSN);
			station_commentStr = ds1.getValue(StationInfo1DS.station_comment);
			navigationStr = ds1.getValue(StationInfo1DS.navigation_name);
			station_nameStr = ds1.getValue(StationInfo1DS.station_name);
			exped_nameStr=expedition.trim();
		}
		if( "&nbsp;".equalsIgnoreCase(exped_nameStr)||"NOT PROVIDED".equals(exped_nameStr) ) 
			exped_nameStr="NOT PROVIDED";
		else
		    exped_nameStr ="<a href='exped_info.jsp?singlenum="+expedition_id+"' target='_self'>"+ exped_nameStr+"</a>";

	}
    if( expedInfo !=null)
    {
      prev_st_id = "";
      String est_id ="";
	  while (expedInfo.next())
	  {
		prev_st_id =  expedInfo.getValue(StationExpedInfo1DS.Station_ID);
		if (prev_st_id.equals(est_id))
		{
			String curName = expedInfo.getValue(StationExpedInfo1DS.scientist_name);
			if(scientist_nameStr.indexOf(curName) == -1)
				  scientist_nameStr += "</br>" + curName;
		}
		else {
			est_id = expedInfo.getValue(StationExpedInfo1DS.Station_ID);
			yearStr  =expedInfo.getValue(StationExpedInfo1DS.year);
			/* exped_nameStr =expedInfo.getValue(StationExpedInfo1DS.exped_name); *//* Get from ds1 */
			scientist_nameStr = expedInfo.getValue(StationExpedInfo1DS.scientist_name);
		}
	  }
    }
    
    if( "&nbsp;".equalsIgnoreCase(station_commentStr)) station_commentStr="N/A";
    if( "&nbsp;".equalsIgnoreCase(scientist_nameStr)) scientist_nameStr="N/A";
    if( "&nbsp;".equalsIgnoreCase(yearStr)) yearStr="N/A";   
    if( "&nbsp;".equalsIgnoreCase(loc)) loc="N/A";
    if( "&nbsp;".equalsIgnoreCase(samp_tec)) samp_tec="N/A";
    if( "&nbsp;".equalsIgnoreCase(navigationStr)) navigationStr="N/A";
    if( "&nbsp;".equalsIgnoreCase(station_nameStr)) station_nameStr="N/A";
    
	String st_igsnURL="N/A";
	if( "&nbsp;".equalsIgnoreCase(st_igsn) )  
		st_igsnURL    ="N/A";
	else
	{
		st_igsnURL = "<a href='#stanInfo' onclick=\"openwindow('"+PetDBConstants.igsnSESARURL+st_igsn+"','"+st_igsn+"','600','400')\" >"+st_igsn+"</a>";
	}
	
	StationInfo3DS  ds2 = (StationInfo3DS)wrapper.getControlList("1");
	StationInfo2DS  ds3 = (StationInfo2DS)wrapper.getControlList("2");
    String ds2_tect = "N/A";
    String ds2_locComment ="N/A";
    Vector ds2_lats = new Vector();
    Vector ds2_lons = new Vector();
    Vector ds2_locOrders = new Vector();
    int totalTypeOfLocOrders=1;

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
<head>
<title>PETDB query - Station Information</title>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META http-equiv="expires" content="0">

<link href="css/petdb.css" rel="stylesheet" type="text/css">

<script  language= "JavaScript" type="text/javascript" src="js/OpenLayers-2.12/OpenLayers.js"></script>
<script  language= "JavaScript" type="text/javascript" src=js/openlayermap.js></script>

<script  language= "JavaScript" src=js/windows.js></script>
<script language= "JavaScript" src=js/set_pub.js></script>
</head>


<body class="pop" width="665px">

<div class="popTop">
<p class="emphasis" style="float:left;">
<font size="+1">Station information</font><br />
</p>
<div class="clearer"></div>
</div><!-- end popTop -->

<!--  Copy from sample infor -->
<table border="0" class="sub2a" width="100%">
  <tr><td>
      <!-- Identification block -->
      <table  border="0" id="#IDBlock">
        <tr><td colspan="2"><span class="keyword">Identification:</td></span></tr>
        <tr><th width="10%" align="left" nowrap scope="row">PetDB Identifier:</th><td width="90%" align="left" class="regTxt"><%= st_id%></td></tr>
        <tr><th width="10%" align="left" nowrap scope="row">IGSN:</th><td width="90%" align="left" class="regTxt"><%= st_igsnURL %></td></tr>
        <tr><th width="10%" align="left" nowrap scope="row">Other Names:</th><td width="90%" align="left" id="aliases" class="regTxt"><%=station_nameStr%></td></tr>
      </table>
      </td>
      <!--  Google Map -->
      <td rowspan=2>

      <div>
          <br/>
          <table border="0" cellspacing="0">
            <tr align="center" valign="bottom"> 
              <td>
                <div id="map_canvas" style="width: 350px; height: 200px"></div>
              </td>
            </tr>
            <tr> 
              <td align="center" valign="middle" colspan="4"><img src="images/shim.gif" width="5" height="5"/></td>
            </tr>
         </table>
      </div>
      <!-- 
      <img src="images/samplelocation.gif" width="200" height="117" align="middle" alt="map" class="imgColR">
       -->
       </td>
  </tr>
  <tr>
     <!-- Collection metadata block -->
     <td>
         <table width="100%" align="left" summary="Collection Metadata">
            <tr>
              <td colspan="3" valign=top><span class="keyword">Collection Metadata:</span></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Field Program/Cruise: </th>
              <td width="90%" align="left" class="regTxt"><%=exped_nameStr %></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Date: </th>
              <td width="90%" align="left" class="regTxt"><%=yearStr %></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Collector/Chief Scientist: </th>
              <td width="90%" align="left" class="regTxt"><%=scientist_nameStr %></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Sampling Technique: </th>
              <td width="90%" align="left" class="regTxt"><%= samp_tec%></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Station Comment: </th>
              <td width="90%" align="left" class="regTxt"><%=station_commentStr %> </td>
            </tr>
         </table>
     </td>

 </tr>
 <tr>
 <!--  Location Description -->
 <td>		  
     <table border="0" width="300" >
            <tr>
              <td colspan="2"><span class="keyword">Location Description:</span></td>
            </tr>
            <tr class="pad">
              <th width="10%" align="left" nowrap scope="row">Geographic descriptors:</th> 
              <td width="90%" align="left" class="smallTxt"><%= loc %></td>
            </tr>
            <tr> 
              <th width="10%" align="left" nowrap scope="row">Tectonic setting:</th>  
              <td width="90%" align="left" class="smallTxt" id="tect_setting">ds2_tect</td>
            </tr> 
            <tr>
              <th width="10%" align="left" nowrap scope="row">Location Comment:</th>
              <td width="90%" align="left" class="smallTxt" id="loc_comment">ds2_locComment</td>
            </tr>
      </table>
  </td>       
  <!-- Location block -->
       <%
if (ds2 != null) {
  

  Vector ds2_elemins       = new Vector();
  Vector ds2_elemaxs       = new Vector();
  Vector ds2_landseas      = new Vector();
  Vector ds2_locPrecisions = new Vector();
  
  while (ds2.next())
  {
    String ds2_locOrder = ds2.getValue(StationInfo3DS.Location_Order);
    String ds2_lat = ds2.getValue(StationInfo3DS.Latitude);
    String ds2_lon = ds2.getValue(StationInfo3DS.Longitude);
    String ds2_elemin = ds2.getValue(StationInfo3DS.Elevation_Min);
    String ds2_elemax = ds2.getValue(StationInfo3DS.Elevation_Max);
    String ds2_landsea = ds2.getValue(StationInfo3DS.Land_Sea);
           ds2_tect = ds2.getValue(StationInfo3DS.Tectonic_Name);
           ds2_locComment = ds2.getValue(StationInfo3DS.Location_comment);
    String ds2_locPrecision = ds2.getValue(StationInfo3DS.Loc_precision);
    
    if( "&nbsp;".equalsIgnoreCase(ds2_locOrder)) ds2_locOrder="N/A";
    if( "&nbsp;".equalsIgnoreCase(ds2_lat) ) ds2_lat="N/A";
    if( "&nbsp;".equalsIgnoreCase(ds2_lon) ) ds2_lon="N/A";
    if( "&nbsp;".equalsIgnoreCase(ds2_elemin) ) ds2_elemin="N/A";
    if( "&nbsp;".equalsIgnoreCase(ds2_elemax) ) ds2_elemax="N/A";
    if( "&nbsp;".equalsIgnoreCase(ds2_landsea) ) ds2_landsea="N/A";
    if( "&nbsp;".equalsIgnoreCase(ds2_tect) ) ds2_tect="N/A";
    if( "&nbsp;".equalsIgnoreCase(ds2_locComment) ) ds2_locComment="N/A";
    if( "&nbsp;".equalsIgnoreCase(ds2_locPrecision) ) ds2_locPrecision="N/A";
    
    ds2_locOrders.add(ds2_locOrder);
    ds2_lats.add(ds2_lat);
    ds2_lons.add(ds2_lon);
    ds2_elemins.add(ds2_elemin);
    ds2_elemaxs.add(ds2_elemax);
    ds2_landseas.add(ds2_landsea);
    ds2_locPrecisions.add(ds2_locPrecision);
    
  }//end of while
  int totalColumn = ds2_lats.size();
  String latStr="";
  String lonStr="";

  if( totalColumn > 1 )
  {
	  String locOnOff="on";
	  for(int i=0;i<totalColumn;i++) {
		  if( ((String) ds2_locOrders.elementAt(i)).equalsIgnoreCase("off") ) totalTypeOfLocOrders++;
	  }
  }
  if( totalColumn > 1 )
  {

	  for(int i=0;i<totalColumn;i++) {
	    latStr += (String) ds2_lats.elementAt(i);
	    lonStr += (String) ds2_lons.elementAt(i);
	    
		if( totalTypeOfLocOrders>1 )
		{
	      if(i==0)
	      {
	    	if( ((String)ds2_locOrders.elementAt(i)).equalsIgnoreCase("on"))
	    	{
	    	  latStr +=" (start)";
	    	  lonStr +=" (start)";
	    	}
	      }
		  if( ((String)ds2_locOrders.elementAt(i)).equalsIgnoreCase("off"))
		  {
	    	  latStr +=" (end)";
	    	  lonStr +=" (end)";
		  }
	    }//if
		
	    if( i<totalColumn-1) 
	    {
	        latStr +="</br>";
	        lonStr +="</br>";
	    }//if
	}//for
  }
  else
  {
	  latStr = (String) ds2_lats.elementAt(0);
	  lonStr = (String) ds2_lons.elementAt(0);
  }
%>
     <td valign="top" rowspan=2>
         <table width="100%" align="left" summary="Location">
            <tr>
              <td colspan="<%= totalColumn %>" valign=top><span class="keyword">Location:</span></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Latitude:</th>
              <td width="90%" align="left" class="regTxt"><%= latStr %></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Longitude:</th>
              <td width="90%" align="left" class="regTxt"><%= lonStr %></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Elevation (min):</th>
              <td width="90%" align="left" class="regTxt"><%= ds2_elemins.elementAt(0)   %></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Elevation (max):</th>
              <td width="90%" align="left" class="regTxt"><%= ds2_elemaxs.elementAt(0)   %></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Navigation:</th>
              <td width="90%" align="left" class="regTxt"><%= navigationStr  %></td>
            </tr>
            <tr valign="top">
              <th width="10%" align="left" nowrap scope="row">Location precision:</th>
              <% int pindex=0; for (int i=0;i<ds2_locPrecisions.size();i++)
              {   String floatStr1 = (String)ds2_locPrecisions.elementAt(pindex);
                  String floatStr2 = (String)ds2_locPrecisions.elementAt(i);
                  if (floatStr1.equals("N/A") || floatStr2.equals("N/A")) continue;
            	  if(Float.parseFloat( floatStr1) < Float.parseFloat( floatStr2) ) pindex=i; }  
              %>
              <td width="90%" align="left" class="regTxt"><%= ds2_locPrecisions.elementAt(pindex) %></td>
            </tr> 
          </table>
     </td>
     	
<%
	
  }//end of if

	
%>
</tr>
</table>
<%
   	Vector ss = new Vector();
   	Vector sns= new Vector();
   	Vector rds = new Vector();
   	Vector as = new Vector();
   	Vector des = new Vector();
   	Vector igsnURLs = new Vector();
    String baseColor="#E4F6F2";
    String lightCellColor="white";
	String cellColor=baseColor;
	if (ds3 != null) 
	{
	String s  = "";
	String sn  = "";
	String rd = "N/A";
	String a = "N/A"; /* FIXME: not used anymore */
	String de = "N/A";
	String prev = "";
	String igsn ="N/A";
	String igsnURL="N/A";

		
	while (ds3.next()) 
	{		
	  	if ( !prev.equals(ds3.getValue(StationInfo2DS.Sample_ID)))
	 	{
			if (prev.length() != 0)
			{	
				sns.add(sn);
				ss.add(s);
				igsnURLs.add(igsnURL);
				rds.add(rd);
				des.add(de);
			}
            	s = ds3.getValue(StationInfo2DS.Sample_ID);
		        prev = s;
            	sn = ds3.getValue(StationInfo2DS.Sample_Num);
            	rd = ds3.getValue(StationInfo2DS.Rock_Decs);
             	a  = ds3.getValue(StationInfo2DS.Alteration);
            	de = ds3.getValue(StationInfo2DS.Data_Existence);
            	igsn=ds3.getValue(StationInfo2DS.IGSN);
            	if( "&nbsp;".equalsIgnoreCase(rd) ) rd="N/A";
            	if( "&nbsp;".equalsIgnoreCase(a) ) a="N/A";
            	if( "&nbsp;".equalsIgnoreCase(de) ) de="N/A";
            	if( "&nbsp;".equalsIgnoreCase(igsn) ==false ) 
            		igsnURL = "<a href='' onclick=\"openwindow('"+PetDBConstants.igsnSESARURL+igsn+"','"+igsn+"','600','400')\" >"+igsn+"</a>";
            	else
            		igsnURL="N/A";
            			
		} 
		else 
		{ 
			a +="; " + ds3.getValue(StationInfo2DS.Alteration);
	 	}
		sns.add(sn);
		ss.add(s);
		igsnURLs.add(igsnURL);
		rds.add(rd);
		des.add(de);
 	  }
	}
       int r =wrapper.closeQueries();	
       int totalRows = ss.size();
       String colP1="25%";
       String colP2="25%";
       String colP11="25%";
       String colP22="25%";
       if(totalRows >= 6 )
       {
    	   colP1="34%";
    	   colP2="16%";
    	   colP11="36%";
    	   colP22="14%";
       }
%>
<table  border="0" class="sub2a" width="100%">
  <tr>
  <td>
     <table  border="0" width="95%">

       <tr>
              <td colspan="4" valign=top><span class="keyword">Samples From This Station:</span></td>
       </tr>
       <tr>
           <th width=<%=colP1 %> >PetDB SampleID</th>
           <th width="25%">IGSN</th>
           <th width="25%">Rock Class</th>
           <th width=<%=colP2 %> >Data Types Available</th>
       </tr>
     </table>
   </td>
   </tr>
   <tr>
   <td>
     <div style="width:95%;max-height:120px;overflow:auto">
     <table border="0" width="100%">

<%
   int counter=0;
	   
   for( int i=0;i<totalRows; i++)
   {
	  if( totalRows >= 6 )
	  {
	    if( (counter%2) == 0)
		   cellColor = lightCellColor;
	    else 
		  cellColor = baseColor;
	  }
	  if( i>= 1)
	  {
		  if(((String) sns.elementAt(i)).equalsIgnoreCase( (String) sns.elementAt(i-1) ) == true ) 
		  {
			  continue;
		  }
		  else counter++;
	  }
	  else
		  counter++;
	  
%>
  
      <tr  bgcolor="<%=cellColor%>">
      
         <%-- <td width=<%=colP11 %> ><a href="sample_info.jsp?sampleID=<%= ss.elementAt(i)%>" target="_self"> <%= ss.elementAt(i)%></a></td>  --%>
         <td width=<%=colP11 %> ><a href='<%= "sample_info.jsp?sampleID="+URLEncoder.encode((String)ss.elementAt(i),"UTF-8")%>' target="_self"> <%= ss.elementAt(i)%></a></td> 
           <td width="25%"><%= igsnURLs.elementAt(i)%></td>
           <td width="25%"><%= rds.elementAt(i)%></td>
           <td width=<%= colP22 %> ><%= des.elementAt(i)%></td>
       </tr>
      
 <% 
 } %>
     </table>
     </div>
  </td>
    

 </tr>
 

</table>

<!--  Copy from sampel info -->
  <!-- 
<form method="POST" action="" name="">
<table border="0" cellspacing="0" class="sub2" id="#stanInfo">

    <tr align="right">
      <td align="left" valign="middle">
	    <table width="100%" cellspacing="0" cellpadding="2" border="1" class="regTxt">
          <tr valign="top">
            <td>Station ID:</td>
            <td><!!%= st_id%></td> 
          </tr>
          <tr valign="top">
            <td>Station IGSN:</td>
            <td><!!%= st_igsnURL %></td>
          </tr>
          <tr valign="top">
            <td>Expedition:</td>
            <td> </td>
          </tr>
          <tr valign="top">
            <td>Sampling Technique:</td>
            <td><!!%= samp_tec%></td>
          </tr>
          <tr valign="top">
            <td>Location description:</td>
            <td><b><!!%= loc%></b></td>
          </tr>
        </table>
      </td>
    </tr>
   
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle"><b>Sampling sites</b>
	  <table width="100%" cellspacing="0" cellpadding="2" border="1" class="regTxt">
          <tr valign="bottom" class="regTxt">
            <th width="10%" align="center">Site Order</th>
            <th width="12%" align="center">Latitude</th>
            <th width="12%" align="center">Longitude</th>
            <th width="12%" align="center">Elevation Min</th>
            <th width="12%" align="center">Elevation Max</th>
            <th width="10%" align="center">Land/Sea</th>
            <th width="10%" align="center">Tectonic</th>
            <th width="22%" align="center">Comment</th>
          </tr>

          <tr valign="top" class="regTxt">
            <td> <!!%= ds2_locOrder %> </td>
            <td> <!!%= ds2_lat      %> </td>
            <td> <!!%= ds2_lon      %> </td>
            <td> <!!%= ds2_elemin   %> </td>
            <td> <!!%= ds2_elemax   %> </td>
            <td> <!!%= ds2_landsea  %> </td>
            <td> <!!%= ds2_tect     %> </td>
            <td> <!!%= ds2_locComment %> </td>
	</tr>

      </table>
	  </td>
    </tr>
    
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>
    <tr align="right">
      <td align="left" valign="middle"><b>Samples</b>
        <table width="100%" cellspacing="0" cellpadding="2" border="1" class="regTxt">
          <tr valign="bottom">
            <th width="20%" align="center">Sample ID</th>
            <th width="20%" align="center">Sample IGSN</th>
            <th width="20%" align="center">Rock Description</th>
            <th width="20%" align="center">Alteration</th>
            <th width="20%" align="center">Data</th>
          </tr>

          <tr valign="top" class="regTxt">
            <td> <a href="sample_info.jsp?sampleID=<!!%= sn%>" target="_self"> <!!%= s%></a> </td>
            <td> <!!%= igsnURL%> </td>
            <td> <!!%= rd%> </td>
            <td> <!!%= a%> </td>
            <td> <!!%= de%> </td>
          </tr>

          <tr valign="top" class="regTxt">
            <td> <a href="sample_info.jsp?sampleID=<!!%= sn%>" target="_self"> <!!%= s%></a> </td>
            <td> <!!%= igsnURL%> </td>
            <td> <!!%= rd%> </td>
            <td> <!!%= a%> </td>
            <td> <!!%= de%> </td>

     </table></td>
    </tr>
    <tr align="right">
      <td align="center" valign="middle"><img src="images/shim.gif" width="5" height="5"></td>
    </tr>

  </table>
</form>
  -->
</body>
<script language="JavaScript" >
<!--
function loadOpenLayerMap()
{
	<% int totalColumn = ds2_lats.size(); 
	  int onYes =0; 
      int offYes =0;
      float lat1=0;
      float lat2=0;
      float difflat=0;
    %>
    var markers = [];
    var map=null; // will be initialize in Gload
    var legCoordinates = [];
    
<%
    for(int i=0;i<totalColumn;i++){
       String clat = (String) ds2_lats.elementAt(i);
        String clong = (String) ds2_lons.elementAt(i);       
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
            //point = new google.maps.LatLng(<%= clat_num %>, <%= clong_num %>);
           // if(map==null) {map = creategoogleMap(<%= clat_num %>, <%= clong_num %>,'map_canvas');}
            if(map==null) { map=loadGMRTMap('map_canvas');}
            
            var lonLat = new OpenLayers.LonLat(<%= clong_num %>, <%=  clat_num%> );
            lonLat = lonLat.transform(
            		new OpenLayers.Projection("EPSG:4326"), // transform from WGS 1984
                    new OpenLayers.Projection("EPSG:900913") // to Spherical Mercator
            		//new openLayers.Projection('EPSG:3395'),
            	//	map.getProjection()
            );
            var markers = new OpenLayers.Layer.Markers( "Markers",{displayInLayerSwitcher:false} );
            map.addLayer(markers);
           // var size = new OpenLayers.Size(15,15);
           // var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
           // var icon = new OpenLayers.Icon('images/bullet.gif',size);
           // markers.addMarker(new OpenLayers.Marker(lonLat,icon));

           var textHTML = "<%= clat %>/<%=clong%>";
           addMarkerWithText(lonLat, textHTML, markers, map);
           map.setCenter(lonLat,6);
           
           // markers.push(createMarkerWithText(point, "<-%= clat %>/<-%=clong%>",map));
            <% 
               if( ((String)ds2_locOrders.elementAt(i)).equalsIgnoreCase("on") && onYes == 0) { 
            	   onYes = 1; lat1=clat_num;
            %>
                   legCoordinates.push(lonLat);
            <% } 
               if( ((String)ds2_locOrders.elementAt(i)).equalsIgnoreCase("off") && offYes == 0) { 
         	   offYes = 1; lat2=clat_num; difflat = Math.abs(lat2-lat1);
            %>
                legCoordinates.push(lonLat);
            <% } %>
<% } %>

<% if(totalColumn >1 && totalTypeOfLocOrders > 1) { %>
  addLines(map, legCoordinates);
  <% if(difflat >= 0.1 ) { %>
    map.setCenter(null,7);
  <% } else {%>
    map.setCenter(null,11);
  <% }%>
<% } %>            
}

//replaceTDText('aliases', "<!!!%= aliasesRefStr %>");
replaceTDText('tect_setting', "<%= ds2_tect %>");
replaceTDText('loc_comment', "<%= ds2_locComment %>");

//loadGoogleMap();
loadOpenLayerMap();

//-->
</script>
</html>
