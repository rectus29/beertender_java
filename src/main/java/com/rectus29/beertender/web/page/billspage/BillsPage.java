package com.rectus29.beertender.web.page.billspage;

import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.tools.DateUtil;
import com.rectus29.beertender.web.BeerTenderApplication;
import com.rectus29.beertender.web.Config;
import com.rectus29.beertender.web.page.base.BeerTenderBasePage;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.panel.cart.OrderPanel;
import org.apache.wicket.datetime.markup.html.basic.DateLabel;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 28/06/2018 11:49               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BillsPage extends BeerTenderBasePage {

	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

    public BillsPage() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

		TimeFrame timeFrame = serviceTimeFrame.getCurrentTimeFrame();

		add(new Label("tfName", timeFrame.getName()));
		add(new Label("tfEndDate", Config.get().dateFormat(timeFrame.getEndDate())));

        add(new OrderPanel("panel"));
    }
}
