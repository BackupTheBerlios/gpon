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


package de.berlios.gpon.persistence;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.berlios.gpon.common.AssociationType;
import de.berlios.gpon.common.ItemType;

public class GponModelDaoImpl
  extends HibernateDaoSupport 
  implements GponModelDao
{  
  public List findAllItemTypes() 
  {
     try {
    
      return getHibernateTemplate().executeFind(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException {
           return session.createCriteria(ItemType.class).list();
         }
        });
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }

  public List findAllAssociationTypes() 
  {
     try {
    
      return getHibernateTemplate().executeFind(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException {
           return session.createCriteria(AssociationType.class).list();
         }
        });
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  public AssociationType findAssociationTypeById(final Long id) {
     try {
      return (AssociationType )getHibernateTemplate().load(AssociationType.class,id);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  public AssociationType findAssociationTypeByName(final String name) {
     try {
      return (AssociationType)getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException {
           return session.createCriteria(AssociationType.class).add(Restrictions.eq("name",name)).uniqueResult();
         }
        });
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  public ItemType findItemTypeById(Long id){
     try {
      return (ItemType)getHibernateTemplate().load(ItemType.class,id);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  public ItemType findItemTypeByName(final String name) {
   try {
      return (ItemType)getHibernateTemplate().execute(new HibernateCallback() {
         public Object doInHibernate(Session session) throws HibernateException {
           return session.createCriteria(ItemType.class).add(Restrictions.eq("name",name)).uniqueResult();
         }
        });
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  // Model manipulation
  
  // add, update, delete item type
  public void addItemType(ItemType itemType) 
  {
    try {
     getHibernateTemplate().save(itemType);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  public void updateItemType(ItemType itemType) 
  {
    try {
     getHibernateTemplate().update(itemType);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  public void deleteItemType(Long id) 
  {
    try {
     ItemType itemType = (ItemType)getHibernateTemplate().load(ItemType.class,id);
     getHibernateTemplate().delete(itemType);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  // add, update, delete association type
  public void addAssociationType(AssociationType associationType) 
  {
   try {
     getHibernateTemplate().save(associationType);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  } 
  
  public void updateAssociationType(AssociationType associationType) 
  {
    try {
     getHibernateTemplate().update(associationType);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  public void deleteAssociationType(Long id) 
  {
    try {
     AssociationType associationType = (AssociationType)getHibernateTemplate().load(AssociationType.class,id);
     getHibernateTemplate().delete(associationType);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }
  
  public void refresh(Object object) 
  {
    try {
     getHibernateTemplate().refresh(object);
    } 
    catch (HibernateException ex) {
      throw convertHibernateAccessException(ex);
    }
  }

  public void flush() {
	getHibernateTemplate().flush();
  }
}


