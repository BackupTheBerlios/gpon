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


package de.berlios.gpon.xmlscript.io;

import java.io.File;
import java.io.IOException;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;

import de.berlios.gpon.xmlscript.GponScriptDocument;


public class Loader {

	public GponScriptDocument load(String name) 
	{
		try {
			return GponScriptDocument.Factory.parse(new File(name));
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	public static void main(String[] args) {
		
		GponScriptDocument gsd = new Loader().load("test.xml");
		
		XmlCursor cursor = gsd.getGponScript().newCursor();
		
		cursor.selectPath("*");
		
        // iterate over the selection
        while ( cursor.toNextSelection() )
        {
            Object o =  cursor.getObject();
            
            cursor.getDomNode().getLocalName();
            
            System.out.println("Node: "+cursor.getDomNode().getLocalName()+
            		           " Class: "+o.getClass().getName());
            
            
            
        }
		
		
		
	}
	
}
