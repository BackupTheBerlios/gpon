package de.berlios.gpon.wui.actions.exploration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import de.berlios.gpon.service.exploration.ExplorationService;
import de.berlios.gpon.service.exploration.messages.GraphMessage;
import de.berlios.gpon.wui.actions.BaseAction;
import de.berlios.gpon.wui.forms.GetEnvironmentForm;
;

public class GetEnvironmentAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping, 
			ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	throws Exception {
	
		GetEnvironmentForm gef = (GetEnvironmentForm)form;
		
		ExplorationService es =
			(ExplorationService)getObjectForBeanId("explorationService");
		
		GraphMessage gm = es.getEnvironment(gef.getObjectId(),gef.getRadius());
		
		request.setAttribute("graphXml",gm.serialize());		
		
		return mapping.findForward("success");
	}

	
	
	
}
