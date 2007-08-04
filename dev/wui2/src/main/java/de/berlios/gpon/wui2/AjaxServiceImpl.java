package de.berlios.gpon.wui2;
      

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemTypeMappedById;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.persistence.search.SimpleQuery;
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
	
	public RemoteItem[] searchItems(RemoteQuery query) {
		
		ItemType it = gponModelDao.findItemTypeById(query.getTypeId());
		SimpleQuery sq = new SimpleQuery();
		sq.setSpec(query.getSpec());
		sq.setType(it.getName());
		
		Set result = gponDataDao.search(sq);
		
		return RemoteObjectConverter.convertItems(result);
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
		
		return RemoteObjectConverter.
		       convertItemType(it);
		
	}

	public RemoteItem getItemById(Long id) {
		Item item = gponDataDao.findItemById(id);
		return RemoteObjectConverter.convertItem(item);
	}

	public RemoteItem updateItem(RemoteItem rItem) {
		Item item = convertToItem(rItem);
		
		gponDataDao.updateItem(item);
		
		return RemoteObjectConverter.convertItem(item);
		
	}

	public RemoteItem createItem(RemoteItem rItem) {
		
		Item item = convertToItem(rItem);
		
		gponDataDao.addItem(item);
		
		return RemoteObjectConverter.convertItem(item);
	}

	public void deleteItem(Long id) {
		gponDataDao.removeItem(id);		
	}

	public RemoteItemType createItemType(RemoteItemType type) {
		
		ItemType itemType = convertToItemType(type);
		
		gponModelDao.addItemType(itemType);
		
		return RemoteObjectConverter.convertItemType(itemType);
	}

	public RemoteItemType updateItemType(RemoteItemType type) {
		
		ItemType dbIt = gponModelDao.findItemTypeById(type.getId());
		
		ItemTypeMappedById mDbIt =
			  new ItemTypeMappedById(dbIt);
		
		ItemType guiIt = convertToItemType(type,true);

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
		
		return RemoteObjectConverter.convertItemType(itemType);
		
	}

	public void deleteItemType(Long id) {
		gponModelDao.deleteItemType(id);		
	}
	
	private ItemType convertToItemType(RemoteItemType type) 
	{
		return convertToItemType(type,false);
	}

		
		private ItemType convertToItemType(RemoteItemType type, boolean fakeIpdIds) {
		
		ItemType itemType = new ItemType();
		
		itemType.setId(type.getId());
		
		if (type.getBaseTypeId()!=null && type.getBaseTypeId().longValue() > 0L) 
		{
			itemType.setBaseType(
			  gponModelDao.findItemTypeById(type.getBaseTypeId())	
			);
		}
		
		itemType.setDescription(type.getDescription());
		itemType.setName(type.getName());
		
		if (type.getItemPropertyDecls()!=null) {
		
		Set propDecls = new HashSet();
		
		int id = -1;
		
		for (int i = 0; i < type.getItemPropertyDecls().length; i++) 
		{
			RemoteItemPropertyDecl rPropDecl =
				type.getItemPropertyDecls()[i];
			
			ItemPropertyDecl ipd =
				new ItemPropertyDecl();
			ipd.setId(znnvl(rPropDecl.getId(), fakeIpdIds?(new Long(id--)):null));
			ipd.setDescription(rPropDecl.getDescription());
			ipd.setMandatory(new Boolean(rPropDecl.isMandatory()));
			ipd.setTypic(new Boolean(rPropDecl.isTypic()));
			ipd.setName(rPropDecl.getName());
			ipd.setPropertyValueTypeId(rPropDecl.getValueTypeId());
			ipd.setItemType(itemType);
			propDecls.add(ipd);
		}
		itemType.setItemPropertyDecls(propDecls);
		}
		
		return itemType;
	}
	
	private Long znnvl(Long value, Long onZeroNegativeOrNull) 
	{
		if (value == null || value.longValue()<=0) 
		{
			return onZeroNegativeOrNull;
		}
		
		return value;
	}

	public RemoteItemType[] getAllItemTypes() {
		List itList = getGponModelDao().findAllItemTypes();
		
		return RemoteObjectConverter.convertItemTypes(itList);
		
	}

	public RemoteAssociationType[] getAllAssociationTypes() {
		List atList = getGponModelDao().findAllAssociationTypes();
		
		return RemoteObjectConverter.convertAssociationTypes(atList);
	}

	public RemoteAssociationType getAssociationTypeById(Long id) {
		AssociationType at = gponModelDao.findAssociationTypeById(id);
		
		return RemoteObjectConverter.
		       convertAssociatonType(at);
	}

	public RemoteAssociationType createAssociationType(RemoteAssociationType type) {
		AssociationType at = convertToAssociationType(type);
		
		gponModelDao.addAssociationType(at);
		
		return RemoteObjectConverter.convertAssociatonType(at);
	}

	public RemoteAssociationType updateAssociationType(RemoteAssociationType type) {
        AssociationType at = convertToAssociationType(type);
		
		gponModelDao.updateAssociationType(at);
		
		return RemoteObjectConverter.convertAssociatonType(at);
	}

	public void deleteAssociationType(Long id) {
				
	}
	
	private AssociationType convertToAssociationType(RemoteAssociationType rAt) 
	{
		AssociationType at = new AssociationType();
		
		BeanUtils.copyProperties(rAt,at);
		
		at.setItemAType(gponModelDao.findItemTypeById(rAt.getItemATypeId()));
		at.setItemBType(gponModelDao.findItemTypeById(rAt.getItemBTypeId()));
		
		return at;
	}
	
	private Item convertToItem(RemoteItem rItem) 
    {
		Item item = new Item();
		ItemType itemType = gponModelDao.findItemTypeById(rItem.getTypeId());
		
		item.setId(rItem.getId());
		item.setItemType(itemType);
		item.setProperties(convertToProperties(rItem.getProperties(),itemType));
		
		return item;
    }

	private Set convertToProperties(RemoteItemProperty[] properties, ItemType type) {
		
		Set set = new HashSet();
		
		if (properties != null) 
		{
			for (int i=0; i < properties.length; i++) 
			{
				RemoteItemProperty prop = properties[i];
				set.add(convertToProperty(prop, type));
			}
		}
		
		return set;
	}

	private ItemProperty convertToProperty(RemoteItemProperty rProp, ItemType type) {
		ItemProperty prop = new ItemProperty();
		ItemTypeMappedById mappedType =
			new ItemTypeMappedById(type);
		
		
		prop.setId(rProp.getId());
		prop.setPropertyDecl(mappedType.getItemPropertyDecl(rProp.getDeclId()+""));
		prop.setValue(rProp.getValue());
		
		return prop;
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
	
	return RemoteObjectConverter.convertItems(resultSet);
	} 
	
}
