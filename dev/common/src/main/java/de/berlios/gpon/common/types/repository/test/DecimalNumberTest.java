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
import junit.framework.TestCase;
import de.berlios.gpon.common.types.repository.DecimalNumber;


public class DecimalNumberTest extends TestCase 
{
  public DecimalNumberTest()
  {
  }
  
  public void testAll() {
  
    DecimalNumber dn = new DecimalNumber();
    
    dn.setDataInInputForm("-123.56");
    System.out.println(dn.getNormal());
    
    dn.setDataInInputForm("0.0");
    System.out.println(dn.getNormal());
    
    dn.setDataInInputForm("20");
    System.out.println(dn.getNormal());
    
    dn.setDataInInputForm("+10006565.000002323");
    System.out.println(dn.getNormal());

    
  }
  
}
