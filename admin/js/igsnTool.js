/* Show div block according onOff flag 
 * if onOff is true, show div with id as 'blockId'
 */
function turnOnBlock(blockId,onOff)
{ 
  var elem = $(blockId);
  if(onOff==true)
  {
    elem.css("display","block");
  }
  else
  {
	elem.css("display","none");
  }
}

/* Given a name of all elements, replace each element with text */
function replaceText(elemName,text)
{ 
  var elems = document.getElementsByName(elemName);
  if(elems.length==0)
  {
	elems =  getElementsByName_iefix('span', elemName);
  }
  for(i=0;i<elems.length;i++)
  {
   elems[i].innerHTML=text;
  }
}

/* IE won't return getElementsByName properly, Here is the fix function */
function getElementsByName_iefix(tag, name) {
    
    var elem = document.getElementsByTagName(tag);
    var arr = new Array();
    for(i = 0,iarr = 0; i < elem.length; i++) {
         att = elem[i].getAttribute("name");
         if(att == name) {
              arr[iarr] = elem[i];
              iarr++;
         }
    }
    return arr;
}
/* Given id of element, replace background color with passed in value*/
function changeElemBackground(elemId,colorText)
{ 
   var elem = document.getElementById(elemId);
   elem.style.background=colorText;
}

function getDatabaseInformation(typeSorT)
{
	/* Fetch information from databse */
//	var idElem = document.getElementById('SorT_Id');
	var idElem = "";
	if(typeSorT=='sample') 
	{ idElem = $("#SorT_Id1"); }
	else 
	{ idElem = $("#SorT_Id2"); }
	if(idElem.val()=="") { alert("Please enter your id or part of your id."); return false; }
    var request = $.ajax({
                    url: "igsnInfo",
                    type: "POST",
                    data: "type="+typeSorT+"&id="+idElem.val() ,
                    dataType: "xml",
                    success:function(xml) {
                    	//alert("xml="+xml);
                    	displayData(xml,typeSorT);
                    },
                    error:function(jqXHR, textStatus) {

                    	if(typeSorT=='sample') 
                         { 
                    		$("#mod_SamplesNoResults1").children().remove();
                            alert( "sample !!! Request failed: " + textStatus );
                            if(textStatus=="parsererror")
                            {
                        	    $("#mod_SamplesNoResults1").append('<span>Too many rows are returned. Browser could not build a table on fly. Please modify your search id and try it again.</span>');
                            }
                            else
                            {
                        	    $("#mod_SamplesNoResults1").append('<span>Request '+textStatus+'</span>');
                            }
                    		
                            turnOnBlock('#mod_SamplesNoResults1',true);
                            turnOnBlock('#mod_searchResultSamples1',false);
                         }
                    	else
                    	{
                    		$("#mod_SamplesNoResults2").children().remove();
                            //    alert( "!!! Request failed: " + textStatus );
                            if(textStatus=="parsererror")
                            {
                                $("#mod_SamplesNoResults2").append('<span>Too many rows are returned. Browser could not build a table on fly. Please modify your search id and try it again.</span>');
                            }
                            else
                            {
                                $("#mod_SamplesNoResults2").append('<span>Request '+textStatus+'</span>');
                            }	
                            turnOnBlock('#mod_SamplesNoResults2',true);
                            turnOnBlock('#mod_searchResultSamples2',false);
                    	}
	
                    }
                    
                  });

}

