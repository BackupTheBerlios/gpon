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


package de.berlios.gpon.xmlscript.process.test;

public class TextTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test  = "hgfhf";
		String test1 = "jadjagjd\nakdhakjhjdhjakhd";
		
		System.out.println("test  nl: "+(test.indexOf("\n")>=0));
		System.out.println("test1 nl: "+(test1.indexOf("\n")>=0));
		
	}

}
