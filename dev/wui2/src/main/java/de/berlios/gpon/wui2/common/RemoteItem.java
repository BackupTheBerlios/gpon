package de.berlios.gpon.wui2.common;

public class RemoteItem {
	Long id;
	Long typeId;
	RemoteItemProperty[] properties;
	RemoteAssociation[]  associations;
	public RemoteAssociation[] getAssociations() {
		return associations;
	}
	public void setAssociations(RemoteAssociation[] associations) {
		this.associations = associations;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public RemoteItemProperty[] getProperties() {
		return properties;
	}
	public void setProperties(RemoteItemProperty[] properties) {
		this.properties = properties;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public String toString() 
	{
		StringBuffer buf = new StringBuffer();
		
		buf.append("[Item: #"+getId()+" t#"+getTypeId());
			
		if (getProperties()!=null) 
		{
			buf.append("\r\n [props:\r\n");
			for (int ip=0; ip < getProperties().length; ip++) 
			{
				if (ip>0)
					buf.append(",\r\n");
				buf.append("\t"+getProperties()[ip].toString());
			}
			buf.append("\r\n ]\r\n");
		}
		
		if (getAssociations()!=null)
		{
			buf.append("\r\n [assoc:\r\n");
			for (int ia=0; ia < getAssociations().length; ia++) 
			{
				if (ia>0)
					buf.append(",\r\n");
				buf.append("\t"+getAssociations()[ia].toString());
				
			}
			buf.append("\r\n ]\r\n");
		}
		
		
		buf.append("]");
		// 
		return buf.toString();
	}
	
}
