function popup(linkobject,width,height) {
 var breite=800; 
 var hoehe=600; 
 
 if (width) 
 {
   breite = width;
 }
 
 if (height) 
 {
   hoehe = height;
 }
 

 var positionX=((screen.availWidth / 2) - breite / 2); 
 var positionY=((screen.availHeight / 2) - hoehe / 2); 
 
 pop=window.open(linkobject.href,'Popup','toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,fullscreen=0,width='+breite+',height='+hoehe+',top=0,left=0'); 
 pop.resizeTo(breite,hoehe); 
 pop.moveTo(positionX,positionY); 
 pop.focus();
 return false;
}