package com.rectus29.beertender.web.page.admin;

/*-----------------------------------------------------*/
/* User: Rectus_29      Date: 03/03/2015 16:19 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.web.page.admin.order.OrderSummaryPanel;
import com.rectus29.beertender.web.page.admin.product.ProductAdminPanel;
import com.rectus29.beertender.web.page.admin.server.ServerAdminPanel;
import com.rectus29.beertender.web.page.admin.timeframe.TimeFrameAdminPanel;
import com.rectus29.beertender.web.page.admin.users.UserAdminPanel;
import com.rectus29.beertender.web.page.base.BeerTenderBasePage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

@ShiroSecurityConstraint(
		constraint = ShiroConstraint.HasPermission, value = "admin:access"
)
public class AdminPage extends BeerTenderBasePage {

	private String PANEL = "panel";

	public AdminPage() {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		String pp = getPageParameters().get(PANEL).toString();

		add(new BookmarkablePageLink<AdminPage>("orderLink", AdminPage.class, new PageParameters().add(PANEL, "order")));
		add(new BookmarkablePageLink<AdminPage>("productLink", AdminPage.class, new PageParameters().add(PANEL, "product")));
		add(new BookmarkablePageLink<AdminPage>("timeframeLink", AdminPage.class, new PageParameters().add(PANEL, "timeframe")));
		add(new BookmarkablePageLink<AdminPage>("usersLink", AdminPage.class, new PageParameters().add(PANEL, "users")));
		add(new BookmarkablePageLink<AdminPage>("serverLink", AdminPage.class, new PageParameters().add(PANEL, "server")));

		//set panel for the given pageparametner
		if ("product".equals(pp)) {
			add(new ProductAdminPanel("panel"));
		} else if ("timeframe".equals(pp)) {
			add(new TimeFrameAdminPanel("panel"));
		} else if ("users".equals(pp)) {
			add(new UserAdminPanel("panel"));
		} else if ("server".equals(pp)) {
			add(new ServerAdminPanel("panel"));
		} else {
			add(new OrderSummaryPanel("panel"));
		}
	}

	@Override
	public String getTitleContribution() {
		return new ResourceModel("administration").getObject();
	}

}
