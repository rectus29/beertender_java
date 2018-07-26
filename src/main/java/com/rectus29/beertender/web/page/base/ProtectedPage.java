package com.rectus29.beertender.web.page.base;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
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
