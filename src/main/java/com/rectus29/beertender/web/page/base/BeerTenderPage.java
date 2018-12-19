package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.web.panel.menupanel.MenuPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 06/07/2018 11:41               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BeerTenderPage extends BeerTenderBasePage {

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
}
