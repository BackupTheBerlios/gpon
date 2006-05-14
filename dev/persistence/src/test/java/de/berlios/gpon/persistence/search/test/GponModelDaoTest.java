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


package de.berlios.gpon.persistence.search.test;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemProperty;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemMappedByName;
import de.berlios.gpon.persistence.GponDataDao;
import de.berlios.gpon.persistence.GponModelDao;
import de.berlios.gpon.persistence.search.SimpleQuery;

public class GponModelDaoTest 
  extends TestCase
{
  ApplicationContext context = null;


  public GponModelDaoTest()
  {
    context = new ClassPathXmlApplicationContext("persistence-test-context.xml"); 
  }

  public void testAllItemTypes() 
  {
    GponModelDao dao = getTxModelDao();
    GponDataDao  dDao = getTxDataDao();
  
    List list = dao.findAllItemTypes();
    
    System.out.println("Item type count: "+((list!=null)?list.size():0));
    
    Iterator it = list.iterator();
    
    while (it.hasNext()) {
      ItemType type = (ItemType)it.next();
      System.out.println("id: "+type.getId()+" name: "+type.getName());
      
      System.out.println("Specialized types: "+type.getSpecializedTypesDeep().size());
      
      Set propDecls =
        type.getInheritedItemPropertyDecls();
      
      Iterator propIt =
        propDecls.iterator();
        
      while (propIt.hasNext()) {
        ItemPropertyDecl ipd =
          (ItemPropertyDecl)propIt.next();
          
        System.out.println("id: "+ipd.getId()+"   prop: "+ipd.getName()+ " m: "+ipd.getMandatory()+" type: "+ipd.getItemType().getDescription());  
      }   
      
      System.out.println("# :"+dDao.findItemsByType(type.getId()).size());
      
    }
    
  }
  
  
  public void _testAllAssociationTypes() 
  {
    GponModelDao mDao = getTxModelDao();
    GponDataDao  dDao = getTxDataDao();
    
    List list = mDao.findAllAssociationTypes();
    
    System.out.println("Assoc type count: "+((list!=null)?list.size():0));
    
    Iterator it = list.iterator();
    
    while (it.hasNext()) {
      AssociationType assType = (AssociationType)it.next();
      System.out.println("id: "+assType.getId()+" name: "+assType.getName());
      System.out.println("# "+dDao.findAssociationsByType(assType.getId()).size());
    }
    
  }
  
  public void _testFindItemById() {
  
    GponDataDao dao = getTxDataDao();
    Item item = dao.findItemById(new Long(7804));
    
    System.out.println(item);
    
    System.out.println("A side associations: "+item.getAssociationsA().size());
    System.out.println("B side associations: "+item.getAssociationsB().size());
  
    Iterator it = item.getAssociationsA().iterator();
    
    if (it.hasNext()) {
      Association a = (Association)it.next();
      System.out.println("First a association:"+a);
    }
  
    item = dao.findItemById(new Long(31864));
    
    System.out.println(item);
    
    System.out.println("A side associations: "+item.getAssociationsA().size());
    System.out.println("B side associations: "+item.getAssociationsB().size());
  }
  
  
  public void _testAddAndRemoveItem() {
    GponModelDao mDao = getTxModelDao();
    GponDataDao  dDao = getTxDataDao();
    
    ItemType itemType = mDao.findItemTypeByName("ci.base");
    System.out.println("ci.base: "+itemType);
    
    Item item = new Item();
    item.setItemType(itemType);
    
    ItemMappedByName mappedItem =
    	new ItemMappedByName(item);
    
    mappedItem.setValue("inventory.no","INV_0815",ItemMappedByName.INPUT_FORM);
    mappedItem.setValue("update.date","17.10.2005",ItemMappedByName.INPUT_FORM);
    
    
    dDao.addItem(mappedItem.getItem());
    
    System.out.println("Item: "+item);
    
    Set newProps = item.getProperties();
    
    Iterator newPropsIt = newProps.iterator();
    
    ItemProperty toRemove = null;
    
    while (newPropsIt.hasNext()) {
      ItemProperty ip = (ItemProperty)newPropsIt.next();
      System.out.println("-> prop: "+ip);    
      toRemove = ip;
    }
    
    item.getProperties().remove(toRemove);
    item.getProperties().add(toRemove);
    
    dDao.updateItem(item);
    
    toRemove.setValue(toRemove.getValue()+"_add");
    
    dDao.updateItem(item);
       
    Iterator veryNewPropsIt = item.getProperties().iterator();
    
    while (veryNewPropsIt.hasNext()) {
      ItemProperty ip = (ItemProperty)veryNewPropsIt.next();
      System.out.println("-> prop: "+ip);    
    }
    
    dDao.removeItem(item.getId());
  }

  public void _testRealAddItem() {
    GponModelDao mDao = getTxModelDao();
    GponDataDao  dDao = getTxDataDao();
    
    ItemType itemType = mDao.findItemTypeByName("ci.base");
    System.out.println("ci.base: "+itemType);
    
    Set propDecls = itemType.getInheritedItemPropertyDecls();
    
    Item item = new Item();
    item.setItemType(itemType);
    
    // add a property for every property declaration
    HashSet props = new HashSet();  
    
    Iterator it = propDecls.iterator();
    
    int loopIdx=0;
    
    while (it.hasNext()) {
      ItemPropertyDecl ipd = (ItemPropertyDecl)it.next();
      
      ItemProperty prop = new ItemProperty();
      prop.setPropertyDecl(ipd);
      prop.setValue("test"+loopIdx++);
      props.add(prop);
    }
    
    item.setProperties(props);   
    
    dDao.addItem(item);
    
    System.out.println("Item: "+item);
  }
  
  public void _testRealAddItemType() 
  {
	// construct head  
    ItemType type = new ItemType();
    type.setDescription("Test type");
    type.setName("types.Test");
    
    // define one property
    Set decls = new HashSet();
    
    ItemPropertyDecl ipd = new ItemPropertyDecl();
    
    ipd.setMandatory(Boolean.TRUE);   
    ipd.setName("name");
    ipd.setDescription("Name");
    ipd.setPropertyValueTypeId(new Long(0));
    ipd.setRank(new Integer(1));
    
    decls.add(ipd);
    
    type.setItemPropertyDecls(decls);
    
    // add type
    getTxModelDao().addItemType(type);
  }

  public void _testSearch() {
	  
	  
	SimpleQuery sq = new SimpleQuery();
	
	sq.setType("server.unix");
	sq.setSpec("${id} > 1000 and "+
			   "(${name} = 'yvis' or ${name} = 'horst') "+
			   " or ${cpu.speed} > 1000 or ${cpu.count} > 1");
	// sq.setSpec("${id} > 1000");
	
	
    Set set = getTxDataDao().search(sq);
    
    System.out.print("List: "+set.size());
    Iterator it = set.iterator();
    
    while (it.hasNext()) 
    {
      Item item = (Item)it.next();
      System.out.println("Item: "+item);
      
      ItemMappedByName mbnItem =
        new ItemMappedByName(item);
        
      System.out.println(" host   : "+mbnItem.getValueObject("name"));
      System.out.println(" % used : "+mbnItem.getValueObject("os.name"));
      
    }
    
    
  
  }

  public void _testInheritedAssociations() {
	      
	GponModelDao model = getTxModelDao();
	
	List itemTypeList = model.findAllItemTypes();
	
	Iterator iter = itemTypeList.iterator();
	
	while (iter.hasNext()) 
	{
		ItemType it = (ItemType)iter.next();
		
		System.out.println(" # Type: "+it.getDescription());
		
		System.out.println("  # A assoc: "+it.getInheritedAssociationTypesA());
		System.out.println("  # B assoc: "+it.getInheritedAssociationTypesB()+"\n");
	}
   }
  
  
  public void testItemContainsAssociation() {
	  
	  GponDataDao data = getTxDataDao();
		  
	  // Mount Points
	  List list = data.findItemsByType(new Long(31804));
		  
	  Item item = (Item)list.get(0);
	  
	  Set associationsB =
		  item.getAssociationsB();
	  
	  Association[] assocs = (Association[])associationsB.toArray(new Association[0]);
	  
	  Association a = assocs[0];
	  
	  Association toFind = new Association();
	  
	  toFind.setAssociationType(a.getAssociationType());
	  toFind.setItemA(a.getItemA());
	  toFind.setItemB(a.getItemB());
	  
	  assertTrue(item.getAssociationsB().contains(toFind));
	  
  }
  
  private GponModelDao getTxModelDao() 
  {
    return (GponModelDao)context.getBean("txGponModelDao");  
  }
  
  private GponDataDao getTxDataDao() 
  {
    return (GponDataDao)context.getBean("txGponDataDao");  
  }
    
  SessionFactory sessionFactory = null;
  
  public void setUp() throws Exception {
	  super.setUp();
	  sessionFactory = (SessionFactory) context.getBean("sessionFactory");
	  Session s = sessionFactory.openSession();
	  TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
  }

  public void tearDown() throws Exception {
	    super.tearDown();
	    SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
	    Session s = holder.getSession(); 
	    s.flush();
	    TransactionSynchronizationManager.unbindResource(sessionFactory);
	    SessionFactoryUtils.closeSessionIfNecessary(s, sessionFactory);
    }
  
  public static void main(String[] args)
  {
    TestRunner runner = new TestRunner();
    
    runner.run(GponModelDaoTest.class);
    
    System.exit(0);
  }
  
  
}
