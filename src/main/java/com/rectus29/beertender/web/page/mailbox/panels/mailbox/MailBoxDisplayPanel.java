package com.rectus29.beertender.web.page.mailbox.panels.mailbox;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.entities.mail.Message;
import com.rectus29.beertender.entities.mail.Recipient;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.event.DeleteMailEvent;
import com.rectus29.beertender.event.OnEvent;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.event.ReplyMailEvent;
import com.rectus29.beertender.service.IserviceMessage;
import com.rectus29.beertender.service.IserviceRecipient;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.Config;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.mailbox.panels.maildisplay.MailDisplayPanel;
import com.rectus29.beertender.web.page.mailbox.panels.send.SendMailPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.*;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.*;

public class MailBoxDisplayPanel extends Panel {

	public static String ALL = "all", ARCHIV = "archiv", TRASH = "trash", SHOP = "shop", SENDED = "sended";
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceRecipient")
	private IserviceRecipient serviceRecipient;
	@SpringBean(name = "serviceMessage")
	private IserviceMessage serviceMessage;
	private String mode;
	private IModel<User> model;
	private LoadableDetachableModel<List<DisplayRow>> ldmRecipient;
	private Form form;
	private BeerTenderModal modal;
	private Boolean checked;
	private WebMarkupContainer preview;
	private PageableListView<DisplayRow> pglv;

	public MailBoxDisplayPanel(String id, IModel<User> model) {
		super(id, model);
		this.model = model;
	}

