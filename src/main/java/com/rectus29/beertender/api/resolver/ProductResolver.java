package com.rectus29.beertender.api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rectus29.beertender.api.OperationResult;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.spring.AppContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 24/10/2018               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class ProductResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private IserviceProduct serviceProduct;

    public ProductResolver() {
        this.serviceProduct = AppContext.getApplicationContext().getBean("serviceProduct", IserviceProduct.class);
    }

	public List<Product> allProducts(Optional<State> state) {
        if (state.isPresent()) {
            return serviceProduct.getAll(Arrays.asList(state.get()));
        } else {
            return serviceProduct.getAll();
        }
    }

    public Product getProduct(Long id) {
        return serviceProduct.get(id);
    }

    public OperationResult saveProduct(Product product){
        return null;
    }
}
