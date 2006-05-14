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


package de.berlios.gpon.persistence;

import de.berlios.gpon.common.validation.DataValidationError;

public class GponDataDaoException extends RuntimeException {

	DataValidationError[] validationErrors;
	
	public DataValidationError[] getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(DataValidationError[] validationErrors) {
		this.validationErrors = validationErrors;
	}

	public GponDataDaoException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GponDataDaoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public GponDataDaoException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public GponDataDaoException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
