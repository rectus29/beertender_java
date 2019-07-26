package com.rectus29.beertender.realms;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.entities.resource.impl.AvatarResource;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.enums.UserAuthentificationType;
import com.rectus29.beertender.service.IserviceUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/*-----------------------------------------------------*/
/*                       Rectus29                      */
/*                Date: 21/09/2018 12:44               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Component
public class GoogleOauthRealms extends BeerTenderRealms {

	public static final String REALM_NAME = "GoogleOauthRealms";

	public GoogleOauthRealms() {
		setName(REALM_NAME);
		setCredentialsMatcher(new AllowAllCredentialsMatcher());
	}

	@Autowired
	@Override
	public void setServiceUser(IserviceUser serviceUser) {
		super.setServiceUser(serviceUser);
	}

	@Override
	@Transactional
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		GoogleOauthToken got = (GoogleOauthToken) token;
		User user = this.serviceUser.getUserByMail(got.getGoogleEmail());
		if (user != null && user.getState().isEnable()) {
			//update DATA from google
			user.setFirstName(got.getGivenName());
			user.setLastName(got.getFamilyName());
			user.setUuid(got.getUserId());
			user.updateAvatarImage(new AvatarResource().setImageBytes(got.getAvatarBytes()));
			//if a first login with google
			if (user.getUserAuthentificationType() == UserAuthentificationType.NONE) {
				//clear password
				user.setPassword("NO-PASSWORD-" + UUID.randomUUID().toString());
				//set login mode
				user.setUserAuthentificationType(UserAuthentificationType.GOOGLE);
				user.setState(State.ENABLE);
			}
			user = this.serviceUser.save(user);
			if (user.getUserAuthentificationType() == UserAuthentificationType.GOOGLE) {
				//auth
				return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), new SimpleByteSource(Base64.decode(user.getSalt())), getName());
			}
		}
		return null;
	}

	@Override
	public Class getAuthenticationTokenClass() {
		return GoogleOauthToken.class;
	}
}
