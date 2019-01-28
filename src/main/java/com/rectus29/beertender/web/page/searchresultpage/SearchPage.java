package com.rectus29.beertender.web.page.searchresultpage;

import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.panel.lazyloadPanel.LazyLoadPanel;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 21/12/2018 17:26               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class SearchPage extends BeerTenderPage {

	public static final String SEARCH = "search";
	private IModel<String> searchStringModel = new Model<>();

	public SearchPage(PageParameters parameters) {
		super(parameters);
		if (parameters.get(SEARCH) != null) {
			searchStringModel = new Model<>(parameters.get(SEARCH).toString());
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new LazyLoadPanel("resultPanel") {
			@Override
			public Component getLazyLoadComponent(String markupId) {
				return new SearchResultPanel(markupId, searchStringModel);
			}
		});

	}

	@Override
	public String getTitleContribution() {
		return "Recherche";
	}
}
