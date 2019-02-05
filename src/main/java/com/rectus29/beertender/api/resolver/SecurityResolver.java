package com.rectus29.beertender.api.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.rectus29.beertender.service.IserviceSession;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.spring.AppContext;
import org.springframework.beans.factory.annotation.Autowired;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 24/10/2018                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class SecurityResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

	@Autowired
	private IserviceUser serviceUser;
	@Autowired
	private IserviceSession serviceSession;

	public SecurityResolver() {
		this.serviceUser = AppContext.getApplicationContext().getBean("serviceUser", IserviceUser.class);
	}

//	public SigninPayload signinUser(AuthData auth) throws IllegalAccessException {
//		Subject currentSubject = org.apache.shiro.SecurityUtils.getSubject();
//		UsernamePasswordToken token = new UsernamePasswordToken(auth.getLogin(), auth.getpasseword());
//		try {
//			currentSubject.login(token);
//			serviceSession.addSubject(currentSubject);
//			User currentUser = serviceUser.getUser(currentSubject);
//			return new SigninPayload(currentUser.getUuid(), currentUser);
//		} catch (Exception ex) {
//			throw new GraphQLException("Invalid credentials");
//		}
//	}
}
