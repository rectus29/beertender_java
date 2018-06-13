package com.rectus29.beertender.web.panel.menupanel;


/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 27/12/14 22:19                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.panel.characterpanel.CharacterPanel;
import com.rectuscorp.evetool.service.IserviceUser;
import com.rectuscorp.evetool.web.page.admin.AdminPage;
import com.rectuscorp.evetool.web.page.crest.CrestPage;
import com.rectuscorp.evetool.web.page.market.MarketPage;
import com.rectuscorp.evetool.web.page.profile.ProfilePage;
import com.rectuscorp.evetool.web.panel.characterpanel.CharacterPanel;
import com.rectuscorp.evetool.web.panel.eveclockpanel.EveClockPanel;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.shiro.page.LogoutPage;

public class MenuPanel extends Panel {

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	public MenuPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new CharacterPanel("characterPanel"));


		add(new BookmarkablePageLink("crest", CrestPage.class));
		add(new BookmarkablePageLink("market", MarketPage.class){
			@Override
			public boolean isVisible() {
				return SecurityUtils.getSubject().isPermitted("market:access");
			}
		});

		add(new BookmarkablePageLink("admin", AdminPage.class) {
			@Override
			public boolean isVisible() {
				return serviceUser.getCurrentUser().isAdmin();
			}
		});
		add(new BookmarkablePageLink<LogoutPage>("logout", LogoutPage.class));
		add(new BookmarkablePageLink<ProfilePage>("profile", ProfilePage.class));

		//        mailNumber = new LoadableDetachableModel<Integer>() {
		//            @Override
		//            protected Integer load() {
		//                int out = 0;
		//                List<Recipient> temp = serviceRecipient.getUnreadFor(serviceUser.getCurrentUser());
		//                out = temp.size();
		//                return out;
		//            }
		//        };
		//
		//
		//
		//        add((bookmark = new WebMarkupContainer("user")));
		//        add((wallet = new WebMarkupContainer("shop")));
		//        add((friend = new WebMarkupContainer("distrib")));
		//        add((store = new WebMarkupContainer("store")));
		////        add((event = new WebMarkupContainer("event")));
		//        add((event = new WebMarkupContainer("promotion")));
		////        add((store = new WebMarkupContainer("store")));
		////        add((account = new WebMarkupContainer("account")));
		////        add((mailbox = new WebMarkupContainer("mailbox")));
		//
		//        String url = RequestCycle.get().getRequest().getClientUrl().toString().split("/")[0];
		//        if (url.equals("bookmark"))
		//            bookmark.add(new AttributeAppender("class", " active"));
		//        else if (url.equals("wallet"))
		//            wallet.add(new AttributeAppender("class", " active"));
		//        else if (url.equals("friend"))
		//            friend.add(new AttributeAppender("class", " active"));
		//        else if (url.equals("store"))
		//            store.add(new AttributeAppender("class", " active"));
		//        else if (url.equals("event"))
		//            event.add(new AttributeAppender("class", " active"));
		//        else if (url.equals("account"))
		//            account.add(new AttributeAppender("class", " active"));
		//        else if (url.equals("mailbox"))
		//            mailbox.add(new AttributeAppender("class", " active"));
		//
		////        bookmark.add(new Label("bookmarknumber", bookmarknumber)).setOutputMarkupId(true);
		////        wallet.add(new Label("creditnumber", creditnumber.getObject() + " €")).setOutputMarkupId(true);
		////        friend.add(new Label("friendnumber", friendnumber)).setOutputMarkupId(true);
		////        mailbox.add(new Label("mailNumber", mailNumber)).setOutputMarkupId(true);
		//        add(new Label("name", serviceUser.getCurrentUser().getFormattedName()));




	}
}
