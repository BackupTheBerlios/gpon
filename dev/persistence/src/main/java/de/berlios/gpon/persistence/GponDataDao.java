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
import java.util.Set;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.persistence.search.SimpleQuery;


public interface GponDataDao 
{
  public Item findItemById(Long id);
  public List findItemsByType(Long typeId);
  public List findAssociationsByType(Long typeId);

  // Item CUD
  public void addItem(Item item);
  public void addItem(Item item, List associationList);
  public void updateItem(Item item);
  public void updateItem(Item item, List associationList);
  
  public void removeItem(Long id);

  // Association CD
  public void addAssociation(Association assoc);
  public void removeAssociation(Association assoc);

  public Set search(SimpleQuery q);
  
  // aux
  public void refresh(Object o);
  
}
