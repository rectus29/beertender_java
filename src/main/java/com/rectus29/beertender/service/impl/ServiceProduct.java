package com.rectus29.beertender.service.impl;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.session.BeerTenderFilter;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("serviceProduct")
public class ServiceProduct extends GenericManagerImpl<Product, Long> implements IserviceProduct {

    @SpringBean(name = "serviceCategory")
    private ServiceCategory serviceCategory;

    public ServiceProduct() {
        super(Product.class);
    }

    @Override
    public List<Product> getFilteredProduct(BeerTenderFilter btf) {
        List<Product> out = new ArrayList<>();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);
        detachedCriteria.add(Restrictions.eq("packaging", btf.getPackagingFilter().getObject()));
//    	detachedCriteria.add(Restrictions.in("categoryList", btf.getCategoryFilterList()));
        out.addAll((List<Product>) getHibernateTemplate().findByCriteria(detachedCriteria));
        //filter on categ manually fo rhte moment
        if (btf.getCategoryFilterModel().getObject() != null) {
            Iterator<Product> it = out.iterator();
            while (it.hasNext()) {
                Product product = it.next();
                if (!product.getCategoryList().contains(btf.getCategoryFilterModel().getObject())) {
                    it.remove();
                }
            }
        }
        return out;
    }


    @Override
    public List<Product> getProductByCategory(List<Category> categoryList) {
        List<Product> result = new ArrayList<>();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Product.class);

        detachedCriteria.add(Restrictions.in("categoryList", categoryList));
//		detachedCriteria.add(Restrictions.eq("state", State.DISABLE));

        result.addAll((List<Product>) getHibernateTemplate().findByCriteria(detachedCriteria));
        return result;
    }
}
