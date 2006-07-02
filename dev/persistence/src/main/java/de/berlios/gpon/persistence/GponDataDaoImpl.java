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

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.berlios.gpon.common.Association;
import de.berlios.gpon.common.Item;
import de.berlios.gpon.common.ItemPropertyDecl;
import de.berlios.gpon.common.ItemType;
import de.berlios.gpon.common.util.ItemTypeMappedByName;
import de.berlios.gpon.common.validation.AssociationValidator;
import de.berlios.gpon.common.validation.DataValidationError;
import de.berlios.gpon.common.validation.DataValidator;
import de.berlios.gpon.common.validation.DefaultDataValidator;
import de.berlios.gpon.persistence.search.SimpleQuery;

public class GponDataDaoImpl extends HibernateDaoSupport implements GponDataDao {

	Log log = LogFactory.getLog(GponDataDaoImpl.class);

	private GponModelDao modelDao;

	public GponDataDaoImpl() {
	}

	public Item findItemById(final Long id) {
		try {
			return (Item) getHibernateTemplate().load(Item.class, id);
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}

	public List findItemsByType(final Long typeId) {
		try {

			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException {
					return session.createCriteria(Item.class).add(
							Restrictions.eq("itemType.id", typeId)).list();
				}
			});
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}

	public List findAssociationsByType(final Long typeId) {
		try {

			return getHibernateTemplate().executeFind(new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException {
					return session.createCriteria(Association.class).add(
							Restrictions.eq("associationType.id", typeId))
							.list();
				}
			});
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}

