<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Station & Location</title>
<!-- Include JQuery Core-->
<script type="text/javascript" src="jquery/js/jquery-1.4.1.min.js"></script>
 <script>
            $(document).ready(function() {                
                $('#submit').click(function(event) {                    
                    $('#station_location_ds').html('');
                    $('#msg').text('');
                    var station_id=$('#station_id').val();
                     if(station_id !="")
                    $.get('stationByLocationServlet',{station_id:station_id},function(responseText) { 
                        $('#searchResult').html(responseText);         
                    });
                });
                
                $('#locationLookupBnt').click(function(event) {                         
                    var lat=$('#lat').val();
                    var lon=$('#lon').val();  
                    if(lat != "" && lon !="")
                    $.get('stationByLocationServlet',{lat:lat,lon:lon},function(responseText) { 
                         $('#lookupDiv').html(responseText);   
                    });
                });
                
                $('#lat').blur(function(event) {                         
                   $('#lookupDiv').html("");
                });
                $('#lon').blur(function(event) {                         
                   $('#lookupDiv').html("");
                });
            });
            
            function selectResult() {
             $('#station_location_ds').show();
             var stationLocation =  $('#station-location option:selected').val();
              $.get('stationByLocationServlet',{station_location:stationLocation},function(responseText) { 
                        $('#station_location_ds').html(responseText);         
                    });
            }
            
 function updateLocation() {   
    var formData = $('#locationForm input, #locationForm select').serialize(); 
    ajaxSubmit(formData);   
 }
 
 function updateStationLocation() {   
     var formData = $('#stationByLocationForm input').serialize(); 
     var stationLocation =  $('#station-location option:selected').val();
     formData += "&stationLocation="+stationLocation;
     ajaxSubmit(formData);   
 }
 
 function ajaxSubmit(formData){
     $.ajax({
            type: "GET",
            url: "stationByLocationServlet",
            data: formData,
        success: function(data) {
        $('#msg').text(data);
    }
    });
 }
 
 function deleteStationLocation() {   
 if(confirm('Are you sure you want to delete this?')==true) {
    var stationLocation =  $('#station-location option:selected').val();;
     $.get('stationByLocationServlet',{del_station_location:stationLocation},function(responseText) { 
        $('#msg').text(responseText);
    }); 
  }
 }
 
 function editLookup() {
    if($('#station_location_ds').html() ==""){
        alert('Please search by Station ID first!');
        return false;
    }
    
    var lookupId = $('#lookupId').val();    
    $('#sl_LOCATION_NUM').val(lookupId);
     $.get('stationByLocationServlet',{lookupId:lookupId},function(responseText) { 
         $('#locationForm').html(responseText);
    }); 
    $('#searchResult').html("");
    $('#lookupDiv').html("");
 }
 
 
</script>

</head>
<body>
<div style="background-color:#C3FDC3;border:0;height:50px;width:100%;text-align:center;">
  <h3 style="display:inline-block;">Update Station and Location</h3>
  <button onclick="window.close()" value="Close" id="Close" style="float:right;">Close</button>
</div>

<table width='100%'><tr><td valign='top' width='60%'>
		<TABLE><TR>
			<TD> Search by Station Id:</TD>	
			<TD><INPUT type="text" id="station_id" name="station_id" value="">&nbsp;<INPUT type="button" id="submit" name="search" value="Search"></TD>
		</TR>
        <tr id="searchResult"> </tr>
        </TABLE>
      <br>
        <div id="station_location_ds"></div>
        <div id="msg"></div>
</td><td width='2%'></td><td valign='top'>
<TABLE><caption><h4>Location Lookup</h4></caption>
<TR><TD>Latitude:</td><td><INPUT type="text" id="lat" name="lat" value=""></td></tr>
<TR><TD>Longitude:</td><td><INPUT type="text" id="lon" name="lon" value=""></td></tr>
<TR><TD/><TD align='right'><INPUT type="button" id ="locationLookupBnt" name="locationLookup" value="Search"></td></TR>
</TABLE>
<div id="lookupDiv"></div>
</td></tr></table>

</body>
</html>