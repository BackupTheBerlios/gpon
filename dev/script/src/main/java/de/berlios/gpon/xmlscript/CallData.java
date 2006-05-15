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


package de.berlios.gpon.xmlscript;

import java.util.List;
import java.util.Set;

import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemMappedByName;


public class CallData {
	// used in Creations
	ItemType itemType;
	// used in Properties
	ItemMappedByName mappedItem;
	// Update Keys
	List updateKeys;
	// used in Associated Queries
	Set baseSet;
	int level;
	// pattern
	String pattern;
	String antiPattern;
	
	
	public String getAntiPattern() {
		return antiPattern;
	}

	public void setAntiPattern(String antiPattern) {
		this.antiPattern = antiPattern;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Set getBaseSet() {
		return baseSet;
	}

	public void setBaseSet(Set baseSet) {
		this.baseSet = baseSet;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public ItemMappedByName getMappedItem() {
		return mappedItem;
	}

	public void setMappedItem(ItemMappedByName mappedItem) {
		this.mappedItem = mappedItem;
	}

	public List getUpdateKeys() {
		return updateKeys;
	}

	public void setUpdateKeys(List updateKeys) {
		this.updateKeys = updateKeys;
	}
}
