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
import java.math.BigDecimal;

import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.ValueType;
import de.berlios.gpon.common.types.ValueTypeValidationException;


public class DecimalNumber 
  extends AbstractValue
  implements Value 
{

  private static final String TRAILING_ZEROS_REGEXP =
    "([.][0]*$)|([0]*$)";

   static final ValueType DECIMALNUMBER_VTD =
    new ValueType("decimal",
                            DecimalNumber.class.getName(),
                            "decimal numbers",
                            true);

  String _data;

  public DecimalNumber()
  {
  }

  public void setDataInInputForm(String data)
  {
    _data = data;
  }

  public void setDataInNormalForm(String dataInNf)
  {
    _data = dataInNf;
  }

  public String getNormal()
  {
    return _data;
  }

  public ValueType getType()
  {
    return DECIMALNUMBER_VTD;
  }

  public void validate() throws ValueTypeValidationException
  {
    try 
    {
      getCoreValue();
    } 
    catch (Throwable t) 
    {
      throw new ValueTypeValidationException("Unable to convert to BigDecimal");
    }
  }

  public String getHtml()
  {
    return _data;
  }
  
  public String getInput() 
  {
    return _data;
  }
  
  public Object getCoreValue() 
  {
    if (_data!=null)  
      return new BigDecimal(_data);
    
    return null;  
  }

}
