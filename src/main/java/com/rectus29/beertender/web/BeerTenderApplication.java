package com.rectus29.beertender.web;

import com.rectus29.beertender.constant.BeertenderConstant;
import com.rectus29.beertender.event.DispatchOnEventMethod;
import com.rectus29.beertender.realms.BeerTenderRealms;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.tools.StringUtils;
import com.rectus29.beertender.web.page.admin.AdminPage;
import com.rectus29.beertender.web.page.billspage.BillsPage;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.page.mailbox.MailBoxPage;
import com.rectus29.beertender.web.page.product.ProductPage;
import com.rectus29.beertender.web.page.profile.ProfilePage;
import com.rectus29.beertender.web.page.test.TestPage;
import com.rectus29.beertender.web.security.enrollpage.EnrollmentPage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import com.rectus29.beertender.web.security.forgotpassword.ForgotPasssword;
import com.rectus29.beertender.web.security.maintenancepage.MaintenancePage;
import com.rectus29.beertender.web.security.restorepassword.RestorePasswordPage;
import com.rectus29.beertender.web.security.signin.SigninPage;
import com.rectus29.beertender.web.security.signout.SignoutPage;
import com.rectus29.beertender.web.security.unauthorizedpage.UnauthorizedPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.IPackageResourceGuard;
import org.apache.wicket.markup.html.SecurePackageResourceGuard;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.ApplicationSettings;
import org.apache.wicket.settings.RequestCycleSettings;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.util.lang.Bytes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.wicketstuff.shiro.annotation.AnnotationsShiroAuthorizationStrategy;
import org.wicketstuff.shiro.authz.ShiroUnauthorizedComponentListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 	   */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class BeerTenderApplication extends WebApplication {

	private static final Logger log = LogManager.getLogger(BeerTenderApplication.class);

	private Config config;
	private String buildNumber = "00";
	private String buildDate = "00";
	private String version = "DEV";
	private Properties properties = new Properties();

	@Override
	public void init() {
		super.init();
		log.debug("Init Beertender application");
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
		mountPage("product/#{productId}", ProductPage.class);
		mountPage("home/#{package}/#{category}", getHomePage());
		mountPage("order/", BillsPage.class);
		mountPage("maintenance/", MaintenancePage.class);

		mountPage("unauthorized", UnauthorizedPage.class);
		mountPage("restorepassword/${uid}", RestorePasswordPage.class);
		mountPage("enroll/${token}", EnrollmentPage.class);
		mountPage("forgotPasssword", ForgotPasssword.class);
		mountPage("logout", SignoutPage.class);
		mountPage("login", SigninPage.class);
		mountPage("test", TestPage.class);

		ApplicationSettings settings = getApplicationSettings();
		settings.setAccessDeniedPage(UnauthorizedPage.class);
		settings.setPageExpiredErrorPage(SigninPage.class);
		settings.setInternalErrorPage(ErrorPage.class);
		getApplicationSettings().setUploadProgressUpdatesEnabled(true);

		IPackageResourceGuard packageResourceGuard = this.getResourceSettings().getPackageResourceGuard();
		if (packageResourceGuard instanceof SecurePackageResourceGuard){
			SecurePackageResourceGuard guard = (SecurePackageResourceGuard) packageResourceGuard;
			//add pattren ti guard here for ressources model access
		}

		try {
			loadBeerTenderProperties();
		} catch (IOException e) {
			log.error("Error while loading property file");
		}

	}

	public static BeerTenderApplication getInstance(){
		return (BeerTenderApplication) get();
	}


	public ApplicationContext getAppCtx() {
		return WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	}

	public Class<? extends Page> getHomePage() {
		return HomePage.class;
	}

	public Config getConfig() {
		return config;
	}

	public BeerTenderRealms getRealms() {
		return (BeerTenderRealms) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean( BeerTenderRealms.class.getSimpleName());
	}

	public void updateRights() {
		((BeerTenderRealms) WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext()).getBean( BeerTenderRealms.class.getSimpleName())).clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}

	@Override
	public Session newSession(Request request, Response response) {
		return new BeerTenderSession(request);
	}

	private void loadBeerTenderProperties() throws IOException {
		//retrieve version info from properties
		InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream("beertender.properties");
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("version property file not found in the classpath");
			}
			// get the property value and print it out
			this.buildDate = StringUtils.isNotBlank(prop.getProperty("build.date"))? prop.getProperty("build.date"): buildDate;
			this.buildNumber = StringUtils.isNotBlank(prop.getProperty("build.revision"))? prop.getProperty("build.revision"): buildNumber;
			this.version = StringUtils.isNotBlank(prop.getProperty("build.version"))? prop.getProperty("build.version"): version;
			this.properties = prop;
		} catch (Exception e) {
			log.error("Error while parsing versio property file", e);
		} finally {
			if(inputStream != null){
				inputStream.close();
			}
		}
	}

	public Object getProperty(String propertyName){
		return this.properties.get(propertyName);
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public String getBuildDate() {
		return buildDate;
	}

	public String getVersion() {
		return version;
	}

	public String getDefaultSenderMail(){
		String defaultSender = (String) this.getProperty(BeertenderConstant.DEFAULTFROMMAIL);
		return (StringUtils.isNotBlank(defaultSender))? defaultSender : "noMail@nomail.fr";
	}
}