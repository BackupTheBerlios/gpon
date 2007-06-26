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


package de.berlios.gpon.persistence;

import java.util.List;

import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.ItemType;


public interface GponModelDao 
{
  // get all
  public List findAllItemTypes();  
  public List findAllAssociationTypes();  
  
  // find associations by id or name
  public AssociationType findAssociationTypeById(Long id);
  public AssociationType findAssociationTypeByName(String name);
  
  // find types by id or name
  public ItemType findItemTypeById(Long id);
  public ItemType findItemTypeByName(String name);
  
  // add, update, delete item type
  public void addItemType(ItemType itemType);  
  public void updateItemType(ItemType itemType);  
  public void deleteItemType(Long id);  
  
  // add, update, delete association type
  public void addAssociationType(AssociationType associationType);  
  public void updateAssociationType(AssociationType associationType);  
  public void deleteAssociationType(Long id);  
  
  // aux
  public void refresh(Object o);
  public void flush();
  
}
