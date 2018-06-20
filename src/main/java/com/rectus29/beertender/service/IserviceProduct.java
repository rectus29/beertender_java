package com.rectus29.beertender.service;


import com.rectus29.beertender.entities.core.Category;
import com.rectus29.beertender.entities.core.Product;

import java.util.List;

public interface IserviceProduct extends GenericManager<Product, Long> {

    public List<Product> getProductByCategory(List<Category> categoryList);
}
