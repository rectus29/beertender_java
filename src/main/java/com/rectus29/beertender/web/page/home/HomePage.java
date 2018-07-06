package com.rectus29.beertender.web.page.home;


import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.page.base.ProtectedPage;
import com.rectus29.beertender.web.panel.productListPanel.ProductListPanel;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BeerTenderPage {

    @SpringBean(name = "serviceUser")
    IserviceUser serviceUser;
    @SpringBean(name = "serviceCategory")
    IserviceCategory serviceCateg;

    public HomePage() {
    }

    public HomePage(IModel model) {
        super(model);
    }

    public HomePage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        List<Category> categoryList = new ArrayList<Category>();
        StringValue pp = getPageParameters().get("categ");
        if (StringUtils.isNotEmpty(pp.toString())) {
            Category category = serviceCateg.get(Long.parseLong(pp.toString()));
            categoryList.add(category);
        }
        add(new ProductListPanel("productListPanel", categoryList));
    }

    @Override
    public String getTitleContribution() {
        return "Accueil";
    }
}
