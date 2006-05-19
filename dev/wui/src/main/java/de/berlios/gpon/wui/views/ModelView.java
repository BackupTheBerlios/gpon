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

import de.berlios.gpon.persistence.GponModelDao;


public interface ModelView {

	public abstract GponModelDao getModel();

	public abstract void setModel(GponModelDao model);
	
	public List getAllItemTypes();
	
	public List getAllAssociationTypes();
	
	public List getAllMultiplicities();
	
	public List getAllVisibilities();
	
	public List getAllPropertyValueTypes();

}
