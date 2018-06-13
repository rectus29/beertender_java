package com.rectus29.beertender.web.page.admin;

/*-----------------------------------------------------*/
/* User: Rectus_29      Date: 03/03/2015 16:19 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.web.page.IMenuContributor;
import com.rectus29.beertender.web.page.admin.server.ServerAdminPanel;
import com.rectus29.beertender.web.page.admin.users.UserAdminPanel;
import com.rectus29.beertender.web.page.base.ProtectedPage;
import com.rectus29.beertender.web.panel.menucontributionpanel.MenuElement;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import java.util.ArrayList;
import java.util.List;

@ShiroSecurityConstraint(
		constraint = ShiroConstraint.HasPermission, value = "admin:access"
)
public class AdminPage extends ProtectedPage implements IMenuContributor {

	public static String SERVER = "server";
	public static String USER = "user";
	public static String PANEL = "panel";

	public AdminPage() {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		if (!getPageParameters().get(PANEL).isEmpty()) {
			if (USER.equals(getPageParameters().get(PANEL).toString())) {
				add(new UserAdminPanel("panel"));
			} else {
				add(new ServerAdminPanel("panel"));
			}
		}else{
			add(new ServerAdminPanel("panel"));
		}
	}

	@Override
	public String getTitleContribution() {
		return new ResourceModel("administration").getObject();
	}

	@Override
	public List<MenuElement> getMenuContribution() {
		List<MenuElement> out = new ArrayList<>();
		out.add(new MenuElement("User") {
			@Override
			public Link getLink() {
				return new BookmarkablePageLink<AdminPage>(getMenuElementMarkupID(), AdminPage.class, new PageParameters().add(PANEL, USER));
			}
		});
		out.add(new MenuElement("Server") {
			@Override
			public Link getLink() {
				return new BookmarkablePageLink<AdminPage>(getMenuElementMarkupID(), AdminPage.class, new PageParameters().add(PANEL, SERVER));
			}
		});
		return out;
	}
}
