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


package de.berlios.gpon.persistence.search.test;

import java.util.Hashtable;

import junit.framework.TestCase;
import de.berlios.gpon.persistence.search.SimpleQuery;

public class SimpleQueryTest 
	extends TestCase
{

	public void testGetSpecProperties() 
	{
		SimpleQuery sq = new SimpleQuery();
		
		sq.setType("server.unix");
		sq.setSpec("${name} < 'yvis' and ${host.arch} like '%bla%'"+
				   " or ${name} = 'horst'");
		
		System.out.println("Props: "+sq.getSpecProperties());
		
		Hashtable replacements =
				new Hashtable();
		
		replacements.put("name","p1.value");
		replacements.put("host.arch","p2.value");
		
		System.out.println("Props: "+sq.getReplacedSpec(replacements));
		
	}
	
}
