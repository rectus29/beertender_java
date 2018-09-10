package com.rectus29.beertender.service;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 13/06/2018 16:28               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.entities.Packaging;
import com.rectus29.beertender.enums.SortOrder;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.
 * <p/>
 * <p>Extend this interface if you want typesafe (no casting necessary) managers
 * for your domain objects.
 *
 * @param <T>  a type variable
 * @param <PK> the primary key for that type
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface GenericManager<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     *
     * @return List of populated objects
     */
    List<T> getAll();

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
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     *
     * @param object the object to save
     * @return the updated object
     */
    T save(T object);

    /**
     * Generic method to delete an object based on class and id
     *
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);

    List<T> getAll(String propertyName, SortOrder order);

    List<T> getAllWithPagination(int start, int count);

    List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder);

    List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder, String filterPropertyName, Object filter);

    List<T> getAllWithPagination(int start, int count, String sortPropertyName, boolean sortAscendingOrder, T filter);

    int count();

    int count(T filter);

    public int countByProperty(String property, Object value);

    int countByNamedQuery(String queryName, Map<String, Object> queryParams);

    public T getByProperty(String property, Object value, boolean strict);

    public List<T> getAllByPropertyOrdered(String property, Object value, String sortPropertyName, boolean sortAscendingOrder);

    public Set<T> getAllByProperty(String property, Object value);

    public List<T> getAllByProperties(HashMap<String, Object> hm);

    public List<T> getAllByPropertiesOrdered(Map<String, Object> hm, String sortPropertyName, boolean sortAscendingOrder);

    public T getByProperties(HashMap<String, Object> hm);

    public Set<T> getAllStartByProperty(String property, Object value);

    Class<T> getEntityClass();

    public List<T> findByExample(final T exampleInstance);

    public List<T> findByExample(T exampleInstance, String[] excludeProperty);

    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams);

    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams, int start, int nb);

    int truncate();

	public List<T> getAllIn(String propertyBame, List<Object> objectList);
}

