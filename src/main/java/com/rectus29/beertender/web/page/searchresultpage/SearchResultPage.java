package com.rectus29.beertender.web.page.searchresultpage;

import com.rectus29.beertender.web.page.base.BeerTenderBasePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 21/12/2018 17:26               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class SearchResultPage extends BeerTenderBasePage {

	public static final String SEARCH = "search";
	private String searchString;

	public SearchResultPage(PageParameters parameters) {
		super(parameters);
		if (parameters.get(SEARCH) != null) {
			searchString = parameters.get(SEARCH).toString();
		}
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

	}
}
