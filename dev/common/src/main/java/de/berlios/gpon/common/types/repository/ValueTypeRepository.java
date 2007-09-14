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


package de.berlios.gpon.common.types.repository;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.ValueType;


public class ValueTypeRepository 
{

  private static Hashtable typedefsById;
  private static Hashtable typedefsByClassName;
  
  static {
  
    typedefsById        = new Hashtable();
    typedefsByClassName = new Hashtable();
    
    // String type
    typedefsById.put(       ShortText.SHORTTEXT_VTD.getId(), 
                            ShortText.SHORTTEXT_VTD);
    typedefsByClassName.put(ShortText.SHORTTEXT_VTD.getClassName(), 
                            ShortText.SHORTTEXT_VTD);
                            
    // URL type
    typedefsById.put(       URL.URL_VTD.getId(), 
                            URL.URL_VTD);                     
    typedefsByClassName.put(URL.URL_VTD.getClassName(), 
                            URL.URL_VTD);                            
                            
  
    // Decimal Number Type
    typedefsById.put       (DecimalNumber.DECIMALNUMBER_VTD.getId(), 
                            DecimalNumber.DECIMALNUMBER_VTD);                     
    typedefsByClassName.put(DecimalNumber.DECIMALNUMBER_VTD.getClassName(), 
                            DecimalNumber.DECIMALNUMBER_VTD);                            


    // Image URL Type
    typedefsById.put       (ImageURL.IMAGEURL_VTD.getId(), 
                            ImageURL.IMAGEURL_VTD);                     
    typedefsByClassName.put(ImageURL.IMAGEURL_VTD.getClassName(), 
                            ImageURL.IMAGEURL_VTD); 
                            
                       
    // Date Type
    typedefsById.put       (DateType.DATETYPE_VTD.getId(), 
                            DateType.DATETYPE_VTD);                     
    typedefsByClassName.put(DateType.DATETYPE_VTD.getClassName(), 
                            DateType.DATETYPE_VTD);  
    
    // Boolean Type
    typedefsById.put       (BooleanType.BOOLEANTYPE_VTD.getId(), 
    						BooleanType.BOOLEANTYPE_VTD);                     
    typedefsByClassName.put(BooleanType.BOOLEANTYPE_VTD.getClassName(), 
    						BooleanType.BOOLEANTYPE_VTD); 
   
  }

  public static List getAllValueTypeDefinitions() {
    return new ArrayList(typedefsById.values());
  }  
  
  public static Value getValueTypeObjectByTypeId(Long id) 
  {
	  try {
      if (typedefsById.containsKey(id)) 
      {
        ValueType vtd = (ValueType)typedefsById.get(id);
        return (Value)Class.forName(vtd.getClassName()).newInstance();
      }
      else 
        return null;
	  }
	  catch(Throwable t) 
	  {
		throw new RuntimeException("Cannot instantiate class for type: ",t);  
	  }
  } 
  
  public static ValueType getValueTypeByTypeId(Long id) 
  {
      if (typedefsById.containsKey(id)) 
      {
        ValueType vtd = (ValueType)typedefsById.get(id);
        return vtd;
      }
      else 
        return null;
  } 
  
  public static ValueType getValueTypeByClassName(String name)
  {
	  return (ValueType)typedefsByClassName.get(name);
  } 
  
  public static Map getValueTypeByTypeIdMap() 
  {
	return typedefsById;  
  }
}
