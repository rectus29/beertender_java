package com.rectus29.beertender.service.impl;


import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceCategory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliv'Generator
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

@Service("serviceCategory")
public class ServiceCategory extends GenericManagerImpl<Category, Long> implements IserviceCategory {


    public ServiceCategory() {
        super(Category.class);
    }

    @Override
    public List<Category> getRootCateg() {
        List<Category> result = new ArrayList<>();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.persistentClass);
        detachedCriteria.add(Restrictions.isNull("parentCategory"));
        detachedCriteria.add(Restrictions.eq("state", State.ENABLE));
        List<Category> out = (List<Category>)getHibernateTemplate().findByCriteria(detachedCriteria);
        result.addAll(out);
        return result;
    }
}

