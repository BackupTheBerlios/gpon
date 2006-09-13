package de.berlios.gpon.wui.forms;

import java.util.List;

import org.apache.struts.action.ActionForm;

import de.berlios.gpon.common.ItemType;

public class ExpertItemSearchForm extends ActionForm {

	String queryText;
	List resultList;
	private ItemType itemType;

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	
}
