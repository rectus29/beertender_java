package com.rectus29.beertender.web.page.admin.product;

import com.rectus29.beertender.web.page.admin.product.panels.list.ProductAdminListPanel;
import org.apache.wicket.markup.html.panel.Panel;

public class ProductAdminPanel extends Panel {

	public ProductAdminPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new ProductAdminListPanel("tabbed"));
	}
}
