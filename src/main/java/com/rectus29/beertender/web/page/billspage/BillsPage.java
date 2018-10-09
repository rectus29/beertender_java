package com.rectus29.beertender.web.page.billspage;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.Config;
import com.rectus29.beertender.web.page.base.BeerTenderBasePage;
import com.rectus29.beertender.web.panel.cart.OrderPanel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 28/06/2018 11:49               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BillsPage extends BeerTenderBasePage {

	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	public BillsPage() {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		TimeFrame timeFrame = serviceTimeFrame.getCurrentTimeFrame();
		Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());

		add(new Label("tfName", timeFrame.getName()));
		add(new Label("tfEndDate", Config.get().dateFormat(timeFrame.getEndDate())));
		add(new Label("billsCode", order.getId()));

		add(new OrderPanel("panel"));
	}
}
