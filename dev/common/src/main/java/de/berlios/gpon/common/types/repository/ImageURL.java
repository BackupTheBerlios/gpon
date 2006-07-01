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
import de.berlios.gpon.common.types.ValueType;


public class ImageURL extends URL 
{

  public static final Long IMAGEURL_TYPE_ID = new Long(4); 
  
  static final ValueType IMAGEURL_VTD =
    new ValueType(IMAGEURL_TYPE_ID,
                  ImageURL.class.getName(),
                  "image urls",
                  false);



  public ImageURL()
  {
  }
  
  public String getHtml() {
    return "<img alt=\""+getInput()+"\" src=\""+getInput()+"\" class=\"GPON_IMAGE_STYLE\"/>";
  }
  
  public ValueType getType()
  {
    return IMAGEURL_VTD;
  }
  
  
}
