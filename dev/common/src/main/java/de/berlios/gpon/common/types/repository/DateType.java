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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.ValueType;
import de.berlios.gpon.common.types.ValueTypeValidationException;


public class DateType 
  extends AbstractValue
  implements Value 
{

  public static final SimpleDateFormat DATE_NF_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

  // TODO: format should be a  system variable
  public static final SimpleDateFormat DATE_INPUT_FORMAT = 
    new SimpleDateFormat("d.M.y HH:mm:ss");
  
  // TODO: format should be a  system variable
  public static final SimpleDateFormat DATE_INPUT_FORMAT_2 = 
    new SimpleDateFormat("d.M.y");
  
  static final ValueType DATETYPE_VTD =
    new ValueType("date",
                            DateType.class.getName(),
                            "date/time",                    
                            false
                            );
  Date   _core=null;
  String _data=null;

  public void setDataInInputForm(String data)
  {
    _data = data;
    _core = null;
  }

  public void setDataInNormalForm(String dataInNf)
  {
    try {
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
    
      _core = sdf1.parse(dataInNf);
      _data = null;
    }
    catch (ParseException ex) {
      throw new IllegalArgumentException("Cannot parse nf to date.");   
    }
  }

  public String getNormal()
  {
    computeCore();
    
    if (_core == null)
      return null;
    
    return DATE_NF_FORMAT.format(_core);    
  }

  public ValueType getType()
  {
    return DATETYPE_VTD;
  }

  public void validate() throws ValueTypeValidationException
  {
    try {
      computeCore();
    } catch (Exception ex) {
      throw new ValueTypeValidationException(ex);
    }
  
  }

  public String getHtml()
  {
    return getInput();
  }
  
  public String getInput() 
  {
    computeData();
    return _data;
  }
  
  public Object getCoreValue() 
  {
    computeCore();
    return _core;
  }
  
  private void computeData() {
    if (_data==null && _core!=null) {
        
        _data = DATE_INPUT_FORMAT.format(_core);
    }
  }
  
  private void computeCore() 
  {
  
    if (_data==null || _data.trim().length()==0)
      return;
  
    String expectedFormat = "";
    String expectedFormat2 = "";
  
    try {
      if (_core==null && _data!=null) 
      {
        System.out.println("sdf pattern: "+DATE_INPUT_FORMAT.toPattern());
      
        expectedFormat = DATE_INPUT_FORMAT.toPattern();
        expectedFormat2 = DATE_INPUT_FORMAT_2.toPattern();
        
        try {
          // timestamp
          _core = DATE_INPUT_FORMAT.parse(_data);
        }
        catch (ParseException e) 
        {
          // date
          _core = DATE_INPUT_FORMAT_2.parse(_data);
        }
      
      } 
    }
    catch (ParseException ex) {
      ex.printStackTrace();
      throw new IllegalArgumentException("Wrong date format in "+_data+" (Pattern: "+expectedFormat+" or "+expectedFormat2+")");
    }
  }
  

}
