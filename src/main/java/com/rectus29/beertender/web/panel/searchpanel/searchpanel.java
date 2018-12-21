package com.rectus29.beertender.web.panel.searchpanel;

import com.rectus29.beertender.service.IserviceSearch;
import com.rectus29.beertender.service.impl.serviceSearch;
import com.rectus29.beertender.web.page.searchresultpage.SearchResultPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 21/12/2018 15:05               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class searchpanel extends Panel {

	@SpringBean(name = "serviceSearch")
	private IserviceSearch serviceSearch;

	private String searchString;

	public searchpanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		String searchString = "";
		add(new Form("searchForm"){
					@Override
					protected void onSubmit() {
						searchpanel.this.setResponsePage(SearchResultPage.class, new PageParameters().add(SearchResultPage.SEARCH, searchString));
					}
				}
				.setDefaultModel(new CompoundPropertyModel(this))
				.add(new TextField("searchString"))
		);
	}
}
