package com.rectus29.beertender.service;


import com.rectus29.beertender.entities.Category;

import java.util.List;

/**
 * Created by Oliv'Generator.
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

public interface IserviceCategory extends GenericManager<Category, Long> {

    public List<Category> getRootCateg();

}