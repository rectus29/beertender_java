package com.rectus29.beertender.web.page.mailbox;

import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.event.EditObjectEvent;
import com.rectus29.beertender.event.OnEvent;
import com.rectus29.beertender.service.IserviceRecipient;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.bookmarkabletabbedpanel.BookmarkableTabbedPanel;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.page.mailbox.panels.mailbox.MailBoxDisplayPanel;
import com.rectus29.beertender.web.page.mailbox.panels.maildisplay.MailDisplayPanel;
import com.rectus29.beertender.web.page.mailbox.panels.send.SendMailPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;

public class MailBoxPage extends BeerTenderPage {

	public static String PANEL = "panel";
	public static String SUMMARYPANEL = "summary";
	public static String ARCHIVE = "archive";
	public static String SENDED = "sended";
	public static String ALL = "all";
	public static String VIEW = "view";
	public static String RECID = "recid";
	public static String TRASH = "trash";
	public static String SEND = "send";
	public static String TO = "to";
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceRecipient")
	private IserviceRecipient serviceRecipient;
	private String panel;
	private Long target;
	private WebMarkupContainer wmc;
	private BookmarkableTabbedPanel tabbed;
	private BeerTenderModal modal;

	public MailBoxPage(PageParameters parameters) {
		super(parameters);
		panel = (parameters.toString().length() > 0) ? parameters.get(this.PANEL).toString() : null;
		target = (!(parameters.get(this.RECID).isNull())) ? Long.parseLong(parameters.get(this.RECID).toString()) : null;

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
		add((wmc = new WebMarkupContainer("wmc")).setOutputMarkupId(true));
		wmc.add(new EmptyPanel("panel"));

		final LoadableDetachableModel<User> ldmUser = new LoadableDetachableModel<User>() {
			@Override
			protected User load() {
				return serviceUser.getCurrentUser();
			}
		};

		ArrayList<AbstractTab> tabbedList = new ArrayList<AbstractTab>();

//        if (target != null && panel != null && panel.equals(VIEW)) {
//            final Recipient recip = serviceRecipient.get(target);
//            if (recip != null && (recip.getTarget().equals(serviceUser.getCurrentUser()) || recip.getMessage().getAuthor().equals(serviceUser.getCurrentUser()))) {
//                modal.setTitle(recip.getMessage().getSubject());
//                modal.setContent(new MailDisplayPanel(modal.getContentId(), new Model<Recipient>() {
//                    @Override
//                    public Recipient getObject() {
//                        return recip;
//                    }
//                }));
//                modal.show(new AjaxRequestTarget(getPage()).);
//            }
//        }

		if (target != null && panel != null && panel.equals(SEND)) {
			tabbedList.add(new AbstractTab(new ResourceModel("MailBoxPage.send")) {
				@Override
				public WebMarkupContainer getPanel(String panelId) {
					return new SendMailPanel(panelId, target);
				}

				@Override
				public IModel<String> getTitle() {
					return new ResourceModel("MailBoxPage.send");
				}
			});
		}

		tabbedList.add(new AbstractTab(new ResourceModel("MailBoxPage.all")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new MailBoxDisplayPanel(panelId, ldmUser, MailBoxDisplayPanel.ALL);
			}

			@Override
			public IModel<String> getTitle() {
				return new ResourceModel("MailBoxPage.mailbox");
			}
		});

		tabbedList.add(new AbstractTab(new ResourceModel("MailBoxPage.sended")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new MailBoxDisplayPanel(panelId, ldmUser, MailBoxDisplayPanel.SENDED);
			}

			@Override
			public IModel<String> getTitle() {
				return new ResourceModel("MailBoxPage.sended");
			}
		});

		tabbedList.add(new AbstractTab(new ResourceModel("archive")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new MailBoxDisplayPanel(panelId, ldmUser, MailBoxDisplayPanel.ARCHIV);
			}

			@Override
			public IModel<String> getTitle() {
				return new ResourceModel("archive");
			}
		});

		tabbedList.add(new AbstractTab(new ResourceModel("trash")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new MailBoxDisplayPanel(panelId, ldmUser, MailBoxDisplayPanel.TRASH);
			}

			@Override
			public IModel<String> getTitle() {
				return new ResourceModel("trash");
			}
		});

//        tabbedList.add(new AbstractTab(new Model<String>(VIEW)) {
//            @Override
//            public WebMarkupContainer getPanel(String panelId) {
//                return new MailBoxDisplayPanel(panelId, ldmUser, MailBoxDisplayPanel.ALL);
//            }
//
//            @Override
//            public IModel<String> getTitle() {
//                return new Model<String>(new ResourceModel("VIEW"));
//            }
//        });

		add((tabbed = new BookmarkableTabbedPanel("tabbed", tabbedList)).setOutputMarkupId(true));

	}

	@OnEvent
	public void onEvent(EditObjectEvent event) {
		wmc.addOrReplace(new MailDisplayPanel("panel", event.getObj()));
		wmc.setVisible(true);
		//tabbed.set.setVisible(false);
		event.getTarget().add(wmc);

	}

	@Override
	public String getTitleContribution() {
		return new ResourceModel("messageBox").getObject();
	}

}
