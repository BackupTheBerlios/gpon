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

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ItemType 
	extends Base
{

	private static Log log = LogFactory.getLog(ItemType.class);

	String name;

	String description;

	ItemType baseType;

	// dependent objects
	Set itemPropertyDecls;

	// next generation types
	Set specializedTypes;

	// associatons we are A
	Set associationTypesA;

	// associatons we are B
	Set associationTypesB;

	// Aux
	boolean inheritedSetValid = false;

	Set inherited = null;

	public String toString() {
		String s = "id=" + id + " name=" + name + " description=" + description;
		return s;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBaseType(ItemType baseType) {
		this.baseType = baseType;
	}

	public ItemType getBaseType() {
		return baseType;
	}

	public void setItemPropertyDecls(Set itemPropertyDecls) {
		this.itemPropertyDecls = itemPropertyDecls;
	}

	public Set getItemPropertyDecls() {
		return itemPropertyDecls;
	}

	public Set getInheritedItemPropertyDecls() {

		if (inheritedSetValid)
			return inherited;

		Set treeSet = new TreeSet(new RankComparator());

		ItemType baseType = this;

		while (baseType != null) {
			treeSet.addAll(baseType.getItemPropertyDecls());
			baseType = baseType.getBaseType();
		}

		// inheritedSetValid = true;
		inherited = treeSet;

		return treeSet;
	}

	public Set getBaseTypes() {
		Set set = new HashSet();

		ItemType baseType = this;

		while (baseType != null) {
			set.add(baseType);
			baseType = baseType.getBaseType();
		}

		return set;
	}

	public Set getSpecializedTypes() {
		return specializedTypes;
	}

	public void setSpecializedTypes(Set specializedTypes) {
		this.specializedTypes = specializedTypes;
	}

	public Set getSpecializedTypesDeep() {

		HashSet nestedSpezialized = new HashSet();

		if (getSpecializedTypes() != null && (!getSpecializedTypes().isEmpty())) {
			Iterator specializedIt = getSpecializedTypes().iterator();

			while (specializedIt.hasNext()) {
				ItemType it = (ItemType) specializedIt.next();
				nestedSpezialized.addAll(it.getSpecializedTypesDeep());
			}
		}

		nestedSpezialized.add(this);

		return nestedSpezialized;
	}

	public class RankComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			ItemPropertyDecl ipd1 = (ItemPropertyDecl) o1;
			ItemPropertyDecl ipd2 = (ItemPropertyDecl) o2;

			// if rank is not set
			int id1 = (ipd1.getId()!=null)?ipd1.getId().intValue():0;
			int id2 = (ipd2.getId()!=null)?ipd2.getId().intValue():0;

			int r1 = (ipd1.getRank() != null) ? ipd1.getRank().intValue() : 0;
			int r2 = (ipd2.getRank() != null) ? ipd2.getRank().intValue() : 0;

			// Object equality
			if (id1 == id2) {
				log.debug("RankComparator: object equality with id: " + id1);
				return 0;
			}

			// oldest first if rank is the same
			if (r1 == r2) {
				log.debug("RankComparator: [by age] id1-id2: " + id1 + " - "
						+ id2 + " = " + (id1 - id2));
				return id1 - id2;
			}

			log.debug("RankComparator: [by rank] r1-r2: " + r1 + " - " + r2
					+ " = " + (r1 - r2));
			return r1 - r2;

		}
	}

	public Set getAssociationTypesA() {
		return associationTypesA;
	}

	public void setAssociationTypesA(Set associationTypesA) {
		this.associationTypesA = associationTypesA;
	}

	public Set getAssociationTypesB() {
		return associationTypesB;
	}

	public void setAssociationTypesB(Set associationTypesB) {
		this.associationTypesB = associationTypesB;
	}

	private Set inheritedAssociationTypesA = null;
	
	public Set getInheritedAssociationTypesA() {
		if (inheritedAssociationTypesA!=null) 
		{
			return inheritedAssociationTypesA;
		}
	
		
		Set set = new HashSet();

		ItemType baseType = this;

		while (baseType != null) {
			set.addAll(baseType.getAssociationTypesA());
			baseType = baseType.getBaseType();
		}

		inheritedAssociationTypesA = set;
		
		return set;
	}

	private Set inheritedAssociationTypesB = null;
	
	public Set getInheritedAssociationTypesB() {
		
		if (inheritedAssociationTypesB!=null) 
		{
			return inheritedAssociationTypesB;
		}
		
		Set set = new HashSet();

		ItemType baseType = this;

		while (baseType != null) {
			set.addAll(baseType.getAssociationTypesB());
			baseType = baseType.getBaseType();
		}

		inheritedAssociationTypesB = set;
		
		return set;
	}

	public boolean equals(Object arg0) {

		if (arg0 instanceof ItemType) {
			ItemType type = (ItemType) arg0;

			if (type.getId() != null) {
				return type.getId().equals(getId());
			}
		}

		return false;
	}

	public int hashCode() {

		if (getId() != null) {
			return getId().intValue();
		}

		return super.hashCode();
	}

	public boolean isAssociated() {
		return (getInheritedAssociationTypesA() != null && !getInheritedAssociationTypesA()
				.isEmpty())
				|| (getInheritedAssociationTypesB() != null && !getInheritedAssociationTypesB()
						.isEmpty());
	}

}
