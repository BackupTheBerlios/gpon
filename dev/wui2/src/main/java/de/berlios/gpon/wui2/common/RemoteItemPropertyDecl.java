package de.berlios.gpon.wui2.common;

public class RemoteItemPropertyDecl {
	Long    id;
	String  name;
	String  description;
	String    valueType;
	String  derivedType;
	String  valueTypeProperties;
	Long    typeId; // to detect inherited properties
	boolean mandatory;
	boolean typic;
	
	public boolean isTypic() {
		return typic;
	}
	public void setTypic(boolean typic) {
		this.typic = typic;
	}
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
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getDerivedType() {
		return derivedType;
	}
	public void setDerivedType(String derivedType) {
		this.derivedType = derivedType;
	}
	public String getValueTypeProperties() {
		return valueTypeProperties;
	}
	public void setValueTypeProperties(String valueTypeProperties) {
		this.valueTypeProperties = valueTypeProperties;
	}
}
