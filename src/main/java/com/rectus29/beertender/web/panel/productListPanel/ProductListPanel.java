package com.rectus29.beertender.web.panel.productListPanel;

import com.rectus29.beertender.entities.core.Category;
import com.rectus29.beertender.entities.core.Product;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceProduct;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;


public class ProductListPanel extends Panel {

    @SpringBean(name = "serviceProduct")
    private IserviceProduct serviceProduct;
    @SpringBean(name = "serviceCategory")
    private IserviceCategory serviceCategory;

    private long categId;

    public ProductListPanel(String id) {
        super(id);
    }

    public ProductListPanel(String id, int categId) {
        super(id);
        this.categId = categId;
    }

    public ProductListPanel(String id, List<Category> categoryList) {
        super(id);

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        LoadableDetachableModel<List<Product>> ldm = new LoadableDetachableModel<List<Product>>() {
            @Override
            protected List<Product> load() {
                List<Category> filterList = new ArrayList<>();
                //filterList.add(serviceCategory.get(categId));
                if (filterList.isEmpty()) {
                    return serviceProduct.getAll();
                }
                return serviceProduct.getProductByCategory(filterList);
            }
        };

//        add(new ListView<Product>("lvProduct", ldm) {
//            @Override
//            protected void populateItem(ListItem<Product> item) {
//
//            }
//        });


    }
}
