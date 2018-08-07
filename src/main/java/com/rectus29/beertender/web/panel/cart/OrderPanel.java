package com.rectus29.beertender.web.panel.cart;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.OrderItem;
import com.rectus29.beertender.enums.ErrorCode;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.component.formattednumberlabel.NumericLabel;
import com.rectus29.beertender.web.security.error.ErrorPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

public class OrderPanel extends Panel {

	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<OrderItem>> ldm;

    public OrderPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        ldm = new LoadableDetachableModel<List<OrderItem>>() {
            @Override
            protected List<OrderItem> load() {
            	Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
					if(order != null){
            			return (List<OrderItem>)order.getOrderItemList();
					}else{
            			setResponsePage(ErrorPage.class, new PageParameters().add("errorCode", ErrorCode.NO_ORDER_FOUND));
					}
					return new ArrayList<>();
            }
        };

        wmc = new WebMarkupContainer("wmc");
        add(wmc.setOutputMarkupId(true));

        ListView lv = new ListView<OrderItem>("lvCartRow", ldm) {
            @Override
            protected void populateItem(final ListItem<OrderItem> item) {
                item.add(new Label("productName", item.getModelObject().getProduct().getName()));
                item.add(new NumericLabel("productqte", item.getModelObject().getQuantity()));
                item.add(new NumericLabel("productunitprice", item.getModelObject().getProduct().getPrice().doubleValue()));
                item.add(new NumericLabel("rowprice", item.getModelObject().getSum()));
                //Action link
                item.add(new AjaxLink("rmLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
						Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
                    	order.removeProduct(item.getModelObject().getProduct());
                    	serviceOrder.save(order);
						ldm.detach();
                        target.add(wmc);
                        send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));
                    }
                });
                item.add(new AjaxLink("plusLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
						Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
						order.addProduct(item.getModelObject().getProduct(), 1);
                        ldm.detach();
                        target.add(wmc);
                        send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));
                    }
                });
                item.add(new AjaxLink("minusLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
						Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
						order.addProduct(item.getModelObject().getProduct(), -1);
                        ldm.detach();
                        target.add(wmc);
                        send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));
                    }
                });
            }
        };
        wmc.add(lv.setOutputMarkupId(true));
    }
}
