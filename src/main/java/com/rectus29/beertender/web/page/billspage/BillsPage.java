package com.rectus29.beertender.web.page.billspage;

import com.rectus29.beertender.entities.Order;
import com.rectus29.beertender.entities.OrderItem;
import com.rectus29.beertender.entities.TimeFrame;
import com.rectus29.beertender.enums.ErrorCode;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.service.IserviceOrder;
import com.rectus29.beertender.service.IserviceTimeFrame;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.Config;
import com.rectus29.beertender.web.component.formattednumberlabel.NumericLabel;
import com.rectus29.beertender.web.component.labels.CurrencyLabel;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.security.error.ErrorPage;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 28/06/2018 11:49               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BillsPage extends BeerTenderPage {

	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;

	@SpringBean(name = "serviceOrder")
	private IserviceOrder serviceOrder;

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	private WebMarkupContainer wmc;
	private LoadableDetachableModel<List<OrderItem>> ldm;

	public BillsPage() {
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		wmc = new WebMarkupContainer("wmc");
		add(wmc.setOutputMarkupId(true));

		ldm = new LoadableDetachableModel<List<OrderItem>>() {
			@Override
			protected List<OrderItem> load() {
				Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());
				if (order != null) {
					return (List<OrderItem>) order.getOrderItemList();
				} else {
					setResponsePage(ErrorPage.class, new PageParameters().add("errorCode", ErrorCode.NO_ORDER_FOUND));
				}
				return new ArrayList<>();
			}
		};

		TimeFrame timeFrame = serviceTimeFrame.getCurrentTimeFrame();
		Order order = serviceOrder.getCurrentOrderFor(serviceUser.getCurrentUser());

		add(new Label("tfName", timeFrame.getName()));
		add(new Label("tfEndDate", Config.get().dateFormat(timeFrame.getEndDate())));
		add(new Label("billsCode", order.getId()));
		add(new CurrencyLabel("billsTotal", order.getOrderPrice()));
		wmc.add(new CurrencyLabel("billsTotal2", order.getOrderPrice()));

		ListView lv = new ListView<OrderItem>("lvCartRow", ldm) {
			@Override
			protected void populateItem(final ListItem<OrderItem> item) {
				item.add(new Label("productName", item.getModelObject().getProduct().getName()));
				item.add(new Label("productpack", item.getModelObject().getProduct().getPackaging().getName()));
				item.add(new CurrencyLabel("productunitprice", item.getModelObject().getProduct().getPrice()));
				item.add(new NumericLabel("productqte", item.getModelObject().getQuantity()));
				item.add(new CurrencyLabel("rowprice", item.getModelObject().getSum()));
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

					@Override
					public boolean isEnabled() {
						return item.getModelObject().getQuantity() > 1;
					}
				});
			}
		};
		wmc.add(lv.setOutputMarkupId(true));
	}
}
