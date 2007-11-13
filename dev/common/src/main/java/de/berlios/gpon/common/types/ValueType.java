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


package de.berlios.gpon.common.types;


public class ValueType 
{
  String  name;
  String  className;
  String  description;
  
  boolean numeric;

  public ValueType()
  {
  }
  
  public ValueType(			 String pName,
                             String pClassName, 
                             String pDescription,
                             boolean numeric
                             )
  {
      setName(pName);
      setClassName(pClassName);
      setDescription(pDescription);
      setNumeric(numeric);
  }

  public void setDescription(String description)
  {
    this.description = description;
  }


  public String getDescription()
  {
    return description;
  }


  public void setName(String name)
  {
    this.name = name;
  }


  public String getName()
  {
    return name;
  }


  public void setClassName(String className)
  {
    this.className = className;
  }


  public String getClassName()
  {
    return className;
  }


  public void setNumeric(boolean numeric)
  {
    this.numeric = numeric;
  }


  public boolean isNumeric()
  {
    return numeric;
  }
  
}
