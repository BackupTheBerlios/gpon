package de.berlios.gpon.service.exploration;

import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.service.exploration.messages.GraphMessage;

public interface ExplorationService {
	
	GraphMessage getEnvironment(Long itemId, int radius);
	
	public void setGponDataDao(GponDataDao gpdd);
	public void setGponModelDao(GponModelDao gpmd);

}
