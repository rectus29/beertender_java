package com.rectus29.beertender.web.security.signin;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.service.IserviceSession;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.page.base.BasePage;
import com.rectus29.beertender.web.security.maintenancepage.MaintenancePage;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class SigninPage extends BasePage {

	private static final Logger log = LogManager.getLogger(SigninPage.class);
	@SpringBean(name = "serviceSession")
	private IserviceSession serviceSession;
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceConfig")
	private IserviceConfig serviceConfig;
	private FeedbackPanel feed;
	private Form form;
	private String password;
	private String username;
	private Boolean rememberMe = false;
	private String session;

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((form = new Form("authenticationForm") {
			@Override
			public final void onSubmit() {
				if (login(getUsername(), getPassword(), getRememberMe())) {
					onSignInSucceeded();
				}
			}
		}).setOutputMarkupId(true));
		form.add(new EmailTextField("username", new PropertyModel<String>(this, "username")));
		form.add(new PasswordTextField("password", new PropertyModel<String>(this, "password")));
		form.add(new CheckBox("rememberMe", new PropertyModel<Boolean>(this, "rememberMe")));
		form.add((feed = new BootstrapFeedbackPanel("feedback")).setOutputMarkupId(true));

	}

	/**
	 * Sign in user if possible.
	 *
	 * @param username The username
	 * @param password The password
	 * @return True if signin was successful
	 */
	private boolean login(String username, String password, boolean rememberMe) {
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
		try {
			currentUser.login(token);
			serviceSession.addSubject(currentUser);
			return true;
		} catch (Exception ex) {
			error(getString("error-invalid-credential"));
		}
		return false;
	}

	private void onSignInSucceeded() {
		User u = serviceUser.getCurrentUser();
		u.setLastLogin(new java.util.Date());
		serviceUser.save(u);

		if (serviceConfig.getByProperty("key", "maintenance_mod", true).getValue().equals("1") && !serviceUser.getCurrentUser().isAdmin()) {
			throw new RestartResponseException(MaintenancePage.class);
		}
		continueToOriginalDestination();
		setResponsePage(getApplication().getHomePage());
	}

	@Override
	public String getTitleContribution() {
		return "Connection";
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		String javascript = "document.body.setAttribute('class', 'signin');";
		response.render(OnDomReadyHeaderItem.forScript(javascript));
	}

	private String getPassword() {
		return password;
	}

	private String getUsername() {
		return username;
	}

	private Boolean getRememberMe() {
		return rememberMe;
	}
}
