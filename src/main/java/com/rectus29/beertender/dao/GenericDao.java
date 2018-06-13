package com.rectus29.beertender.dao;/**
 * User: rectus_29
 * Date: 20 mai 2009
 */


import com.rectus29.beertender.dao.tools.SortOrder;
import com.rectuscorp.evetool.dao.tools.SortOrder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */

/**
 * Generic DAO (Data Access Object) with common methods to CRUD POJOs.
 * <p/>
 * <p>Extend this interface if you want typesafe (no casting necessary) DAO's for your
 * domain objects.
 *
 * @param <T>  a type variable
 * @param <PK> the primary key for that type
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 */
public interface GenericDao<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    List<T> getAll();

    List<T> getAll(String propertyName, SortOrder order);

    List<T> getAllWithPagination(int start, int count);

    List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder);

    List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder, String filterPropertyName, Object filter);

    List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder, T filter);

    int count();

    int count(T filter);

    int countByProperty(String property, Object value);

    int countByNamedQuery(String queryName, Map<String, Object> queryParams);

    T getByProperty(String property, Object value, boolean strict);

    Set<T> getAllByProperty(String property, Object value);

    Class<T> getEntityClass();

    public Set<T> getAllStartByProperty(String property, Object value);

    /**
     * Gets all records without duplicates.
     * <p>Note that if you use this method, it is imperative that your model
     * classes correctly implement the hashcode/equals methods</p>
     *
     * @return List of populated objects
     */
    List<T> getAllDistinct();

    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     *
     * @param id the id of the entity
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     *
     * @param object the object to save
     * @return the persisted object
     */
    T save(T object);

    /**
     * Generic method to delete an object based on class and id
     *
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);

    /**
     * Find a list of records by using a named query
     *
     * @param queryName   query name of the named query
     * @param queryParams a map of the query names and the values
     * @return a list of the records found
     */
    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams, int start, int nb);

    public List<T> findByExample(T exampleInstance, String[] excludeProperty);

    public List<T> findByExample(final T exampleInstance);

    public List<T> getAllByPropertyOrdered(String property, Object value, String sortPropertyName, boolean sortAscendingOrder);

    public List<T> getAllByProperties(HashMap<String, Object> hm);

    public List<T> getAllByPropertiesOrdered(Map<String, Object> hm, String sortPropertyName, boolean sortAscendingOrder);

    public T getByProperties(HashMap<String, Object> hm);


    public int truncate();

}