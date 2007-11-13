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

  private static Hashtable typedefsByName;
  private static Hashtable typedefsByClassName;
  
  static {
  
    typedefsByName      = new Hashtable();
    typedefsByClassName = new Hashtable();
    
    // String type
    typedefsByName.put(       Text.SHORTTEXT_VTD.getName(), 
                            Text.SHORTTEXT_VTD);
    typedefsByClassName.put(Text.SHORTTEXT_VTD.getClassName(), 
                            Text.SHORTTEXT_VTD);
                            
    // URL type
    typedefsByName.put(       URL.URL_VTD.getName(), 
                            URL.URL_VTD);                     
    typedefsByClassName.put(URL.URL_VTD.getClassName(), 
                            URL.URL_VTD);                            
                            
  
    // Decimal Number Type
    typedefsByName.put       (DecimalNumber.DECIMALNUMBER_VTD.getName(), 
                            DecimalNumber.DECIMALNUMBER_VTD);                     
    typedefsByClassName.put(DecimalNumber.DECIMALNUMBER_VTD.getClassName(), 
                            DecimalNumber.DECIMALNUMBER_VTD);                            
                       
    // Date Type
    typedefsByName.put       (DateType.DATETYPE_VTD.getName(), 
                            DateType.DATETYPE_VTD);                     
    typedefsByClassName.put(DateType.DATETYPE_VTD.getClassName(), 
                            DateType.DATETYPE_VTD);  
    
    // Boolean Type
    typedefsByName.put       (BooleanType.BOOLEANTYPE_VTD.getName(), 
    						BooleanType.BOOLEANTYPE_VTD);                     
    typedefsByClassName.put(BooleanType.BOOLEANTYPE_VTD.getClassName(), 
    						BooleanType.BOOLEANTYPE_VTD); 
   
  }

  public static List getAllValueTypeDefinitions() {
    return new ArrayList(typedefsByName.values());
  }  
  
  public static Value getValueTypeObjectByTypeName(String name) 
  {
	  try {
      if (typedefsByName.containsKey(name)) 
      {
        ValueType vtd = (ValueType)typedefsByName.get(name);
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
  
  public static ValueType getValueTypeByTypeName(String name) 
  {
      if (typedefsByName.containsKey(name)) 
      {
        ValueType vtd = (ValueType)typedefsByName.get(name);
        return vtd;
      }
      else 
        return null;
  } 
  
  public static ValueType getValueTypeByClassName(String name)
  {
	  return (ValueType)typedefsByClassName.get(name);
  } 
  
  public static Map getValueTypeByTypeNameMap() 
  {
	return typedefsByName;  
  }
}
