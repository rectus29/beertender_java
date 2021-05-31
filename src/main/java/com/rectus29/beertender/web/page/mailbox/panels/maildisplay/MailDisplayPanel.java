package com.rectus29.beertender.web.page.mailbox.panels.maildisplay;


import com.rectus29.beertender.entities.IDecorableElement;
import com.rectus29.beertender.entities.mail.Recipient;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.event.DeleteMailEvent;
import com.rectus29.beertender.event.OnEvent;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.event.ReplyMailEvent;
import com.rectus29.beertender.service.IserviceRecipient;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.BeerTenderConfig;
import com.rectus29.beertender.web.component.avatarimage.AvatarImage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class MailDisplayPanel extends Panel {

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceRecipient")
	private IserviceRecipient serviceRecipient;
	private IModel<Recipient> model;
	private AjaxLink archiv, read, delete, reply;
	private Boolean print = false;
	private WebMarkupContainer buttonContainer;

	public MailDisplayPanel(String id, IModel<Recipient> model) {
		super(id, model);
		this.model = model;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((buttonContainer = new WebMarkupContainer("buttonContainer") {
			@Override
			public boolean isVisible() {
				return !isPrint();
			}
		}).setOutputMarkupId(true));

//		buttonContainer.add(new BookmarkablePageLink("print", MailPrintPage.class, new PageParameters().add(MailPrintPage.RID, model.getObject().getId())));

		buttonContainer.add((reply = new AjaxLink("replyLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				send(getPage(), Broadcast.BREADTH, new ReplyMailEvent(target, model));
			}
		}));
		buttonContainer.add((archiv = new AjaxLink("archiveLink") {
			@Override
			public void onClick(AjaxRequestTarget ajaxRequestTarget) {
				model.getObject().setArchived((!model.getObject().getArchived()));
				serviceRecipient.save(model.getObject());
				send(getPage(), Broadcast.BREADTH, new RefreshEvent(ajaxRequestTarget));
			}
		}).add(new Label("archiveLabel", new Model<String>() {
			@Override
			public String getObject() {
				return (model.getObject().getArchived()) ? new ResourceModel("unarchive").getObject() : new ResourceModel("MailBoxDisplay.archive").getObject();
			}
		})).setOutputMarkupId(true));

		buttonContainer.add((read = new AjaxLink("markAsNotReadLink") {
			@Override
			public void onClick(AjaxRequestTarget ajaxRequestTarget) {
				model.getObject().setReaded((!model.getObject().getReaded()));
				serviceRecipient.save(model.getObject());
				send(getPage(), Broadcast.BREADTH, new RefreshEvent(ajaxRequestTarget));
			}
		}).add(new Label("markAsNotReadLabel", new Model<String>() {
			@Override
			public String getObject() {
				return (model.getObject().getReaded()) ? new ResourceModel("markAsNotRead").getObject() : new ResourceModel("markAsRead").getObject();
			}
		}).setOutputMarkupId(true)));

		buttonContainer.add((delete = new AjaxLink("deleteLink") {
			@Override
			public void onClick(AjaxRequestTarget ajaxRequestTarget) {
				if (model.getObject().getState().equals(State.DELETED)) {
					model.getObject().setState(State.ENABLE);
				} else {
					model.getObject().setState(State.DELETED);
				}
				serviceRecipient.save(model.getObject());
				send(getPage(), Broadcast.BREADTH, new DeleteMailEvent(ajaxRequestTarget));

			}
		}).add(new Label("deleteLabel", new Model<String>() {
			@Override
			public String getObject() {
				return (model.getObject().getState().equals(State.ENABLE)) ? new ResourceModel("Mailbox.delete").getObject() : new ResourceModel("Mailbox.restore").getObject();
			}
		}).setOutputMarkupId(true)));

		add(new Label("mailData", model.getObject().getMessage().getText()).setEscapeModelStrings(false));
		add(new AvatarImage("avatar", new Model<>(model.getObject().getMessage().getAuthor())));
		add(new Label("userLink", model.getObject().getMessage().getAuthor()));
		add(new Label("target", model.getObject().getTarget().getFormattedName()));
		add(new Label("date", BeerTenderConfig.get().dateHourFormat(model.getObject().getCreated())));

		model.getObject().setReaded(true);
		serviceRecipient.save(model.getObject());
	}

	@OnEvent
	public void onEvent(RefreshEvent event) {
		model.detach();
		event.getTarget().add(archiv, read, delete);
	}

	public Boolean isPrint() {
		return print;
	}
}
