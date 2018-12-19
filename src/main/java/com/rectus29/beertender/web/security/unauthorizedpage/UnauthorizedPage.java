package com.rectus29.beertender.web.security.unauthorizedpage;

import com.rectus29.beertender.web.page.base.BasePage;
import com.rectus29.beertender.web.page.home.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/*-----------------------------------------------------*/
/*                     Rectus_29                       */
/*                                                     */
/*                Date: 08/08/2018 16:04               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class UnauthorizedPage extends BasePage {

	public UnauthorizedPage() {}

	public UnauthorizedPage(IModel model) {
		super(model);
	}

	public UnauthorizedPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new BookmarkablePageLink<HomePage>("homeLink", HomePage.class));
		add(new BookmarkablePageLink<HomePage>("backHomeLink", HomePage.class));
	}
}
