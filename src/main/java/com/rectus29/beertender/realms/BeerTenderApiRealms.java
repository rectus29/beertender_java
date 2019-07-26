package com.rectus29.beertender.realms;

import com.rectus29.beertender.service.IserviceUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
public class BeerTenderApiRealms extends BeerTenderRealms {

	public static final String REALM_NAME = "BeerTenderApiRealms";
	private static final Logger logger = LogManager.getLogger(BeerTenderApiRealms.class);
	protected IserviceUser serviceUser;

	/**
	 * Instantiates a new Eve tool realms.
	 */
	public BeerTenderApiRealms() {
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
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals) {
		return super.getAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

}
