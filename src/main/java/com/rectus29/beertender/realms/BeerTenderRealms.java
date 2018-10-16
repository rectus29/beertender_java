package com.rectus29.beertender.realms;

import com.rectus29.beertender.entities.Permission;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.UserAuthentificationType;
import com.rectus29.beertender.service.IserviceUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
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
/*                Date: 13/06/2018 16:28               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

@Component
public class BeerTenderRealms extends AuthorizingRealm {

	public static final String REALM_NAME = "BeerTenderRealms";
	private static final Logger logger = LogManager.getLogger(BeerTenderRealms.class);
	protected IserviceUser serviceUser;

	/**
	 * Instantiates a new Eve tool realms.
	 */
	public BeerTenderRealms() {
		setName(REALM_NAME);
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName(Sha256Hash.ALGORITHM_NAME);
		matcher.setHashIterations(1024);
		matcher.setStoredCredentialsHexEncoded(false);
		setCredentialsMatcher(matcher);
	}

	/**
	 * Sets service user.
	 *
	 * @param serviceUser the service user
	 */
	@Autowired
	public void setServiceUser(IserviceUser serviceUser) {
		setName(REALM_NAME);
		this.serviceUser = serviceUser;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = this.serviceUser.getByProperty("email", token.getUsername(), true);
		if (user != null) {
			if (user.getUserAuthentificationType() == UserAuthentificationType.NONE) {
				//set login mode
				user.setUserAuthentificationType(UserAuthentificationType.EMBED);
				user = this.serviceUser.save(user);
			}
			return new SimpleAuthenticationInfo(user.getId(), user.getPassword(), new SimpleByteSource(Base64.decode(user.getSalt())), getName());
		} else {
			return null;
		}
	}

	@Override
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		return super.getAuthorizationInfo(principals);
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Long userId;
		try {
			userId = (Long) principals.fromRealm(getName()).iterator().next();
			User user = this.serviceUser.get(userId);
			if (user != null) {
				SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				//apply permission for core only
				for (Permission rc : user.getRole().getPermissions()) {
					info.addStringPermission(rc.getCodeString());
				}
				logger.debug("permission loading done");
				return info;
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

}
