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


public interface Value 
{
  
  // data from upper layers are in input form
  public void setDataInInputForm(String data);
  // data from storage are in normal form
  public void setDataInNormalForm(String dataInNf);
   
  public ValueType getType();
  
  public void validate() throws ValueTypeValidationException;
  
  public String toHtml();
  public String toInput();
  public String toNormal();
  
  public Object getCoreValue();
  
}
