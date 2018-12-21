package com.rectus29.beertender.web.panel.searchpanel;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 21/12/2018 15:05               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class searchpanel extends Panel {

	public searchpanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Form("searchForm"){
			@Override
			protected void onInitialize() {
				super.onInitialize();
				add(new TextField("searchInput", new PropertyModel(this, "searchString")));
			}
		})
	}
}
