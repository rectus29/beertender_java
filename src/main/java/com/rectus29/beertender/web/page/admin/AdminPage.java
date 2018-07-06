package com.rectus29.beertender.web.page.admin;

/*-----------------------------------------------------*/
/* User: Rectus_29      Date: 03/03/2015 16:19 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.web.component.BootstrapTabbedPAnel.BootstrapAjaxTabbedPanel;
import com.rectus29.beertender.web.page.admin.order.OrderAdminPanel;
import com.rectus29.beertender.web.page.admin.server.ServerAdminPanel;
import com.rectus29.beertender.web.page.admin.users.UserAdminPanel;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.page.base.ProtectedPage;
import org.apache.wicket.extensions.ajax.markup.html.tabs.AjaxTabbedPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;

import java.util.ArrayList;
import java.util.List;

@ShiroSecurityConstraint(
        constraint = ShiroConstraint.HasPermission, value = "admin:access"
)
public class AdminPage extends BeerTenderPage {

    public static String SERVER = "server";
    public static String USER = "user";
    public static String PANEL = "panel";
    private AjaxTabbedPanel atp;

    public AdminPage() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        List<AbstractTab> panelList = new ArrayList<>();
        panelList.add(new AbstractTab(new Model<String>("Commandes")) {
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new OrderAdminPanel(panelId);
            }
        });
        panelList.add(new AbstractTab(new Model<String>("Serveur")) {
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new ServerAdminPanel(panelId);
            }
        });
        panelList.add(new AbstractTab(new Model<String>("Utilisateur")) {
            @Override
            public WebMarkupContainer getPanel(String panelId) {
                return new UserAdminPanel(panelId);
            }
        });
        add(atp = new BootstrapAjaxTabbedPanel("tabPanel", panelList));
    }

    @Override
    public String getTitleContribution() {
        return new ResourceModel("administration").getObject();
    }

}
