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

package de.berlios.gpon.wui.displaytag.decorators;

import org.displaytag.decorator.TableDecorator;
import org.displaytag.util.LookupUtil;

import de.berlios.gpon.common.AssociationType;

public class UniversalDecorator extends TableDecorator {
	public UniversalDecorator() {
	}

	public String getEditAndDeleteLink() {
		Object lObject = getCurrentRowObject();

		// get the id
		Object id = null;
		try {
			id = LookupUtil.getBeanProperty(lObject, "id");
		} catch (Exception ex) {
			return "##No id property found##";
		}
		if (id != null) {
			return "<a href=\"edit.jsp?itemId=" + id + "\">&Auml;ndern</a>"
					+ "&nbsp;<a href=\"delete.jsp?itemId=" + id
					+ "\">L&ouml;schen</a>";
		} else {
			return "##id property empty##";
		}
	}

	public String getPrepareEditAndDeleteLink() {
		Object lObject = getCurrentRowObject();

		// get the id
		Object id = null;
		try {
			id = LookupUtil.getBeanProperty(lObject, "id");
		} catch (Exception ex) {
			return "##No id property found##";
		}
		if (id != null) {
			return "<a href=\"pre-edit.do?itemId=" + id + "\">&Auml;ndern</a>"
					+ "&nbsp;<a href=\"pre-delete.do?itemId=" + id
					+ "\">L&ouml;schen</a>";
		} else {
			return "##id property empty##";
		}
	}

	public String getPrepareEditAndDeleteTypeLink() {
		Object lObject = getCurrentRowObject();

		// get the id
		Object id = null;
		try {
			id = LookupUtil.getBeanProperty(lObject, "id");
		} catch (Exception ex) {
			return "##No id property found##";
		}
		if (id != null) {
			return "<a href=\"pre-edit.do?objectId=" + id
					+ "\">&Auml;ndern</a>"
					+ "&nbsp;<a href=\"pre-delete.do?objectId=" + id
					+ "\">L&ouml;schen</a>";
		} else {
			return "##id property empty##";
		}
	}

	public String getViewDetailLink() {
		Object lObject = getCurrentRowObject();

		// get the id
		Object id = null;
		try {
			id = LookupUtil.getBeanProperty(lObject, "id");
		} catch (Exception ex) {
			return "##No id property found##";
		}
		if (id != null) {
			return "<a target=\"_blank\" href=\"itemDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&itemId="
					+ id + "\">Detail</a>";
		} else {
			return "##id property empty##";
		}
	}

	public String getViewTypeDetailLink() {
		Object lObject = getCurrentRowObject();

		// get the id
		Object id = null;
		try {
			id = LookupUtil.getBeanProperty(lObject, "id");
		} catch (Exception ex) {
			return "##No id property found##";
		}
		if (id != null) {
			return "<a target=\"_blank\" href=\"itemTypeDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&objectId="
					+ id + "\">Detail</a>";
		} else {
			return "##id property empty##";
		}
	}

	public String getMultiplicityLabel() {
		Object lObject = getCurrentRowObject();

		// get the id
		String multiplicity = null;
		try {
			multiplicity = (String) LookupUtil.getBeanProperty(lObject,
					"multiplicity");
		} catch (Exception ex) {
			return "##No multiplicity property found##";
		}

		// translate Multiplicity Constants
		if (multiplicity != null) {
			if (AssociationType.MultiplicityConstants.ONE_TO_MANY
					.equals(multiplicity)) {
				return "1:N";
			} else if (AssociationType.MultiplicityConstants.MANY_TO_MANY
					.equals(multiplicity)) {
				return "M:N";
			} else {
				return "?";
			}
		} else {
			return "";
		}
	}

	public String getViewAssociationTypeDetailLink() {
		Object lObject = getCurrentRowObject();

		// get the id
		Object id = null;
		try {
			id = LookupUtil.getBeanProperty(lObject, "id");
		} catch (Exception ex) {
			return "##No id property found##";
		}
		if (id != null) {
			return "<a target=\"_blank\" href=\"associationTypeDetails.do?decorator=popup&confirm=true&record=start&renderMode=html&objectId="
					+ id + "\">Detail</a>";
		} else {
			return "##id property empty##";
		}
	}
}
