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


package de.berlios.gpon.common.types.repository.test;
import java.util.List;

import junit.framework.TestCase;
import de.berlios.gpon.common.types.ValueType;
import de.berlios.gpon.common.types.repository.ValueTypeRepository;


public class ValueTypeRepositoryTest extends TestCase 
{
  public ValueTypeRepositoryTest()
  {
  }
  
  public void testGetAllTypes() 
  {
    List allTypes = ValueTypeRepository.getAllValueTypeDefinitions();
    
    ValueType[] array = (ValueType[])allTypes.toArray(new ValueType[0]);
    
    for (int i = 0; i < array.length; i++) {
      ValueType vt = array[i];
    
      System.out.println(i+": "+vt.getName());
    
    }
    
    
    System.out.println(allTypes);
  }
  
}
