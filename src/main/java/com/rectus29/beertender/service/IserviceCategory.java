package com.rectus29.beertender.service;


import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.service.impl.ServiceCategory;

import java.util.List;

/**
 * Created by Oliv'Generator.
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

public interface IserviceCategory extends GenericManager<Category, Long> {

    public List<Category> getRootCateg();

	ServiceCategory.FilteredResult getFilteredProduct(List<Category> filter);
}