package de.berlios.gpon.service.exploration.messages;

import com.thoughtworks.xstream.XStream;

public class BaseMessage {
	
	protected static XStream getXStream() 
	{
		XStream xstream = new XStream();
		
		xstream.alias("associationInfo",AssociationInfo.class);
		xstream.alias("associationInfoMessage",AssociationInfoMessage.class);
		
		xstream.alias("graphMessage",GraphMessage.class);
		xstream.alias("graphEdge",GraphEdge.class);
		xstream.alias("graphNode",GraphNode.class);
		xstream.alias("attribute",Attribute.class);
		
		
		return xstream;
		
	} 
	
	public String serialize() 
	 {
		 return getXStream().toXML(this); 
	 }
}
