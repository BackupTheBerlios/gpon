package de.berlios.gpon.service.exploration.messages;

public class AssociationInfoMessage 
	extends BaseMessage
{
 AssociationInfo[] associationInfos;
 
 public static AssociationInfoMessage deserialize(String msg) 
 {
	 return (AssociationInfoMessage)getXStream().fromXML(msg);
 }
 
 

public AssociationInfo[] getAssociationInfos() {
	return associationInfos;
}

public void setAssociationInfos(AssociationInfo[] associationInfos) {
	this.associationInfos = associationInfos;
}

 
 
 
}
