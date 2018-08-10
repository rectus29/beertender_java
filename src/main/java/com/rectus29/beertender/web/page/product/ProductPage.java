package com.rectus29.beertender.web.page.product;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.panel.addtobillsmodalpanel.AddToBillsPanel;
import com.rectus29.beertender.web.security.error.ErrorPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.math.BigDecimal;

public class ProductPage extends BeerTenderPage {

    public static String PRODUCT_ID = "productId";
    
    @SpringBean(name = "serviceProduct")
    private IserviceProduct serviceProduct;
    private StringValue productId;
    private Product product;

    public ProductPage(PageParameters parameters) {
        super(parameters);
        this.productId = getPageParameters().get(PRODUCT_ID);
        if (this.productId.isNull() || this.productId.isEmpty()) {
            setResponsePage(ErrorPage.class, new PageParameters().add("ErrorCode", 422));
        }
        this.product = serviceProduct.get(this.productId.toLong());
        if (product == null) {
            setResponsePage(ErrorPage.class, new PageParameters().add("ErrorCode", 404));
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("beerName", product.getName()));
        add(new Label("beerText", product.getDescription()));
        add(new CurrencyLabel("beerPrize", new Model<BigDecimal>(product.getPrice())));

        add(new AjaxLink("addToCartLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                modal.setContent(new AddToBillsPanel(modal.getContentId(), product));
                modal.show(target);
            }
        });

    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
    }

    @Override
    public String getTitle() {
        return product.getName();
    }
}