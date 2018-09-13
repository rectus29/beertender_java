package com.rectus29.beertender.web.component.bookmarkabletabbedpanel;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 12/09/2018 13:57               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import java.util.List;


public class BookmarkableTabbedPanel<T extends BookmarkableTab> extends TabbedPanel<T> {

	private PageParameters pageParameters;
	private String tabParameterName = "tab";
	private int defaultTabIndex = 0;


	public BookmarkableTabbedPanel(String id, List<T> tabs) {
		super(id, tabs);
		this.pageParameters = this.getWebPage().getPageParameters();
		if (pageParameters.getNamedKeys().contains(tabParameterName)) {
			StringValue tab = pageParameters.get(tabParameterName);
			setSelectedTab(tab.toInt());
		} else {
			setSelectedTab(defaultTabIndex);
		}
	}

	/**
	 * Using this constructor the following defaults take effect:
	 * <ul>
	 * <li>tabParameterName = "tab"</li>
	 * <li>defaultTabIndex = 0</li>
	 * </ul>
	 *
	 * @param id             component id
	 * @param tabs           list of ITab objects used to represent tabs
	 * @param pageParameters Container for parameters to a requested page. A
	 *                       parameter for the selected tab will be inserted.
	 */
	public BookmarkableTabbedPanel(String id, List<T> tabs, PageParameters pageParameters) {
		super(id, tabs);
		this.pageParameters = pageParameters;

		if (pageParameters.getNamedKeys().contains(tabParameterName)) {
			StringValue tab = pageParameters.get(tabParameterName);
			int indexToSelect = defaultTabIndex;
			if(tabs.get(0) instanceof BookmarkableNamedTab){
				indexToSelect = foundNamedTabIndex(tab.toString());
			}else{
				indexToSelect = tab.toInt();
			}

			setSelectedTab(indexToSelect);
		} else {
			setSelectedTab(defaultTabIndex);
		}
	}

	private int foundNamedTabIndex(String tab) {
		for(int i =0 ; i< this.getTabs().size(); i++){
			if(tab.equals(((BookmarkableNamedTab)this.getTabs().get(i)).getName())){
				return i;
			}
		}
		return 0;
	}


	/**
	 * @param id              component id
	 * @param tabs            list of ITab objects used to represent tabs
	 * @param defaultTabIndex Set the tab to by displayed by default. The url
	 *                        for this tab will not contain any tab specific information. If you want to
	 *                        display the first tab by default, you can use the constructor without this
	 *                        parameter.
	 * @param pageParameters  Container for parameters to a requested page. A
	 *                        parameter for the selected tab will be inserted.
	 */
	public BookmarkableTabbedPanel(String id, List<T> tabs, int defaultTabIndex, String tabParameterName, PageParameters pageParameters) {
		this(id, tabs, pageParameters);
		this.defaultTabIndex = defaultTabIndex;
		setSelectedTab(defaultTabIndex);
		this.tabParameterName = tabParameterName;
	}


	@Override
	protected WebMarkupContainer newLink(String linkId, int index) {
		String paramValue = index + "";
		if (getTabs().get(index) instanceof BookmarkableNamedTab) {
			paramValue = ((BookmarkableNamedTab) this.getTabs().get(index)).getName();
		}
		WebMarkupContainer link = new BookmarkablePageLink(linkId, getPage().getClass(), new PageParameters().add(tabParameterName, paramValue));
		if (index == getSelectedTab())
			link.setEnabled(false);
		return link;
	}

	@Override
	protected LoopItem newTabContainer(final int tabIndex) {
		return new LoopItem(tabIndex) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void onComponentTag(final ComponentTag tag) {
				super.onComponentTag(tag);
				String cssClass = tag.getAttribute("class");
				if (cssClass == null) {
					cssClass = " ";
				}
				cssClass += " tab" + getIndex();

				if (getIndex() == getSelectedTab()) {
					cssClass += " active";
				}
				if (getIndex() == getTabs().size() - 1) {
					cssClass += " last";
				}
				tag.put("class", cssClass.trim());
			}

			@Override
			public boolean isVisible() {
				return ((ITab) getTabs().get(tabIndex)).isVisible();
			}
		};
	}

}