package com.rectus29.beertender.web.panel.productListPanel;

import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Product;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceProduct;
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
import java.util.List;


public class ProductListPanel extends Panel {

    @SpringBean(name = "serviceProduct")
    private IserviceProduct serviceProduct;
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
                if (ProductListPanel.this.filter.isEmpty()) {
                    return serviceProduct.getAll();
                }
                return serviceProduct.getProductByCategory(ProductListPanel.this.filter);
            }
        };
        add((wmc = new WebMarkupContainer("wmc")).setOutputMarkupId(true));

        wmc.add(new ListView<Product>("lv", ldm) {
            @Override
            protected void populateItem(ListItem<Product> item) {
                item.add(new Label("productName", item.getModelObject().getName()));
                item.add(new CurrencyLabel("price", new Model<>(item.getModelObject().getPrice())));
                item.add(new Label("desc", item.getModelObject().getDescription()));

            }
        });


    }
}
