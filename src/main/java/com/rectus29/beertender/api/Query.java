package com.rectus29.beertender.api;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.spring.AppContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 12/10/2018 09:52               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class Query implements GraphQLQueryResolver {

	@Autowired
	private IserviceCategory serviceCategory;
	@Autowired
	private IserviceProduct serviceProduct;
	@Autowired
	private IserviceUser serviceUser;

	public Query() {
		this.serviceCategory = AppContext.getApplicationContext().getBean("serviceCategory", IserviceCategory.class);
		this.serviceProduct = AppContext.getApplicationContext().getBean("serviceProduct", IserviceProduct.class);
		this.serviceUser = AppContext.getApplicationContext().getBean("serviceUser", IserviceUser.class);
	}

	public List<Category> allCateg() {
		return serviceCategory.getAll();
	}

	public Category getCateg(Long id) {
		return serviceCategory.get(id);
	}

	public List<Product> allProduct(Optional<State> state) {
		if (state.isPresent()) {
			return serviceProduct.getAll(Arrays.asList(state.get()));
		} else {
			return serviceProduct.getAll();
		}
	}

	public User getUser(Long id) {
		return serviceUser.get(id);
	}

	public List<User> allUser(Optional<State> state) {
		if (state.isPresent()) {
			return serviceUser.getAll(Arrays.asList(state.get()));
		} else {
			return serviceUser.getAll();
		}
	}

	public Product getProduct(Long id) {
		return serviceProduct.get(id);
	}

}
