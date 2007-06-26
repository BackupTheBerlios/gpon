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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.jsp.tagext.IterationTag;

import de.berlios.gpon.common.ItemType.RankComparator;

public class Item 
extends Base
{

	public class RankComparator implements Comparator {
		public int compare(Object o1, Object o2) {

			ItemProperty ip1 = (ItemProperty) o1;
			ItemProperty ip2 = (ItemProperty) o2;

			ItemPropertyDecl ipd1 = ip1.getPropertyDecl();
			ItemPropertyDecl ipd2 = ip2.getPropertyDecl();

			// if rank is not set
			int id1 = ipd1.getId().intValue();
			int id2 = ipd2.getId().intValue();

			int r1 = (ipd1.getRank() != null) ? ipd1.getRank().intValue() : 0;
			int r2 = (ipd2.getRank() != null) ? ipd2.getRank().intValue() : 0;

			// Object equality
			if (id1 == id2) {
				return 0;
			}

			// oldest first if rank is the same
			if (r1 == r2) {
				return id1 - id2;
			}

			return r1 - r2;
		}
	}

	ItemType itemType;

	Set properties;

	Set associationsA;

	Set associationsB;

	public String toString() {
		String s = "id=" + id + " itemType=" + itemType;
		return s;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setProperties(Set properties) {
		this.properties = properties;
	}

	public Set getProperties() {
		return properties;
	}

	public Set getPropertiesSorted() {
		Set treeSet = new TreeSet(new RankComparator());
		treeSet.addAll(getProperties());
		return treeSet;
	}

	public void setAssociationsA(Set associationsA) {
		this.associationsA = associationsA;
	}

	public Set getAssociationsA() {
		return associationsA;
	}

	public void setAssociationsB(Set associationsB) {
		this.associationsB = associationsB;
	}

	public Set getAssociationsB() {
		return associationsB;
	}

	public int hashCode() {
		if (getId() != null) {
			return getId().intValue();
		} else {
			return super.hashCode();
		}
	}

	public boolean equals(Object o) {

		if (!(o instanceof Item))
			return false;

		Item item = (Item) o;

		System.out.println("Item equals: " + getId() + " = " + item.getId());

		return (item != null && item.getId() != null && getId() != null && item
				.getId().equals(getId()));
	}

	public List getAssociationsByTypeAndSide(AssociationType at, String side) {

		List result = new ArrayList();
		Set associations = null;

		if (side.equals("a")) {
			associations = getAssociationsA();
		} else if (side.equals("b")) {
			associations = getAssociationsB();
		} else {
			throw new IllegalArgumentException("side is neither 'a' nor 'b'");
		}

		if (associations == null) {
			return null;
		}

		Iterator it = associations.iterator();

		while (it.hasNext()) {
			Association ass = (Association) it.next();

			if (ass.getAssociationType().equals(at)) {
				result.add(ass);
			}
		}

		if (result.size() > 0) {
			return result;
		}

		return null;
	}

}
