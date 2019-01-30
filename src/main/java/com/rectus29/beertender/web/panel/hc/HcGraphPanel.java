package com.rectus29.beertender.web.panel.hc;

import com.rectus29.beertender.web.Config;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
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
public abstract class HcGraphPanel extends Panel {

	protected String height = "300px";
	protected String width = "400px";
	protected boolean legend = true;
	protected Label chart;

	public HcGraphPanel(String id) {
		super(id);
	}

	public HcGraphPanel(String id, IModel<?> model) {
		super(id, model);
	}

	protected abstract String getJS();

	public String getColor() {
		return Config.get().getDefaultColor();
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		chart = new Label("chart1");
		chart.add(new AttributeModifier("style", "width:" + (width) + "; margin:auto; height:" + (height) + ";"));
		chart.setOutputMarkupId(true);
		add(chart);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(OnDomReadyHeaderItem.forScript(getJS()));
	}
}
