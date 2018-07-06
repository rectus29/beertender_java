package com.rectus29.beertender.web.panel.addtobillsmodalpanel;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.page.billspage.BillsPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class AddToBillsPanel extends Panel {

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;
    private Product product;
    private Integer qte = 1;

    public AddToBillsPanel(String id, Product product) {
        super(id);
        this.product = product;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("productName", this.product.getName()));
        add(new NumberTextField<Integer>("qte", new PropertyModel<Integer>(this, "qte")));
        add(new AjaxLink("addToCart") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                BeerTenderSession.get().getCart().addProduct(
                        AddToBillsPanel.this.product,
                        AddToBillsPanel.this.qte
                );
                setResponsePage(BillsPage.class);
            }
        });
    }
}
