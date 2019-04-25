package com.rectus29.beertender.web.page.admin.order.edit;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.web.panel.cart.OrderPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*					rectus29                           */
/*                Date: 16/10/2018                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class OrderEditPanel extends Panel {

    @SpringBean(name = "serviceOrder")
    private IserviceOrder serviceOrder;
    private IModel<Order> orderIModel;

    public OrderEditPanel(String id, IModel<Order> model) {
        super(id);
        this.orderIModel = model;
    }

    public OrderEditPanel(String contentId, Order orderObject) {
        this(contentId, new Model<>(orderObject));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("TimeFrame", orderIModel.getObject().getTimeFrame().getName()));
        add(new EnumLabel<State>("orderState", orderIModel.getObject().getState()));
        add(new OrderPanel("orderPanel", orderIModel));

        add(new AjaxLink("submit") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                onSave(ajaxRequestTarget, orderIModel);
            }
        });
        add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {
                onCancel(ajaxRequestTarget, null);
            }
        });

    }

    protected void onSave(AjaxRequestTarget target, IModel<Order> orderIModel){

    }

    protected void onCancel(AjaxRequestTarget target, IModel<Order> orderIModel){

    }
}
