package com.rectus29.beertender.service.impl;/**
 * User: rectus_29
 * Date: 20 mai 2009
 */

import com.rectus29.beertender.enums.SortOrder;
import com.rectus29.beertender.service.GenericManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;


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
public abstract class GenericManagerImpl<T, PK extends Serializable> implements GenericManager<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    protected Class<T> persistentClass;
    protected HibernateTemplate hibernateTemplate;
    private SessionFactory sessionFactory;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericManagerImpl(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate ServiceFactory
     */
    public GenericManagerImpl(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    protected DetachedCriteria getDetachedCriteria(){
    	return DetachedCriteria.forClass(this.persistentClass);
	}

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return hibernateTemplate.loadAll(this.persistentClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> getAllDistinct() {
        Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T get(PK id) {
        T entity = hibernateTemplate.get(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            //throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public boolean exists(PK id) {
        T entity = hibernateTemplate.get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public T save(T object) {
        return hibernateTemplate.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        hibernateTemplate.delete(this.get(id));
    }

    public int countByNamedQuery(String queryName, Map<String, Object> queryParams) {
        Query query = getNamedQuery(getSession(), queryName);

        for (String s : queryParams.keySet()) {
            query.setParameter(s, queryParams.get(s));
        }

        return ((Long) query.uniqueResult()).intValue();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];

        int index = 0;
        for (String s : queryParams.keySet()) {
            params[index] = s;
            values[index++] = queryParams.get(s);
        }

        return (List<T>)hibernateTemplate.findByNamedQueryAndNamedParam(queryName, params, values);
    }

    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams, int start, int nb) {

        Query query = getNamedQuery(getSession(), queryName);
        query.setMaxResults(nb);
        query.setFirstResult(start);

        for (String s : queryParams.keySet()) {
            query.setParameter(s, queryParams.get(s));
        }

        return query.list();
    }

    private Query getNamedQuery(final Session session, final String queryName) {

        try {
            return session.getNamedQuery(queryName);
        } catch (MappingException mappingException) {

            return null;
        }
    }

    public List<T> getAll(String propertyName, SortOrder order) {
        return createCriteria()
                .addOrder(order == SortOrder.ASC ? Order.asc(propertyName) : Order.desc(propertyName))
                .list();
    }

    public List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder, String filterPropertyName, Object filter) {
        return createCriteria()
                .add(Restrictions.like(filterPropertyName, filter instanceof String ? "%" + filter + "%" : filter))
                .setFirstResult(start)
                .setMaxResults(count)
                .addOrder(sortAscendingOrder ? Order.asc(sortPropertyName) : Order.desc(sortPropertyName))
                .list();
    }

    public List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder, T filter) {
        return createCriteria()
                .add(Example.create(filter)
                        .ignoreCase()
                        .enableLike())
                .setFirstResult(start)
                .setMaxResults(count)
                .addOrder(sortAscendingOrder ? Order.asc(sortPropertyName) : Order.desc(sortPropertyName))
                .list();
    }


    public List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder) {
        return createCriteria()
                .setFirstResult(start)
                .setMaxResults(count)
                .addOrder(sortAscendingOrder ? Order.asc(sortPropertyName) : Order.desc(sortPropertyName))
                .list();
    }

    public List<T> getAllWithPagination(int start, int count) {
        return createCriteria()
                .setFirstResult(start)
                .setMaxResults(count)
                .list();
    }

    public Set<T> getAllByProperty(String property, Object value) {
        Criteria crit = createCriteria();
        if (value instanceof List) {
            crit.add(Restrictions.in("id", ((List<Long>) value).toArray()));
        } else if (value instanceof Boolean) {
            crit.add(Restrictions.eq(property, value));
        } else {
            crit.add(Restrictions.like(property, value instanceof String ? "%" + value + "%" : value));
        }
        return new HashSet<T>(crit.list());
    }

    public Set<T> getAllStartByProperty(String property, Object value) {
        Criteria crit = createCriteria();
        if (value instanceof List) {
            crit.add(Restrictions.in("id", ((List<Long>) value).toArray()));
        } else if (value instanceof Boolean) {
            crit.add(Restrictions.eq(property, value));
        } else {
            crit.add(Restrictions.like(property, value instanceof String ? value + "%" : value));
        }
        return new HashSet<T>(crit.list());
    }

    public List<T> getAllByPropertyOrdered(String property, Object value, String sortPropertyName, boolean sortAscendingOrder) {
        Criteria crit = createCriteria();
        if (value instanceof List) {
            crit.add(Restrictions.in("id", ((List<Long>) value).toArray()));
        } else if (value instanceof Boolean) {
            crit.add(Restrictions.eq(property, value));
        } else {
            crit.add(Restrictions.like(property, value instanceof String ? "%" + value + "%" : value));
        }
        crit.addOrder(sortAscendingOrder ? Order.asc(sortPropertyName) : Order.desc(sortPropertyName));

        return crit.list();
    }


    public List<T> getAllByProperties(HashMap<String, Object> hm) {
        Criteria criteria = createCriteria();

        Set entries = hm.entrySet();
        Iterator it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() instanceof List)
                criteria.add(Restrictions.in((String) entry.getKey(), (List) entry.getValue()));
            else if (entry.getValue() instanceof Boolean)
                criteria.add(Restrictions.eq((String) entry.getKey(), entry.getValue()));
            else
                criteria.add(Restrictions.like((String) entry.getKey(), entry.getValue() instanceof String ? "%" + entry.getValue() + "%" : entry.getValue()));
        }

        return criteria.list();
    }

    public List<T> getAllByPropertiesOrdered(Map<String, Object> hm, String sortPropertyName, boolean sortAscendingOrder) {
        Criteria criteria = createCriteria();

        Set entries = hm.entrySet();
        Iterator it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() instanceof List)
                criteria.add(Restrictions.in((String) entry.getKey(), (List) entry.getValue()));
            else if (entry.getValue() instanceof Boolean)
                criteria.add(Restrictions.eq((String) entry.getKey(), entry.getValue()));
            else
                criteria.add(Restrictions.like((String) entry.getKey(), entry.getValue() instanceof String ? "%" + entry.getValue() + "%" : entry.getValue()));
        }

        criteria.addOrder(sortAscendingOrder ? Order.asc(sortPropertyName) : Order.desc(sortPropertyName));

        return criteria.list();

    }


    public T getByProperty(String property, Object value, boolean strict) {
        List<T> result = new ArrayList<T>();
        if (strict) {
            result = createCriteria().add(Restrictions.eq(property, value)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } else {
            result = createCriteria().add(Restrictions.like(property, value instanceof String ? "%" + value + "%" : value)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        }
        if (result.size() > 0) {
            return result.get(0);
        } else {
            return null;
        }
    }

    public T getByProperties(HashMap<String, Object> hm) {
        Criteria criteria = createCriteria();
        Set entries = hm.entrySet();
        Iterator it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (entry.getValue() instanceof List)
                criteria.add(Restrictions.in((String) entry.getKey(), (List) entry.getValue()));
            else if (entry.getValue() instanceof Boolean)
                criteria.add(Restrictions.eq((String) entry.getKey(), entry.getValue()));
            else
                criteria.add(Restrictions.like((String) entry.getKey(), entry.getValue() instanceof String ? "%" + entry.getValue() + "%" : entry.getValue()));
        }
        if (criteria.list().size() > 0) {
            return (T) criteria.list().get(0);
        } else {
            return null;
        }
    }

    public int count() {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.list().get(0)).intValue();
    }

    public int count(T filter) {
        Criteria criteria = createCriteria()
                .add(Example.create(filter)
                        .ignoreCase()
                        .enableLike());
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.list().get(0)).intValue();
    }

    public int countByProperty(String property, Object value) {
        Criteria criteria = createCriteria()
                .add(Restrictions.like(property, value instanceof String ? "%" + value + "%" : value));
        criteria.setProjection(Projections.rowCount());
        return ((Number) criteria.list().get(0)).intValue();
    }


    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    protected Criteria createCriteria() {
        return getSession().createCriteria(getEntityClass());
    }

    public Class<T> getEntityClass() {
        return this.persistentClass;
    }

    public List<T> findByExample(final T exampleInstance) {
        Criteria crit = getSession().createCriteria(getEntityClass());
        crit.add(Example.create(exampleInstance));
        final List<T> result = crit.list();
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
        Criteria crit = getSession().createCriteria(getEntityClass());
        Example example = Example.create(exampleInstance);
        for (String exclude : excludeProperty) {
            example.excludeProperty(exclude);
        }
        crit.add(example);
        return crit.list();
    }


    public int truncate() {
        List<T> Tlist = getAll();

        int rowAffected = Tlist.size();
        for (T t : Tlist)
            hibernateTemplate.delete(t);

        return rowAffected;
    }

}

