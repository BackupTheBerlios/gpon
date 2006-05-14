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


package de.berlios.gpon.common.util;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.types.repository.ValueTypeRepository;


public abstract class AbstractMappedItemType 
{
	
	  private ItemType itemType;
	
	  protected Hashtable propertyDeclMap = new Hashtable();

	  public AbstractMappedItemType()
	  {
	  }
	  
	  public int getSize()
	  {
	    // Number of Property Declarations
	    if (propertyDeclMap!=null)  
	    {
	      return propertyDeclMap.size();
	    }
	    else 
	    {
	      return 0;
	    }
	  }

	  public boolean hasItemPropertyDeclaration(String key)
	  {
	    boolean containsKey = (propertyDeclMap!=null)?propertyDeclMap.containsKey(key):false;
	    
	    return containsKey;  
	  }

	  public ItemPropertyDecl getItemPropertyDecl(String key) 
	  {
	    return (ItemPropertyDecl)propertyDeclMap.get(key);
	  }

	  public void removeItemPropertyDecl(String key)
	  {
		  if (propertyDeclMap.containsKey(key)) {
				ItemPropertyDecl pDecl = 
					(ItemPropertyDecl)propertyDeclMap.get(key);

				// remove from map
				propertyDeclMap.remove(key);
				// remove from item
				if (!itemType.getItemPropertyDecls().remove(pDecl)) 
				{
				 throw new IllegalArgumentException("Unable to remove property declaration "+key);
				}
			}
	  }
	  
		public void setItemPropertyDecl(String key, ItemPropertyDecl pPropDecl) {
			
			ItemPropertyDecl propDecl;
			
			// decl exists already
				if (key!=null && propertyDeclMap.containsKey(key)) {
					propDecl = 
						(ItemPropertyDecl)propertyDeclMap.get(key);
					
				} else {
					propDecl = new ItemPropertyDecl();
					propDecl.setItemType(this.itemType);
					// keep item and property map in sync
					
					// FIXME: We dont have a key
					// propertyDeclMap.put(key, propDecl);
					
					if (itemType.getItemPropertyDecls()==null) {
						itemType.setItemPropertyDecls(new HashSet());
					}
					
					itemType.getItemPropertyDecls().add(propDecl);
				}

				// assignment
				
				propDecl.setDescription(pPropDecl.getDescription());
				propDecl.setMandatory(pPropDecl.getMandatory());
				propDecl.setName(pPropDecl.getName());
				propDecl.setPropertyValueTypeId(pPropDecl.getPropertyValueTypeId());
				propDecl.setRank(pPropDecl.getRank());
				propDecl.setTypic(pPropDecl.getTypic());
		
		}
	  
	  
	  public Map getMap() {
		  return propertyDeclMap;
	  }

	  public ItemType getItemType() {
		  return itemType;
	  }
	  
	  protected void init(ItemType pItemType) 
	  {
		this.itemType = pItemType;  
		  
	    if (itemType==null)
	      throw new IllegalArgumentException("cannot initialize with null item type.");
	    
	    if (itemType.getInheritedItemPropertyDecls()!=null) {
	      Iterator it = itemType.getInheritedItemPropertyDecls().iterator();
	      while (it.hasNext()) {
	        ItemPropertyDecl ipd = (ItemPropertyDecl) it.next();
	        
	        Object hashKey = getHashKeyForItemPropertyDeclaration(ipd);
	        
	        propertyDeclMap.put(hashKey,ipd);
	      }
	    }
	  }
	  
	  // has to be implemented by subclasses 
	  protected abstract String getHashKeyForItemPropertyDeclaration(ItemPropertyDecl ipd);
}

