package com.rectus29.beertender.web.page.home;



import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.page.base.ProtectedPage;
import com.rectus29.beertender.web.panel.productListPanel.ProductListPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class HomePage extends ProtectedPage {

	@SpringBean(name = "serviceUser")
    IserviceUser serviceUser;

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

 		if(this.getPageParameters().isEmpty()){
			add(new ProductListPanel("productListPanel"));
		}

	}

	@Override
	public String getTitleContribution() {
		return "Accueil";
	}
}
