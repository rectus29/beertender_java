package com.rectus29.beertender.web.page.base;

import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.component.andilmodal.WicketModal;
import com.rectus29.beertender.web.panel.cart.CartPanel;
import com.rectus29.beertender.web.panel.menupanel.MenuPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.shiro.ShiroConstraint;
import org.wicketstuff.shiro.annotation.ShiroSecurityConstraint;


@ShiroSecurityConstraint(constraint = ShiroConstraint.IsAuthenticated)
public class ProtectedPage extends BasePage {

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;
    private Label nbProductLabel;
    private WicketModal modal;

    public ProtectedPage() {
        super();

    }

    public ProtectedPage(IModel model) {
        super(model);

    }

    public ProtectedPage(PageParameters parameters) {
        super(parameters);

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add((modal = new WicketModal("modal")).setOutputMarkupId(true));
        add(new Label("login", serviceUser.getCurrentUser().getUserName()));
        add((new MenuPanel("menuPanel")).setOutputMarkupId(true));

        add(new AjaxLink("cartLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                modal.setTitle("Votre Panier");
                modal.setContent(new CartPanel(modal.getContentId()));
                modal.show(target);
            }
        }.add((nbProductLabel = new Label("nbProduct", BeerTenderSession.get().getCart().getCartRowList().size())).setOutputMarkupId(true)));
    }

    @Override
    public void onEvent(IEvent event) {
        if (event.getPayload() instanceof RefreshEvent) {
            ((RefreshEvent) event).getTarget().add(nbProductLabel);
        }
    }

}
