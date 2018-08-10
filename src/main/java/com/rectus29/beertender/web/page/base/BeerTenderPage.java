package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.wicketmodal.WicketModal;
import com.rectus29.beertender.web.page.admin.AdminPage;
import com.rectus29.beertender.web.page.home.HomePage;
import com.rectus29.beertender.web.panel.cartmodalpanel.CartModalPanel;
import com.rectus29.beertender.web.panel.menupanel.MenuPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
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
    @SpringBean(name = "serviceOrder")
    private IserviceOrder serviceOrder;
    private Label nbProductLabel;

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
        add((new MenuPanel("menuPanel")).setOutputMarkupId(true));
    }

    @Override
    public void onEvent(IEvent event) {
        if (event.getPayload() instanceof RefreshEvent) {
            ((RefreshEvent) event.getPayload()).getTarget().add(nbProductLabel);
        }
    }
}
