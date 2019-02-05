package com.rectus29.beertender.api.typeresolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.rectus29.beertender.api.type.SigninPayload;
import com.rectus29.beertender.entities.User;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 25/01/2019                  */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class SigninPayloadTypeResolver implements GraphQLResolver<SigninPayload> {

	public User user(SigninPayload payload) {
		return payload.getUser();
	}
}
