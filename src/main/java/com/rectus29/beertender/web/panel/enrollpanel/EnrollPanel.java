package com.rectus29.beertender.web.panel.enrollpanel;

import com.rectus29.beertender.entities.Role;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceRole;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                Date: 02/10/2018 13:46               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class EnrollPanel extends Panel {

	private String email;
	@SpringBean(name = "serviceRole")
	private IserviceRole serviceRole;

	public EnrollPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new Form("form")
				.add(new EmailTextField("mail", new PropertyModel<>(this, email)))
				.add(new BootstrapFeedbackPanel("feed"))
				.add(new AjaxSubmitLink("send") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						super.onSubmit(target, form);
						User enrollUser = new User();
						enrollUser.setEmail(email);
						enrollUser.setRole(serviceRole.getRoleByName("user"));
						enrollUser.setState(State.PENDING);
						success(new ResourceModel("success").getObject());
						target.add(form);
						EnrollPanel.this.onSubmit(target);
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

	protected void onSubmit(AjaxRequestTarget target) {
	}

	protected void onCancel(AjaxRequestTarget target) {
	}
}
