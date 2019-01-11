package com.rectus29.beertender.web.page.admin.order.pay;

import com.rectus29.beertender.entities.Order;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

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

	protected void onSave(AjaxRequestTarget target, IModel<Order> orderIModel){

	}

	protected void onCancel(AjaxRequestTarget target, IModel<Order> orderIModel){

	}
}
