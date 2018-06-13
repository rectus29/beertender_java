package com.rectus29.beertender.service.impl;/**
 * User: rectus_29
 * Date: 20 mai 2009
 */

import com.rectus29.beertender.dao.GenericDao;
import com.rectus29.beertender.dao.tools.SortOrder;
import com.rectus29.beertender.service.GenericManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * <p>To register this class in your Spring context file, use the following XML.
 * <pre>
 *     &lt;bean id="userManager" class="org.appfuse.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="org.appfuse.dao.hibernate.GenericDaoHibernate"&gt;
 *                 &lt;constructor-arg value="org.appfuse.model.User"/&gt;
 *                 &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 * <p/>
 * <p>If you're using iBATIS instead of Hibernate, use:
 * <pre>
 *     &lt;bean id="userManager" class="org.appfuse.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="org.appfuse.dao.ibatis.GenericDaoiBatis"&gt;
 *                 &lt;constructor-arg value="org.appfuse.model.User"/&gt;
 *                 &lt;property name="dataSource" ref="dataSource"/&gt;
 *                 &lt;property name="sqlMapClient" ref="sqlMapClient"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */


@Transactional
public class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());

    /**
     * GenericDao instance, set by constructor of this class
     */
    protected GenericDao<T, PK> genericDao;

    /**
     * Public constructor for creating a new GenericManagerImpl.
     *
     * @param genericDao the GenericDao to use for persistence
     */
    public GenericManagerImpl(final GenericDao<T, PK> genericDao) {
        this.genericDao = genericDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return genericDao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        return genericDao.get(id);
    }

    public T getByProperty(String property, Object value, boolean strict){
        return genericDao.getByProperty(property, value, strict);
    }
    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        return genericDao.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        return genericDao.save(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        genericDao.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    public int truncate() {
        return genericDao.truncate();
    }

    public List<T> getAll(String propertyName, SortOrder order){
        return genericDao.getAll(propertyName, order);
    }


    public List<T> getAllWithPagination(int start, int count){
        return genericDao.getAllWithPagination(start, count);
    }

    public List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder){
        return genericDao.getAllWithPagination(start, count, sortPropertyName, sortAscendingOrder);
    }

    public List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder, String filterPropertyName, Object filter){
        return genericDao.getAllWithPagination(start, count, sortPropertyName, sortAscendingOrder, filterPropertyName, filter);
    }

    public List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder, T filter){
        return genericDao.getAllWithPagination(start, count, sortPropertyName, sortAscendingOrder, filter);
    }

    public int count(){
        return genericDao.count();
    }

    public int count(T filter){
        return genericDao.count(filter);
    }

    public int countByNamedQuery(String queryName, Map<String, Object> queryParams){
        return genericDao.countByNamedQuery(queryName, queryParams);
    }

    public int countByProperty(String property, Object value){
        return genericDao.countByProperty( property,  value);
    }
    public Set<T> getAllByProperty(String property, Object value){
        return genericDao.getAllByProperty(property, value);
    }
    public Set<T> getAllStartByProperty(String property, Object value){
        return genericDao.getAllStartByProperty(property, value);
    }
    public List<T> getAllByPropertyOrdered(String property, Object value, String sortPropertyName, boolean sortAscendingOrder) {
        return genericDao.getAllByPropertyOrdered(property, value, sortPropertyName, sortAscendingOrder);
    }
    public List<T> getAllByProperties(HashMap<String, Object> hm){
        return genericDao.getAllByProperties(hm);
    }
    public List<T> getAllByPropertiesOrdered(Map<String, Object> hm, String sortPropertyName, boolean sortAscendingOrder){
        return genericDao.getAllByPropertiesOrdered(hm, sortPropertyName, sortAscendingOrder);
    }
    public Class<T> getEntityClass(){
        return genericDao.getEntityClass();
    }

    public List<T> findByExample(final T exampleInstance){
        return genericDao.findByExample(exampleInstance);
    }

    public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
        return genericDao.findByExample(exampleInstance, excludeProperty);
    }

    public T getByProperties(HashMap<String, Object> hm){
        return genericDao.getByProperties(hm);
    }

    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        return genericDao.findByNamedQuery(queryName, queryParams);
    }

    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams, int start, int nb){
        return genericDao.findByNamedQuery(queryName, queryParams, start, nb);

    }

}

