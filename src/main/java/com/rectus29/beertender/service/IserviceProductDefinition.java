package com.rectus29.beertender.service;


import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.enums.State;

import java.util.List;

/**
 * Created by Oliv'Generator.
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

public interface IserviceProductDefinition extends GenericManager<ProductDefinition, Long> {

	List<ProductDefinition> getAll(List<State> stateList);
}