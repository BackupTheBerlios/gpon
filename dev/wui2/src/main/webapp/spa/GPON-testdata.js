function getSamplePropHandleSet() 
{
  var propHandleArray = new Array();
  propHandleArray.push(new GPON.PropHandle('Name','text','Schulz'));
  propHandleArray.push(new GPON.PropHandle('Vorname','text','Claire'));
  propHandleArray.push(new GPON.PropHandle('Geburtsdatum','date','30.5.1973'));
  propHandleArray.push(new GPON.PropHandle('Bild','imgUrl','http://img.avatars.yahoo.com/users/1PFDBYWwKAAECPmE_dI3o4VxKEG0D.medium.jpg'));
  propHandleArray.push(new GPON.PropHandle('Lebenslauf','longtext','Das ist sehr langer Text.'));
  return propHandleArray;
}

function getSampleItemType() 
{
	var propDeclArray = new Array();
	propDeclArray.push(new GPON.ItemPropertyDecl(1,'name','Name','text'));
  propDeclArray.push(new GPON.ItemPropertyDecl(2,'firstname','Vorname','text'));
  propDeclArray.push(new GPON.ItemPropertyDecl(3,'birthday','Geburtsdatum','date'));
  propDeclArray.push(new GPON.ItemPropertyDecl(4,'image','Bild','imgUrl'));
  propDeclArray.push(new GPON.ItemPropertyDecl(5,'cv','Lebenslauf','longtext'));
	
	var itemType = new GPON.ItemType(1,'person','Person',null,propDeclArray);
  
	return itemType;
}
