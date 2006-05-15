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


package de.berlios.gpon.xmlscript.evaluator;

import java.util.ArrayList;
import java.util.List;


import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlObject;

import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.Evaluator;
import de.berlios.gpon.xmlscript.LookupService;
import de.berlios.gpon.xmlscript.ScriptContext;

public class DefaultTraversingEvaluator implements Evaluator {

	final static String m_namespaceDeclaration = "declare namespace xq='http://sf.net/gpon/xmlscript';";

	List results = new ArrayList();

	public Object evaluate(XmlObject xmlObject, ScriptContext sc, CallData cd) {

		if (xmlObject != null) {
			XmlCursor cursor = xmlObject.newCursor();

			cursor.selectPath("*");

			while (cursor.toNextSelection()) {
				XmlObject o = cursor.getObject();

				String nodeName = cursor.getDomNode().getLocalName();

				boolean evaluate = true;

				if (cd.getPattern() != null
						&& !nodeName.matches(cd.getPattern())) {
					System.out.println("pattern mismatch: " + nodeName + "<->"
							+ cd.getPattern());
					evaluate = false;
				}

				if (evaluate == true && cd.getAntiPattern() != null
						&& nodeName.matches(cd.getAntiPattern())) {
					evaluate = false;
				}

				if (evaluate) {
					System.out.println("Entering [" + cd.getLevel()
							+ "] Node: " + cursor.getDomNode().getLocalName()
							+ " Class: " + o.getClass().getName());
					Evaluator evaluator = (Evaluator) LookupService
							.getBeanForId(nodeName + "Evaluator");

					if (evaluator == null) {
						evaluator = new DefaultTraversingEvaluator();
					}

					Object result = evaluator.evaluate(o, sc, cd);
					if (result != null) {
						results.add(result);
					}

				}
			}
		}
		return (results.size() > 0) ? results.get(0) : null;
	}

	List getResults() {
		return results;
	}

}
