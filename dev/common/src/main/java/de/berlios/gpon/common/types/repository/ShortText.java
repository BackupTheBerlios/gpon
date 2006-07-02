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
import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.ValueType;
import de.berlios.gpon.common.types.ValueTypeValidationException;


public class ShortText 
  extends AbstractValue
  implements Value 
{

  public static final Long SHORTTEXT_TYPE_ID = new Long(1);
  
  private String _data;
  
  static final ValueType SHORTTEXT_VTD =
    new ValueType(SHORTTEXT_TYPE_ID,
                            de.berlios.gpon.common.types.repository.ShortText.class.getName(),
                            "short text",
                            "",
                            false);
  
  public void setDataInInputForm(String data)
  {
    _data = data;
  }

  public ValueType getType()
  {
    return SHORTTEXT_VTD;
  }

  public void validate() throws ValueTypeValidationException
  {
    if (false)
      throw new ValueTypeValidationException();
  }
  
  public void setDataInNormalForm(String dataInNf) {
    // Application format == DB format
    setDataInInputForm(dataInNf);
  }
  
  public String getNormal() 
  {
    // Application format == DB format
    return _data;
  }
  
  public String getHtml() 
  {
    return _data;
  }

  public String getInput() 
  {
    return _data;
  }

}
