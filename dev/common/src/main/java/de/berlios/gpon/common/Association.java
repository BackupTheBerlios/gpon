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


public class Association {
  Long id;
  // for convinient access
  Item itemA;
  Item itemB;

  AssociationType associationType;      

  public String toString() {
    String s = "id=" + id + " itemAId=" + itemA +
               " itemBId=" + itemB + " associationTypeId= " +
               associationType;
    return s;
  }
  
  public void setId(Long id) {
    this.id = id;
  }


  public Long getId() {
    return id;
  }


  public void setItemA(Item itemA)
  {
    this.itemA = itemA;
  }


  public Item getItemA()
  {
    return itemA;
  }


  public void setItemB(Item itemB)
  {
    this.itemB = itemB;
  }


  public Item getItemB()
  {
    return itemB;
  }


  public void setAssociationType(AssociationType associationType)
  {
    this.associationType = associationType;
  }


  public AssociationType getAssociationType()
  {
    return associationType;
  }

  public String getAtAB() {
	String at = (getAssociationType()!=null)?""+getAssociationType().getId():"";  
	String a = (getItemA()!=null)?""+getItemA().getId():"";
	String b = (getItemB()!=null)?""+getItemB().getId():"";
	
	return at+":"+a+":"+b;
  }
  
  public boolean equals(Object o) {
	  
	  if (o==null)
		  return false;
	  	  
	  if (!(o instanceof Association))
	  {
		  return false;
	  }
	  
	  Association other = (Association)o;
	  
	  return other.getAtAB().equals(getAtAB());
  }

public int hashCode() {
	// TODO Auto-generated method stub
	return getAtAB().hashCode();
} 

}
