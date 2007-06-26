package de.berlios.gpon.wui2.common;

public class RemoteAssociation {
	Long id;
	Long typeId;
	Long itemAId;
	Long itemBId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getItemAId() {
		return itemAId;
	}
	public void setItemAId(Long itemAId) {
		this.itemAId = itemAId;
	}
	public Long getItemBId() {
		return itemBId;
	}
	public void setItemBId(Long itemBId) {
		this.itemBId = itemBId;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public String toString() 
	{
		return "[assoc: #"+getId()+" t#"+getTypeId()+" a#"+getItemAId()+" b#"+getItemBId()+"]";
	}
}
