package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.component.wicketmodal.WicketModal;
import com.rectus29.beertender.web.page.admin.AdminPage;
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
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;


@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class ProtectedPage extends BasePage {


    public ProtectedPage() {
        super();

    }

    public ProtectedPage(IModel model) {
        super(model);

    }

    public ProtectedPage(PageParameters parameters) {
        super(parameters);

    }



}
