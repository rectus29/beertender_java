package com.rectus29.beertender.web.security.enrollpage;

import com.rectus29.beertender.entities.Token;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.enums.UserAuthentificationType;
import com.rectus29.beertender.service.IserviceToken;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.validator.PasswordPolicyValidator;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.page.base.BasePage;
import com.rectus29.beertender.web.security.restorepassword.RestorePasswordPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.SimpleByteSource;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                     rectus29                        */
/*                                                     */
/*                Date: 11/12/2018 15:19               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class EnrollmentPage extends BasePage {

	private static final Logger log = LogManager.getLogger(RestorePasswordPage.class);

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	@SpringBean(name = "serviceToken")
	private IserviceToken serviceToken;

	private String mdp1String, mdp2String = null;
	private PasswordTextField mdp1, mdp2;
	private User user = null;
	private FeedbackPanel feed;
	private String tokenString;
	private String emailString;
	private Token token;

	public EnrollmentPage(PageParameters parameters) {
		//retreive token
		tokenString = parameters.get("token").toString();
		try {
			token = serviceToken.getByProperty("token", tokenString, true);
			if (token != null) {
				if (token.isExpired()) {
					throw new Exception("Votre lien n'est plus valable");
				} else {
					//retrieve the user for the given token
					user = serviceUser.get(token.getObjectId());
				}
			} else {
				throw new Exception("Information de session invalide");
			}
		} catch (Exception ex) {
			error(ex.getMessage());
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form form = new Form("enrollForm") {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new EmailTextField("mail", new PropertyModel<String>(EnrollmentPage.this, "emailString")).setRequired(true));
				add((mdp1 = new PasswordTextField("mdp1", new PropertyModel<String>(EnrollmentPage.this, "mdp1String"))).add(new PasswordPolicyValidator()).setRequired(true));
				add((mdp2 = new PasswordTextField("mdp2", new PropertyModel<String>(EnrollmentPage.this, "mdp2String"))).add(new PasswordPolicyValidator()).setRequired(true));
				add(new EqualPasswordInputValidator(mdp1, mdp2));
				add(new AjaxSubmitLink("submit") {
					@Override
					protected void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form<?> form) {
						if (user.getEmail().equals(emailString)) {
							//set user password
							user.setPassword(new Sha256Hash(mdp1String, new SimpleByteSource(Base64.decode(user.getSalt())), 1024).toBase64());
							user.setState(State.ENABLE);
							user.setUserAuthentificationType(UserAuthentificationType.EMBED);
							serviceUser.save(user);
							//burn the token
							token.setState(State.DISABLE);
							serviceToken.save(token);
							info("Votre mot de passe a été changé avec succés,\n vous pouvez maintenant vous connecter à la platforme");
						} else {
							info("Le mail saisi est inconnu");
						}
						ajaxRequestTarget.add(feed);
					}

					@Override
					protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> form) {
						error("Erreur lors du traitement");
						ajaxRequestTarget.add(feed);
					}
				});
			}

			@Override
			public boolean isVisible() {
				return !EnrollmentPage.this.getFeedbackMessages().hasMessage(FeedbackMessage.ERROR);
			}
		};
		add((feed = new BootstrapFeedbackPanel("feedback")).setOutputMarkupId(true));
		add(form);

	}
}
