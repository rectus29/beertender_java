package com.rectus29.beertender.web.page.admin.productDefinition.edit;

import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceProductDefinition;
import com.rectus29.beertender.web.component.BootStrapFeedbackPanel.BootStrapFeedbackPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.math.BigDecimal;
import java.util.Arrays;

public abstract class ProductDefifnitionAdminEditPanel extends Panel {

	private static final Logger log = LogManager.getLogger(ProductDefifnitionAdminEditPanel.class);

	@SpringBean(name = "serviceProductDefinition")
	private IserviceProductDefinition serviceProductDefinition;
	@SpringBean(name = "servicePackaging")
	private IservicePackaging servicePackaging;
	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;

	private IModel<ProductDefinition> productIModel;
	private Form form;
	private BootStrapFeedbackPanel feed;

	public ProductDefifnitionAdminEditPanel(String id) {
		super(id);
		this.productIModel = new Model<ProductDefinition>(new ProductDefinition());
	}

	public ProductDefifnitionAdminEditPanel(String id, IModel<ProductDefinition> model) {
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
						new PropertyModel<>(productIModel, "role"),
						Arrays.asList(State.values()),
						new ChoiceRenderer<>("name")
				).setRequired(true));
				add(new DropDownChoice<>("productDef",
						new PropertyModel<>(productIModel, "productDefinition"),
						serviceProductDefinition.getAll(Arrays.asList(State.ENABLE)),
						new ChoiceRenderer<>("name")
				).setRequired(true));
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
//						productIModel.setObject(serviceProduct.save(productIModel.getObject()));
//						success(new ResourceModel("success").getObject());
//						target.add(form);
//						ProductDefifnitionAdminEditPanel.this.onSubmit(target, productIModel);
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
				add((feed = new BootStrapFeedbackPanel("feed")).setOutputMarkupId(true));
			}
		}).setOutputMarkupId(true));

	}

	public abstract void onSubmit(AjaxRequestTarget target, IModel<Product> productModel);

	public abstract void onCancel(AjaxRequestTarget target);

}
