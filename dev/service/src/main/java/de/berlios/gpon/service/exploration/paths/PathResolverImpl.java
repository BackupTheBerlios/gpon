package de.berlios.gpon.service.exploration.paths;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.path.Path;
import de.berlios.gpon.common.util.path.Step;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;


public class PathResolverImpl implements PathResolver {

	Log log = LogFactory.getLog(PathResolverImpl.class);
	
	PathFinder pathFinder;
	
	Hashtable pathInstanceCache = new Hashtable();
	Hashtable typeCache = new Hashtable();
	Hashtable itemCache = new Hashtable();

	private Item getCachedItem(Long itemId) 
{		
	if (!itemCache.containsKey(itemId)) {
		Item item = getPathFinder().getGponDataDao().findItemById(itemId);
		itemCache.put(itemId,item);
	}
	else 
	{
		log.info("cached item: "+itemId);
	}
	
	return (Item)itemCache.get(itemId);
}

	public Set getItemsForPath(Long itemId, String pathDescriptor) {
		try {
			Path path = pathFinder.getPathByDescriptor(pathDescriptor);

			Item startItem = getCachedItem(itemId);

			return getItemsForPathNTCore(startItem, path);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private Set getItemsForPathNTCore(Item item, Path path) {

		String itemPathHash = getItemPathHash(item.getId(),path);
		
		if (pathInstanceCache.containsKey(itemPathHash)) 
		{
			Set cachedSet  =(Set)pathInstanceCache.get(itemPathHash);
			log.info("cached set size: "+((cachedSet!=null)?cachedSet.size():0));
			return cachedSet;
		}
		else 
		{
			log.info("compute set for : "+itemPathHash);
		}
		
		try {
			
			if (path.getSteps().size()>1) 
			{
				Set items = new HashSet();
				Step nextStep = (Step)path.getSteps().get(0);
					
				Set associatedItems =
					getItemsForStep(item,nextStep);
				// construct new Path object with the remaining steps
				Path remainingPath = new Path();
				
				log.info("traversal");
				
				for (int i = 1; i < path.getSteps().size(); i++) 
				{
					remainingPath.setStart(getTypeCachedById(nextStep.getFromTypeId()));
					remainingPath.addStep((Step)path.getSteps().get(i));
					remainingPath.setEnd(path.getEnd());
				}
				
				Iterator nextItemsIt =
					associatedItems.iterator();
				
				while (nextItemsIt.hasNext()) {
				
					Item ci =
						(Item)nextItemsIt.next();
					
				    Set result = getItemsForPathNTCore(ci,remainingPath);
				    
				    if (result!=null) 
				    {
				    	items.addAll(result);
				    }
				    
			  }
		
			 pathInstanceCache.put(itemPathHash,items);
				
			 return items;
				
			}
			else if (path.getSteps().size()==1)
			{
				
				Set leafSet = getItemsForStep(item,(Step)path.getSteps().get(0));
				log.info("leaf ("+((leafSet!=null)?leafSet.size():0)+")");
				
				pathInstanceCache.put(itemPathHash,leafSet);
				
				return leafSet;
				
			}
			else 
			{
				throw new RuntimeException("Path size not >= 1");
			}
			
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	private Set getItemsForStep(Item item, Step step) {
		try {

			Set items = new HashSet();

			AssociationType at = step.getAssociationType();

			String side = step.isReverse() ? "b" : "a";

			// TODO: we need a filtering mechanism to filter out
			// associations with a given type
			//
			// i.e.: CollectionUtils.select
			
			
			List associations =
				item.getAssociationsByTypeAndSide(at,side);

			if (associations != null) {
				Iterator assocIt = associations.iterator();

				while (assocIt.hasNext()) {
					Association assoc = (Association) assocIt.next();

					Long currentAssociatedItemId = (step.isReverse()) ? assoc
							.getItemA().getId() : assoc.getItemB().getId();

					Item currentAssociatedItem = getCachedItem(currentAssociatedItemId);

					items.add(currentAssociatedItem);
				}
			}

			return items;

		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public PathFinder getPathFinder() {
		return pathFinder;
	}

	public void setPathFinder(PathFinder pathFinder) {
		this.pathFinder = pathFinder;
	}

	ItemType getTypeCachedById(Long id) 
	{
		if (!typeCache.containsKey(id)) 
		{
			typeCache.put(id,pathFinder.getGponModelDao().findItemTypeById(id));
		}
		else 
		{
			log.info("type cached: "+id);
		}
		return (ItemType)typeCache.get(id);
	}
	
	private String getItemPathHash(Long itemId, Path path) 
	{
		return itemId+"|"+path.getDigest();
	}
}
