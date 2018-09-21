package com.rectus29.beertender.realms;

import org.apache.shiro.authc.AuthenticationToken;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 21/09/2018 12:50               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class GoogleOauthToken implements AuthenticationToken {

	private String googleEmail;

	public GoogleOauthToken(String googleEmail) {
		this.googleEmail = googleEmail;
	}

	@Override
	public Object getPrincipal() {
		return googleEmail;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	public String getGoogleEmail() {
		return googleEmail;
	}
}
