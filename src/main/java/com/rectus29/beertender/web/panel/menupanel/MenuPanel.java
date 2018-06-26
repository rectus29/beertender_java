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

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.page.admin.AdminPage;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.page.profile.ProfilePage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.shiro.page.LogoutPage;

import java.util.List;

public class MenuPanel extends Panel {

    @SpringBean(name = "serviceCategory")
    private IserviceCategory serviceCategory;

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;

    public MenuPanel(String id) {
        super(id);


        add((new WebMarkupContainer("wmc") {
            @Override
            protected void onInitialize() {
                super.onInitialize();

                LoadableDetachableModel model = new LoadableDetachableModel<List<Category>>() {
                    @Override
                    protected List<Category> load() {
                        return serviceCategory.getRootCateg();
                    }
                };

                add(new ListView<Category>("rvLink", model) {
                    @Override
                    protected void populateItem(ListItem<Category> listItem) {
                        listItem.add(
                                new BookmarkablePageLink(
                                        "link",
                                        HomePage.class,
                                        new PageParameters()
                                                .add("categ", listItem.getModelObject().getId())
                                ).add(new Label("label",listItem.getModelObject().getName()))
                        );

                    }
                });
            }
        }).setOutputMarkupId(true));


    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new BookmarkablePageLink("admin", AdminPage.class) {
            @Override
            public boolean isVisible() {
                return serviceUser.getCurrentUser().isAdmin();
            }
        });
        add(new BookmarkablePageLink<LogoutPage>("logout", LogoutPage.class));
        add(new BookmarkablePageLink<ProfilePage>("profile", ProfilePage.class));
    }
}
