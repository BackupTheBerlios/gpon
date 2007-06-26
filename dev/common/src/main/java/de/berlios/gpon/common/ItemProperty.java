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


public class ItemProperty 
extends Base
{

	String value;

	ItemPropertyDecl propertyDecl;

	Item item;

	public String toString() {
		String s = "id=" + id + " value=" + value + " propertyDecl="
				+ propertyDecl + " item=" + item;
		return s;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setPropertyDecl(ItemPropertyDecl propertyDecl) {
		this.propertyDecl = propertyDecl;
	}

	public ItemPropertyDecl getPropertyDecl() {
		return propertyDecl;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Item getItem() {
		return item;
	}
}
