package com.rectus29.beertender.web.page.profile;

import com.rectus29.beertender.web.page.IMenuContributor;
import com.rectus29.beertender.web.page.base.ProtectedPage;
import com.rectus29.beertender.web.page.profile.account.AccountPanel;
import com.rectus29.beertender.web.panel.menucontributionpanel.MenuElement;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.ArrayList;
import java.util.List;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 28/02/2016                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class ProfilePage extends ProtectedPage implements IMenuContributor {

    public static String PANEL = "panel";
    public static String APIKEY = "apikey";
    public static String CHARACTER = "character";
    public static String ACCOUNT = "account";
    private Panel panel;

    public ProfilePage() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();


        add(panel = new AccountPanel("panel"));

    }

    @Override
    public String getTitleContribution() {
        return "Account management";
    }

    @Override
    public List<MenuElement> getMenuContribution() {
        List<MenuElement> out = new ArrayList<MenuElement>();
        out.add(new MenuElement("account") {
            @Override
            public Link getLink() {
                return new BookmarkablePageLink<ProfilePage>(getMenuElementMarkupID(), ProfilePage.class, new PageParameters().add(PANEL, ACCOUNT));
            }
        });
        out.add(new MenuElement("character") {
            @Override
            public Link getLink() {
                return new BookmarkablePageLink<ProfilePage>(getMenuElementMarkupID(), ProfilePage.class, new PageParameters().add(PANEL, CHARACTER));
            }
        });
        out.add(new MenuElement("apiKey") {
            @Override
            public Link getLink() {
                return new BookmarkablePageLink<ProfilePage>(getMenuElementMarkupID(), ProfilePage.class, new PageParameters().add(PANEL, APIKEY));
            }
        });
        return out;
    }
}
