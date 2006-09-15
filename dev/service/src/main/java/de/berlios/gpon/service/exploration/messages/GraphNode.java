package de.berlios.gpon.service.exploration.messages;

public class GraphNode {

	Long objectId;
	
	Attribute[] attributes;

	public Attribute[] getAttributes() {
		return attributes;
	}

	public void setAttributes(Attribute[] attributes) {
		this.attributes = attributes;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	
	
}
