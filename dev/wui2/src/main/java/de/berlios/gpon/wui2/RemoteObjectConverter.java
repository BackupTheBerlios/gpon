package de.berlios.gpon.wui2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Base;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.wui2.common.RemoteAssociation;
import de.berlios.gpon.wui2.common.RemoteAssociationType;
import de.berlios.gpon.wui2.common.RemoteItem;
import de.berlios.gpon.wui2.common.RemoteItemProperty;
import de.berlios.gpon.wui2.common.RemoteItemPropertyDecl;
import de.berlios.gpon.wui2.common.RemoteItemType;

public class RemoteObjectConverter {

	public static RemoteItem[] convertItems(Collection items) 
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

	public static RemoteItem convertItem(Item item) 
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

	public static  RemoteItemProperty[] convertProperties(Collection properties) {
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

	public static  RemoteItemProperty convertProperty(ItemProperty prop) {
		
		RemoteItemProperty rProp = new RemoteItemProperty();
		
		rProp.setId(prop.getId());
		rProp.setDeclId(prop.getPropertyDecl().getId());
		rProp.setValue(prop.getValue());
		
		return rProp;
	}

	public static RemoteAssociation[] convertAssociations(Collection associations) {
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

	public static RemoteAssociation convertAssociation(Association association) {
		RemoteAssociation rAssociation =
			new RemoteAssociation();
		rAssociation.setId(association.getId());
		rAssociation.setTypeId(association.getAssociationType().getId());
		rAssociation.setItemAId(association.getItemA().getId());
		rAssociation.setItemBId(association.getItemB().getId());
		return rAssociation;
	}

	public static  RemoteItemType convertItemType(ItemType itemType) 
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

	public static RemoteAssociationType convertAssociatonType(AssociationType associationType) 
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

	public static RemoteAssociationType[] convertAssociationTypes(Collection associationTypes) {
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

	public static RemoteItemPropertyDecl convertPropertyDecl(ItemPropertyDecl propertyDecl) 
	{
		RemoteItemPropertyDecl rPropDecl =
			new RemoteItemPropertyDecl();
		
		rPropDecl.setDescription(propertyDecl.getDescription());
		rPropDecl.setId(propertyDecl.getId());
		rPropDecl.setMandatory(propertyDecl.getMandatory().booleanValue());
		rPropDecl.setTypic(propertyDecl.getTypic().booleanValue());
		rPropDecl.setName(propertyDecl.getName());
		rPropDecl.setTypeId(propertyDecl.getItemType().getId());
		rPropDecl.setValueTypeId(propertyDecl.getPropertyValueTypeId());
		
		return rPropDecl;
	}

	public static RemoteItemPropertyDecl[] convertPropertyDecls(Collection propDecls) 
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

	public static RemoteItemType[] convertItemTypes(Collection itemTypes) {
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

	public static Long[] convertToIdList(Collection baseObjects) 
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

}
