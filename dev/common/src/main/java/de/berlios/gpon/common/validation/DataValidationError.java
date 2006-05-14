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


package de.berlios.gpon.common.validation;

public class DataValidationError 
{

  private int code;
  private Object[] details;

  public static final int MANDATORY_FIELD_ERROR = 1;
  public static final int FIELD_VALIDATION_ERROR = 2;
  public static final int VALUE_EXTRACTION_ERROR = 3;
  
  public static final int WRONG_ITEM_TYPE_ERROR = 4;

  /**
   * 
   */
  public DataValidationError(int pCode, Object[] pDetails)
  {
    code    = pCode;
    details = pDetails;
  }


  public int getCode()
  {
    return code;
  }

  public Object[] getDetails()
  {
    return details;
  }
}
