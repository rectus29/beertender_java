package com.rectus29.beertender.web.page.searchresultpage;

import com.rectus29.beertender.web.page.base.BeerTenderBasePage;
import com.rectus29.beertender.web.panel.lazyloadPanel.LazyLoadPanel;
import com.rectus29.beertender.web.panel.searchpanel.SearchPanel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 21/12/2018 17:26               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class SearchPage extends BeerTenderBasePage {

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

		add(new SearchPanel("searchFormPanel", searchStringModel){
				@Override
				protected void onSubmit(AjaxRequestTarget target, String searchString) {
					super.onSubmit(target, searchString);
				}
			}
		);

		add(new LazyLoadPanel("resultPanel") {
			@Override
			public Component getLazyLoadComponent(String markupId) {
				return new SearchResultPanel(markupId, searchStringModel);
			}
		});

	}
}
