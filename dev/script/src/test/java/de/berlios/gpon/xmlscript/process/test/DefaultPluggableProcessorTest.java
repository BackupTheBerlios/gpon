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

import de.berlios.gpon.xmlscript.GponScriptDocument;
import de.berlios.gpon.xmlscript.LookupService;
import de.berlios.gpon.xmlscript.Processor;
import de.berlios.gpon.xmlscript.io.Loader;
import junit.framework.TestCase;


public class DefaultPluggableProcessorTest 
extends TestCase {

	public void testDml() 
	{
		GponScriptDocument gsd = new Loader().load("test.xml");
		
		Processor p = 
			(Processor)LookupService.getBeanForId("txScriptProcessor");
		
		p.process(gsd.getGponScript());
		
	}
	
	public void testDmlCarsAndDrivers() 
	{
		GponScriptDocument gsd = new Loader().load("CreateDriversAndCars.xml");
		
		Processor p = 
			(Processor)LookupService.getBeanForId("txScriptProcessor");
		
		p.process(gsd.getGponScript());
		
	}
	
	
	public void _testDdl() 
	{
		GponScriptDocument gsd = new Loader().load("CreateType.xml");
		
		Processor p = 
			(Processor)LookupService.getBeanForId("txScriptProcessor");
		
		p.process(gsd.getGponScript());
		
	}
	
}
