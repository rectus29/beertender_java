package com.rectus29.beertender.web.page.admin.order;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.OrderItem;
import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.web.component.formattednumberlabel.NumericLabel;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.component.wicketmodal.BeerTenderModal;
import com.rectus29.beertender.web.page.admin.order.edit.OrderEditPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;


/*-----------------------------------------------------*/
/*                     Rectus_29                       */
/*                                                     */
/*                Date: 08/08/2018 16:04               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class OrderSummaryChildPanel extends Panel {

	private IModel<TimeFrame> timeFrameModel;
	private BeerTenderModal modal;

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
				item.add(new Label("user", item.getModelObject().getUser().getFormattedName()));
				item.add(new AjaxLink("orderEditLink"){
                    @Override
                    public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                        modal.setContent(new OrderEditPanel(modal.getContentId(), item.getModel()));
                        modal.setTitle("#" + item.getModelObject().getId() + " - " + item.getModelObject().getUser().getFormattedName() );
                        modal.show(ajaxRequestTarget, BeerTenderModal.ModalFormat.MEDIUM);
                    }
                });
				item.add(new ListView<OrderItem>("orderItemRv", item.getModelObject().getOrderItemList()) {
					@Override
					protected void populateItem(ListItem<OrderItem> item) {
						item.add(new Label("product", item.getModelObject().getProduct().getName()));
						item.add(new Label("type", item.getModelObject().getProduct().getPackaging().getName()));
						item.add(new NumericLabel("qte", item.getModelObject().getQuantity()));
						item.add(new CurrencyLabel("unitPrice", new PropertyModel<OrderItem>(item.getModelObject(), "quantity")));
						item.add(new CurrencyLabel("total", new Model(item.getModelObject().getSum())));
					}
				});
				item.add(new CurrencyLabel("orderTotal", new Model<>(item.getModelObject().getOrderPrice())));
			}
		};
		add(rv);

		add((modal = new BeerTenderModal("modal")).setOutputMarkupId(true));
	}
}
