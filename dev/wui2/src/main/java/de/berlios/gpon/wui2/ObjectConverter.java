package de.berlios.gpon.wui2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Base;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemTypeMappedById;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.wui2.common.RemoteAssociation;
import de.berlios.gpon.wui2.common.RemoteAssociationType;
import de.berlios.gpon.wui2.common.RemoteItem;
import de.berlios.gpon.wui2.common.RemoteItemProperty;
import de.berlios.gpon.wui2.common.RemoteItemPropertyDecl;
import de.berlios.gpon.wui2.common.RemoteItemType;

public class ObjectConverter {

	GponModelDao gponModelDao = null;
	GponDataDao  gponDataDao  = null;
	
	
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

	public  RemoteItem[] convertItems(Collection items) 
	{
		if (items==null || items.size()==0) 
		{
			return null;
		}
		
		List list = new ArrayList();
		Iterator it = items.iterator();
		
		while (it.hasNext()) 
		{
			Item item = (Item)it.next();
			RemoteItem rItem = convertItem(item);
			list.add(rItem);
		}
		
		return (RemoteItem[])list.toArray(new RemoteItem[0]);	
	}

	public  RemoteItem convertItem(Item item) 
	{
		RemoteItem rItem = new RemoteItem();
		
		rItem.setId(item.getId());
		rItem.setTypeId(item.getItemType().getId());
		
		// union
		Set associations = new HashSet();
		if (item.getAssociationsA()!=null) { associations.addAll(item.getAssociationsA()); }
		if (item.getAssociationsB()!=null) { associations.addAll(item.getAssociationsB()); }
		rItem.setAssociations(convertAssociations(associations));
		rItem.setProperties(convertProperties(item.getPropertiesSorted()));
		
		return rItem;
	}

	public   RemoteItemProperty[] convertProperties(Collection properties) {
		if (properties==null || properties.size()==0) 
		{
			return null;
		}
		
		List list = new ArrayList();
		Iterator it = properties.iterator();
		
		while (it.hasNext()) 
		{
			ItemProperty prop = (ItemProperty)it.next();
			RemoteItemProperty rProp = convertProperty(prop);
			list.add(rProp);
		}
		
		return (RemoteItemProperty[])list.toArray(new RemoteItemProperty[0]);
	}

	public   RemoteItemProperty convertProperty(ItemProperty prop) {
		
		RemoteItemProperty rProp = new RemoteItemProperty();
		
		rProp.setId(prop.getId());
		rProp.setDeclId(prop.getPropertyDecl().getId());
		rProp.setValue(prop.getValue());
		
		return rProp;
	}

	public  RemoteAssociation[] convertAssociations(Collection associations) {
		if (associations==null || associations.size()==0) 
		{
			return null;
		}
		
		List list = new ArrayList();
		Iterator it = associations.iterator();
		
		while (it.hasNext()) 
		{
			Association assoc = (Association)it.next();
			RemoteAssociation rAssoc = convertAssociation(assoc);
			list.add(rAssoc);
		}
		
		return (RemoteAssociation[])list.toArray(new RemoteAssociation[0]);
	}

	public  RemoteAssociation convertAssociation(Association association) {
		RemoteAssociation rAssociation =
			new RemoteAssociation();
		rAssociation.setId(association.getId());
		rAssociation.setTypeId(association.getAssociationType().getId());
		rAssociation.setItemAId(association.getItemA().getId());
		rAssociation.setItemBId(association.getItemB().getId());
		return rAssociation;
	}

	public   RemoteItemType convertItemType(ItemType itemType) 
	{
		RemoteItemType rItemType =
			new RemoteItemType();
		
		rItemType.setId(itemType.getId());
		if (itemType.getBaseType()!=null) 
		{
			rItemType.setBaseTypeId(itemType.getBaseType().getId());	
		}
		if (itemType.getBaseTypes()!=null) 
		{
			rItemType.setSuperTypeIds(convertToIdList(itemType.getBaseTypes()));
		}
		rItemType.setName(itemType.getName());
		rItemType.setDescription(itemType.getDescription());
		rItemType.setItemPropertyDecls(convertPropertyDecls(
				itemType.getInheritedItemPropertyDecls())
		);
		
		Set allAssociations = new HashSet();
		if (itemType.getInheritedAssociationTypesA()!=null) {
			allAssociations.addAll(itemType.getInheritedAssociationTypesA());
		}
		if (itemType.getInheritedAssociationTypesB()!=null) {
			allAssociations.addAll(itemType.getInheritedAssociationTypesB());
		}
		rItemType.setAssociationTypes(convertAssociationTypes(allAssociations));
		
		return rItemType;
	}