	// Data Manipulation
	public void addItem(final Item item) {
		try {

			DataValidator dv = new DefaultDataValidator(item);

			DataValidationError[] errors = dv.validate();

			if (errors != null && errors.length > 0) {
				GponDataDaoException ex = new GponDataDaoException();
				ex.setValidationErrors(errors);
				throw ex;
			}

			getHibernateTemplate().save(item);
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}

	public void updateItem(Item item) {
		_updateItem(item);
	}
	
	private void _updateItem(Item item) {
		try {

			DataValidator dv = new DefaultDataValidator(item);

			DataValidationError[] errors = dv.validate();

			if (errors != null && errors.length > 0) {
				GponDataDaoException ex = new GponDataDaoException();
				ex.setValidationErrors(errors);

				throw ex;
			}

			getHibernateTemplate().update(item);
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}

	public void removeItem(Long id) {
		try {
			Item item = (Item) getHibernateTemplate().load(Item.class, id);
			getHibernateTemplate().delete(item);
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}

	
	public void addAssociation(Association assoc) {
		_addAssociation(assoc);
	}
	
	private void _addAssociation(Association assoc) {
		try {
			
			DataValidator dv = new AssociationValidator(assoc);

			DataValidationError[] errors = dv.validate();

			if (errors != null && errors.length > 0) {
				GponDataDaoException ex = new GponDataDaoException();
				ex.setValidationErrors(errors);
				throw ex;
			}
			
			getHibernateTemplate().save(assoc);
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}

	public void removeAssociation(Association assoc) {
		_removeAssociation(assoc);
	}

	private void _removeAssociation(Association assoc) {
		try {
			Long id = assoc.getId();
			getHibernateTemplate().delete(assoc);
		} catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.berlios.gpon.persistence.GponDataDao#search(de.berlios.gpon.persistence.search.SimpleQuery)
	 */
	public Set search(final SimpleQuery query) {
		try {
			List results = getHibernateTemplate().executeFind(
					new HibernateCallback() {
						public Object doInHibernate(Session session)
								throws HibernateException {

							StringBuffer hqlBuffer = new StringBuffer();
							StringBuffer specializedTypesBuffer = new StringBuffer();
							// start
							hqlBuffer.append("select i from Item i ");

							ItemType iType = getModelDao().findItemTypeByName(
									query.getType());

							Iterator specialzedIt = iType
									.getSpecializedTypesDeep().iterator();

							while (specialzedIt.hasNext()) {
								ItemType specType = (ItemType) specialzedIt
										.next();
								specializedTypesBuffer.append(specType.getId());
								if (specialzedIt.hasNext()) {
									specializedTypesBuffer.append(",");
								}
							}

							if (query.getSpec() != null
									&& query.getSpec().trim().length() > 0) {

								List queryProps = query.getSpecProperties();

								ItemTypeMappedByName mappedItemType = new ItemTypeMappedByName(
										iType);

								Iterator it = queryProps.iterator();
								Hashtable propNameReplacementHash = new Hashtable();
								StringBuffer bindBuffer = new StringBuffer();
								int paramIdx = 0;

								while (it.hasNext()) {
									// increment index
									paramIdx++;
									// get property name
									String propName = (String) it.next();

									if (mappedItemType.hasItemPropertyDeclaration(propName)) {
										String paramName = "p" + paramIdx;
										ItemPropertyDecl ipd = mappedItemType
												.getItemPropertyDecl(propName);

										hqlBuffer
												.append(" join i.properties as "
														+ paramName + " ");

										if (bindBuffer.length() != 0) {
											bindBuffer.append(" and ");
										}

										bindBuffer.append(paramName).append(
												".propertyDecl = ").append(
												ipd.getId());

										propNameReplacementHash.put(propName,
												paramName + ".value");
									} else if (propName.equals("id")) {
										propNameReplacementHash.put(propName,
												"i.id");
									}

									else {
										log.error("Unknown property "
												+ propName);
										// TODO: Exception and Error Handling

									}
								}

								hqlBuffer.append(" where i.itemType.id in (")
										.append(
												specializedTypesBuffer
														.toString()).append(
												") and (");

								if (bindBuffer.length() > 0) {
									hqlBuffer.append(bindBuffer.toString())
											.append(") and (");
								}
								;

								// condition
			
								hqlBuffer
										.append(query
												.getReplacedSpec(propNameReplacementHash));

								// after condition
								hqlBuffer.append(")");

							} else {

								hqlBuffer.append(" where i.itemType.id in (")
										.append(
												specializedTypesBuffer
														.toString())
										.append(")");

							}

							return session.createQuery(hqlBuffer.toString())
									.list();

						}
					});

			Set resultSet = new HashSet();

			resultSet.addAll(results);

			return resultSet;
		} catch (HibernateException ex) {
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
	
	public GponModelDao getModelDao() {
		return modelDao;
	}

	public void setModelDao(GponModelDao modelDao) {
		this.modelDao = modelDao;
	}

	public void updateItem(Item item, List associationList) {

		
		
		// union
		Set allAssoc = new HashSet();
		allAssoc.addAll(item.getAssociationsA());
		allAssoc.addAll(item.getAssociationsB());
		
		
		Iterator currentAssocIterator =
			allAssoc.iterator();
		
		while (currentAssocIterator.hasNext()) 
		{
			Association assoc =
				(Association)currentAssocIterator.next();
			
			if (associationList==null || !associationList.contains(assoc)) 
			{
				log.debug("Remove assoc id: "+assoc.getId()+
						  " a: "+assoc.getItemA().getId()+
						  " b: "+assoc.getItemB().getId());
				
				log.debug("before: a: "+item.getAssociationsA().size()+
						  " b: "+item.getAssociationsB().size());
				
				if (item.getId().equals(assoc.getItemA().getId())) 
				{
					item.getAssociationsA().remove(assoc);
				}
				else 
				{
					item.getAssociationsB().remove(assoc);
				}
				
				log.debug("after: a: "+item.getAssociationsA().size()+
						  " b: "+item.getAssociationsB().size());
				
				
				// _removeAssociation(assoc); 
			}
		}
		
		getHibernateTemplate().flush();
		
		if (associationList!=null) 
		{
			Iterator newAssocIterator =
				associationList.iterator();
			
			while (newAssocIterator.hasNext())
			{
				Association assoc =
					(Association)newAssocIterator.next();
				
				if (!allAssoc.contains(assoc)) 
				{
					log.debug("Add assoc id: "+assoc.getId()+
							  " a: "+assoc.getItemA().getId()+
							  " b: "+assoc.getItemB().getId());
					
					if (item.getId().equals(assoc.getItemA())) 
					{
						item.getAssociationsA().add(assoc);
					}
					else 
					{
						item.getAssociationsB().add(assoc);
					}
					
					
//					_addAssociation(assoc);
				}
			}
		}
		
		
		
		this._updateItem(item);
	}

}
