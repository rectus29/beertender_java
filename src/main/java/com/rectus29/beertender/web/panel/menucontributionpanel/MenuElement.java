package com.rectus29.beertender.web.panel.menucontributionpanel;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 30/06/2016 12:11               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import org.apache.wicket.markup.html.link.Link;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The type Menu element.
 */
public abstract class MenuElement implements Serializable {
	private String text;
	private String markupID;
	private List<MenuElement> elementChildList = new ArrayList<>();

	/**
	 * Instantiates a new Menu element.
	 *
	 * @param text the text
	 * @param link the link
	 */
	public MenuElement(String text) {
		this.text = text;
		this.markupID = UUID.randomUUID().toString();
	}

	/**
	 * Gets text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets text.
	 *
	 * @param text the text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets link.
	 *
	 * @return the link
	 */
	public abstract Link getLink();


	protected String getMenuElementMarkupID() {
		return markupID;
	}
}
