package com.rectus29.beertender.web.page.product;

import com.rectus29.beertender.web.page.base.ProtectedPage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

public class ProductPage extends ProtectedPage {

    public static String PRODUCT_ID = "productId";
    private StringValue productId;


    public ProductPage(PageParameters parameters) {
        super(parameters);
        this.productId = getPageParameters().get(PRODUCT_ID);
        if(this.productId.isNull() || this.productId.isEmpty()){
            setResponsePage(ErrorPage.class, new PageParameters().add("ErrorCode", 422));
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();


    }

    @Override
    public void onEvent(IEvent event) {
        super.onEvent(event);
    }
}
