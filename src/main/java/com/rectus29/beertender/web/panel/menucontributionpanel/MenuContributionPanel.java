package com.rectus29.beertender.web.panel.menucontributionpanel;

import com.rectus29.beertender.web.page.IMenuContributor;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.ArrayList;
import java.util.List;
/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 30/06/2016 11:59                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class MenuContributionPanel extends Panel {

	public MenuContributionPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add((new WebMarkupContainer("wmc") {
			@Override
			protected void onInitialize() {
				super.onInitialize();
				RepeatingView rv = new RepeatingView("rvLink");
				if (getPage() instanceof IMenuContributor) {
					List<MenuElement> menuElements =
							(((IMenuContributor) getPage()).getMenuContribution() != null)
									?((IMenuContributor) getPage()).getMenuContribution()
									:new ArrayList<MenuElement>();
					for (MenuElement el : menuElements) {
						rv.add(new MenuElementPanel(rv.newChildId(), el));
					}
				}
				add(rv);
			}
		}).setOutputMarkupId(true));
	}
}
