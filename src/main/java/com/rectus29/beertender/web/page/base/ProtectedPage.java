package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.panel.menucontributionpanel.MenuContributionPanel;
import com.rectus29.beertender.web.panel.menupanel.MenuPanel;
import com.rectus29.beertender.web.panel.serverstatepanel.ServerStatePanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;


@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class ProtectedPage extends BasePage {

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;

    public ProtectedPage() {
        super();

    }

    public ProtectedPage(IModel model) {
        super(model);

    }

    public ProtectedPage(PageParameters parameters) {
        super(parameters);

    }

    public BeerTenderSession getBeerTenderSession() {
        return (BeerTenderSession) getSession();
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add((new MenuPanel("menuPanel")).setOutputMarkupId(true));
    }
}
