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


package de.berlios.gpon.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class Util 
{
  public Util()
  {
  }
  
  public static String beanToString(Object bean, List dontDisplay) 
  {
    StringBuffer out = new StringBuffer("");
  
    if (bean!=null) 
    {
      PropertyDescriptor[] propDescs =
        BeanUtils.getPropertyDescriptors(bean.getClass());
      
  
      
      if (propDescs!=null) 
      {
        int displayed = 0;
      
        for (int i = 0;i < propDescs.length; i++) 
        {    

          try
          {
          
            if (dontDisplay==null ||
                !dontDisplay.contains(propDescs[i].getName())) 
            {
              if (displayed>0) 
              {
                out.append(", ");
              }
              
              Object o = propDescs[i].getReadMethod().invoke(bean,null);
              
              out.append(propDescs[i].getName()).append(": '").append((o!=null)?o.toString():"").append("'\n");  
              
              displayed++;
            }          
          }
          catch (InvocationTargetException e)
          {
            throw new UndeclaredThrowableException(e);          
          }
          catch (IllegalAccessException e)
          {
            throw new UndeclaredThrowableException(e);
          }
        }
      }
    }
    return out.toString();
  }
}
