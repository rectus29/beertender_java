package com.rectus29.beertender.api.type;

import com.rectus29.beertender.entities.User;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 25/01/2019                  */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class SigninPayload {

	private final String token;
	private User user;

	public SigninPayload(String token, User user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public User getUser() {
		return user;
	}

	public SigninPayload setUser(User user) {
		this.user = user;
		return this;
	}
}
