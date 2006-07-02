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
import java.net.MalformedURLException;

import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.ValueType;
import de.berlios.gpon.common.types.ValueTypeValidationException;
import de.berlios.gpon.common.types.ValueTypeValidationMessage;


public class URL 
  extends AbstractValue
  implements Value 
{

  public static final String MALFORMED_URL_MESSAGE_KEY = URL.class.getName()+"MALFORMED_URL_MESSAGE";

  public static final Long URL_TYPE_ID = new Long(2);

  static final ValueType URL_VTD =
    new ValueType(URL_TYPE_ID,
                            URL.class.getName(),
                            "urls",
                            "a",
                            false);

  private String _data;

  public URL()
  {
  }

  public ValueType getType()
  {
    return URL_VTD;
  }

  public void validate() throws ValueTypeValidationException
  {
    try {
      // simply instantiate to get url parsed
      new java.net.URL(_data);
    } catch (MalformedURLException ex) {
      ValueTypeValidationException vtve =
        new ValueTypeValidationException(ex);
      
      ValueTypeValidationMessage vtvm =
        new ValueTypeValidationMessage(MALFORMED_URL_MESSAGE_KEY,
                                       new Object[] { _data });
      
      vtve.setMessages(new ValueTypeValidationMessage[] { vtvm });
      
      throw vtve;
    }
  }

  public void setDataInNormalForm(String dataInNf)
  {
    _data = dataInNf;
  }
  
  public void setDataInInputForm(String dataInIf)
  {
    _data = dataInIf;
  }
  
  public String getInput()
  {
    return _data;
  }
  
  public String getHtml()
  {
    return "<a href=\""+_data+"\">"+_data+"</a>";
  }
  
  public String getNormal()
  {
    return _data;
  }
  
}
