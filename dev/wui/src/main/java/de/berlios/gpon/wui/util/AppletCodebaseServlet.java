package de.berlios.gpon.wui.util;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppletCodebaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7920864493313609111L;

	Log log = LogFactory.getLog(AppletCodebaseServlet.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		doIt(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		doIt(request, response);
	}

	private void doIt(HttpServletRequest request, HttpServletResponse response) {

		// compute the portion after <context path>/<servlet path>

		log.info("result: "
				+ request.getRequestURI().substring(
						request.getContextPath().length()
								+ request.getServletPath().length()+1));

		String result = request.getRequestURI().substring(
				request.getContextPath().length()
						+ request.getServletPath().length()+1);

		InputStream is = getClass().getClassLoader()
		.getResourceAsStream(result);
		
		if (is==null) 
		{
			log.error("resource "+result+" can not be retrieved. ");
			try {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
			} 
				catch (IOException io) 
			{
				log.error("Unable to send a "+HttpServletResponse.SC_NOT_FOUND);
			}
			return;
		}
		
		try {
			byte[] buffer = new byte[8192];
			int read = 0;

			while ((read = is.read(buffer)) != -1) {
				response.getOutputStream().write(buffer, 0, read);
			}

			response.getOutputStream().close();
		} catch (IOException e) {
			log.error(e);
		}

	}

}
