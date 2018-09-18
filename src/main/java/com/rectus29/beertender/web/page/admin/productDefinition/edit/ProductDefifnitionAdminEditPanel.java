package com.rectus29.beertender.web.page.admin.productDefinition.edit;

import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceProductDefinition;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class ProductDefifnitionAdminEditPanel extends Panel {

	private static final Logger log = LogManager.getLogger(ProductDefifnitionAdminEditPanel.class);

	@SpringBean(name = "serviceProductDefinition")
	private IserviceProductDefinition serviceProductDefinition;
	@SpringBean(name = "servicePackaging")
	private IservicePackaging servicePackaging;
	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;

	private IModel<ProductDefinition> productDefinitionIModel;
	private Form form;
	private BootstrapFeedbackPanel feed;

	public ProductDefifnitionAdminEditPanel(String id) {
		super(id);
		this.productDefinitionIModel = new Model<ProductDefinition>(new ProductDefinition());
	}

	public ProductDefifnitionAdminEditPanel(String id, IModel<ProductDefinition> model) {
		super(id, model);
		this.productDefinitionIModel = model;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();



	}
}
