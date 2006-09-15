package de.berlios.gpon.service.exploration.messages;

public class AssociationInfo {
	Long itemId;
	Long associationTypeId;
	String associationTypeName;
	String associationTypeDescription;
	String itemARolename;
	String itemBRolename;
	boolean reverse;
	public String getAssociationTypeDescription() {
		return associationTypeDescription;
	}
	public void setAssociationTypeDescription(String associationTypeDescription) {
		this.associationTypeDescription = associationTypeDescription;
	}
	public Long getAssociationTypeId() {
		return associationTypeId;
	}
	public void setAssociationTypeId(Long associationTypeId) {
		this.associationTypeId = associationTypeId;
	}
	public String getAssociationTypeName() {
		return associationTypeName;
	}
	public void setAssociationTypeName(String associationTypeName) {
		this.associationTypeName = associationTypeName;
	}
	public String getItemARolename() {
		return itemARolename;
	}
	public void setItemARolename(String itemARolename) {
		this.itemARolename = itemARolename;
	}
	public String getItemBRolename() {
		return itemBRolename;
	}
	public void setItemBRolename(String itemBRolename) {
		this.itemBRolename = itemBRolename;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public boolean isReverse() {
		return reverse;
	}
	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}
	

}
