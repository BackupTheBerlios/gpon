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


package de.berlios.gpon.wui.session;

import javax.servlet.http.HttpSession;


import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.berlios.gpon.wui.views.DataView;
import de.berlios.gpon.wui.views.ModelView;

public class SessionUtility {

	private WebApplicationContext ctx = null;

	private HttpSession session;

	public SessionUtility(HttpSession pSession) {
		this.session = pSession;
	}

	public void init() {
		ModelView modelView = (ModelView) getObjectForBeanId("modelView");
		DataView  dataView  = (DataView)  getObjectForBeanId("dataView");
		// set what you need in the app
		session.setAttribute("GPON_MODEL_VIEW", modelView);
		session.setAttribute("GPON_DATA_VIEW",  dataView);
	}

	private Object getObjectForBeanId(String id) {
		if (ctx == null) {
			ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(session
							.getServletContext());
		}
		return ctx.getBean(id);
	}
}
