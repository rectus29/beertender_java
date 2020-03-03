package com.rectus29.beertender.web.page.admin.product.panels.edit;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.entities.resource.impl.ImageResource;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceProductDefinition;
import com.rectus29.beertender.tools.ImageUtils;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.component.multipledropdownchoice.MultipleDropDownChoice;
import com.rectus29.beertender.web.component.productimage.ProductImage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.*;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ProductAdminEditPanel extends Panel {

	private static final Logger log = LogManager.getLogger(ProductAdminEditPanel.class);

	@SpringBean(name = "serviceProductDefinition")
	private IserviceProductDefinition serviceProductDefinition;
	@SpringBean(name = "servicePackaging")
	private IservicePackaging servicePackaging;
	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;
	@SpringBean(name = "serviceCategory")
	private IserviceCategory serviceCategory;

	private IModel<Product> productIModel;
	private IModel<ProductDefinition> productDefinitionIModel;
	private Form form;
	private FileUploadField fileUpload;
	private BootstrapFeedbackPanel feed;
	private DropDownChoice ddc;
	private WebMarkupContainer wmc, defEditPanel;

	public ProductAdminEditPanel(String id) {
		super(id);
		this.productIModel = new Model<>(new Product());
		this.productDefinitionIModel = new Model<>(new ProductDefinition());
	}

	public ProductAdminEditPanel(String id, IModel<Product> model) {
		super(id, model);
		this.productIModel = model;
		this.productDefinitionIModel = new Model<ProductDefinition>(model.getObject().getProductDefinition());
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add((form = new Form("form") {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				//add wmc
                add((wmc = new WebMarkupContainer("definitionPanel")).setOutputMarkupId(true));

                //add def selector
                wmc.add(ddc = new DropDownChoice<>("productDef",
                        productDefinitionIModel,
                        new LoadableDetachableModel<List<? extends ProductDefinition>>() {
                            @Override
                            protected List<? extends ProductDefinition> load() {
                                List<ProductDefinition> out = new ArrayList<>();
                                out.add(new ProductDefinition());
                                out.addAll(serviceProductDefinition.getAll(State.ENABLE));
                                return out;
                            }
                        },
                        new ChoiceRenderer<>("name")
                ));
				ddc.add(new AjaxFormComponentUpdatingBehavior("change"){
					@Override
					protected void onUpdate(AjaxRequestTarget target) {
						if(ddc.getDefaultModelObject() != null){
							defEditPanel.setVisible(true);
						}else{
							defEditPanel.setVisible(false);
						}
					}
				});
				add((defEditPanel = new WebMarkupContainer("defEditPanel"){
                    @Override
                    protected void onInitialize() {
                        super.onInitialize();
                        add(new TextField<String>("defName", new PropertyModel<>(productDefinitionIModel,"name")));
                        add(new TextArea<String>("defDesc", new PropertyModel<>(productDefinitionIModel,"description")));
                    }
                }).setOutputMarkupId(true).setVisible(false));

                add(new DropDownChoice<>("state",
                        new PropertyModel<>(productIModel, "state"),
                        Arrays.asList(State.values()),
                        new ChoiceRenderer<>("name")
                ).setRequired(true));

                add(new ProductImage("productImg", productIModel));

                add(fileUpload = new FileUploadField("defimg"));

				add(new DropDownChoice<>("packaging",
						new PropertyModel<>(productIModel, "packaging"),
						servicePackaging.getAll(Arrays.asList(State.ENABLE)),
						new ChoiceRenderer<>("name")
				).setRequired(true));

				add(new NumberTextField<>("price",
						new PropertyModel<BigDecimal>(productIModel, "price")
				).setRequired(true));

				add(new MultipleDropDownChoice("categ",
						new PropertyModel<Category>(productIModel, "categoryList"),
						serviceCategory.getAll(),
						new ChoiceRenderer<Category>("name")
				));

				add(new AjaxSubmitLink("submit") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						//set definition in product
						productIModel.getObject().setProductDefinition(productDefinitionIModel.getObject());
						if(fileUpload.getFileUpload() != null && ImageUtils.isAnImage(fileUpload.getFileUpload().getBytes())){
                            productIModel.getObject().setFileImage(new ImageResource().setImageBytes(fileUpload.getFileUpload().getBytes()));
						}
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
