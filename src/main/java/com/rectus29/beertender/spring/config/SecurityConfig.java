package com.rectus29.beertender.spring.config;

import com.rectus29.beertender.realms.BeerTenderRealms;
import com.rectus29.beertender.realms.GoogleOauthRealms;
import com.rectus29.beertender.service.BeerTenderSessionListener;
import org.apache.shiro.authc.AbstractAuthenticator;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 08/04/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
@Configuration
public class SecurityConfig {

	private final String cipherKey = "9Jo0hLz8XhDpPsOOYOnSUA==";

	@Bean
	public BeerTenderRealms beerTenderRealms() {
		return new BeerTenderRealms();
	}

	@Bean
	public GoogleOauthRealms googleOauthRealms() {
		return new GoogleOauthRealms();
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public EhCacheManager ehCacheManager() {
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
		return ehCacheManager;
	}

	@Bean(name = "cipherKeyBytes")
	public MethodInvokingFactoryBean cipherKeyBytes() {
		MethodInvokingFactoryBean cipherKeyBytes = new MethodInvokingFactoryBean();
		cipherKeyBytes.setTargetClass(org.apache.shiro.codec.Base64.class);
		cipherKeyBytes.setTargetMethod("decode");
		cipherKeyBytes.setArguments(new Object[]{cipherKey});
		return cipherKeyBytes;
	}

	@Autowired
	@Bean(name = "authenticator")
	public ModularRealmAuthenticator authenticator(BeerTenderRealms beerTenderRealms, GoogleOauthRealms googleOauthRealms) {
		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
		authenticator.setRealms(Arrays.asList(beerTenderRealms, googleOauthRealms));
		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
		return authenticator;

	}

	@Autowired
	@Bean(name = "myRememberMeManager")
	public CookieRememberMeManager myRememberMeManager(MethodInvokingFactoryBean cipherKeyBytes) {
		CookieRememberMeManager myRememberMeManger = new CookieRememberMeManager();
		myRememberMeManger.setCipherKey(cipherKey.getBytes());
		return myRememberMeManger;
	}

	@Autowired
	@Bean
	public org.apache.shiro.mgt.SecurityManager securityManager(Realm beerTenderRealms, Realm googleOauthRealms, AbstractAuthenticator authenticator, CacheManager cacheManager, SessionManager sessionMngr) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealms(Arrays.asList(beerTenderRealms, googleOauthRealms));
		securityManager.setAuthenticator(authenticator);
		securityManager.setCacheManager(cacheManager);
		securityManager.setSessionManager(sessionMngr);
		return securityManager;
	}

	@Bean
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionListeners(Arrays.asList(new BeerTenderSessionListener()));
		return sessionManager;
	}

	@Autowired
	@Bean(name = "ShiroFilter")
	public FactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		return factoryBean;
	}

}
