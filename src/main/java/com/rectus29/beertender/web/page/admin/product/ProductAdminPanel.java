package com.rectus29.beertender.web.page.admin.product;

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.page.admin.product.panels.list.ProductAdminListPanel;
import com.rectus29.beertender.web.page.admin.product.panels.priceedit.PriceEditPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

//import com.rectus29.beertender.web.component.switchbutton.SwitchButton;

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


//		add(new SwitchButton("productDefinition") {
//			@Override
//			public void onPush(AjaxRequestTarget target) {
//				wmc.addOrReplace(new ProductDefinitionAdminListPanel("tabbed"));
//				target.add(wmc);
//			}
//
//			@Override
//			public void onRelease(AjaxRequestTarget target) {
//				wmc.addOrReplace(new ProductAdminListPanel("tabbed"));
//				target.add(wmc);
//			}
//
//			@Override
//			protected String getOnLabel() {
//				return "Definition";
//			}
//
//			@Override
//			protected String getOffLabel() {
//				return "Produit";
//			}
//
//			@Override
//			public boolean isVisible() {
//				return SecurityUtils.getSubject().isPermitted("system:productdefinition:edit");
//			}
//		});

		add(new AjaxLink("priceEdit") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				wmc.addOrReplace(new PriceEditPanel("tabbed"));
				target.add(wmc);
			}
		});


		wmc.add(new ProductAdminListPanel("tabbed"));
	}
}
