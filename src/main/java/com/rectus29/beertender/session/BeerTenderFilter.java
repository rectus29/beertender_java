package com.rectus29.beertender.session;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Packaging;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/09/2018 17:47               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderFilter {

	private IModel<Packaging> packagingFilterModel = new Model<Packaging>();
	private IModel<Category> categoryFilterModel = new Model<Category>();

	public BeerTenderFilter() {
	}

	public IModel<Packaging> getPackagingFilter() {
		return packagingFilterModel;
	}

	public BeerTenderFilter setPackagingFilter(IModel<Packaging> packagingFilter) {
		this.packagingFilterModel = packagingFilter;
		return this;
	}

	public IModel<Packaging> getPackagingFilterModel() {
		return packagingFilterModel;
	}

	public IModel<Category> getCategoryFilterModel() {
		return categoryFilterModel;
	}

	public void setCategoryFilterModel(IModel<Category> categoryFilterModel) {
		this.categoryFilterModel = categoryFilterModel;
	}

	public BeerTenderFilter setPackagingFilterModel(IModel<Packaging> packagingFilterModel) {
		this.packagingFilterModel = packagingFilterModel;
		return this;
	}


	public boolean isEmpty() {
		if(this.packagingFilterModel == null || this.packagingFilterModel.getObject() == null){
			return true;
		}
		return false;
	}
}
