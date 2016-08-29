</div>
<script language="JavaScript" src="js/windows.js" type="text/javascript"></script>

<script language="JavaScript" src="js/JQuery/js/jquery-1.3.2.min.js" type="text/javascript"></script>
<!-- 
<script language="JavaScript" src="js/JQuery/js/jquery-1.6.2.min.js" type="text/javascript"></script>
 --> 
<script language="JavaScript" src="js/JQuery/jquery.qtip-1.0.0-rc3.min.js" type="text/javascript"></script>

<script language="JavaScript" src="js/JQuery/js/jquery-ui-1.7.3.custom.min.js" type="text/javascript"></script>

<!-- <script language="JavaScript" src="js/JQuery/js/jquery-ui-1.8.16.custom.min.js" type="text/javascript"></script> -->

<script language="JavaScript" src="js/loadHelpContent.js" type="text/javascript"></script>

<script language="JavaScript" src="js/JQuery/jquery-busybox-1.1.js" type="text/javascript"></script>
<link href="js/JQuery/css/busybox.css" rel="stylesheet" type="text/css"/>

<script>

function confrmSub(){
	if (counter < 1000){
	 window.location = 'pg3.jsp';
	 return;
	 }
	if (confirm(msg)){
	window.location = 'pg3.jsp';
	return;
    }
  return;
}


function confPopup(lnkVar,w_){
  var winWidth = "900";
  if (w_) winWidth = w_;
  if (counter < 1000){
		openWindow1(lnkVar,'setWin','900',winWidth);
	    return;
  }
  if(confirm(msg)){
	   openWindow1(lnkVar,'setWin','600',winWidth);
       return;
  }
  return;
}

/* Read help JSON file and create all Qtip for the first page */
creatAndShowHelpQtip();

//var box= $('busy-box');
	 
	// Show the 'busy boxes' passing some configuration
//	box.busyBox(
//	  spinner: '<img src="images/processing.gif" />'
//	);
	 
//$.ajax(
//	  url: 'my-url',
//	  success: function(response){
//	    box.html(response);
//	  },
//	  complete: function(){
	    // Closes the 'busy boxes'
//	    box.busyBox('close');
//	  }
//);

</script>
</body>
</html>
