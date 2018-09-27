package com.rectus29.beertender.web.security.restorepassword;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.tools.DateUtils;
import com.rectus29.beertender.validator.PasswordPolicyValidator;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.page.base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.SimpleByteSource;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.Date;

/*-----------------------------------------------------*/
/* 	User: Rectus for          Date: 21/12/12 11:22 	   */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


public class RestorePasswordPage extends BasePage {

	private static final Logger log = LogManager.getLogger(RestorePasswordPage.class);

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

//    @SpringBean(name = "serviceMail")
//    private IserviceMail serviceMail;

	private String mdp1String = null;
	private String mdp2String = null;
	private User user = null;

	public RestorePasswordPage(PageParameters parameters) {

		Form form = new Form("wocRestorePasswordForm");
		add(form);

		final PasswordTextField mdp1 = new PasswordTextField("mdp1", new PropertyModel<String>(this, "mdp1String"));
		mdp1.add(new PasswordPolicyValidator());
		form.add(mdp1);
		final PasswordTextField mdp2 = new PasswordTextField("mdp2", new PropertyModel<String>(this, "mdp2String"));
		mdp1.add(new PasswordPolicyValidator());
		form.add(mdp2);

		form.add(new EqualPasswordInputValidator(mdp1, mdp2));

		final FeedbackPanel feed = new BootstrapFeedbackPanel("feedback");
		feed.setOutputMarkupId(true);
		add(feed);

		//test de validiter de la com..mismastore.session
		try {
			String uid = parameters.get("uid").toString();
			user = serviceUser.getByProperty("restoreSession", uid, true);
			//test de la com..mismastore.session
			if (user != null) {
				if (user.getRestoreSessionDate().before(DateUtils.addDays(new Date(), -1))) {
					throw new Exception("Votre session de restoration date de plus de 24h, veuillez recommencer la procédure");
				}
			} else {
				throw new Exception("Information de session invalide");
			}
		} catch (Exception ex) {
			form.setVisible(false);
			error(ex.getMessage());
		}

		final AjaxSubmitLink submit = new AjaxSubmitLink("submit") {
			@Override
			protected void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form<?> form) {
				user.setPassword(new Sha256Hash(mdp1String, new SimpleByteSource(Base64.decode(user.getSalt())), 1024).toBase64());
				user.setRestoreSession(null);
				serviceUser.save(user);
				info("Votre mot de passe a été changé avec succés,\n vous pouvez maintenant vous connecter à la platforme");
				ajaxRequestTarget.add(feed);
			}

			@Override
			protected void onError(AjaxRequestTarget ajaxRequestTarget, Form<?> form) {
				error("Erreur lors du traitement");
				ajaxRequestTarget.add(feed);
			}
		};
		form.add(submit);
	}


	public String getMdp1String() {
		return mdp1String;
	}

	public void setMdp1String(String mdp1String) {
		this.mdp1String = mdp1String;
	}

	public String getMdp2String() {
		return mdp2String;
	}

	public void setMdp2String(String mdp2String) {
		this.mdp2String = mdp2String;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
