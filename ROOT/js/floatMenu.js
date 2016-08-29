NS6=false;
IE4=(document.all);
if (!IE4) {NS6=(document.getElementById);}
NS4=(document.layers);

floatX=0;
floatY=350;
layerwidth=15;
layerheight=20;
halign="left";
valign="top";
delayspeed=3;

// This script is copyright (c) Henrik Petersen, NetKontoret
// Feel free to use this script on your own pages as long as you do not change it.
// It is illegal to distribute the script as part of a tutorial / script archive.
// Updated version available at: http://www.echoecho.com/toolfloatinglayer.htm
// This comment and the 4 lines above may not be removed from the code.

function adjust() {
if (self.pageYOffset) { // all except Explorer
if (lastX==-1 || delayspeed==0)
{
lastX=floatX;
lastY=self.pageYOffset + floatY;
}
else
{
var dx=Math.abs(0);
var dy=Math.abs(self.pageYOffset+floatY-lastY);
var d=Math.sqrt(dx*dx+dy*dy);
var c=Math.round(d/10);

// below lines removed so horiz. scrolling won't happen
//if (window.pageXOffset+floatX>lastX) {lastX=lastX+delayspeed+c;}
//if (window.pageXOffset+floatX<lastX) {lastX=lastX-delayspeed-c;}
if (self.pageYOffset+floatY>lastY) {lastY=lastY+delayspeed+c;}
if (self.pageYOffset+floatY<lastY) {lastY=lastY-delayspeed-c;}
}
if (NS4){
document.layers['floatlayer'].pageX = floatX +'px';
document.layers['floatlayer'].pageY = lastY +'px';
}
if (NS6){
document.getElementById('floatlayer').style.left=floatX +'px';
document.getElementById('floatlayer').style.top=lastY +'px';
}
}
else if (document.documentElement && document.documentElement.scrollTop)
	// Explorer 6 Strict
{
if (lastX==-1 || delayspeed==0)
{
lastX=floatX;
lastY=document.documentElement.scrollTop + floatY;
}
else
{
var dx=Math.abs(0);
var dy=Math.abs(document.documentElement.scrollTop+floatY-lastY);
var d=Math.sqrt(dx*dx+dy*dy);
var c=Math.round(d/10);

// below lines removed so horiz. scrolling won't happen
//if (document.body.scrollLeft+floatX>lastX) {lastX=lastX+delayspeed+c;}
//if (document.body.scrollLeft+floatX<lastX) {lastX=lastX-delayspeed-c;}
if (document.documentElement.scrollTop+floatY>lastY) {lastY=lastY+delayspeed+c;}
if (document.documentElement.scrollTop+floatY<lastY) {lastY=lastY-delayspeed-c;}
}
document.all['floatlayer'].style.left = floatX +'px';
document.all['floatlayer'].style.top = lastY +'px';
}


setTimeout('adjust()',50);
}

function define()
{
if (self.pageYOffset) { // all except Explorer
if (halign=="left") {floatX=ifloatX};
if (halign=="right") {floatX=self.innerWidth-ifloatX-layerwidth-20};
if (halign=="center") {floatX=Math.round((self.innerWidth-20)/2)-Math.round(layerwidth/2)};
if (valign=="top") {floatY=ifloatY};
if (valign=="bottom") {floatY=self.innerHeight-ifloatY-layerheight};
if (valign=="center") {floatY=Math.round((self.innerHeight-20)/2)-Math.round(layerheight/2)};
}
if (document.documentElement && document.documentElement.scrollTop)
{
if (halign=="left") {floatX=ifloatX};
if (halign=="right") {floatX=document.documentElement.offsetWidth-ifloatX-layerwidth-20}
if (halign=="center") {floatX=Math.round((document.documentElement.offsetWidth-20)/2)-Math.round(layerwidth/2)}
if (valign=="top") {floatY=ifloatY};
if (valign=="bottom") {floatY=document.documentElement.offsetHeight-ifloatY-layerheight}
if (valign=="center") {floatY=Math.round((document.documentElement.offsetHeight-20)/2)-Math.round(layerheight/2)}
}
}

