package de.berlios.gpon.wui2.common;

public class RemoteItemType {
	Long id;
	String name;
	String description;
	Long   baseTypeId;
	Long[] superTypeIds;
	RemoteItemPropertyDecl [] itemPropertyDecls;
	RemoteAssociationType [] associationTypes;
	
	public RemoteAssociationType[] getAssociationTypes() {
		return associationTypes;
	}
	public void setAssociationTypes(RemoteAssociationType[] associationTypes) {
		this.associationTypes = associationTypes;
	}
	public Long getBaseTypeId() {
		return baseTypeId;
	}
	public void setBaseTypeId(Long baseTypeId) {
		this.baseTypeId = baseTypeId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public RemoteItemPropertyDecl[] getItemPropertyDecls() {
		return itemPropertyDecls;
	}
	public void setItemPropertyDecls(RemoteItemPropertyDecl[] propertyDecls) {
		this.itemPropertyDecls = propertyDecls;
	}
	public Long[] getSuperTypeIds() {
		return superTypeIds;
	}
	public void setSuperTypeIds(Long[] superTypeIds) {
		this.superTypeIds = superTypeIds;
	}
}
