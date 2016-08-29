//Qtip help bubble
$.fn.qtip.styles.helpstyle = { // Last part is the name of the style
   width: 250,
   //height:200,
  // background: '#FFFFFFF',
   color: 'black',
   textAlign: 'center',
  // border: {
  //    width: 0.01,
  //    radius: 5,
  //    color: '#98AFC7'
  // },
   tip: { // Now an object instead of a string
         corner: 'bottomLeft', // We declare our corner within the object using the corner sub-option
         //color: '#6699CC',
         size: {
          x: 8, // Be careful that the x and y values refer to coordinates on screen, not height or width.
          y : 8 // Depending on which corner your tooltip is at, x and y could mean either height or width!
          }
        },
   name: 'light' // Inherit the rest of the attributes from the preset dark style
}
   
/* Will get all help text from a JSON file and create all Qtip for that Help JSON file*/
function creatAndShowHelpQtip()
{
	//var helpcontent= new Array();

	$.ajax({
		 type: "GET",
		 url: "json/petdbhelp.json",
		 beforeSend: function(x) {
		  if(x && x.overrideMimeType) {
		   x.overrideMimeType("application/j-son;charset=UTF-8");
		  }
		 },
		 asyn:false,
		 dataType: "json",
		 success: function(data){
			 $.each(data, function(key, val) {
					  //alert(key+": "+val);
				     key ="#"+key; // preappend # sign
					// helpcontent[ key ]= val;
					 //create qtip
						  $(key).qtip({
					            content: val,
					            style: 'helpstyle', // The name of the newly created custom style above
					            show: 'mouseover',
					            hide: 'mouseout',
					            position: {
					                    target: 'mouse',
					                    corner: {
					                           target: 'topRight',
					                           tooltip: 'bottomLeft'
					                    }
					                   }
					            }); 
				  });
		 }
		});
}

/* Will create one Qtip according to pass in text and id (key) and show it */
function creatAndShowQtip(key,text)
{
	key ="#"+key; // preappend # sign
	//create qtip
	$(key).qtip({
				content: text,
				style: 'helpstyle', // The name of the newly created custom style above
			    show: 'mouseover',
			    hide: 'mouseout',
			    position: {
					      target: 'mouse',
					      corner: {
					                           target: 'topRight',
					                           tooltip: 'bottomLeft'
					              }
			              }
				});
}

/* Will create Qtip for Analysis Comment from description array and id name*/
function createAnalysisCommentQtip(commentDesc,idName)
{
	var total=commentDesc.length;
    for (var i=1;i<=total;i++) { 
		var idNum = idName+i;
		var contentStr = "<u>Analysis Comment</u> <br>"+ commentDesc[i-1];
		creatAndShowQtip(idNum,contentStr);
    }
}

/* startColNum and endColNum are deleted */
function removeEmptyAnalysisColumn(colIdPrefix, startColNum, endColNum)
{
  var commentCnt=0; // find if there is any analysis comment in this column
  for(var i=startColNum;i<=endColNum;i++)
  {
	var colIdName = '#'+colIdPrefix+'Col'+i;
	var commentIdName = '#'+colIdPrefix+i;
    if ( $(colIdName).find(commentIdName).length !=0 ) { commentCnt=1; break;}
  }
  //alert(commentCnt);
  if(commentCnt == 0 ) //remove empty column
  {
	  var colIdName = '#'+colIdPrefix+'Col0';
	  $(colIdName).remove(); //remove heading
	  for(var i=startColNum;i<=endColNum;i++)
	  {
		var colIdName = '#'+colIdPrefix+'Col'+i;
		var commentIdName = '#'+colIdPrefix+i;
	    $(colIdName).remove();
	  }
  }
}
//$("#helptip").qtip({
//content: {
//   url: 'localcontent.html',
//   data: { id: 5 },
//   method: 'get'
//}
//});