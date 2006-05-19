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


package de.berlios.gpon.wui.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class LazyElMap implements Map {

	LazyElMapGetHandler getHandler;
	
	public LazyElMapGetHandler getGetHandler() {
		return getHandler;
	}

	public void setGetHandler(LazyElMapGetHandler getHandler) {
		this.getHandler = getHandler;
	}

	public LazyElMap(LazyElMapGetHandler handler) {
		setGetHandler(handler);
	}
	
	public int size() {
		if (true)
			throw new UnsupportedOperationException("size");
		return 0;
	}

	public boolean isEmpty() {
		if (true)
			throw new UnsupportedOperationException("isEmpty");
		
		return false;
	}

	public boolean containsKey(Object arg0) {
		if (true)
			throw new UnsupportedOperationException("containsKey");
		return false;
	}

	public boolean containsValue(Object arg0) {
		if (true)
			throw new UnsupportedOperationException("containsValue");
		return false;
	}

	public Object get(Object key) {
		return getGetHandler().get(key);
	}

	public Object put(Object arg0, Object arg1) {
		if (true)
			throw new UnsupportedOperationException("put");
		return null;
	}

	public Object remove(Object arg0) {
		if (true)
			throw new UnsupportedOperationException("remove");
		
		return null;
	}

	public void putAll(Map arg0) {
		if (true)
			throw new UnsupportedOperationException("putAll");
		
	}

	public void clear() {
		if (true)
			throw new UnsupportedOperationException("clear");

	}

	public Set keySet() {
		if (true)
			throw new UnsupportedOperationException("keySet");
		return null;
	}

	public Collection values() {
		if (true)
			throw new UnsupportedOperationException("values");
		
		return null;
	}

	public Set entrySet() {
		if (true)
			throw new UnsupportedOperationException("entrySet");
		
		return null;
	}
}
