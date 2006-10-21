package de.berlios.gpon.service.exploration.paths;

import java.util.Set;

import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;

public interface PathResolver {

	public abstract Set getItemsForPath(Long itemId, String pathDescriptor);

	public PathFinder getPathFinder();

	public void setPathFinder(PathFinder pathFinder);
}