package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.core.Category;
import com.rectus29.beertender.entities.core.Product;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.GenericManager;
import com.rectus29.beertender.service.IserviceProduct;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("serviceProduct")
public class ServiceProduct extends GenericManagerImpl<Product, Long> implements IserviceProduct {

    @SpringBean(name = "serviceCategory")
    private ServiceCategory serviceCategory;

    public ServiceProduct() {
        super(Product.class);
    }

    @Override
    public List<Product> getProductByCategory(List<Category> categoryList) {
        List<Product> result = new ArrayList<>();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(this.persistentClass);
        detachedCriteria.add(Restrictions.in("categoryList", categoryList));
        detachedCriteria.add(Restrictions.eq("state", State.ENABLE));
        List<Product> out = (List<Product>)getHibernateTemplate().findByCriteria(detachedCriteria);
        result.addAll(out);
        return result;
    }
}
