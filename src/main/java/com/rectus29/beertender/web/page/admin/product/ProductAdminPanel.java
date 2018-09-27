package com.rectus29.beertender.web.page.admin.product;

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.page.admin.product.panels.list.ProductAdminListPanel;
import com.rectus29.beertender.web.page.admin.productDefinition.list.ProductDefinitionAdminListPanel;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ProductAdminPanel extends Panel {

	private WebMarkupContainer wmc;
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	public ProductAdminPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((wmc = new WebMarkupContainer("wmc")).setOutputMarkupId(true));

		add(new AjaxLink("productDefinition") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				wmc.addOrReplace(new ProductDefinitionAdminListPanel("tabbed"));
				target.add(wmc);
			}

			@Override
			public boolean isVisible() {
				return SecurityUtils.getSubject().isPermitted("system:productdefinition:edit");
			}
		});

		wmc.add(new ProductAdminListPanel("tabbed"));
	}
}
