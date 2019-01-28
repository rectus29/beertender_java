package com.rectus29.beertender.web.panel.searchpanel;

import com.rectus29.beertender.tools.StringUtils;
import com.rectus29.beertender.web.page.searchresultpage.SearchPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
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

	public searchpanel(String id, String search) {
		super(id);
		this.searchString = search;
	}

	public searchpanel(String id, IModel<String> model) {
		super(id, model);
		this.searchString = model.getObject();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		if (StringUtils.isBlank(searchString)) {
			searchString = getPage().getPageParameters().get(SearchPage.SEARCH).toString();
		}
		Form form;
		add((form = new Form("searchForm"))
				.add(new TextField<>("searchString", new PropertyModel<String>(searchpanel.this, "searchString")))
				.add(new AjaxFormSubmitBehavior(form, "submit") {
					@Override
					protected void onSubmit(AjaxRequestTarget target) {
						searchpanel.this.onSubmit(target, searchpanel.this.searchString);
					}
				})
		);
	}


	/**
	 * default on submit behaviour
	 *
	 * @param target       the ajaxtarget
	 * @param searchString the searchstring submitted
	 */
	protected void onSubmit(AjaxRequestTarget target, String searchString) {
		setResponsePage(SearchPage.class, new PageParameters().add(SearchPage.SEARCH, searchString));
	}
}
