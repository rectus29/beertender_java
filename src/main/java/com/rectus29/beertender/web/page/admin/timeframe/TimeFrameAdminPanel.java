package com.rectus29.beertender.web.page.admin.timeframe;

import com.rectus29.beertender.web.page.admin.timeframe.panels.list.TimeFrameAdminListPanel;
import org.apache.wicket.markup.html.panel.Panel;

/*-----------------------------------------------------*/
/*                     Adelya                          */
/*                                                     */
/*                Date: 13/07/2018 10:16               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class TimeFrameAdminPanel extends Panel {

	public TimeFrameAdminPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new TimeFrameAdminListPanel("tabbed"));
	}
}
