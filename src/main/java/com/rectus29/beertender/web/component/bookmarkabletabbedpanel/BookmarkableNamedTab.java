package com.rectus29.beertender.web.component.bookmarkabletabbedpanel;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 12/09/2018 13:57               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;


public abstract class BookmarkableNamedTab extends BookmarkableTab {

	public BookmarkableNamedTab(IModel<String> title) {
		super(title);
	}

	public abstract String getName();

	@Override
	public abstract WebMarkupContainer getPanel(String panelId);
}