	public MailBoxDisplayPanel(String id, IModel<User> model, String mode) {
		super(id, model);
		this.model = model;
		this.mode = mode;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((form = new Form("form")).setOutputMarkupId(true));
		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
		add((preview = new WebMarkupContainer("mailPreview")).setOutputMarkupId(true));
		preview.add((new WebMarkupContainer("mailContent")).setOutputMarkupId(true));
		ldmRecipient = new LoadableDetachableModel<List<DisplayRow>>() {
			@Override
			protected List<DisplayRow> load() {
				ArrayList<DisplayRow> out = new ArrayList<DisplayRow>();
				HashMap<String, Object> param = new HashMap<String, Object>();

				if (mode == null) {
					param.put("state", State.ENABLE);
					param.put("readed", false);
					param.put("target", model.getObject());
					for (Recipient temp : serviceRecipient.getAllByProperties(param))
						out.add(new DisplayRow(temp));
				} else {
					if (mode.equals(SENDED)) {
						param.put("author", model.getObject());
						param.put("state", State.ENABLE);
						for (Message temp : serviceMessage.getAllByProperties(param))
							for (Recipient tempRec : temp.getRecipientList())
								out.add(new DisplayRow(tempRec, temp, FromTo.TO));
					} else {
						if ((mode.equals(ALL) || mode.equals(SHOP))) {
							param.put("state", State.ENABLE);
							param.put("archived", false);
						} else if (mode.equals(ARCHIV)) {
							param.put("archived", true);
							param.put("state", State.ENABLE);
						} else if (mode.equals(TRASH)) {
							param.put("author", model.getObject());
							param.put("state", State.DELETED);
							for (Message temp : serviceMessage.getAllByProperties(param))
								for (Recipient tempRec : temp.getRecipientList())
									out.add(new DisplayRow(tempRec, temp, FromTo.TO));
							param.clear();
							param.put("state", State.DELETED);
						}
						param.put("target", model.getObject());
						for (Recipient tempRec : serviceRecipient.getAllByProperties(param))
							out.add(new DisplayRow(tempRec, FromTo.FROM));
					}
				}

				Collections.sort(out, new Comparator<DisplayRow>() {
					@Override
					public int compare(DisplayRow o1, DisplayRow o2) {
						return -(o1.getRecipient().getCreated().compareTo(o2.getRecipient().getCreated()));
					}
				});
				return out;
			}
		};

		form.add((pglv = new PageableListView<DisplayRow>("lvRecipient", ldmRecipient, 6) {
			@Override
			protected void populateItem(final ListItem<DisplayRow> components) {

				components.add(new CheckBox("check", new PropertyModel<Boolean>(components.getModel(), "check")).setOutputMarkupId(true));
				if ((!components.getModelObject().getRecipient().getReaded()) && components.getModelObject().getFromTo().equals(FromTo.FROM))
					components.add(new AttributeModifier("class", (components.getModelObject().getRecipient().getReaded()) ? "" : " unread"));

				components.add(new Label("from", (components.getModelObject().getFromTo().equals(FromTo.FROM)) ? new ResourceModel("from").getObject() + " " + components.getModelObject().getRecipient().getMessage().getAuthor().getFormattedName() : new ResourceModel("to").getObject() + " " + components.getModelObject().getRecipient().getTarget().getFormattedName()));
				components.add(new WebMarkupContainer("tag").add(new AttributeModifier("class", (components.getModelObject().getFromTo().equals(FromTo.FROM) ? "icon-arrow-right" : "icon-arrow-left"))));
				components.add(new AjaxLink("link2") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						preview.addOrReplace(new MailDisplayPanel("mailContent", new Model<Recipient>(components.getModelObject().getRecipient())));
						target.add(preview);
					}
				}.add(new Label("subject", components.getModelObject().getRecipient().getMessage().getSubject())));
				components.add(new Label("date", Config.get().dateHourFormat(components.getModelObject().getRecipient().getCreated())));
				components.setOutputMarkupId(true);

			}
		}).setOutputMarkupId(true));
		form.add(new PagingNavigator("nav", pglv) {
			@Override
			public boolean isVisible() {
				return (ldmRecipient.getObject().size() > 6);
			}
		});
		form.add(new Label("nb", new LoadableDetachableModel<String>() {
			@Override
			protected String load() {
				return ldmRecipient.getObject().size() + "";
			}
		}).setOutputMarkupId(true));

		form.add(new AjaxSubmitLink("archiveAll") {
			@Override
			public boolean isVisible() {
				return (mode == "all");
			}

			@Override
			public void onSubmit(AjaxRequestTarget target, Form form) {
				for (DisplayRow temp : ldmRecipient.getObject()) {
					if (temp.getCheck()) {
						temp.getRecipient().setArchived(true);
						serviceRecipient.save(temp.getRecipient());
					}
				}
				checked = false;
				ldmRecipient.detach();
				preview.addOrReplace(new WebMarkupContainer("mailContent"));
				target.add(form, preview);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		});
		form.add(new AjaxSubmitLink("markAll") {
			@Override
			public boolean isVisible() {
				return (mode == "all" || mode == "archiv");
			}

			@Override
			public void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form form) {
				for (DisplayRow temp : ldmRecipient.getObject()) {
					if (temp.getCheck()) {
						temp.getRecipient().setReaded(false);
						serviceRecipient.save(temp.getRecipient());
					}
				}
				checked = false;
				ldmRecipient.detach();
				ajaxRequestTarget.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		});
		form.add(new AjaxSubmitLink("deleteAll") {
			@Override
			public boolean isVisible() {
				return (mode == "all" || mode == "archiv" || mode == "sended");  //Todo utiliser .equals() eventuellement ?
			}

			@Override
			public void onSubmit(AjaxRequestTarget ajaxRequestTarget, Form form) {
				for (DisplayRow temp : ldmRecipient.getObject()) {
					if (temp.getCheck()) {
						if (mode == "sended") {
							temp.getMessage().setState(State.DELETED);
							serviceMessage.save(temp.getMessage());
						} else {
							temp.getRecipient().setState((temp.getRecipient().getState().equals(State.DELETED)) ? State.DISABLE : State.DELETED);
							temp.getRecipient().setReaded(true);
							serviceRecipient.save(temp.getRecipient());
						}
					}
				}
				checked = false;
				ldmRecipient.detach();
				preview.addOrReplace(new WebMarkupContainer("mailContent"));
				ajaxRequestTarget.add(form, preview);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		});
		form.add(new AjaxSubmitLink("restoreAll") {
			@Override
			public boolean isVisible() {
				return (mode == "trash");
			}

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				for (DisplayRow temp : ldmRecipient.getObject()) {
					if (temp.getCheck()) {
						if (temp.fromTo == FromTo.TO) {
							temp.getMessage().setState(State.ENABLE);
							serviceMessage.save(temp.getMessage());
						} else {
							temp.getRecipient().setState(State.ENABLE);
							serviceRecipient.save(temp.getRecipient());
						}
					}
				}
				checked = false;
				ldmRecipient.detach();
				preview.addOrReplace(new WebMarkupContainer("mailContent"));
				target.add(form, preview);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				//To change body of implemented methods use File | Settings | File Templates.
			}
		});
		form.add(new AjaxSubmitLink("unarchiveAll") {
			@Override
			public boolean isVisible() {
				return (mode == "archiv");
			}

			@Override
			public void onSubmit(AjaxRequestTarget target, Form form) {
				for (DisplayRow temp : ldmRecipient.getObject()) {
					if (temp.getCheck()) {
						temp.getRecipient().setArchived(false);
						serviceRecipient.save(temp.getRecipient());
					}
				}
				checked = false;
				ldmRecipient.detach();
				preview.addOrReplace(new WebMarkupContainer("mailContent"));
				target.add(form, preview);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		});
		form.add(new AjaxSubmitLink("markAsReadAll") {
			@Override
			public boolean isVisible() {
				return (mode == "archiv" || mode == "all");
			}

			@Override
			public void onSubmit(AjaxRequestTarget target, Form form) {
				for (DisplayRow temp : ldmRecipient.getObject()) {
					if (temp.getCheck()) {
						temp.getRecipient().setReaded(true);
						serviceRecipient.save(temp.getRecipient());
					}
				}
				checked = false;
				ldmRecipient.detach();
				target.add(form);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
			}
		});
		form.add(new AjaxCheckBox("checkAll", new PropertyModel<Boolean>(this, "checked")) {
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				for (DisplayRow li : ldmRecipient.getObject()) {
					li.setCheck(this.getConvertedInput());
				}
				target.add(form);
			}
		});

	}

	@Override
	protected void onDetach() {
		super.onDetach();
		ldmRecipient.detach();
	}

	@OnEvent
	public void onEvent(RefreshEvent event) {
		model.detach();
		preview.addOrReplace(new WebMarkupContainer("mailContent"));
		event.getTarget().add(form, preview);
	}

	@OnEvent
	public void onEvent(ReplyMailEvent event) {
		modal.setTitle(new ResourceModel("reply").getObject());
		modal.setContent(new SendMailPanel(modal.getContentId(), event.getObj()));
		ldmRecipient.detach();
		preview.addOrReplace(new WebMarkupContainer("mailContent"));
		modal.show(event.getTarget(), BeerTenderModal.ModalFormat.AUTO);
		event.getTarget().add(modal, form, preview);
	}

	@OnEvent
	public void onEvent(DeleteMailEvent event) {
		modal.close(event.getTarget());
		ldmRecipient.detach();
		preview.addOrReplace(new WebMarkupContainer("mailContent"));
		event.getTarget().add(modal, form, preview);
	}


	public enum FromTo {FROM, TO}

	private class DisplayRow {
		private FromTo fromTo = FromTo.FROM;
		private Recipient recipient;
		private Message message;
		private Boolean check = false;

		public DisplayRow(Recipient recipient) {
			this.recipient = recipient;
		}

		public DisplayRow(Recipient recipient, Message message, FromTo fromTo) {
			this.recipient = recipient;
			this.message = message;
			this.fromTo = fromTo;
		}

		public DisplayRow(Recipient recipient, FromTo fromTo) {
			this.recipient = recipient;
			this.fromTo = fromTo;
		}

		public Recipient getRecipient() {
			return recipient;
		}

		public void setRecipient(Recipient recipient) {
			this.recipient = recipient;
		}

		public Message getMessage() {
			return message;
		}

		public Boolean getCheck() {
			return check;
		}

		public void setCheck(Boolean check) {
			this.check = check;
		}

		private FromTo getFromTo() {
			return fromTo;
		}

		private void setFromTo(FromTo fromTo) {
			this.fromTo = fromTo;
		}
	}
}
