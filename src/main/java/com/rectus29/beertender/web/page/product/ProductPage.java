package com.rectus29.beertender.web.page.product;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.enums.ErrorCode;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.component.productimage.ProductImage;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.page.billspage.BillsPage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.math.BigDecimal;

public class ProductPage extends BeerTenderPage {

    public static String PRODUCT_ID = "productId";
	private Integer qte = null;
	private Form form= null;

    @SpringBean(name = "serviceProduct")
    private IserviceProduct serviceProduct;
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;
    private StringValue productId;
    private IModel<Product> productIModel;

    public ProductPage(PageParameters parameters) {
        super(parameters);
        this.productId = getPageParameters().get(PRODUCT_ID);
        if (this.productId.isNull() || this.productId.isEmpty()) {
            setResponsePage(ErrorPage.class, new PageParameters().add("ErrorCode", 422));
        }
        this.productIModel = new Model<>(serviceProduct.get(this.productId.toLong()));
        if (productIModel.getObject() == null) {
            setResponsePage(ErrorPage.class, new PageParameters().add("ErrorCode", 404));
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new ProductImage("beerImg", productIModel));
        add(new Label("beerName", productIModel.getObject().getName()));
        add(new Label("beerText", productIModel.getObject().getDescription()).setEscapeModelStrings(false));

		add((this.form = new Form("form"))
				.add(new NumberTextField<Integer>("qte", new PropertyModel<Integer>(this, "qte")))
				.add(new AjaxSubmitLink("addToCart") {
					@Override
					protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
						Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
						if (order == null) {
							setResponsePage(ErrorPage.class, new PageParameters().add("errorCode", ErrorCode.NO_ORDER_FOUND));
						}
						if(ProductPage.this.qte != null && ProductPage.this.qte > 0){
							order.addProduct(
								ProductPage.this.productIModel.getObject(),
								ProductPage.this.qte
							);
							serviceOrder.save(order);
							success("Ajout réalisé avec succés");
						}else{
							info("Veuillez saisir une quantité");
						}
						target.add(form);
						send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));

					}
				})
				.add(new BootstrapFeedbackPanel("feed").setOutputMarkupId(true))
				.add(new CurrencyLabel("beerPrize", new Model<BigDecimal>(productIModel.getObject().getPrice())))
				.setOutputMarkupId(true)
		);
    }

    @Override
    public String getTitle() {
        return productIModel.getObject().getName();
    }
}
