package com.rectus29.beertender.api;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.impl.ServiceCategory;
import com.rectus29.beertender.spring.AppContext;

import java.util.List;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 12/10/2018 09:52               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class Query implements GraphQLQueryResolver {

	private IserviceCategory serviceCategory;

	public Query() {
		this.serviceCategory = AppContext.getApplicationContext().getBean("serviceCategory", ServiceCategory.class);
	}

	public List<Category> allCateg() {
		return serviceCategory.getAll();
	}


}