function displayData(xml,typeSorT)
{
	var totalrows=$(xml).find("TotalRows").text();
	var errMsg =$(xml).find("error").text();
//	alert(totalrows);
	
	var label_mod_searchResultSamples="#mod_searchResultSamples";
	var label_mod_SamplesNoResults="#mod_SamplesNoResults";
	var label_mod_sampelID_IGSN_header="#mod_sampelID_IGSN_header";
	var label_mod_sampelID_IGSN="#mod_sampelID_IGSN";
//	var label_saveall="#saveall";
	var label_mod_SamplesNoResults="#mod_SamplesNoResults";
	var label_mod_searchResultSamples="#mod_searchResultSamples";
	
    if(typeSorT=='sample')
    {
    	label_mod_searchResultSamples="#mod_searchResultSamples1";
    	label_mod_SamplesNoResults="#mod_SamplesNoResults1";
    	label_mod_sampelID_IGSN_header="#mod_sampelID_IGSN_header1";
    	label_mod_sampelID_IGSN="#mod_sampelID_IGSN1";
//    	label_saveall="#saveall1";
    	label_mod_SamplesNoResults="#mod_SamplesNoResults1";
    	label_mod_searchResultSamples="#mod_searchResultSamples1";
    	SAMPLE='SAMPLE';
    }
    else
    {
    	label_mod_searchResultSamples="#mod_searchResultSamples2";
    	label_mod_SamplesNoResults="#mod_SamplesNoResults2";
    	label_mod_sampelID_IGSN_header="#mod_sampelID_IGSN_header2";
    	label_mod_sampelID_IGSN="#mod_sampelID_IGSN2";
//    	label_saveall="#saveall2";
    	label_mod_SamplesNoResults="#mod_SamplesNoResults2";
    	label_mod_searchResultSamples="#mod_searchResultSamples2";
    	SAMPLE='STATION';
    }
	if( totalrows=="0")
	{
		turnOnBlock(label_mod_searchResultSamples,false);
		var info  ='<span>No '+SAMPLE+' found. Please modify your search and click ';
		    info +='Get '+SAMPLE+'(S) button again.</span>';
		$(label_mod_SamplesNoResults).children().remove();
        $(label_mod_SamplesNoResults).append(info);  
		turnOnBlock(label_mod_SamplesNoResults,true);
		return;
	}
	else if( errMsg !="" )
	{
		turnOnBlock(label_mod_searchResultSamples,false);
		var info  ='<span>'+errMsg+'</span>';
		$(label_mod_SamplesNoResults).children().remove();
        $(label_mod_SamplesNoResults).append(info);  
		turnOnBlock(label_mod_SamplesNoResults,true);
		return;		
	}else	
	{
	  var mTable = buildHeaderTable(typeSorT);
	  var xmlTable = buildXMLTable(xml,typeSorT);
	
	  /* clean contents */
	  $(label_mod_sampelID_IGSN_header).children().remove();
	  $(label_mod_sampelID_IGSN).children().remove();
//	  $(label_saveall).children().remove();
	  if(totalrows > 10 )
	  {
		  $(label_mod_sampelID_IGSN).height('250px');
	  }
	  else
	  {
		  var ht=30*totalrows+20;
		  $(label_mod_sampelID_IGSN).height(ht);
	  }
	  
	  /* Add contents */
	  $(label_mod_sampelID_IGSN_header).append(mTable);
	  $(label_mod_sampelID_IGSN).append(xmlTable);
//	  $(label_saveall).append('<input type="hidden" value="'+typeSorT+'" id="type" />');
//	  $(label_saveall).append('<br/>');
//	  $(label_saveall).append('<input type="submit" value="Save All"></input>');
	  /* Turn on specified block */
	  turnOnBlock(label_mod_SamplesNoResults,false);
	  turnOnBlock(label_mod_searchResultSamples,true);	
	}
}

function buildHeaderTable(typeSorT)
{
//	alert( 'buildHeaderTable');
	typeSorT=typeSorT.toUpperCase();
	var myTable = '' ;
	myTable += '<table id="mod_sampelID_IGSN_header_table" border="1" width="630">' ;
	myTable +=  '<tr>';
	myTable +=  '<th width="38%"><span name="type">'+typeSorT+'</span>_ID</th>';
	myTable +=  '<th width="18%"><span name="type">'+typeSorT+'</span>_NUM</th>';
	myTable +=  '<th width="32%">Enter IGSN</th>';
	myTable +=  '<th width="12%">&nbsp;</th>';
	myTable +=  "</tr>" ;
	myTable += "</table>"
	return myTable;
}
function buildXMLTable(xml,typeSorT)
{
//	alert( 'buildXMLTable');
	typeSorT=typeSorT.toUpperCase();
	var myTable = '' ;

	myTable += '<table id="mod_ID_IGSN_table" border="0" width="625" overflow="auto" padding="2">' ;
	var i=0;
    //find every row and print the each column
	$(xml).find("row").each(function()
	{   i++;
		myTable +=  '<tr>';
	    myTable += '<td width="40%">'+$(this).find(typeSorT+"_ID").text()+'</td>';
	    myTable += '<td width="18%">'+$(this).find(typeSorT+"_NUM").text()+'</td>';
	    var igsnVal = $(this).find("IGSN").text();
	    //alert(igsnVal);
	    if(igsnVal=="") 
	    {
	    	saveStr="Save";
	    }
	    else
	    {
	    	saveStr="Modify";
	    }
	    myTable += '<td width="30%"><input type="text" id="'+igsnVal+'" value="'+igsnVal+'" maxlength="9" size="9"/></td>';
	    myTable += '<td align="right" width="12%"><input type="button" onclick="updateIGSN(\''+typeSorT+'\','+i+')" value="'+saveStr+'"/></td>';
	    myTable +=  '</tr>';
	});
	
	myTable += '</table>' ;    
    
   return myTable;
}

