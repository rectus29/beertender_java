package com.rectus29.beertender.web.panel.hc;

import com.rectuscorp.evetool.web.Config;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 30/09/2015 16:59                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public abstract class GraphPanel extends Panel {

	public GraphPanel(String id) {
		super(id);
	}

	public GraphPanel(String id, IModel<?> model) {
		super(id, model);
	}

	public abstract String getData();

	protected abstract String getJS();

	public String getColor() {
		return Config.get().getDefaultColor();
	}
}
