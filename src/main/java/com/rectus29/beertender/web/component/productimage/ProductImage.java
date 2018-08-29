package com.rectus29.beertender.web.component.productimage;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.tools.StringUtils;
import com.rectus29.beertender.web.BeerTenderApplication;
import org.apache.wicket.Application;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.model.IModel;

/**
 * Created by IntelliJ IDEA.
 * User: Rectus for
 * Date: 06/03/12
 * Time: 15:13
 */
public class ProductImage extends WebComponent {

    private IModel<Product> model;

	public ProductImage(String id, IModel<Product> productModel) {
		super(id);
		this.model = productModel;
	}

    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
        if(StringUtils.isEmpty(model.getObject().getImagePath())){
			tag.put("src", ((BeerTenderApplication) Application.get()).getAppCtx().getApplicationName() + "/img/products/default-bottle.png");
		}else{
			tag.put("src", ((BeerTenderApplication) Application.get()).getAppCtx().getApplicationName() + "/" + model.getObject().getImagePath());
		}
    }
}