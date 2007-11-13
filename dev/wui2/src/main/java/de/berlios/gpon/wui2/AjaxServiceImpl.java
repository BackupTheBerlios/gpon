package de.berlios.gpon.wui2;
      

import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemMappedById;
import de.berlios.gpon.common.util.ItemTypeMappedById;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.persistence.search.SimpleQuery;
import de.berlios.gpon.wui2.common.RemoteAssociation;
import de.berlios.gpon.wui2.common.RemoteAssociationType;
import de.berlios.gpon.wui2.common.RemoteItem;
import de.berlios.gpon.wui2.common.RemoteItemProperty;
import de.berlios.gpon.wui2.common.RemoteItemPropertyDecl;
import de.berlios.gpon.wui2.common.RemoteItemType;
import de.berlios.gpon.wui2.common.search.RemoteQuery;

public class AjaxServiceImpl 
implements AjaxService
{

	Log log = LogFactory.getLog(AjaxServiceImpl.class);
	
	GponDataDao  gponDataDao  = null;
	GponModelDao gponModelDao = null;
	ObjectConverter objectConverter = null;
	
	public ObjectConverter getObjectConverter() {
		return objectConverter;
	}

	public void setObjectConverter(ObjectConverter remoteObjectConverter) {
		this.objectConverter = remoteObjectConverter;
	}

	public RemoteItem[] searchItems(RemoteQuery query) {
		
		ItemType it = gponModelDao.findItemTypeById(query.getTypeId());
		SimpleQuery sq = new SimpleQuery();
		sq.setSpec(query.getSpec());
		sq.setType(it.getName());
		
		Set result = gponDataDao.search(sq);
		
		return getObjectConverter().convertItems(result);
	}

	public GponDataDao getGponDataDao() {
		return gponDataDao;
	}

	public void setGponDataDao(GponDataDao gponDataDao) {
		this.gponDataDao = gponDataDao;
	}

	public GponModelDao getGponModelDao() {
		return gponModelDao;
	}

	public void setGponModelDao(GponModelDao gponModelDao) {
		this.gponModelDao = gponModelDao;
	}

	public RemoteItemType getItemTypeById(Long id) {
		
		ItemType it = gponModelDao.findItemTypeById(id);
		
		return getObjectConverter().
		       convertItemType(it);
		
	}

	public RemoteItem getItemById(Long id) {
		Item item = gponDataDao.findItemById(id);
		return getObjectConverter().convertItem(item);
	}

	public RemoteItem updateItem(RemoteItem rItem) {
		Item item = getObjectConverter().convertRemoteItem(rItem);
		List assocList = getObjectConverter().convertRemoteAssociations(rItem.getAssociations());
		List propList  = new ArrayList(item.getProperties());
		
		Item dbItem = gponDataDao.findItemById(item.getId()); 
		
		ItemMappedById imbi = new ItemMappedById(dbItem);
		
		imbi.syncWith(new ItemMappedById(item));
		
		Item syncedItem = imbi.getItem();
		
		gponDataDao.updateItem(syncedItem,assocList);
		
		return getObjectConverter().convertItem(syncedItem);
		
	}

	public RemoteItem createItem(RemoteItem rItem) {
		
		Item item = getObjectConverter().convertRemoteItem(rItem);
		List assocList = getObjectConverter().convertRemoteAssociations(rItem.getAssociations());
		
		gponDataDao.addItem(item,assocList);
		
		return getObjectConverter().convertItem(item);
	}

	public void deleteItem(Long id) {
		gponDataDao.removeItem(id);		
	}

	public RemoteItemType createItemType(RemoteItemType type) {
		
		ItemType itemType = getObjectConverter().convertRemoteItemType(type);
		
		gponModelDao.addItemType(itemType);
		
		return getObjectConverter().convertItemType(itemType);
	}

	public RemoteItemType updateItemType(RemoteItemType type) {
		
		ItemType dbIt = gponModelDao.findItemTypeById(type.getId());
		
		ItemTypeMappedById mDbIt =
			  new ItemTypeMappedById(dbIt);
		
		ItemType guiIt = getObjectConverter().convertRemoteItemType(type,true);

		ItemTypeMappedById mGuiIt =
			  new ItemTypeMappedById(guiIt);
		
		// remove unused ipds
		String[] oldKeys = (String[])mDbIt.getMap().keySet().toArray(new String[0]);

		for (int i =  0; i < oldKeys.length; i++) 
		{
			if (!mGuiIt.hasItemPropertyDeclaration(oldKeys[i])) 
			{
				ItemPropertyDecl ipd =
					mDbIt.getItemPropertyDecl(oldKeys[i]);
				
				// only non inherited props are deleted (inherited props belong to super types)
				if (ipd.getItemType().getId().equals(type.getId())) 
				{
				  mDbIt.removeItemPropertyDecl(oldKeys[i]);
				}
			}			
		}
		
		// set ipd values for new and ipds still in
		if (guiIt.getItemPropertyDecls()!=null && guiIt.getItemPropertyDecls().size()>0) 
		{
			Iterator it = guiIt.getItemPropertyDecls().iterator();
			
			int newId = -1;
			
			while (it.hasNext()) 
			{
				ItemPropertyDecl ipd = (ItemPropertyDecl)it.next();
				
				if (ipd.getId()!=null && ipd.getId().longValue()>0L) 
				{
					mDbIt.setItemPropertyDecl(ipd.getId().toString(),ipd);
				}
				else 
				{
					// we need an id to map
					mDbIt.setItemPropertyDecl(new Long(newId).toString(),ipd);
					newId--;
				}
			}
		}
		
		// props finished
		
		// head attributes
		ItemType itemType = mDbIt.getItemType(); 
		itemType.setBaseType(guiIt.getBaseType());
		itemType.setDescription(guiIt.getDescription());
		itemType.setName(guiIt.getName());
		
		// persist
		gponModelDao.updateItemType(itemType);
		
		return getObjectConverter().convertItemType(itemType);
		
	}

	public void deleteItemType(Long id) {
		gponModelDao.deleteItemType(id);		
	}
	
	public RemoteItemType[] getAllItemTypes() {
		List itList = getGponModelDao().findAllItemTypes();
		
		return getObjectConverter().convertItemTypes(itList);
		
	}

	public RemoteAssociationType[] getAllAssociationTypes() {
		List atList = getGponModelDao().findAllAssociationTypes();
		
		return getObjectConverter().convertAssociationTypes(atList);
	}

	public RemoteAssociationType getAssociationTypeById(Long id) {
		AssociationType at = gponModelDao.findAssociationTypeById(id);
		
		return getObjectConverter().
		       convertAssociatonType(at);
	}

	public RemoteAssociationType createAssociationType(RemoteAssociationType type) {
		AssociationType at = getObjectConverter().convertRemoteAssociationType(type);
		
		gponModelDao.addAssociationType(at);
		
		return getObjectConverter().convertAssociatonType(at);
	}

	public RemoteAssociationType updateAssociationType(RemoteAssociationType type) {
        AssociationType at = getObjectConverter().convertRemoteAssociationType(type);
		
		gponModelDao.updateAssociationType(at);
		
		return getObjectConverter().convertAssociatonType(at);
	}

	public void deleteAssociationType(Long id) {
				
	}
	
	public RemoteItem[] searchItemsFulltext(Long typeId, String searchText) {
			
	ItemType it =
		gponModelDao.findItemTypeById(typeId);
	
	Set props = it.getInheritedItemPropertyDecls();
	
	// get typic properties and search for them
	
	Iterator iterator = 
		props.iterator();
	
	String query = "";
	
	while (iterator.hasNext()) 
	{
		ItemPropertyDecl ipd =
			(ItemPropertyDecl)iterator.next();
		
		if (ipd.getTypic().booleanValue()) 
		{
			if (query.length()>0) 
			{
				query = query + "||";
			}
			
			query = query + "${"+ipd.getName()+"}";
			
		}
	}
	
	query = "lower("+query + ") like lower('"+searchText+"%')";
	
	SimpleQuery sq = new SimpleQuery();
		
	sq.setType(it.getName());
	sq.setSpec(query);
			
	Set resultSet = gponDataDao.search(sq);
	
	return getObjectConverter().convertItems(resultSet);
	} 
	
}
