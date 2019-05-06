package com.rectus29.beertender.api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rectus29.beertender.api.OperationResult;
import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.spring.AppContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 24/10/2018                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class CategoryResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private IserviceCategory serviceCategory;

    public CategoryResolver() {
        this.serviceCategory = AppContext.getApplicationContext().getBean("serviceCategory", IserviceCategory.class);
    }

	public List<Category> allCategs() {
        return serviceCategory.getAll();
    }

    public Category getCateg(Long id) {
        return serviceCategory.get(id);
    }

    public OperationResult saveCategory(Category category){
        return null;
    }
}
