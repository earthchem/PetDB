<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IGSN Entry Tool</title>
 
 <!-- 
<link href="../search/js/JQuery/css/overcast/jquery-ui-1.7.3.custom.css" rel="stylesheet" type="text/css"/>
<script language="JavaScript" src="../search/js/JQuery/js/jquery-1.3.2.min.js" type="text/javascript"></script>
<script language="JavaScript" src="../search/js/JQuery/js/jquery-1.3.2.min.js" type="text/javascript"></script>
 -->

<!-- Incude CSS for JQuery Vertical Tabs -->
<link rel="stylesheet" type="text/css" href="jquery/css/jquery-jvert-tabs-1.1.4.css" />

<!-- Include JQuery Core-->
<script type="text/javascript" src="jquery/js/jquery-1.4.1.min.js"></script>

<!-- Include JQuery Vertical Tabs plugin -->
<script type="text/javascript" src="jquery/js/jquery-jvert-tabs-1.1.4.js"></script>

<script type="text/javascript" src="js/igsnTool.js"></script>

</head>
<body>

<!-- Header -->
<div style="background-color:#C3FDC3;border:0;height:50px;width:100%;text-align:center;">
  <h3 style="display:inline-block;">PetDB IGSN Entry Tool</h3>
  <button onclick="window.close()" value="Close" id="Close" style="float:right;">Close</button>
</div>

<script type="text/javascript">
$(document).ready(function(){

	$("#vtabs1").jVertTabs();

});
</script>


  <!--  Vertical Tabs -->
 <div id="vtabs1">
	<div>
		<ul>
			<li ><a href="#vtabs-content-a" style="font-family:arial;font-size:15px;">Add Sample IGSNs</a></li>
			<li ><a href="#vtabs-content-b" style="font-family:arial;font-size:15px;">Add Station IGSNs</a></li>
		</ul>
	</div>
	<div>
	    <div id="#vtabs-content-a" style="min-height:400px;width:100%;">
		<div id="getSample" style="margin:10px;padding:5px;">
           <form action="" id="inputId1" method="POST">
           <label for="SorT_Id1" style="display:inline-block">Enter part of or full PetDB <b>SAMPLE ID</b></label>
           <input type="text" maxlength="40" id='SorT_Id1'/>
           <input  type="button" onClick="getDatabaseInformation('sample');" id="getDatabaseInfo1" value="Get SAMPLE(s)"/>
           </form>
        </div>
     
        <div id='mod_SamplesNoResults1'  style="display:none;border: 1px solid red;;min-height:25px;"></div>

        <div id='mod_searchResultSamples1'  style="display:none;margin:10px;padding:5px;background-color:#C3FDC3;">
          <form action="" method="POST" id="form1" onsubmit="return validate(this);">
            <div id='mod_sampelID_IGSN_header1' style="width:100%;"></div>
            <div id='mod_sampelID_IGSN1' style="width:100%;overflow:auto;"></div>
          </form>
        </div>
    
        <div id='searchResultSamples1'  style="display:none;margin:10px;padding:5px;background-color:#C3FDC3;">
         <h3>You have successfully added (or updated ) IGSN(s) for SAMPLE(s):</h3>
         <table id="showID_IGSN1" border="1" style="width:100%;">
           <tr> 
             <th width="40%">SAMPLE_ID</th>
             <th width="30%">SAMPLE_NUM</th>
             <th width="30%">IGSN</th>
           </tr>
         </table>
         <a href="">Add More IGSN >></a>
        </div>	
	    </div>
		
	<div id="#vtabs-content-b" style="min-height:400px;width:100%;">
	    <div id="getSample2" style="margin:10px;padding:5px;">
           <form action="" id="inputId2" method="POST">
           <label for="SorT_Id2" style="display:inline-block">Enter part of or full PetDB <b>STATION ID</b></label>
           <input type="text" maxlength="40" id='SorT_Id2'/>
           <input  type="button" onClick="getDatabaseInformation('station');" id="getDatabaseInfo2" value="Get STATIONS(s)"/>
           </form>
        </div>
    
        <div id='mod_SamplesNoResults2'  style="display:none;border: 1px solid red;min-height:25px;"></div>

       <div id='mod_searchResultSamples2'  style="display:none;margin:10px;padding:5px;background-color:#C3FDC3;">
           <form action="" method="POST" id="form2" onsubmit="return validate(this);">
              <div id='mod_sampelID_IGSN_header2' style="width:100%;"></div>
              <div id='mod_sampelID_IGSN2' style="width:100%;overflow:auto;"></div>
           </form>
       </div>
    
        <div id='searchResultSamples2'  style="display:none;margin:10px;padding:5px;background-color:#C3FDC3;">
            <h4>You have successfully added (or updated ) IGSN(s) for STATIONS(s):</h4>
            <table id="showID_IGSN2" border="1" style="width:100%;">
            <tr> 
              <th width="40%">STATION_ID</th>
              <th width="30%">STATION_NUM</th>
              <th width="30%">IGSN</th>
           </tr>
           </table>
           <a href="">Add More IGSN >></a>
        </div>
	 </div>	<!-- #vtabs-content-b  -->
	</div>
</div>

</body>
</html>