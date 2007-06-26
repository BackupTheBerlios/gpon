package de.berlios.gpon.wui2.common;

public class RemoteItemProperty {
	Long id;
	Long declId;
	String value;
	
	public Long getDeclId() {
		return declId;
	}
	public void setDeclId(Long declId) {
		this.declId = declId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() 
	{
		return "[prop: #"+getId()+" d#"+getDeclId()+" v: "+getValue()+"]";
	}
}
