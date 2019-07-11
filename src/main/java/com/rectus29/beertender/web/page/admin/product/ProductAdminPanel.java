package com.rectus29.beertender.web.page.admin.product;

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.bootstraptabbedpanel.BootstrapAjaxTabbedPanel;
import com.rectus29.beertender.web.page.admin.packaging.list.PackagingAdminListPanel;
import com.rectus29.beertender.web.page.admin.priceedit.PriceEditPanel;
import com.rectus29.beertender.web.page.admin.product.panels.list.ProductAdminListPanel;
import com.rectus29.beertender.web.page.admin.productDefinition.list.ProductDefinitionAdminListPanel;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

//import com.rectus29.beertender.web.component.switchbutton.SwitchButton;

public class ProductAdminPanel extends Panel {

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	public ProductAdminPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();


		List<ITab> tabs = new ArrayList<>();
		tabs.add(new AbstractTab(new Model<>("Produits")) {
			@Override
			public Panel getPanel(String panelId) {
				return new ProductAdminListPanel(panelId);
			}
		});
		tabs.add(new AbstractTab(new Model<>("Edition des prix")) {
			@Override
			public Panel getPanel(String panelId) {
				return new PriceEditPanel(panelId);
			}
		});
		tabs.add(new AbstractTab(new Model<>("Definition")) {
			@Override
			public Panel getPanel(String panelId) {
				return new ProductDefinitionAdminListPanel(panelId);
			}
		});
		tabs.add(new AbstractTab(new Model<>("Packaging")) {
			@Override
			public Panel getPanel(String panelId) {
				return new PackagingAdminListPanel(panelId);
			}
		});
		add(new BootstrapAjaxTabbedPanel<>("tabbed", tabs));
	}
}
