package com.rectus29.beertender.web.page.admin.users;

/*-----------------------------------------------------*/
/* User: Rectus_29       Date: 17/09/13 14:40 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/


import com.rectus29.beertender.web.page.admin.users.panels.list.UserListPanel;
import org.apache.wicket.markup.html.panel.Panel;

public class UserAdminPanel extends Panel {

    public UserAdminPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new UserListPanel("tabbed"));
    }
}
