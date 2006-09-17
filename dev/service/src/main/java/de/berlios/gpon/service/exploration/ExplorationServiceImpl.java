package de.berlios.gpon.service.exploration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.service.exploration.messages.AssociationInfoMessage;
import de.berlios.gpon.service.exploration.messages.Attribute;
import de.berlios.gpon.service.exploration.messages.GraphEdge;
import de.berlios.gpon.service.exploration.messages.GraphMessage;
import de.berlios.gpon.service.exploration.messages.GraphNode;

public class ExplorationServiceImpl implements ExplorationService {

	GponDataDao gponDataDao;

	GponModelDao gponModelDao;

	public AssociationInfoMessage getAssociationInfos(Long itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphMessage getNeighbours(Long itemId, Long associationTypeId,
			boolean reverse) {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphMessage getEnvironment(Long itemId, int radius) {

		Item startItem = gponDataDao.findItemById(itemId);

		Set allEdges = new HashSet();
		Set allNodes = new HashSet();

		Set itemsToStartNextIteration = new HashSet();
		itemsToStartNextIteration.add(startItem);
		allNodes.add(startItem);

		for (int i = 0; i < radius; i++) {

			Set nextItemsToStartNextIteration = new HashSet();
			
			Iterator itemsToStartNextIterationIterator = itemsToStartNextIteration
					.iterator();

			while (itemsToStartNextIterationIterator.hasNext()) {

				Item item = (Item) itemsToStartNextIterationIterator.next();

				allEdges.addAll(item.getAssociationsA());
				allEdges.addAll(item.getAssociationsB());

				// items on a b side
				Iterator assocIterator = 
					item.getAssociationsA().iterator();
				
				while (assocIterator.hasNext()) 
				{
					Association assoc =
						(Association)assocIterator.next();
					
					nextItemsToStartNextIteration.add(assoc.getItemB());
				}
				
				// items on a a side
				assocIterator = 
					item.getAssociationsB().iterator();
				
				while (assocIterator.hasNext()) 
				{
					Association assoc =
						(Association)assocIterator.next();
					
					nextItemsToStartNextIteration.add(assoc.getItemA());
				}
			}
			
			allNodes.addAll(nextItemsToStartNextIteration);
			itemsToStartNextIteration = nextItemsToStartNextIteration;
		}

		return formGraphMessage(allNodes, allEdges);
		
	}

	public GraphMessage formGraphMessage(Set items, Set associations) 
	{
		GraphMessage gm = new GraphMessage();
		
		Set graphNodes = new HashSet();
		Set graphEdges = new HashSet();
		
		Iterator nodeIterator = 
			items.iterator();
		
		while (nodeIterator!= null && nodeIterator.hasNext()) 
		{
			Item item = (Item)nodeIterator.next();
			
			GraphNode node = new GraphNode();
			
			node.setObjectId(item.getId());
			
			List attributes = new ArrayList();
			// parameters
			Iterator propsIt = item.getProperties().iterator();

			while (propsIt !=null && propsIt.hasNext()) 
			{
				ItemProperty ip = (ItemProperty)propsIt.next();
				
				Attribute attr = new Attribute(ip.getPropertyDecl().getDescription(),ip.getValue());
				attributes.add(attr);
			}
			
			node.setAttributes((Attribute[])attributes.toArray(new Attribute[0]));
			
			graphNodes.add(node);
		}
			
		Iterator assocIterator = associations.iterator();
		
		while (assocIterator!=null && assocIterator.hasNext()) 
		{
			Association assoc = (Association)assocIterator.next();
			
			GraphEdge graphEdge = 
				new GraphEdge(assoc.getItemA().getId(),
							  assoc.getItemB().getId(),
							  assoc.getAssociationType().getDescription());
			
			graphEdges.add(graphEdge);
		}

		gm.setGraphEdges((GraphEdge[])graphEdges.toArray(new GraphEdge[0]));
		gm.setGraphNodes((GraphNode[])graphNodes.toArray(new GraphNode[0]));
		
		return gm;
	}
	
	
	public void setGponDataDao(GponDataDao gponDataDao) {
		this.gponDataDao = gponDataDao;
	}

	public void setGponModelDao(GponModelDao gponModelDao) {
		this.gponModelDao = gponModelDao;
	}

}
