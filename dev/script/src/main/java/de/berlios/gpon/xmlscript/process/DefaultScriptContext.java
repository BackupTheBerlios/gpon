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


package de.berlios.gpon.xmlscript.process;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.xmlscript.ScriptContext;


public class DefaultScriptContext implements ScriptContext {

	private Hashtable ht = new Hashtable();
	
	
	public void setAttribute(String name, Object value) {
		ht.put(name,value);

	}

	public Object getAttribute(String name) {
		// TODO Auto-generated method stub
		return ht.get(name);
	}
	
	

	GponDataDao  dataDao;
	GponModelDao modelDao;


	public GponDataDao getDataDao() {
		return dataDao;
	}

	public void setDataDao(GponDataDao dataDao) {
		this.dataDao = dataDao;
	}

	public GponModelDao getModelDao() {
		return modelDao;
	}

	public void setModelDao(GponModelDao modelDao) {
		this.modelDao = modelDao;
	}

	public void removeAttribute(String name) {
		ht.remove(name);
		
	}

	public Set getAttributeSet() {
		return ht.keySet();
	}
	
	
	
}
