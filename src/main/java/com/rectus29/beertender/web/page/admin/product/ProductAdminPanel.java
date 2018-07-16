package com.rectus29.beertender.web.page.admin.product;

import com.rectus29.beertender.web.page.admin.product.panels.list.ProductAdminListPanel;
import org.apache.wicket.markup.html.panel.Panel;

/*-----------------------------------------------------*/
/*                     Adelya                          */
/*                                                     */
/*                Date: 13/07/2018 22:06               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
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
