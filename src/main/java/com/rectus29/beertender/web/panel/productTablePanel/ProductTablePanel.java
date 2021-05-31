package com.rectus29.beertender.web.panel.productTablePanel;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.OrderItem;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ProductTablePanel extends Panel {

    @SpringBean(name = "serviceProduct")
    private IserviceProduct serviceProduct;
    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;
    @SpringBean(name = "serviceCategory")
    private IserviceCategory serviceCategory;
    @SpringBean(name = "serviceOrder")
    private IserviceOrder serviceOrder;
    private List<Category> filter = new ArrayList<>();
    private WebMarkupContainer wmc;

    public ProductTablePanel(String id) {
        super(id);
    }

    public ProductTablePanel(String id, List<Category> categoryList) {
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
                    out.addAll(serviceProduct.getAll(Product.getEnableState()));
                } else {
                    out.addAll(serviceProduct.getFilteredProduct(BeerTenderSession.get().getBeerTenderFilter()));
                }
                out.sort(Comparator.comparingInt(Product::getSeqOrder));
                return out;
            }
        };
        add((wmc = new WebMarkupContainer("wmc")).setOutputMarkupId(true));

        LoadableDetachableModel<List<OrderItem>> orderLdm = new LoadableDetachableModel<List<OrderItem>>() {
            @Override
            protected List<OrderItem> load() {
                Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
                if (order != null) {
                    return order.getOrderItemList();
                }
                return new ArrayList<>();
            }
        };

        wmc.add(new ListView<Product>("lv", ldm) {
            @Override
            protected void populateItem(ListItem<Product> item) {
                item.setOutputMarkupId(true);
                item.add(new Label("productName", item.getModelObject().getName()));
                item.add(new Label("packaging", item.getModelObject().getPackaging().getName()));
                item.add(new CurrencyLabel("price", new Model<>(item.getModelObject().getPrice())));
                OrderItem orderItem = orderLdm.getObject().stream().filter(el -> el.getProduct().equals(item.getModelObject())).findFirst().orElse(new OrderItem());
                item.add(new Label("cartQte", orderItem.getQuantity()));
//				item.add(new ListView<Category>("rvCateg", item.getModelObject().getCategoryList()) {
//					@Override
//					protected void populateItem(ListItem<Category> item) {
//						item.add(new Label("categBadge", item.getModelObject().getName()));
//					}
//				});
            }
        });


    }
}
