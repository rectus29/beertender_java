package com.rectus29.beertender.web.page.mailbox.panels.mailswitchpanel;


import com.rectus29.beertender.entities.mail.Recipient;
import com.rectus29.beertender.web.page.mailbox.panels.maildisplay.MailDisplayPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class MailSwitchPanel extends Panel {

	private IModel<Recipient> model;
	private WebMarkupContainer wmc;

	public MailSwitchPanel(String id, IModel<Recipient> model) {
		super(id, model);
		this.model = model;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((wmc = new WebMarkupContainer("wmc")).setOutputMarkupId(true));
		wmc.add(new MailDisplayPanel("panel", model) {

		});
	}

//    @OnEvent
//    public void onEvent(ReplyMailEvent event) {
//        wmc.addOrReplace(new SendMailPanel("panel", model));
//        event.getTarget().add(wmc);
//    }

}
