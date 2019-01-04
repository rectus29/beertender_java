package com.rectus29.beertender.web.panel.searchpanel;

import com.rectus29.beertender.web.page.searchresultpage.SearchPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 21/12/2018 15:05               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class searchpanel extends Panel {

	private String searchString;

	public searchpanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		String searchString = "";
		add(new Form("searchForm") {
					@Override
					protected void onSubmit() {
						searchpanel.this.setResponsePage(SearchPage.class, new PageParameters().add(SearchPage.SEARCH, searchString));
					}
				}
						.setDefaultModel(new CompoundPropertyModel(this))
						.add(new TextField("searchString"))
		);
	}
}
