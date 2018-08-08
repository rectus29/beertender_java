package com.rectus29.beertender.web.page.admin.order;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.OrderItem;
import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.web.component.formattednumberlabel.NumericLabel;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;


/*-----------------------------------------------------*/
/*                     Rectus_29                       */
/*                                                     */
/*                Date: 08/08/2018 16:04               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class OrderSummaryChildPanel extends Panel {

	private IModel<TimeFrame> timeFrameModel;

	public OrderSummaryChildPanel(String id, IModel<TimeFrame> model) {
		super(id, model);
		this.timeFrameModel = model;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		ListView rv = new ListView<Order>("orderRv", timeFrameModel.getObject().getOrderList()){
			@Override
			protected void populateItem(ListItem<Order> item) {
				item.add(new Label("user", item.getModelObject().getUser().getFormatedName()));
				item.add(new ListView<OrderItem>("orderItemRv", item.getModelObject().getOrderItemList()) {
					@Override
					protected void populateItem(ListItem<OrderItem> item) {
						item.add(new Label("product", item.getModelObject().getProduct().getName()));
						item.add(new Label("type", "-"));
						item.add(new NumericLabel("qte", item.getModelObject().getQuantity()));
						item.add(new CurrencyLabel("unitPrice", new PropertyModel<OrderItem>(item.getModelObject(), "quantity")));
						item.add(new NumericLabel("total", item.getModelObject().getSum()));
					}
				});
			}
		};
		add(rv);
	}
}
