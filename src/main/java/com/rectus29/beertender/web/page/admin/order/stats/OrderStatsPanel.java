package com.rectus29.beertender.web.page.admin.order.stats;

import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.web.panel.hc.hclinepanel.HcLinePanel;
import com.rectus29.beertender.web.panel.hc.hclinepanel.SeriesDataObject;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 19/12/2018 14:57               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class OrderStatsPanel extends Panel {

	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;
	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

	public OrderStatsPanel(String id) {
		super(id);
	}

	public OrderStatsPanel(String id, IModel<?> model) {
		super(id, model);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new HcLinePanel("orderPriceLine"){
			@Override
			public List<SeriesDataObject> getData() {
				List<SeriesDataObject>  seriesList = new ArrayList<>();
				SeriesDataObject orderSum = new SeriesDataObject(new HashMap<>(), "Montant Commande");
				SeriesDataObject orderParticipant = new SeriesDataObject(new HashMap<>(), "Nombre participants");
				for(TimeFrame temp : serviceTimeFrame.getAll()){
					orderSum.getData().put(temp.getName(), temp.getOrderSum());
					orderParticipant.getData().put(temp.getName(), temp.getOrderList().size());
				}
				return seriesList;
			}
		});
	}
}
