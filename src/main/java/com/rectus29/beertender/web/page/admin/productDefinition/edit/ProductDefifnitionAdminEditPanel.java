package com.rectus29.beertender.web.page.admin.productDefinition.edit;

import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.service.IserviceProductDefinition;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

public abstract class ProductDefifnitionAdminEditPanel extends Panel {

	private static final Logger log = LogManager.getLogger(ProductDefifnitionAdminEditPanel.class);

	@SpringBean(name = "serviceProductDefinition")
	private IserviceProductDefinition serviceProductDefinition;

	private IModel<ProductDefinition> productDefinitionIModel;
	private Form form;
	private BootstrapFeedbackPanel feed;

	public ProductDefifnitionAdminEditPanel(String id) {
		super(id);
		this.productDefinitionIModel = new Model<>(new ProductDefinition());
	}

	public ProductDefifnitionAdminEditPanel(String id, IModel<ProductDefinition> model) {
		super(id, model);
		this.productDefinitionIModel = model;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((form = new Form("form") {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new TextField<>("defName", new PropertyModel<String>(productDefinitionIModel, "name")).setRequired(true));
				add(new TextArea<>("defDesc", new PropertyModel<String>(productDefinitionIModel, "description")).setRequired(true));
				add(new AjaxSubmitLink("submit") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						productDefinitionIModel.setObject(serviceProductDefinition.save(productDefinitionIModel.getObject()));
						success(new ResourceModel("success").getObject());
						target.add(form);
						ProductDefifnitionAdminEditPanel.this.onSubmit(target, productDefinitionIModel);
					}

					@Override
					public void onError(AjaxRequestTarget target, Form<?> form) {
						target.add(feed);
					}

				});
				add(new AjaxLink("cancel") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						onCancel(target);
					}
				});
				add((feed = new BootstrapFeedbackPanel("feed")).setOutputMarkupId(true));
			}
		}).setOutputMarkupId(true));

	}

	public abstract void onSubmit(AjaxRequestTarget target, IModel<ProductDefinition> productModel);

	public abstract void onCancel(AjaxRequestTarget target);

}
