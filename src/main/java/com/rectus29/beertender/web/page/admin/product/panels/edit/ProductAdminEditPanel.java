package com.rectus29.beertender.web.page.admin.product.panels.edit;

import com.rectus29.beertender.entities.Product;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class ProductAdminEditPanel extends Panel {

	public ProductAdminEditPanel(String id) {
		super(id);
	}

	public ProductAdminEditPanel(String id, IModel<?> model) {
		super(id, model);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
	}

	public abstract void onSubmit(AjaxRequestTarget target, IModel<Product> productModel);

	public abstract void onCancel(AjaxRequestTarget target);

}
