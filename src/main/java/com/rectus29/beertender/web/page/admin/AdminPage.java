package com.rectus29.beertender.web.page.admin;

/*-----------------------------------------------------*/
/* User: Rectus_29      Date: 03/03/2015 16:19 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.web.component.bookmarkabletabbedpanel.BookmarkableNamedTab;
import com.rectus29.beertender.web.component.bookmarkabletabbedpanel.BookmarkableTabbedPanel;
import com.rectus29.beertender.web.page.admin.order.OrderSummaryPanel;
import com.rectus29.beertender.web.page.admin.product.ProductAdminPanel;
import com.rectus29.beertender.web.page.admin.server.ServerAdminPanel;
import com.rectus29.beertender.web.page.admin.timeframe.TimeFrameAdminPanel;
import com.rectus29.beertender.web.page.admin.users.UserAdminPanel;
import com.rectus29.beertender.web.page.base.BeerTenderBasePage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import java.util.ArrayList;
import java.util.List;

@ShiroSecurityConstraint(
		constraint = ShiroConstraint.HasPermission, value = "admin:access"
)
public class AdminPage extends BeerTenderBasePage {

	public AdminPage() {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		List<BookmarkableNamedTab> panelList = new ArrayList<>();
		panelList.add(new BookmarkableNamedTab(new Model<String>("Commandes")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new OrderSummaryPanel(panelId);
			}

			@Override
			public String getName() {
				return "commande";
			}
		});
		panelList.add(new BookmarkableNamedTab(new Model<String>("Produits")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new ProductAdminPanel(panelId);
			}

			@Override
			public String getName() {
				return "product";
			}
		});
		panelList.add(new BookmarkableNamedTab(new Model<String>("Time Frame")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new TimeFrameAdminPanel(panelId);
			}

			@Override
			public String getName() {
				return "timeframe";
			}
		});
		panelList.add(new BookmarkableNamedTab(new Model<String>("Utilisateur")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new UserAdminPanel(panelId);
			}

			@Override
			public String getName() {
				return "users";
			}
		});
		panelList.add(new BookmarkableNamedTab(new Model<String>("Serveur")) {
			@Override
			public WebMarkupContainer getPanel(String panelId) {
				return new ServerAdminPanel(panelId);
			}

			@Override
			public String getName() {
				return "server";
			}

		});
		add(new BookmarkableTabbedPanel<>("tabPanel", panelList, getPageParameters()));
	}

	@Override
	public String getTitleContribution() {
		return new ResourceModel("administration").getObject();
	}

}
