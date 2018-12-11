package com.rectus29.beertender.web.panel.enrollpanel;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceMail;
import com.rectus29.beertender.service.IserviceRole;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                Date: 02/10/2018 13:46               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class EnrollPanel extends Panel {

	@SpringBean(name = "serviceRole")
	private IserviceRole serviceRole;
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceMail")
	private IserviceMail serviceMail;
	private Model<User> userModel;


	public EnrollPanel(String id) {
		super(id);
		this.userModel = new Model<User>(new User()
				.setRole(serviceRole.getRoleByName("user"))
				.setState(State.PENDING)
		);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Form("form")
				.add(new TextField<String>("lastName", new PropertyModel<>(userModel, "lastName")))
				.add(new TextField<String>("firstname", new PropertyModel<>(userModel, "firstName")))
				.add(new EmailTextField("mail", new PropertyModel<>(userModel, "email")))
				.add(new BootstrapFeedbackPanel("feed"))
				.add(new AjaxSubmitLink("save") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						super.onSubmit(target, form);
						userModel.setObject(serviceUser.save(userModel.getObject()));
						success(new ResourceModel("success").getObject());
						target.add(form);
						EnrollPanel.this.onSubmit(target, userModel);
					}
				})
				.add(new AjaxSubmitLink("saveAndSend") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						super.onSubmit(target, form);
						userModel.setObject(serviceUser.save(userModel.getObject()));
						success(new ResourceModel("success").getObject());
						target.add(form);
						serviceMail.sendEnrollmentMail(userModel.getObject());
						EnrollPanel.this.onSubmit(target, userModel);
					}
				})
				.add(new AjaxLink("cancel") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						EnrollPanel.this.onCancel(target);
					}
				})
				.setOutputMarkupId(true)
		);
	}

	protected void onSubmit(AjaxRequestTarget target, IModel<User> enroledUserModel) {
	}

	protected void onCancel(AjaxRequestTarget target) {
	}
}
