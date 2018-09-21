package com.rectus29.beertender.realms;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.tools.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 21/09/2018 12:44               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class GoogleOauthRealm extends AuthorizingRealm {

	public static final String REALM_NAME = "GoogleOauthRealm";
	Logger logger = LoggerFactory.getLogger(GoogleOauthRealm.class);
	protected IserviceUser serviceUser;
	protected IserviceConfig serviceConfig;


	@Autowired
	public void setServiceUser(IserviceUser serviceUser, IserviceConfig serviceConfig) {
		setName(REALM_NAME);
		this.serviceUser = serviceUser;
		this.serviceConfig = serviceConfig;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		throw new UnsupportedOperationException("Not yet supported. Do we need this at all?");
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		GoogleOauthToken got = (GoogleOauthToken) token;
		User user = serviceUser.getUserByMail(got.getGoogleEmail());
		if (user != null) {
			SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), new SimpleByteSource(Base64.decode(user.getSalt())), getName());
			return auth;
		}
		return null;
	}

	@Override
	public Class getAuthenticationTokenClass() {
		return GoogleOauthToken.class;
	}

	/*
	 * This class does not enforce credentials-matching: this is left to googleOAuth.
	 * @return AllowAllCredentialsMatcher
	 */
	@Override
	public CredentialsMatcher getCredentialsMatcher() {
		return new AllowAllCredentialsMatcher();
	}
}
