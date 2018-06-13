package com.rectus29.beertender.web;

import com.rectus29.beertender.event.DispatchOnEventMethod;
import com.rectus29.beertender.realms.BeerTenderRealms;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.page.admin.AdminPage;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.page.mailbox.MailBoxPage;
import com.rectus29.beertender.web.page.profile.ProfilePage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import com.rectus29.beertender.web.security.forgotpassword.ForgotPasssword;
import com.rectus29.beertender.web.security.restorepassword.RestorePasswordPage;
import com.rectus29.beertender.web.security.signin.SigninPage;
import com.rectus29.beertender.web.security.signout.SignoutPage;
import com.rectus29.beertender.web.security.unauthorizedpage.UnauthorizedPage;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.ApplicationSettings;
import org.apache.wicket.settings.RequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.lang.Bytes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;

/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 	   */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class BeerTenderApplication extends WebApplication {
	private Config config;
	private String realmsName = BeerTenderRealms.class.getSimpleName();

	@Override
	public void init() {
		super.init();
		getDebugSettings().setAjaxDebugModeEnabled(true);
		//gestion de l'annotation @OnEvent
		getFrameworkSettings().add(new DispatchOnEventMethod());
		//gestion de spring
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));
		//taille max de l'upload
		getApplicationSettings().setDefaultMaximumUploadSize(Bytes.megabytes(100));
		getResourceSettings().setThrowExceptionOnMissingResource(false);
		//ligne pour forcer le raffraichissement sur history back
		getRequestCycleSettings().setRenderStrategy(RequestCycleSettings.RenderStrategy.ONE_PASS_RENDER);
		// Configure Shiro
		AnnotationsShiroAuthorizationStrategy authz = new AnnotationsShiroAuthorizationStrategy();
		getSecuritySettings().setAuthorizationStrategy(authz);
		getSecuritySettings().setUnauthorizedComponentInstantiationListener(
				new ShiroUnauthorizedComponentListener(
						SigninPage.class,
						UnauthorizedPage.class,
						authz)
		);

		config = Config.get();
		config.set(getServletContext().getRealPath("/"));

		mountPage("admin/#{panel}", AdminPage.class);
		mountPage("profile/#{panel}", ProfilePage.class);
		mountPage("mail/#{panel}", MailBoxPage.class);

		mountPage("unauthorized", UnauthorizedPage.class);
		mountPage("restorepassword/${uid}", RestorePasswordPage.class);
		mountPage("forgotPasssword", ForgotPasssword.class);
		mountPage("logout", SignoutPage.class);
		mountPage("login", SigninPage.class);

		ApplicationSettings settings = getApplicationSettings();
		settings.setAccessDeniedPage(UnauthorizedPage.class);
		settings.setPageExpiredErrorPage(SigninPage.class);
		settings.setInternalErrorPage(ErrorPage.class);
		getApplicationSettings().setUploadProgressUpdatesEnabled(true);
	}

	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	public Config getConfig() {
		return config;
	}

	public BeerTenderRealms getRealms() {
		return (BeerTenderRealms) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(realmsName);
	}

	public void updateRights() {
		((BeerTenderRealms) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean(realmsName)).clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new BeerTenderSession(request);
	}
}