	public  RemoteAssociationType convertAssociatonType(AssociationType associationType) 
	{
		RemoteAssociationType rAsscociationType
		 = new RemoteAssociationType();
		
		// copyBeanProperties {
		rAsscociationType.setDescription(associationType.getDescription());
		rAsscociationType.setId(associationType.getId());
		rAsscociationType.setItemARoleName(associationType.getItemARoleName());
		rAsscociationType.setItemBRoleName(associationType.getItemBRoleName());
		rAsscociationType.setItemATypeId(associationType.getItemAType().getId());
		rAsscociationType.setItemBTypeId(associationType.getItemBType().getId());
		rAsscociationType.setMultiplicity(associationType.getMultiplicity());
		rAsscociationType.setName(associationType.getName());
		rAsscociationType.setVisibility(associationType.getVisibility());
		// }
		
		return rAsscociationType;
	}

	public  RemoteAssociationType[] convertAssociationTypes(Collection associationTypes) {
		if (associationTypes==null || associationTypes.size()==0) 
		{
			return null;
		}
		
		List list = new ArrayList();
		Iterator it = associationTypes.iterator();
		
		while (it.hasNext()) 
		{
			AssociationType assoct = (AssociationType)it.next();
			RemoteAssociationType rAssocType = convertAssociatonType(assoct);
			list.add(rAssocType);
		}
		
		return (RemoteAssociationType[])list.toArray(new RemoteAssociationType[0]);
	}

	public  RemoteItemPropertyDecl convertPropertyDecl(ItemPropertyDecl propertyDecl) 
	{
		RemoteItemPropertyDecl rPropDecl =
			new RemoteItemPropertyDecl();
		
		rPropDecl.setDescription(propertyDecl.getDescription());
		rPropDecl.setId(propertyDecl.getId());
		rPropDecl.setMandatory(propertyDecl.getMandatory().booleanValue());
		rPropDecl.setTypic(propertyDecl.getTypic().booleanValue());
		rPropDecl.setName(propertyDecl.getName());
		rPropDecl.setTypeId(propertyDecl.getItemType().getId());
		rPropDecl.setValueType(propertyDecl.getValueType());
		rPropDecl.setDerivedType(propertyDecl.getDerivedValueType());
		rPropDecl.setValueTypeProperties(propertyDecl.getValueTypeProperties());
		
		return rPropDecl;
	}

	public  RemoteItemPropertyDecl[] convertPropertyDecls(Collection propDecls) 
	{
		if (propDecls==null || propDecls.size()==0) 
		{
			return null;
		}
		
		List list = new ArrayList();
		Iterator it = propDecls.iterator();
		
		while (it.hasNext()) 
		{
			ItemPropertyDecl propDecl = (ItemPropertyDecl)it.next();
			RemoteItemPropertyDecl rPropDecl = convertPropertyDecl(propDecl);
			list.add(rPropDecl);
		}
		
		return (RemoteItemPropertyDecl[])
		         list.toArray(new RemoteItemPropertyDecl[0]);
	}

	public  RemoteItemType[] convertItemTypes(Collection itemTypes) {
		if (itemTypes==null || itemTypes.size()==0) 
		{
			return null;
		}
		
		List list = new ArrayList();
		Iterator it = itemTypes.iterator();
		
		while (it.hasNext()) 
		{
			ItemType itemt = (ItemType)it.next();
			RemoteItemType rItemType = convertItemType(itemt);
			list.add(rItemType);
		}
		
		return (RemoteItemType[])list.toArray(new RemoteItemType[0]);
	}

