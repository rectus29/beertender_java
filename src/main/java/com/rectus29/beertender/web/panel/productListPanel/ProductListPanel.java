package com.rectus29.beertender.web.panel.productListPanel;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.entities.User;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.page.product.ProductPage;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ProductListPanel extends Panel {

	@SpringBean(name = "serviceProduct")
	private IserviceProduct serviceProduct;
	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;
	@SpringBean(name = "serviceCategory")
	private IserviceCategory serviceCategory;
	private List<Category> filter = new ArrayList<>();
	private WebMarkupContainer wmc;

	public ProductListPanel(String id) {
		super(id);
	}

	public ProductListPanel(String id, List<Category> categoryList) {
		super(id);
		this.filter = categoryList;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		LoadableDetachableModel<List<Product>> ldm = new LoadableDetachableModel<List<Product>>() {
			@Override
			protected List<Product> load() {
				List<Product> out = new ArrayList<>();
				if (BeerTenderSession.get().getBeerTenderFilter().isEmpty()) {
					out.addAll(serviceProduct.getAll());
				} else {
					out.addAll(serviceProduct.getFilteredProduct(BeerTenderSession.get().getBeerTenderFilter()));
				}
				out.sort(Comparator.comparingInt(Product::getSeqOrder));
				return out;
			}
		};
		add((wmc = new WebMarkupContainer("wmc")).setOutputMarkupId(true));

		wmc.add(new ListView<Product>("lv", ldm) {
			@Override
			protected void populateItem(ListItem<Product> item) {
//                item.add(new ProductImage("productImage", item.getModel()));
				item.add(new Label("productName", item.getModelObject().getName()));
				item.add(new Label("shortdesc", StringUtils.left(item.getModelObject().getProductDefinition().getDescription(), 110)));
				item.add(new Label("packaging", item.getModelObject().getPackaging().getName()));
				item.add(new CurrencyLabel("price", new Model<>(item.getModelObject().getPrice())));
				item.add(new BookmarkablePageLink<ProductPage>(
								"btnProduct",
								ProductPage.class,
								new PageParameters().add(ProductPage.PRODUCT_UID, item.getModelObject().getUniqueId())
						)
				);
				item.add(new ListView<Category>("rvCateg", item.getModelObject().getCategoryList()) {
					@Override
					protected void populateItem(ListItem<Category> item) {
						item.add(new Label("categBadge", item.getModelObject().getName()));
					}
				});
				item.add(new AjaxLink("bookmarkLink") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						User user = serviceUser.getCurrentUser();
						user.addBookmark(item.getModelObject());
						user = serviceUser.save(user);
						target.add(this);
					}
				}.add(new Label("bookmarkLabel")
						.add(new AttributeModifier("class", (serviceUser.getCurrentUser().getProductBookmarkList().contains(item.getModelObject())) ? "fa fa-star" : "fa fa-star-o"))
						).setOutputMarkupId(true)
								.setVisible(false)
				);
			}
		});


	}
}
