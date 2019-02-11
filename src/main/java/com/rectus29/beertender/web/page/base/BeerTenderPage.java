package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.avatarimage.AvatarImage;
import com.rectus29.beertender.web.panel.menupanel.MenuPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/07/2018 11:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderPage extends BeerTenderBasePage {

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;

    public BeerTenderPage() {
    }

    public BeerTenderPage(IModel model) {
        super(model);
    }

    public BeerTenderPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("login", serviceUser.getCurrentUser().getFormattedName()));
        add(new AvatarImage("avatarImg"));

        add((new MenuPanel("menuPanel")).setOutputMarkupId(true));
    }
}
