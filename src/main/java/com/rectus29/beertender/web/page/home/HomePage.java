package com.rectus29.beertender.web.page.home;


import com.rectus29.beertender.entities.Category;
import com.rectus29.beertender.entities.Packaging;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IservicePackaging;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.service.impl.ServiceCategory;
import com.rectus29.beertender.session.BeerTenderFilter;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.panel.productListPanel.ProductListPanel;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
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
	@SpringBean(name = "servicePackaging")
	IservicePackaging servicePackaging;

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
        StringValue packageParam = getPageParameters().get("package");
        StringValue categParam = getPageParameters().get("categ");

		// if there is some param retreiveobject here
		// TODO to displace somewhere else like filter init or other
		if(StringUtils.isNotEmpty(packageParam.toString())){
			Packaging pack = servicePackaging.getByProperty("shortName", packageParam.toString(), true);
			if(pack != null)
				BeerTenderSession.get().getBeerTenderFilter().setPackagingFilterModel(new Model<>(pack));
		}
		if(StringUtils.isNotEmpty(categParam.toString())){
			Category categ = serviceCateg.getByProperty("shortName", categParam.toString(), true);
			if(categ != null)
				BeerTenderSession.get().getBeerTenderFilter().addCategoryFilterModel(new Model<>(categ));
		}

        add(new ProductListPanel("productListPanel", categoryList));
    }

    @Override
    public String getTitleContribution() {
        return "Accueil";
    }
}
