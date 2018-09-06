package com.rectus29.beertender.service.impl;


import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.tools.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
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
		detachedCriteria.add(Restrictions.eq("isRoot", true));
		detachedCriteria.add(Restrictions.eq("state", State.ENABLE));
		List<Category> out = (List<Category>) getHibernateTemplate().findByCriteria(detachedCriteria);
		result.addAll(out);
		return result;
	}

	@Override
	public FilteredResult getFilteredProduct(List<Category> filter) {
		FilteredResult out = new FilteredResult();
		for(Category tempcateg : filter){
			out.getFilteredProduct().addAll(tempcateg.getProductList());
			for(Product tempProduct:tempcateg.getProductList()){
				out.getChildCategory().addAll(tempProduct.getCategoryList());
			}
		}
		//cleaning the output categ to avoid loop
		out.getChildCategory().removeAll(filter);
		return out;
	}

	@Override
	public Category save(Category object) {
		//clean and set the short name
		String cleanValue = StringUtils.stripAccents(object.getName());
		cleanValue = StringUtils.deleteWhitespace(cleanValue);
		object.setShortName(cleanValue);
		return super.save(object);
	}

	public class FilteredResult {
		Set<Product> filteredProduct = new TreeSet<>();
		Set<Category> childCategory = new TreeSet<>();

		public FilteredResult() {
		}

		public Set<Product> getFilteredProduct() {
			return filteredProduct;
		}

		public void setFilteredProduct(Set<Product> filteredProduct) {
			this.filteredProduct = filteredProduct;
		}

		public Set<Category> getChildCategory() {
			return childCategory;
		}

		public void setChildCategory(Set<Category> childCategory) {
			this.childCategory = childCategory;
		}
	}


}

