package com.rectus29.beertender.web.page.product;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.OrderItem;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.enums.ErrorCode;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.component.bootstrapfeedbackpanel.BootstrapFeedbackPanel;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.component.productimage.ProductImage;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

public class ProductPage extends BeerTenderPage {
	public static String PRODUCT_UID = "productUniqueId";
	private Long qte = null;
    @SpringBean(name = "serviceProduct")
    private IserviceProduct serviceProduct;
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;
	private IModel<Product> productIModel;

    public ProductPage(PageParameters parameters) {
        super(parameters);
		StringValue productUniqueId = getPageParameters().get(PRODUCT_UID);
		if (productUniqueId.isNull() || productUniqueId.isEmpty()) {
            setResponsePage(ErrorPage.class, new PageParameters().add("ErrorCode", 422));
        }
		this.productIModel = new Model<>(serviceProduct.getByUniqueId(productUniqueId.toString()));
        if (productIModel.getObject() == null) {
            setResponsePage(ErrorPage.class, new PageParameters().add("ErrorCode", 404));
        }
		Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
		if (order != null) {
			for (OrderItem temp : order.getOrderItemList()) {
				if (temp.getProduct() == productIModel.getObject()) {
					qte = temp.getQuantity();
				}
			}
		}
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
		add(new ProductImage("beerImg", productIModel));
        add(new Label("beerName", productIModel.getObject().getName()));
        add(new Label("beerPack", productIModel.getObject().getPackaging().getName()));
        add(new Label("beerText", productIModel.getObject().getDescription()).setEscapeModelStrings(false));
		add(new ListView<Category>("rvCateg", productIModel.getObject().getCategoryList()){
			@Override
			protected void populateItem(ListItem<Category> item) {
				item.add(new Label("categBadge", item.getModelObject().getName()));
			}
		});
		add(new Form("form")
				.add(new NumberTextField<>("qte", new PropertyModel<Integer>(this, "qte")))
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
						} else {
							info("Veuillez saisir une quantité");
						}
						target.add(form);
						send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));
					}

					@Override
					public boolean isEnabled() {
						return BeerTenderSession.get().isOrderEnable();
					}
				})
				.add(new BootstrapFeedbackPanel("feed").setOutputMarkupId(true))
				.add(new CurrencyLabel("beerPrize", new Model<>(productIModel.getObject().getPrice())))
				.setOutputMarkupId(true)
		);
    }

    @Override
    public String getTitle() {
        return productIModel.getObject().getName();
    }
}
