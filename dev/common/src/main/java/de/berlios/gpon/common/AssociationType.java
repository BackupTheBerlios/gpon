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

public class AssociationType {
	Long id;

	String name;

	String multiplicity;

	String description;

	Long baseAssociationTypeId;

	ItemType itemAType;

	ItemType itemBType;

	String itemARoleName;

	String itemBRoleName;

	// ab,ba,abba
	String visibility;

	String predicates;

	String predicatesA;

	String predicatesB;

	public String toString() {
		String s = "id=" + id + " name=" + name + " multiplicity="
				+ multiplicity + " description=" + description
				+ " baseAssociationTypeId=" + baseAssociationTypeId
				+ " itemARoleName=" + itemARoleName + " itemBRoleName="
				+ itemBRoleName;
		return s;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setMultiplicity(String multiplicity) {
		this.multiplicity = multiplicity;
	}

	public String getMultiplicity() {
		return multiplicity;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setBaseAssociationTypeId(Long baseAssociationTypeId) {
		this.baseAssociationTypeId = baseAssociationTypeId;
	}

	public Long getBaseAssociationTypeId() {
		return baseAssociationTypeId;
	}

	public void setItemARoleName(String itemARoleName) {
		this.itemARoleName = itemARoleName;
	}

	public String getItemARoleName() {
		return itemARoleName;
	}

	public void setItemBRoleName(String itemBRoleName) {
		this.itemBRoleName = itemBRoleName;
	}

	public String getItemBRoleName() {
		return itemBRoleName;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getVisibility() {
		return visibility;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if (!(obj instanceof AssociationType)) {
			return false;
		}

		// equality by identity
		return (this.getId().equals(((AssociationType) obj).getId()));
	}

	public int hashCode() {
		if (this.getId() != null) {
			return this.getId().intValue();
		} else {
			return 0;
		}
	}

	public void setItemAType(ItemType itemAType) {
		this.itemAType = itemAType;
	}

	public ItemType getItemAType() {
		return itemAType;
	}

	public void setItemBType(ItemType itemBType) {
		this.itemBType = itemBType;
	}

	public ItemType getItemBType() {
		return itemBType;
	}

	public class VisibilityConstants {
		public static final String AB = "ab";

		public static final String BA = "ba";

		public static final String ABBA = "abba";
	}

	public class MultiplicityConstants {
		public static final String ONE_TO_MANY = "OneToMany";

		public static final String MANY_TO_MANY = "ManyToMany";
	}

	public String getPredicates() {
		return predicates;
	}

	public void setPredicates(String predicates) {
		this.predicates = predicates;
	}

	public String getPredicatesA() {
		return predicatesA;
	}

	public void setPredicatesA(String predicatesA) {
		this.predicatesA = predicatesA;
	}

	public String getPredicatesB() {
		return predicatesB;
	}

	public void setPredicatesB(String predicatesB) {
		this.predicatesB = predicatesB;
	}
}
