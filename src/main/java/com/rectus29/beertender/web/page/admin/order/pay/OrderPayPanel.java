package com.rectus29.beertender.web.page.admin.order.pay;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.payment.Payment;
import com.rectus29.beertender.enums.PaymentMethod;
import com.rectus29.beertender.web.component.datelabel.DateLabel;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                Date: 11/01/2019 				       */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class OrderPayPanel extends Panel {

	private IModel<Order> orderIModel = new Model<>();

	public OrderPayPanel(String id, IModel<Order> model) {
		super(id, model);
		orderIModel = model;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new ListView<Payment>("paymentRow", orderIModel.getObject().getPaymentList()){
			@Override
			protected void populateItem(ListItem<Payment> item) {
				item.add(new Label("paymenetId", item.getModelObject().getId()));
				item.add(new DateLabel("paymenetDate", item.getModelObject().getCreated()));
				item.add(new EnumLabel<>("paymenetType", item.getModelObject().getPaymentMethod()));
				item.add(new CurrencyLabel("paymenetAmount", item.getModelObject().getAmount()));
			}
		});
		add(new Label("nothingHere", new ResourceModel("nothingHere")){
			@Override
			public boolean isVisible() {
				return orderIModel.getObject().getPaymentList().isEmpty();
			}
		});
	}

	protected void onSave(AjaxRequestTarget target, IModel<Order> orderIModel){

	}

	protected void onCancel(AjaxRequestTarget target, IModel<Order> orderIModel){

	}
}
