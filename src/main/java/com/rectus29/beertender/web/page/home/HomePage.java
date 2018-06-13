package com.rectus29.beertender.web.page.home;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 14:16 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.page.base.ProtectedPage;
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

	}

	@Override
	public String getTitleContribution() {
		return "Accueil";
	}
}
