package com.rectus29.beertender.web.page.admin.timeframe;

import com.rectus29.beertender.web.page.admin.timeframe.panels.list.TimeFrameAdminListPanel;
import org.apache.wicket.markup.html.panel.Panel;

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
