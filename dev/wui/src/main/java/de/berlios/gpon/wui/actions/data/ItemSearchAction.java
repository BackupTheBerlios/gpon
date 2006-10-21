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
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.util.CollectionUtil;

import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.types.Value;
import de.berlios.gpon.common.util.ItemMappedById;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.search.QueryStringHelper;
import de.berlios.gpon.persistence.search.SimpleQuery;
import de.berlios.gpon.service.exploration.paths.PathResolver;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.ItemSearchForm;
import de.berlios.gpon.wui.util.ItemMap;

public class ItemSearchAction extends BaseAction {

	private static Log log = LogFactory.getLog(ItemSearchAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ItemSearchForm isf = (ItemSearchForm) form;

		// TODO: convert search criteria to a query

		ItemType it = isf.getItemType();

		log.debug("Item type: " + it.getDescription());

		SimpleQuery sq = new SimpleQuery();

		sq.setType(it.getName());

		String queryString = QueryStringHelper.getQuery(it, isf.getMap());

		log.debug("Query string: " + queryString);

		sq.setSpec(queryString);

		GponDataDao data = (GponDataDao) getObjectForBeanId("gponDataDao");

		Set resultSet = data.search(sq);

		List idMapped = new ArrayList();

		Iterator iterator = resultSet.iterator();

		while (iterator.hasNext()) {
			Item item = (Item) iterator.next();

			ItemMappedById mI = new ItemMappedById(item);

			idMapped.add(new ItemMap(mI));
		}

		if (idMapped != null) {
			addAssociatedProperties(idMapped, isf.getAssociatedPropertyMap());
		}

		isf.setResultList(idMapped);

		return mapping.findForward("success");
	}

	private void addAssociatedProperties(List idMapped,
			HashMap associatedPropertyMap) {

		// build up a pathDigest -> ipd-List mapping
		final Hashtable pathAndProperties = new Hashtable();

		CollectionUtils.forAllDo(associatedPropertyMap.keySet(), new Closure() {
			// foreach key in associatedPropertyMap do:
			public void execute(Object o) {
				String key = (String) o;

				String[] keySplit = ItemSearchForm
						.splitAssociatedPropertyKey(key);

				String pathDigest = keySplit[0];
				String ipdId = keySplit[1];

				if (!pathAndProperties.containsKey(pathDigest)) {
					pathAndProperties.put(pathDigest, new ArrayList());
				}

				((List) pathAndProperties.get(pathDigest)).add(new Long(ipdId));
			}
		});

		final String[] digests = (String[]) Collections
				.list(pathAndProperties.keys()).toArray(new String[0]);

		final PathResolver pathResolver = (PathResolver) getObjectForBeanId("pathResolver");

		CollectionUtils.forAllDo(idMapped, new Closure() {

			public void execute(Object o) {

				ItemMap im = (ItemMap) o;

				// foreach digest

				for (int digIdx = 0; digIdx < digests.length; digIdx++) {
					String digest = digests[digIdx];

					Set items = 
						pathResolver.getItemsForPath(im.getItem().getId(), digest);

					if (items != null && items.size() > 0) {
						if (items.size() > 1) {
							throw new RuntimeException(
									"more than one associated item found");
						}

						// get one & only item
						Item item = ((Item[]) items
								.toArray(new Item[0]))[0];

						Long[] ipds = (Long[]) ((List) pathAndProperties
								.get(digest)).toArray(new Long[0]);

						ItemMappedById associatedImbi =
							new ItemMappedById(item);
						
						for (int ipdIdx = 0; ipdIdx < ipds.length; ipdIdx++) {
							Value value = associatedImbi
									.getValueObject(ipds[ipdIdx]+"");

							if (value!=null) 
							{
								im.addAdditionalAttribute(digest + "|" + ipds[ipdIdx],value);
							}
						}
					}
				}

			}
		});
	}

}
