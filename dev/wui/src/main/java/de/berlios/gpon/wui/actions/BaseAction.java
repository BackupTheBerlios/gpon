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


package de.berlios.gpon.wui.actions;


import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.validation.DataValidationError;

public class BaseAction extends Action {
	private WebApplicationContext ctx = null;

	public BaseAction() {
	}

	public Object getObjectForBeanId(String id) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(servlet
							.getServletContext());
		}
		return ctx.getBean(id);
	}

	public ActionErrors convertValidationErrors(DataValidationError[] dvErrors) {
		if (dvErrors != null && dvErrors.length > 0) {
			ActionErrors errors = new ActionErrors();

			for (int i = 0; i < dvErrors.length; i++) {
				DataValidationError dvError = dvErrors[i];

				if (dvError.getCode() == DataValidationError.MANDATORY_FIELD_ERROR) {
					ItemPropertyDecl propDecl = null;
					if (dvError.getDetails() != null
							&& dvError.getDetails().length > 0) {
						if (dvError.getDetails()[0] instanceof ItemPropertyDecl) {
							propDecl = (ItemPropertyDecl) dvError.getDetails()[0];
						}
					}

					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"errors.required", (propDecl != null) ? propDecl
									.getDescription() : ""));

				} else if (dvError.getCode() == DataValidationError.FIELD_VALIDATION_ERROR) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"errors.validation", dvError.getDetails()));
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
							"errors.unknown"));
				}
			}
			return errors;
		}

		return null;
	}
	
	public ActionErrors convertException(Exception ex) 
	{
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_ERROR, 
					new ActionError(
							"errors.exception",
							ex.getClass().getName(),ex.getMessage()));
		return errors;
	}

}
