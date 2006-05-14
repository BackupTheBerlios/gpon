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


public class ItemPropertyDecl {
	public static final Character FALSE = new Character('0');

	public static final Character TRUE = new Character('1');

	Long id;

	String name;

	String description;

	Long propertyValueTypeId;

	ItemType itemType;

	Character mandatoryCharacter = FALSE;

	Character typicCharacter = FALSE;

	Integer rank;

	public static final String ID_PROPERTY_DECL_NAME = "id";

	public static final Long ID_PROPERTY_DECL_ID = new Long("-43");

	public String toString() {
		String s = "id=" + id + " name=" + name + " description=" + description
				+ " propertyValueTypeId=" + propertyValueTypeId + " mandatory="
				+ getMandatory() + " rank=" + rank;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setPropertyValueTypeId(Long propertyValueTypeId) {
		this.propertyValueTypeId = propertyValueTypeId;
	}

	public Long getPropertyValueTypeId() {
		return propertyValueTypeId;
	}

	public boolean isComplete() {
		return (this.getName() != null && this.getName().trim().length() > 0
				&& this.getDescription() != null
				&& this.getDescription().trim().length() > 0
				&& this.getPropertyValueTypeId() != null && this
				.getPropertyValueTypeId().longValue() > 0);
	}

	public boolean isPartial() {
		return (!isComplete() && ((this.getName() != null && this.getName()
				.trim().length() > 0)
				|| (this.getDescription() != null && this.getDescription()
						.trim().length() > 0) || (this.getPropertyValueTypeId() != null && this
				.getPropertyValueTypeId().longValue() > 0)));
	}

	public boolean isEmpty() {
		return (!isComplete() && !isPartial());
	}

	public void setMandatory(Boolean mandatory) {
		if (mandatory != null && mandatory.equals(Boolean.TRUE)) {
			setMandatoryCharacter(TRUE);
		}
		else {
			setMandatoryCharacter(FALSE);
		}
	}

	public Boolean getMandatory() {
		return mandatoryCharacter.equals(TRUE) ? Boolean.TRUE : Boolean.FALSE;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getRank() {
		return rank;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setMandatoryCharacter(Character mandatoryCharacter) {
		this.mandatoryCharacter = mandatoryCharacter;
	}

	public Character getMandatoryCharacter() {
		return mandatoryCharacter;
	}

	public Character getTypicCharacter() {
		return typicCharacter;
	}

	public void setTypicCharacter(Character typicCharacter) {
		this.typicCharacter = typicCharacter;
	}

	public void setTypic(Boolean typic) {
		if (typic != null && typic.equals(Boolean.TRUE)) {
			setTypicCharacter(TRUE);
		}
		else {
			setTypicCharacter(FALSE);
		}
	}

	public Boolean getTypic() {
		return typicCharacter.equals(TRUE) ? Boolean.TRUE : Boolean.FALSE;
	}

}
