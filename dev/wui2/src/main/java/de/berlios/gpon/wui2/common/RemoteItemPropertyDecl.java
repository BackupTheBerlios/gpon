package de.berlios.gpon.wui2.common;

public class RemoteItemPropertyDecl {
	Long    id;
	String  name;
	String  description;
	Long    valueTypeId;
	Long    typeId; // to detect inherited properties
	boolean mandatory;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isMandatory() {
		return mandatory;
	}
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public Long getValueTypeId() {
		return valueTypeId;
	}
	public void setValueTypeId(Long valueTypeId) {
		this.valueTypeId = valueTypeId;
	}
}
