package com.rectus29.beertender.api.resolver;


import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rectus29.beertender.api.OperationResult;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceUser;
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
public class UserResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private IserviceUser serviceUser;

    public UserResolver() {
        this.serviceUser = AppContext.getApplicationContext().getBean("serviceUser", IserviceUser.class);
    }

    public User getUser(Long id) {
        return serviceUser.get(id);
    }

	public List<User> allUsers(Optional<State> state) {
        if (state.isPresent()) {
            return serviceUser.getAll(Arrays.asList(state.get()));
        } else {
            return serviceUser.getAll();
        }
    }

    public OperationResult saveUser(User user){
        return null;
    }
}
