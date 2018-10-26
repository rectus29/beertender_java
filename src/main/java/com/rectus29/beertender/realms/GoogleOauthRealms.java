package com.rectus29.beertender.realms;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.entities.resource.impl.AvatarResource;
import com.rectus29.beertender.enums.UserAuthentificationType;
import com.rectus29.beertender.service.IserviceUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 21/09/2018 12:44               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Component
public class GoogleOauthRealms extends BeerTenderRealms {

	private Logger logger = LogManager.getLogger(BeerTenderRealms.class);
	public static final String REALM_NAME = "GoogleOauthRealms";

	public GoogleOauthRealms() {
		setName(REALM_NAME);
	}

	@Autowired
	public void setServiceUser(IserviceUser serviceUser) {
		setName(REALM_NAME);
		this.serviceUser = serviceUser;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		GoogleOauthToken got = (GoogleOauthToken) token;
		User user = this.serviceUser.getUserByMail(got.getGoogleEmail());
		if (user != null) {
			//if a first login with google
			if (user.getUserAuthentificationType() == UserAuthentificationType.NONE) {
				//update DATA from google
				user.setFirstName(got.getGivenName());
				user.setLastName(got.getFamilyName());
				user.setUuid(got.getUserId());
				//clear password
				user.setPassword("NO-PASSWORD-" + UUID.randomUUID().toString());
				//set login mode
				user.setUserAuthentificationType(UserAuthentificationType.GOOGLE);
				user.setAvatarImage(new AvatarResource().setImageBytes(got.getAvatarBytes()));
				user = this.serviceUser.save(user);
			}
			if (user.getUserAuthentificationType() == UserAuthentificationType.GOOGLE) {
				//auth
				SimpleAuthenticationInfo auth = new SimpleAuthenticationInfo(user.getId(), user.getPassword(), new SimpleByteSource(Base64.decode(user.getSalt())), getName());
				return auth;
			}
		}
		return null;
	}

	@Override
	public Class getAuthenticationTokenClass() {
		return GoogleOauthToken.class;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
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
