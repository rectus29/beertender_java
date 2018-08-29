package com.rectus29.beertender.web.panel.addtobillsmodalpanel;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.enums.ErrorCode;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.productimage.ProductImage;
import com.rectus29.beertender.web.component.staticimage.StaticImage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public abstract class AddToBillsPanel extends Panel {

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;
	private IModel<Product> productIModel;
	private Integer qte = 1;

	public AddToBillsPanel(String id, IModel<Product> product) {
		super(id);
		this.productIModel = product;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add((new Form("form"))
				.add(new ProductImage("productImage", this.productIModel))
				.add(new Label("productName", this.productIModel.getObject().getName()))
				.add(new NumberTextField<Integer>("qte", new PropertyModel<Integer>(AddToBillsPanel.this, "qte")))
				.add(new AjaxSubmitLink("addToCart") {

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
						if (order == null) {
							setResponsePage(ErrorPage.class, new PageParameters().add("errorCode", ErrorCode.NO_ORDER_FOUND));
						}
						order.addProduct(
								AddToBillsPanel.this.productIModel.getObject(),
								AddToBillsPanel.this.qte
						);
						serviceOrder.save(order);
						AddToBillsPanel.this.onValid(target);
					}
				})
				.add(new AjaxLink("cancel") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						AddToBillsPanel.this.onCancel(target);
					}
				})
				.setOutputMarkupId(true));
	}

	public abstract void onCancel(AjaxRequestTarget target);

	public abstract void onValid(AjaxRequestTarget target);
}
