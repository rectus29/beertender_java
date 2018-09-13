package com.rectus29.beertender.service;


import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.session.BeerTenderFilter;

import java.util.List;

public interface IserviceProduct extends GenericManager<Product, Long> {

    public List<Product> getProductByCategory(List<Category> categoryList);

	public List<Product> getFilteredProduct(BeerTenderFilter btf);
}
