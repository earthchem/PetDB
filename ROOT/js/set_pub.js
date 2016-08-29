function CheckBoxChecked(a)
{
	a.checked=true;
}

function CheckBoxUnChecked(a)
{
	a.checked=false;
}

function selectall(a)
{
	if (a.checked==true)
	{
		for (var i=0; i<a.form.elements.length; i++){
		a.form.elements[i].checked=true;
		}
	}
	if (a.checked==false)
	{
		for (var i=0; i<a.form.elements.length; i++){
		a.form.elements[i].checked=false;
		}
	}
}
function deselectall(a)
{
	if (a.checked==false)
	{

		a.form.checkbox0.checked=false;
	}

}

//delete all items in Select
function DelAllOptions(a)
{

	a.options.length=0

}

//delete seleted options
function DelSelectOptions(a)
{

	for (var k=a.options.length-1; k>-1; k--){
		if (a.options[k].selected==true){
			a.options[k]=null;
		}
	}

}


//add values for a single select box
function AddValuesForSingleSelect(b)
{

	var i=b.options.length;
	for (var ctr=0;ctr<a.options.length; ctr++){
		b.options[ctr+i]=new Option(a.options[ctr].text,a.options[ctr].value);
	}


}

//move all items in SelectA to SelectB
function AllSelectA2SelectB(a,b)
{
	aryValue=new Array();
	aryText=new Array();

	var i=b.options.length;
	for (var ctr=0;ctr<a.options.length; ctr++){
		b.options[ctr+i]=new Option(a.options[ctr].text,a.options[ctr].value);
	}

	a.options.length=0

}
//move selected options from SELECT_A to SELECT_B
function SelectA2SelectB(a,b)
{
	aryValue=new Array();
	aryText=new Array();

	var i=b.options.length;
	var j=0;
	for (var k=a.options.length-1; k>-1; k--){
		if (a.options[k].selected==true){
			aryValue[j]=a.options[k].value;
			aryText[j]=a.options[k].text;
			j++;
			a.options[k]=null;
		}
	}

	var t=aryValue.length-1
	for(ctr=0;ctr<aryValue.length;ctr++){
		b.options[ctr+i]=new Option(aryText[t-ctr],aryValue[t-ctr]);
	}

}

// substituted by the above one
function SelectA2SelectB2(a,b)
{
	aryAValue=new Array();
	aryAText=new Array();
	aryBValue=new Array();
	aryBText=new Array();

	for (var i=0; i<b.options.length; i++){
		aryBValue[i]=b.options[i].value;
		aryBText[i]=b.options[i].text;
	}

	j=0; //i--;
	for (var k=0; k<a.options.length; k++){
		if (a.options[k].selected==true){
			aryBValue[i]=a.options[k].value;
			aryBText[i]=a.options[k].text;
			i++
		}else{
			aryAValue[j]=a.options[k].value;
			aryAText[j]=a.options[k].text;
			j++;
		}
	}


	a.length=0;
	for(ctr=0;ctr<aryAValue.length;ctr++){
		a.options[ctr]=new Option(aryAText[ctr],aryAValue[ctr]);
	}

	b.length=0;
	for(ctr=0;ctr<aryBValue.length;ctr++){
		b.options[ctr]=new Option(aryBText[ctr],aryBValue[ctr]);
	}
}

function SelectAllOptions(b)
{
	for (var i=0; i<b.options.length; i++){
		b.options[i].selected=true;
	}

}

function AlertAvailable()
{
	var alerttext="Click the buttons on the lower panel to see which are avaliable.";
	alert(alerttext);
}



function DisplayButtonValue(b,bstr)
{
	b.value="   " + bstr + "   ";
}

function DisplaySelectValue(s0,s1,d,a)
{
  	var ss0,ss1; 
	//var d="|";
	  // Split at each space character  
	ss0 = s0.split(d); 
	ss1 = s1.split(d);  
	var reallength=0;
	a.length=0;
      //For every item add a new option
      for(i=1;i<ss1.length;i++){
       		a.options[i-1]=new Option(ss1[i],ss0[i]);
      }

}


function clearselectoption(a)
{
	a.length=0;
}


function tearoff (url, name, width, height)
        {
                //window.name = 'PUMain';
	
                tearWin=open(url, name, 'personalbar=no,toolbar=no,status=no,scrollbars=yes,location=no,resizable=yes,menubar=no,width=' + width + ',height=' + height );
                if(window.focus) {
                        tearWin.focus();
                }
        }

function ShowAvailble(s0,s1,d,bstr)
{
	DisplayButtonValue(document.frm_queryexpedition.ButtonAvailable,bstr);
	DisplaySelectValue(s0,s1,d,document.frm_queryexpedition.ItemsAvailable);
}
function BodyLoadFunction()
{
	clearselectoption(document.frm_queryexpedition.ItemsAvailable);
	clearselectoption(document.frm_queryexpedition.ItemNum);
}

