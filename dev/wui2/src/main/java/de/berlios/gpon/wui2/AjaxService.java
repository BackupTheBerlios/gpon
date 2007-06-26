package de.berlios.gpon.wui2;

import de.berlios.gpon.wui2.common.RemoteAssociationType;
import de.berlios.gpon.wui2.common.RemoteItem;
import de.berlios.gpon.wui2.common.RemoteItemType;
import de.berlios.gpon.wui2.common.search.RemoteQuery;

public interface AjaxService {

	// search, create, update, delete items
	public RemoteItem[] searchItems(RemoteQuery query);
	public RemoteItem getItemById(Long id);
	public RemoteItem createItem(RemoteItem item);
	public void updateItem(RemoteItem item);
	public void deleteItem(Long id);
	
	// search, create, update, delete types
	public RemoteItemType[] getAllItemTypes();
	public RemoteItemType getItemTypeById(Long id);
	public RemoteItemType createItemType(RemoteItemType type);
	public RemoteItemType updateItemType(RemoteItemType type);
	public void deleteItemType(Long id);
	
    // search, create, update, delete types
	public RemoteAssociationType[] getAllAssociationTypes();
	public RemoteAssociationType getAssociationTypeById(Long id);
	public RemoteAssociationType createAssociationType(RemoteAssociationType type);
	public RemoteAssociationType updateAssociationType(RemoteAssociationType type);
	public void deleteAssociationType(Long id);
	
	
}
