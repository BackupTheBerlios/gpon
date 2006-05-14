/*
GPON General Purpose Object Network
Copyright (C) 2006 Daniel Schulz

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/


package de.berlios.gpon.common;
import java.util.Set;


public class Item {
	
  Long id;
  ItemType itemType;
  
  Set properties;
  Set associationsA;
  Set associationsB;
  
  public String toString() {
    String s = "id=" + id + " itemType=" + itemType;
    return s;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getId() {
    return id;
  }


  public void setItemType(ItemType itemType)
  {
    this.itemType = itemType;
  }


  public ItemType getItemType()
  {
    return itemType;
  }


  public void setProperties(Set properties)
  {
    this.properties = properties;
  }


  public Set getProperties()
  {
    return properties;
  }


  public void setAssociationsA(Set associationsA)
  {
    this.associationsA = associationsA;
  }


  public Set getAssociationsA()
  {
    return associationsA;
  }


  public void setAssociationsB(Set associationsB)
  {
    this.associationsB = associationsB;
  }


  public Set getAssociationsB()
  {
    return associationsB;
  }

public int hashCode() {
	if (getId()!=null) {
		return getId().intValue();
	}
	else {
		return super.hashCode();
	}
}

public boolean equals(Object o) {
	
	if (!(o instanceof Item))
		return false;
	
	Item item = (Item)o;
	
	System.out.println("Item equals: "+getId()+" = "+item.getId());
	
	return (item!=null && item.getId()!=null && getId() != null && item.getId().equals(getId()));
}
 
}
