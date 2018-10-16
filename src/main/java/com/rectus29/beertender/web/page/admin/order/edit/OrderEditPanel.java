package com.rectus29.beertender.web.page.admin.order.edit;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.enums.State;
import com.rectus29.beertender.web.panel.cart.OrderPanel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.EnumLabel;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 16/10/2018                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class OrderEditPanel extends Panel {

    private IModel<Order> orderIModel;

    public OrderEditPanel(String id, IModel<Order> model) {
        super(id, model);
        this.orderIModel = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new Label("TimeFrame", orderIModel.getObject().getTimeFrame().getName()));
        add(new EnumLabel<State>("orderState", orderIModel.getObject().getState()));
        add(new OrderPanel("orderPanel"));

        add(new AjaxLink("submit") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {

            }
        });
        add(new AjaxLink("cancel") {
            @Override
            public void onClick(AjaxRequestTarget ajaxRequestTarget) {

            }
        });

    }

    protected void onSave(AjaxRequestTarget target, IModel<Order> orderIModel){

    }

    protected void onCancel(AjaxRequestTarget target, IModel<Order> orderIModel){

    }
}
