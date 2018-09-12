package com.rectus29.beertender.web.component.bookmarkabletabbedpanel;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 12/09/2018 13:57               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;


public abstract class BookmarkableTab extends AbstractTab {

	public BookmarkableTab(IModel<String> title) {
		super(title);
	}

	@Override
	public abstract WebMarkupContainer getPanel(String panelId);
}