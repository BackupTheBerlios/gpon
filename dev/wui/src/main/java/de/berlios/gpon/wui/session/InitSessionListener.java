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
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class InitSessionListener implements HttpSessionListener {

	private static Log log = LogFactory.getLog(InitSessionListener.class);
	
	public void sessionCreated(HttpSessionEvent event) {
		
		HttpSession session = event.getSession();
		
		log.debug("Got session: "+session.getId());
		
		SessionUtility su =
			new SessionUtility(session);
		
		// Dunno what to do, but su does
		su.init();
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// Scratching balls
	}

}
