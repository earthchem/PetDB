/* no menu bar */
function openWindow2(myLink,h_, w_,w_Name)
  {
  var winName = "set_win";
  if (w_Name) winName = w_Name;
  var w_specs = "height=" + h_ + ",width=" + w_ + ",dependent=no,resizable=yes,scrollbars=yes,menubar=no,toolbar=no";
  // alert("openWindow1 height="+h_+" width="+w_);
  if(!window.focus) return;
  myWin=window.open(myLink,winName,w_specs);
  myWin.focus();
  myLink.target=winName;
  }
/* no menu bar, no scroll */
function openWindow3(myLink,h_, w_,w_Name)
  {
  var winName = "set_win";
  if (w_Name) winName = w_Name;
  var w_specs = "height=" + h_ + ",width=" + w_ + ",dependent=no,resizable=no,scrollbars=no,menubar=no,toolbar=no";
  // alert("openWindow1 height="+h_+" width="+w_);
  if(!window.focus) return;
  myWin=window.open(myLink,winName,w_specs);
  myWin.focus();
  myLink.target=winName;
  }
/* have menue bar */
function openWindow1(myLink,windowName,h_,w_)
  {
  var w_specs = "height=600,width=800,dependent=no,resizable=yes,scrollbars=yes,menubar=yes,toolbar=no";
  if((h_)&&(w_))w_specs = "height=" + h_ + ",width=" + w_ + ",dependent=no,resizable=yes,scrollbars=yes,menubar=yes,toolbar=no\"";
  if(! window.focus) return;
  //alert("openWindow1 height="+h_+" width="+w_);
  var myWin=window.open(myLink,windowName,w_specs);
  myWin.focus();
  myLink.target=windowName;
  return false;
}

function openwindow(myLink,windowName,h_,w_)
{
var w_specs = "height=300,width=500,dependent=no,resizable=yes,scrollbars=yes,menubar=yes,toolbar=yes";
if((h_)&&(w_))w_specs = "height=" + h_ + ",width=" + w_ + ",dependent=no,resizable=yes,scrollbars=yes,menubar=no,toolbar=no";
if(! window.focus)return;
var myWin=window.open(myLink,windowName,w_specs);
myWin.focus();
myLink.target=windowName;
return false;
}

function SubmitForm0(a0, a1, a2, a3, a4 )
{
        document.form0.singlenum.value = a0;
        document.form0.table_num.value = a1;
        document.form0.table_title.value = a2;
        document.form0.rows.value = a3;
        document.form0.items.value = a4;
        document.form0.submit();
        return;
}
function SubmitForm1()
{
        document.form1.submit();
        return;
}

function submitForm(formName,actionStr)
{
	var formElem=document.getElementById(formName);
	formElem.action=actionStr;
	/*formElem.onsubmit=true; Won't work in IE */
    formElem.submit();
	return false;
}

function replaceTDText(id,str) {
	// id is the ID of the td cell, str is new text
	var mycel = document.getElementById(id);
	var myceltext = mycel.childNodes.item(0);
	mycel.removeChild(myceltext);

	var spanTag = document.createElement("span");
    spanTag.innerHTML = str;
    mycel.appendChild(spanTag);
}