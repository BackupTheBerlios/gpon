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

package de.berlios.gpon.wui.actions.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlOptions;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemMappedById;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.persistence.search.QueryStringHelper;
import de.berlios.gpon.persistence.search.SimpleQuery;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ExpertItemSearchForm;
import de.berlios.gpon.wui.forms.ItemSearchForm;
import de.berlios.gpon.wui.util.ItemMap;
import de.berlios.gpon.xmlscript.GponScript;
import de.berlios.gpon.xmlscript.GponScriptDocument;
import de.berlios.gpon.xmlscript.GponScriptOutput;
import de.berlios.gpon.xmlscript.Object;
import de.berlios.gpon.xmlscript.Processor;
import de.berlios.gpon.xmlscript.Query;
import de.berlios.gpon.xmlscript.QueryDocument;
import de.berlios.gpon.xmlscript.Set;
import de.berlios.gpon.xmlscript.Object;

public class ExpertItemSearchAction extends BaseAction {

	private static Log log = LogFactory.getLog(ExpertItemSearchAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ExpertItemSearchForm isf = (ExpertItemSearchForm) form;

		System.out.println("Query: " + isf.getQueryText());

		Processor processor = (Processor) getObjectForBeanId("txScriptProcessor");
		GponDataDao data = (GponDataDao) getObjectForBeanId("gponDataDao");
		GponModelDao model = (GponModelDao) getObjectForBeanId("gponModelDao");

		QueryDocument qd = QueryDocument.Factory.parse(isf.getQueryText());
		Query q = qd.getQuery();
		q.setId("query0");

		ItemType itemType = model.findItemTypeByName(q.getType());
		
		isf.setItemType(itemType);
		
		GponScriptDocument scriptDoc = GponScriptDocument.Factory.newInstance();

		GponScript script = scriptDoc.addNewGponScript();

		script.setQueryArray(new Query[] { q });

		GponScriptOutput scriptOutput = processor.process(script);

		Set[] sets = scriptOutput.getSetArray();

		if (sets != null) {
			for (int i = 0; i < sets.length; i++) {
				Set set = sets[i];

				if (set.getId().equals("query0")) {
					de.berlios.gpon.xmlscript.Object[] objects = set
							.getObjectArray();

					List idMapped = new ArrayList();

					if (objects != null) {

						for (int o = 0; o < objects.length; o++) {
							Object object = objects[o];

							String objectId = object.getId();

							Item item = data.findItemById(new Long(objectId));

							log.info("Object id:" + objectId + " type: "
									+ item.getItemType().getName());

							ItemMappedById mI = new ItemMappedById(item);

							idMapped.add(new ItemMap(mI));

						}

						isf.setResultList(idMapped);
					}

				}
			}
		}

		return mapping.findForward("success");

	}

}
