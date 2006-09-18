package de.berlios.gpon.wui.forms;

import org.apache.struts.action.ActionForm;

public class GetEnvironmentForm extends ActionForm {

	Long objectId;
	int radius;
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	} 
	
}