	public  Long[] convertToIdList(Collection baseObjects) 
	{
		if (baseObjects==null || baseObjects.size()==0) 
		{
			return null;
		}
		
		List list = new ArrayList();
		Iterator it = baseObjects.iterator();
		
		while (it.hasNext()) 
		{
			Base baseObject = (Base)it.next();
			list.add(baseObject.getId());
		}
		
		return (Long[])
		         list.toArray(new Long[0]);
	}

	public ItemType convertRemoteItemType(RemoteItemType type) 
	{
		return convertRemoteItemType(type,false);
	}

	public ItemType convertRemoteItemType(RemoteItemType type, boolean fakeIpdIds) {
		
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
			ipd.setValueType(rPropDecl.getValueType());
			ipd.setDerivedValueType(rPropDecl.getDerivedType());
			ipd.setValueTypeProperties(rPropDecl.getValueTypeProperties());
			ipd.setItemType(itemType);
			propDecls.add(ipd);
		}
		itemType.setItemPropertyDecls(propDecls);
		}
		
		return itemType;
	}

	public Long znnvl(Long value, Long onZeroNegativeOrNull) 
	{
		if (value == null || value.longValue()<=0) 
		{
			return onZeroNegativeOrNull;
		}
		
		return value;
	}

	public AssociationType convertRemoteAssociationType(RemoteAssociationType rAt) 
	{
		AssociationType at = new AssociationType();
		
		BeanUtils.copyProperties(rAt,at);
		
		at.setItemAType(gponModelDao.findItemTypeById(rAt.getItemATypeId()));
		at.setItemBType(gponModelDao.findItemTypeById(rAt.getItemBTypeId()));
		
		return at;
	}

	public Item convertRemoteItem(RemoteItem rItem) 
	{
		Item item = new Item();
		ItemType itemType = gponModelDao.findItemTypeById(rItem.getTypeId());
		
		item.setId(rItem.getId());
		item.setItemType(itemType);
		item.setProperties(convertRemoteProperties(rItem.getProperties(),itemType));
		
		return item;
	}

	public Association convertRemoteAssociation(RemoteAssociation rAssoc) 
	{
		Association assoc = new Association();
		AssociationType assocT = gponModelDao.findAssociationTypeById(rAssoc.getTypeId());
		
		assoc.setId(rAssoc.getId());
		assoc.setAssociationType(assocT);
		
		Item itemA = null;
		Item itemB = null;
		
		if (rAssoc.getItemAId()!=null) {
			itemA = gponDataDao.findItemById(rAssoc.getItemAId());
		}
		if (rAssoc.getItemBId()!=null) {
			itemB = gponDataDao.findItemById(rAssoc.getItemBId());
		}
		
		assoc.setItemA(itemA);
		assoc.setItemB(itemB);
		
		return assoc;
	}

	public List convertRemoteAssociations(RemoteAssociation[] rAssoces) 
	{
		if (rAssoces==null || rAssoces.length==0) 
		{
			return null;
		}		
		
		List list = new ArrayList();
		
		for (int i = 0; i < rAssoces.length; i++) 
		{
			RemoteAssociation rAssoc = rAssoces[i];
			list.add(convertRemoteAssociation(rAssoc));
		}
		
		return list;
	}

	public Set convertRemoteProperties(RemoteItemProperty[] properties, ItemType type) {
		
		Set set = new HashSet();
		
		if (properties != null) 
		{
			for (int i=0; i < properties.length; i++) 
			{
				RemoteItemProperty prop = properties[i];
				set.add(convertRemoteProperty(prop, type));
			}
		}
		
		return set;
	}

	public ItemProperty convertRemoteProperty(RemoteItemProperty rProp, ItemType type) {
		ItemProperty prop = new ItemProperty();
		ItemTypeMappedById mappedType =
			new ItemTypeMappedById(type);
		
		
		prop.setId(rProp.getId());
		prop.setPropertyDecl(mappedType.getItemPropertyDecl(rProp.getDeclId()+""));
		prop.setValue(rProp.getValue());
		
		return prop;
	}

}
