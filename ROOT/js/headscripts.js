var switchTo;

function switchView(switchTo){
	document.getElementById('srchChoice').src = "<%=serverPath%>/images/" + switchTo + "bar.gif";
	if(switchTo == 'advanced')showLayer('advanced');
	if(switchTo == 'basic')hideLayer('advanced');
	return;
}

function showLayer(id) {
	document.getElementById(id).style.display = "block";
	return;
}

function hideLayer(id) {
	document.getElementById(id).style.display = "none";
	return;
}

function showBlock(link, id) {
var thsObj =  document.getElementById(id);
var parentObj=document.getElementById("menu");
//alert(parentObj);
var divItems = parentObj.getElementsByTagName("div");
for (i=0; i<divItems.length; i++){
		if (divItems[i] != thsObj){
		if(divItems[i].style.display == 'block') divItems[i].style.display = 'none';
	}
}

thsObj.style.left= (findPosX(parentObj) + parentObj.offsetWidth - 25) + "px";
thsObj.style.top= findPosY(link) + "px";
thsObj.style.display = 'block';
setTimeout('wait()', 300);
return;
}

function wait(){
clearTimeout();
}

  function findPosX(obj)
  {
    var curleft = 0;
    if(obj){
    if(obj.offsetParent)
        while(1)
        {
          curleft += obj.offsetLeft;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.x)
        curleft += obj.x;
    return curleft;}
  }

  function findPosY(obj)
  {
    var curtop = 0;
    if(obj){
    if(obj.offsetParent)
        while(1)
        {
          curtop += obj.offsetTop;
          if(!obj.offsetParent)
            break;
          obj = obj.offsetParent;
        }
    else if(obj.y)
        curtop += obj.y;
    return curtop;}
  }

function stayOn(link){link.style.display = 'block';}

function goOff(link){
	if(link.style)link.style.display = 'none';
}

function hideAll(){
	//FIXME Lulin commented out the following because they are not working
//var parentObj = document.getElementById("menu");
//var divItems = parentObj.getElementsByTagName("div");
//for (i=0; i<divItems.length; i++){
//	if(divItems[i].style.display == 'block') divItems[i].style.display = 'none';
//    }
}