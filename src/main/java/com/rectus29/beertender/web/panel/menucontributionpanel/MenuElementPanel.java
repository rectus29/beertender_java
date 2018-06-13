package com.rectus29.beertender.web.panel.menucontributionpanel;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.IMarkupCacheKeyProvider;
import org.apache.wicket.markup.IMarkupResourceStreamProvider;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 03/08/2016 15:01                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class MenuElementPanel extends Panel implements IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

	private MenuElement menuElement;

	public MenuElementPanel(String id, MenuElement menuElement) {
		super(id);
		this.menuElement = menuElement;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		this.add(menuElement.getLink());
	}

	@Override
	public IResourceStream getMarkupResourceStream(MarkupContainer container, Class<?> containerClass) {
		String markup = "<wicket:panel><a wicket:id=" + menuElement.getMenuElementMarkupID() + ">" + menuElement.getText() + "</a></wicket:panel>";
		StringResourceStream resourceStream = new StringResourceStream(markup);
		return resourceStream;
	}

	/**
	 * Avoid markup caching for this component
	 */
	@Override
	public String getCacheKey(MarkupContainer arg0, Class<?> arg1) {
		return null;
	}
}
