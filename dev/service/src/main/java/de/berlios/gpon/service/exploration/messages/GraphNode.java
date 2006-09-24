package de.berlios.gpon.service.exploration.messages;

import java.util.Collection;
import java.util.List;

public class GraphNode {

	Long objectId;
	String objectType;
	
	Collection attributes;

	public GraphNode() {
		// TODO Auto-generated constructor stub
	}
	
	public Collection getAttributes() {
		return attributes;
	}

	public void setAttributes(Collection attributes) {
		this.attributes = attributes;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	
}
