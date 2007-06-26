package de.berlios.gpon.wui2.common;

public class RemoteAssociationType {
	Long id;
	String name;
	String multiplicity;
	String description;
	Long itemATypeId;
	Long itemBTypeId;
	String itemARoleName;
	String itemBRoleName;
	// ab,ba,abba
	String visibility;
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
	public String getItemARoleName() {
		return itemARoleName;
	}
	public void setItemARoleName(String itemARoleName) {
		this.itemARoleName = itemARoleName;
	}
	public Long getItemATypeId() {
		return itemATypeId;
	}
	public void setItemATypeId(Long itemATypeId) {
		this.itemATypeId = itemATypeId;
	}
	public String getItemBRoleName() {
		return itemBRoleName;
	}
	public void setItemBRoleName(String itemBRoleName) {
		this.itemBRoleName = itemBRoleName;
	}
	public Long getItemBTypeId() {
		return itemBTypeId;
	}
	public void setItemBTypeId(Long itemBTypeId) {
		this.itemBTypeId = itemBTypeId;
	}
	public String getMultiplicity() {
		return multiplicity;
	}
	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVisibility() {
		return visibility;
	}
	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
}
