package com.rectus29.beertender.web.panel.addtobillsmodalpanel;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.enums.ErrorCode;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.page.billspage.BillsPage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class AddToBillsPanel extends Panel {

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;
    @SpringBean(name = "serviceOrder")
    private IserviceOrder serviceOrder;
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
            	Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
            	if(order == null){
            		setResponsePage(ErrorPage.class, new PageParameters().add("errorCode", ErrorCode.NO_ORDER_FOUND));
				}
				order.addProduct(
                        AddToBillsPanel.this.product,
                        AddToBillsPanel.this.qte
                );
				serviceOrder.save(order);
                setResponsePage(BillsPage.class);
            }
        });
    }
}
