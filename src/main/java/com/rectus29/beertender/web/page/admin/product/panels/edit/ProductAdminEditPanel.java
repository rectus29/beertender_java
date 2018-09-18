package com.rectus29.beertender.web.page.admin.product.panels.edit;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceProductDefinition;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.page.admin.productDefinition.edit.ProductDefifnitionAdminEditPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.math.BigDecimal;
import java.util.Arrays;

public abstract class ProductAdminEditPanel extends Panel {

	private static final Logger log = LogManager.getLogger(ProductAdminEditPanel.class);

	@SpringBean(name = "serviceProductDefinition")
	private IserviceProductDefinition serviceProductDefinition;
	@SpringBean(name = "servicePackaging")
	private IservicePackaging servicePackaging;
	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;

	private IModel<Product> productIModel;
	private IModel<ProductDefinition> productDefinitionIModel;
	private Form form;
	private BootstrapFeedbackPanel feed;

	public ProductAdminEditPanel(String id) {
		super(id);
		this.productIModel = new Model<>(new Product());
	}

	public ProductAdminEditPanel(String id, IModel<Product> model) {
		super(id, model);
		this.productIModel = model;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((form = new Form("form") {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new DropDownChoice<>("state",
						new PropertyModel<>(productIModel, "state"),
						Arrays.asList(State.values()),
						new ChoiceRenderer<>("name")
				).setRequired(true));

				WebMarkupContainer wmc, defEditPanel;
				add((wmc = new WebMarkupContainer("definitionPanel")));
				wmc.setOutputMarkupId(true);
				//add def selector
				wmc.add(new DropDownChoice<>("productDef",
						new PropertyModel<>(productIModel, "productDefinition"),
						serviceProductDefinition.getAll(Arrays.asList(State.ENABLE)),
						new ChoiceRenderer<>("name")
				).setRequired(true));
				wmc.add((defEditPanel = new WebMarkupContainer("defEditPanel"){
					@Override
					protected void onInitialize() {
						super.onInitialize();
						add(new TextField<String>("defName", new PropertyModel<>(productDefinitionIModel,"name")));
						add(new TextArea<String>("defDesc", new PropertyModel<>(productDefinitionIModel,"description")));
						add(new FileUploadField("defimg", new PropertyModel<>(productDefinitionIModel,"imagePath")));
					}
				}).setOutputMarkupId(true).setVisible(false));

				wmc.add(new AjaxLink("editdef") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						defEditPanel.setVisible(true);
						target.add(wmc);
					}
				});


				add(new DropDownChoice<>("packaging",
						new PropertyModel<>(productIModel, "packaging"),
						servicePackaging.getAll(Arrays.asList(State.ENABLE)),
						new ChoiceRenderer<>("name")
				).setRequired(true));
				add(new NumberTextField<>("price",
						new PropertyModel<BigDecimal>(productIModel, "price")
				).setRequired(true));

				add(new AjaxSubmitLink("submit") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						productIModel.setObject(serviceProduct.save(productIModel.getObject()));
						success(new ResourceModel("success").getObject());
						target.add(form);
						ProductAdminEditPanel.this.onSubmit(target, productIModel);
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

	public abstract void onSubmit(AjaxRequestTarget target, IModel<Product> productModel);

	public abstract void onCancel(AjaxRequestTarget target);

}
