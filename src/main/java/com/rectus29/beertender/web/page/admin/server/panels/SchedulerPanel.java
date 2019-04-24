package com.rectus29.beertender.web.page.admin.server.panels;

import org.apache.wicket.markup.html.panel.Panel;

/*-----------------------------------------------------*/
/*                     Rectus_29                       */
/*                                                     */
/*                Date: 07/08/2018 11:40               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class SchedulerPanel extends Panel {

//	@SpringBean(name = "BeerTenderScheduler")
//	private ThreadPoolTaskScheduler beerTenderScheduler;

	public SchedulerPanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		//beerTenderScheduler.schedule();

	}
}
