package com.rectus29.beertender.service.impl;


import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceProductDefinition;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: rectus_29
 * Date: 03 sept. 2012
 * Time: 05:32:27
 */

@Service("serviceProductDefinition")
public class ServiceProductDefinition extends GenericManagerImpl<ProductDefinition, Long> implements IserviceProductDefinition {

	public ServiceProductDefinition() {
		super(ProductDefinition.class);
	}
}

