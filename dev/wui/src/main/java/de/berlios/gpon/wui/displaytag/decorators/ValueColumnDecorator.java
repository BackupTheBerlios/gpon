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


package de.berlios.gpon.wui.displaytag.decorators;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.displaytag.decorator.ColumnDecorator;

import de.berlios.gpon.common.types.Value;

public class ValueColumnDecorator 
  implements ColumnDecorator
{
    private static Log log = LogFactory.getLog(ValueColumnDecorator.class);

    /**
     * transform the given object into a String representation.
     * @param columnValue Object
     * @return String
     */
    public final String decorate(Object columnValue)
    {
        if (columnValue!=null) {
          try {
            Value value = (Value)columnValue;
            
            return value.getHtml();
            
          } catch (Exception ex) {
            log.error("Unable to decorate value object correctly.");
            return columnValue.toString();
          }
        }
        else 
        {
          return "";
        }
    }
}
