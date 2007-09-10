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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlOptions;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.util.ItemMappedByName;
import de.berlios.gpon.xmlscript.CallData;
import de.berlios.gpon.xmlscript.GponScript;
import de.berlios.gpon.xmlscript.GponScriptOutput;
import de.berlios.gpon.xmlscript.GponScriptOutputDocument;
import de.berlios.gpon.xmlscript.LookupService;
import de.berlios.gpon.xmlscript.Processor;
import de.berlios.gpon.xmlscript.Property;
import de.berlios.gpon.xmlscript.ScriptContext;
import de.berlios.gpon.xmlscript.evaluator.DefaultTraversingEvaluator;

public class DefaultPluggableProcessor implements Processor {

	Object o;

	ScriptContext scriptContext;

	public ScriptContext getScriptContext() {
		return scriptContext;
	}

	public void setScriptContext(ScriptContext scriptContext) {
		this.scriptContext = scriptContext;
	}

	public GponScriptOutput process(GponScript script) {
		try {
			DefaultTraversingEvaluator de = new DefaultTraversingEvaluator();

			ScriptContext sc = getScriptContext();

			CallData cd = new CallData();
			cd.setLevel(0);

			de.evaluate(script, sc, cd);

			// construct output document

			GponScriptOutputDocument outDoc = GponScriptOutputDocument.Factory
					.newInstance();

			GponScriptOutput out = outDoc.addNewGponScriptOutput();

			out.setGponScript(script);

			Set attributes = sc.getAttributeSet();

			Iterator it = attributes.iterator();

			while (it.hasNext()) {
				String name = (String) it.next();

				Set set = (Set) sc.getAttribute(name);

				System.out.println("Id: " + name + " size: "
						+ ((set != null) ? set.size() : 0));

				de.berlios.gpon.xmlscript.Set outputSet = out.addNewSet();

				outputSet.setId(name);

				Iterator setIterator = set.iterator();

				while (setIterator.hasNext()) {
					Item item = (Item) setIterator.next();

					de.berlios.gpon.xmlscript.Object outputObject = outputSet
							.addNewObject();

					outputObject.setId("" + item.getId());

					ItemMappedByName mItem = new ItemMappedByName(item);

					Map map = mItem.getMap();

					Iterator propIterator = map.keySet().iterator();

					while (propIterator.hasNext()) {
						Object key = propIterator.next();

						ItemProperty ip = (ItemProperty) map.get(key);

						Value valObject = mItem.getValueObject(key.toString());

						Property prop = outputObject.addNewProperty();

						prop.setName(key.toString());

						if (valObject != null) {

							String value = valObject.getInput();

							// Handling multiline values
							if (value.indexOf("\n") >= 0) {
								XmlCursor cursor = prop.newCursor();
								cursor.toFirstContentToken();
								cursor.insertChars(value);
								cursor.dispose();
							} else {
								prop.setValue(value);
							}
						}
						prop.setId("" + ip.getId());

					}
				}
			}

			// if (false || true) throw new RuntimeException("aetsch baetsch");

			XmlOptions xmlOps = new XmlOptions();

			xmlOps.setSavePrettyPrint();

			System.out.println("Output: " + outDoc.xmlText(xmlOps));

			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
