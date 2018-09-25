package com.rectus29.beertender.realms;

import org.apache.shiro.authc.AuthenticationToken;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 21/09/2018 12:50               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class GoogleOauthToken implements AuthenticationToken {

	private String googleEmail;
	private String name;
	String familyName;
	String givenName;
	String userId;

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

	public GoogleOauthToken setGoogleEmail(String googleEmail) {
		this.googleEmail = googleEmail;
		return this;
	}

	public String getName() {
		return name;
	}

	public GoogleOauthToken setName(String name) {
		this.name = name;
		return this;
	}

	public String getFamilyName() {
		return familyName;
	}

	public GoogleOauthToken setFamilyName(String familyName) {
		this.familyName = familyName;
		return this;
	}

	public String getGivenName() {
		return givenName;
	}

	public GoogleOauthToken setGivenName(String givenName) {
		this.givenName = givenName;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public GoogleOauthToken setUserId(String userId) {
		this.userId = userId;
		return this;
	}
}
