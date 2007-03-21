package de.berlios.gpon.wui.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.taglibs.standard.lang.jstl.test.Bean1;

import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;

public class AjaxAction extends BaseAction {

	GponModelDao model;

	GponDataDao data;

	public GponDataDao getData() {
		return data;
	}

	public void setData(GponDataDao data) {
		this.data = data;
	}

	public GponModelDao getModel() {
		return model;
	}

	public void setModel(GponModelDao model) {
		this.model = model;
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// get the requested operation
		String op = request.getParameter("op");

		if (op == null) {
			return mapping.findForward("success");
		}

		String json = null;

		GponModelDao model = (GponModelDao) getObjectForBeanId("txGponModelDao");
		GponDataDao data = (GponDataDao) getObjectForBeanId("txGponDataDao");

		setData(data);
		setModel(model);

		if (op.equals("getTypes")) {

			json = getAllTypesAsJson();

		} else {
			// unknown op
		}

		request.setAttribute("json", json);
		return mapping.findForward("success");
	}

	private String getAllTypesAsJson() {

		List types = getModel().findAllItemTypes();
		final List beans = new ArrayList();

		CollectionUtils.forAllDo(types, new Closure() {

			public void execute(Object o) {
				ItemType type = (ItemType) o;
				try {
					String json = toJSON(type);
					beans.add(json);
				} catch (Throwable t) {
					throw new RuntimeException(t);
				}

			}
		});

		JSONArray array = JSONArray.fromCollection(beans);
		return array.toString();
	}
	
	private String toJSON(Object o) 
	{
		if (o instanceof ItemType) {
			ItemType itemType = (ItemType) o;

			Map map = new HashMap();
			map.put("id",itemType.getId());
			if (itemType.getBaseType()!=null) {
				map.put("baseTypeId",itemType.getBaseType().getId());
			}
			map.put("description",itemType.getDescription());
			map.put("name",itemType.getName());
			map.put("propertyDecls",toJSON(itemType.getItemPropertyDecls()));

			JSONObject jo = JSONObject.fromObject(map);
			
			return jo.toString();
		}
		
		if (o instanceof ItemPropertyDecl) {
			ItemPropertyDecl ipd = (ItemPropertyDecl) o;
			
			Map m = new HashMap();
			m.put("id",ipd.getId());
			m.put("name", ipd.getName());
			m.put("description", ipd.getDescription());
			m.put("mandatory",ipd.getMandatory());
			m.put("valueType",ipd.getPropertyValueTypeName());
			m.put("rank",ipd.getRank());
			m.put("typic",ipd.getTypic());
			
			JSONObject jo = JSONObject.fromObject(m);
			
			return jo.toString();
			
		}
		
		if (o instanceof Collection) {
			Collection col = (Collection) o;
			Collection strCol = new ArrayList();
			
			Iterator it = col.iterator();
			
			while (it.hasNext()) 
			{
				Object item = it.next();
				strCol.add(toJSON(item));
			}
			
			JSONArray ja = JSONArray.fromCollection(strCol);
			
			return ja.toString();
		}
		
		return null;
	}

}
