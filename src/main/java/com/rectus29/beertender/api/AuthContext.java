package com.rectus29.beertender.api;

import com.rectus29.beertender.entities.User;
import graphql.servlet.GraphQLContext;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.HandshakeRequest;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 25/01/2019                  */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class AuthContext extends GraphQLContext {

	private final User user;

	public AuthContext(HttpServletRequest httpServletRequest, HandshakeRequest handshakeRequest, Subject subject, User user) {
		super(httpServletRequest, handshakeRequest, subject);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
}
