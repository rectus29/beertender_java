package com.rectus29.beertender.session;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Packaging;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.HashSet;
import java.util.Set;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/09/2018 17:47               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderFilter {

	private IModel<Packaging> packagingFilterModel;
	private Set<IModel<Category>> categoryFilterModelList = new HashSet<>();

	public BeerTenderFilter() {
	}

	public IModel<Packaging> getPackagingFilter() {
		return packagingFilterModel;
	}



	public BeerTenderFilter setPackagingFilter(IModel<Packaging> packagingFilter) {
		this.packagingFilterModel = packagingFilter;
		return this;
	}

	public Set<IModel<Category>> getCategoryFilterList() {
		return categoryFilterModelList;
	}

	public void setCategoryFilterModelList(Set<IModel<Category>> categoryFilterList) {
		this.categoryFilterModelList = categoryFilterList;
	}

	public BeerTenderFilter addCategoryFilterModel(IModel<Category> categoryFilterModel) {
		this.categoryFilterModelList.add(categoryFilterModel);
		return this;
	}



	public BeerTenderFilter setPackagingFilterModel(IModel<Packaging> packagingFilterModel) {
		this.packagingFilterModel = packagingFilterModel;
		return this;
	}
}
