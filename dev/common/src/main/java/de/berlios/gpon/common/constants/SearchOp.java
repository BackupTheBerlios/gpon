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


package de.berlios.gpon.common.constants;

public class SearchOp {
	public final static int OP_UNKNOWN = -1;

	public final static int OP_EQUALS = 1;

	public final static int OP_NOT_EQUALS = 2;

	public final static int OP_STARTS_WITH = 3;

	public final static int OP_ENDS_WITH = 4;

	public final static int OP_INCLUDES = 5;

	public final static int OP_GREATER_THAN = 6;

	public final static int OP_LESS_THAN = 7;

	public final static int OP_GREATER_EQUALS = 8;

	public final static int OP_LESS_EQUALS = 9;
}
