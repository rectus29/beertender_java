package com.rectus29.beertender.web.page.mailbox.panels.send;


import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.entities.mail.Message;
import com.rectus29.beertender.entities.mail.Recipient;
import com.rectus29.beertender.service.IserviceMessage;
import com.rectus29.beertender.service.IserviceRecipient;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.page.mailbox.MailBoxPage;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class SendMailPanel extends Panel {

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceRecipient")
	private IserviceRecipient serviceRecipient;
	@SpringBean(name = "serviceMessage")
	private IserviceMessage serviceMessage;
	private IModel<User> model;
	private IModel<Recipient> parentMail;
	private Form form;
	private Message message = new Message();

	public SendMailPanel(String id, IModel model) {
		super(id, model);
		if (model.getObject() instanceof User) {
			this.model = model;
		} else if (model.getObject() instanceof Recipient) {
			this.model = new Model<User>(((Recipient) model.getObject()).getMessage().getAuthor());
			parentMail = model;
			this.message.setText("Re : \n" + parentMail.getObject().getMessage().getText());
			this.message.setSubject("Re : " + parentMail.getObject().getMessage().getSubject());
		}
	}

	public SendMailPanel(String id, long model) {
		super(id);
		User user = serviceUser.get(model);
		if (user == null) {
			throw new RestartResponseException(MailBoxPage.class);
		}
		this.model = new Model<User>(user);
	}


	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((form = new Form("form")).setOutputMarkupPlaceholderTag(true));
		form.add(new TextField<String>("to", new PropertyModel<String>(model, "username")).setEnabled(false));
		form.add(new TextField<String>("from", new Model<String>(serviceUser.getCurrentUser().getFormattedName())).setEnabled(false));
		form.add(new TextField<String>("subject", new PropertyModel<String>(this, "message.subject")).setRequired(true));
		form.add(new TextArea<String>("text", new PropertyModel<String>(this, "message.text")).setRequired(true));
		form.add(new BootstrapFeedbackPanel("feed"));

		form.add(new AjaxSubmitLink("submit") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				message.setAuthor(serviceUser.getCurrentUser());
				Recipient recip = new Recipient(model.getObject(), message);
				if (parentMail != null) {
					recip.setParentRecipient(parentMail.getObject());
				}
				serviceRecipient.save(recip);
				success(new ResourceModel("MailBoxPage.saveok").getObject());
				target.add(form);
				SendMailPanel.this.onSubmit(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				success(new ResourceModel("MailBoxPage.error").getObject());
			}

		});

		form.add(new AjaxLink("cancel") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				onCancel(target);
			}
		});

	}

	protected void onSubmit(AjaxRequestTarget target) {

	}

	protected void onCancel(AjaxRequestTarget target) {
		throw new RestartResponseException(MailBoxPage.class);
	}


}
