package com.rectus29.beertender.session;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Packaging;

import java.util.HashSet;
import java.util.Set;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/09/2018 17:47               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderFilter {

	private Packaging packagingFilter;
	private Set<Category> categoryFilterList = new HashSet<>();

	public BeerTenderFilter() {
	}

	public Packaging getPackagingFilter() {
		return packagingFilter;
	}

	public BeerTenderFilter setPackagingFilter(Packaging packagingFilter) {
		this.packagingFilter = packagingFilter;
		return this;
	}

	public Set<Category> getCategoryFilterList() {
		return categoryFilterList;
	}

	public void setCategoryFilterList(Set<Category> categoryFilterList) {
		this.categoryFilterList = categoryFilterList;
	}

	public BeerTenderFilter addCategoryFilterList(Category categoryFilter) {
		this.categoryFilterList.add(categoryFilter);
		return this;
	}
}
