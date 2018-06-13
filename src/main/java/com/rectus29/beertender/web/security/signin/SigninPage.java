package com.rectus29.beertender.web.security.signin;

import com.rectus29.beertender.entities.core.User;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.service.IserviceSession;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.BootStrapFeedbackPanel.BootStrapFeedbackPanel;
import com.rectus29.beertender.web.page.base.BasePage;
import com.rectus29.beertender.web.security.maintenancepage.MaintenancePage;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
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
				if (login(getUsername(), getPassword(), false)) {
					onSignInSucceeded();
				}
			}
		}).setOutputMarkupId(true));
		form.add(new TextField<String>("username", new PropertyModel<String>(this, "username")));
		form.add(new PasswordTextField("password", new PropertyModel<String>(this, "password")));
		form.add((feed = new BootStrapFeedbackPanel("feedback")).setOutputMarkupId(true));

	}

	/**
	 * Sign in user if possible.
	 *
	 * @param username The username
	 * @param password The password
	 * @return True if signin was successful
	 */
	public boolean login(String username, String password, boolean rememberMe) {
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

	protected void onSignInSucceeded() {
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

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}
}
