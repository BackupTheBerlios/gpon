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


package de.berlios.gpon.wui.views;

import java.util.List;
import java.util.Map;

import de.berlios.gpon.persistence.GponDataDao;


public interface DataView {

	public abstract GponDataDao getData();

	public abstract void setData(GponDataDao data);
	
	// finally lists all items according
	// the previously made modifier settings 
	public List getList();
	
	// dedicated for EL usage
	
	// The map yields a modified DataView-Object
	// e.g. GPON_DATA_VIEW.focusItem[4711].associatedItems['a|unix.mountpoint|computer'].list
	// yields all items 

	public Map getFocusItem();
	
	public Map getAssociatedItems();
	
}