function updateIGSN(type,rowNum)
{
	var doit = false;
	
	if(type=='SAMPLE')
	{
		var tableParent=$("div #mod_sampelID_IGSN1");
	}
	else
	{
		var tableParent=$("div #mod_sampelID_IGSN2");
	}
//	alert("type="+type+" "+tableParent.html());
    var firstCellText = tableParent.find("#mod_ID_IGSN_table").find('tr:nth-child('+rowNum+')').find('td').eq(0).text();
    var secondCellText= tableParent.find("#mod_ID_IGSN_table").find('tr:nth-child('+rowNum+')').find('td').eq(1).text();
    var thirdCellText = tableParent.find("#mod_ID_IGSN_table").find('tr:nth-child('+rowNum+')').find('input:first').attr('value');
//    alert(firstCellText+ " "+ secondCellText+" "+thirdCellText);
    var insertYes = "yes";
    if(thirdCellText==undefined || thirdCellText=="") 
    {
  	  alert('Please enter IGSN for ['+firstCellText+'] ['+secondCellText+'].');
  	  return false;
    }
    else
    {
    	if(thirdCellText.length !=9 )
    	{
    	  alert("IGSN ("+thirdCellText+") is not valid. It needs 9 characters.");
    	  return false;
    	}
    	var thirdCellId = tableParent.find('#mod_ID_IGSN_table').find('tr:nth-child('+rowNum+')').find('input:first').attr('id');
    	if(thirdCellId !="" )
    	{
    	  doit = confirm('Are you sure to replace the IGSN for ['+firstCellText+'] ['+secondCellText+']?' );
    	  insertYes="no";
    	}
    	else
    	{
    		doit = true;
    	}
    }
	if(doit == true )
	{
	    var request = $.ajax({
            url: "igsnUpdate",
            type: "POST",
            data: "type="+type+"&iyes="+insertYes+"&num="+secondCellText+"&igsn="+thirdCellText+"&updateOne=yes",
            dataType: "xml",
            success:function(xml) {
            	var errText=$(xml).find("error").text();
            	
            	if(errText != "no")
            	{
            	    alert("Failed: "+errText );
            	    return;
                }
            	else 
            	{
            		if(type=='SAMPLE')
            		{
            			var tableParent=$("div #mod_sampelID_IGSN1");
            		}
            		else
            		{
            			var tableParent=$("div #mod_sampelID_IGSN2");
            		}
            	//	alert(tableParent.html());
            		tableParent.find('#mod_ID_IGSN_table').find('tr:nth-child('+rowNum+')').find('input:first').attr('id',thirdCellText);
            	    var thirdCellIdStr =tableParent.find('#mod_ID_IGSN_table').find('tr:nth-child('+rowNum+')').find('input:first').attr('id');
            	//    alert(thirdCellIdStr);
            	    if(type=='SAMPLE')
            	    {
               	      $('#showID_IGSN1').append('<tr><td>'+firstCellText+'</td><td>'+secondCellText+'</td><td>'+thirdCellText+'</td></tr>');
               	    //  alert($('#showID_IGSN1').html());
               	      turnOnBlock('#searchResultSamples1',true);
               	    //  turnOnBlock('#mod_searchResultSamples1',false);
            	    }
            	    else
            	    {
               	      $('#showID_IGSN2').append('<tr><td>'+firstCellText+'</td><td>'+secondCellText+'</td><td>'+thirdCellText+'</td></tr>');
               	      turnOnBlock('#searchResultSamples2',true);
               	    //  turnOnBlock('#mod_searchResultSamples2',false);
            	    }  
            	}
            },
            error:function(jqXHR, textStatus) {
                 if(type=='SAMPLE')
                 {
                     $("#mod_SamplesNoResults1").children().remove();
                   //  alert( "!!! Request failed: " + textStatus );
                     if(textStatus=="parsererror")
                     {
                     	 $("#mod_SamplesNoResults1").append('<span>Too many rows are returned. Browser could not build a table on fly. Please modify your search id and try it again.</span>');
                     }
                     else
                     {
                	     $("#mod_SamplesNoResults1").append('<span>'+textStatus+'</span>');
                     }
                     turnOnBlock('#mod_SamplesNoResults1',true);
                     turnOnBlock('#mod_searchResultSamples1',false);	
                 }
                 else
                 {
                     $("#mod_SamplesNoResults2").children().remove();
                   //  alert( "!!! Request failed: " + textStatus );
                     if(textStatus=="parsererror")
                     {
                     	 $("#mod_SamplesNoResults2").append('<span>Too many rows are returned. Browser could not build a table on fly. Please modify your search id and try it again.</span>');
                     }
                     else
                     {
                	     $("#mod_SamplesNoResults2").append('<span>'+textStatus+'</span>');
                     }
                     turnOnBlock('#mod_SamplesNoResults2',true);
                     turnOnBlock('#mod_searchResultSamples2',false);	
                 }
            }
            
          });
	}
	else
	{
	//	alert('Cancel it');
	}

}

function validate(myForm)
{
	myForm.find('#mod_ID_IGSN_table tr').each(function() {
      var firstCellText = $(this).find('td').eq(0).text();
      var secondCellText = $(this).find('td').eq(1).text();
      var thirdCellText = $(this).find('input:first').attr('value');
     // alert(firstCellText+ " "+ secondCellText+" "+thirdCellText);
      if(thirdCellText==undefined || thirdCellText=="") 
      {
    	  alert('Please enter IGSN for ['+firstCellText+'] ['+secondCellText+']');
    	  return false;
      }     
  });
	return true;
}