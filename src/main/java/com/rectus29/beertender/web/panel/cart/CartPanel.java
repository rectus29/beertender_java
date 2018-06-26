package com.rectus29.beertender.web.panel.cart;

import com.rectus29.beertender.entities.Cart;
import com.rectus29.beertender.event.RefreshEvent;
import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.component.formattednumberlabel.NumericLabel;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;

import java.util.Collection;
import java.util.List;

public class CartPanel extends Panel {

    public CartPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        LoadableDetachableModel<List<Cart.CartRow>> ldm = new LoadableDetachableModel<List<Cart.CartRow>>() {
            @Override
            protected List<Cart.CartRow> load() {
                return BeerTenderSession.get().getCart().getCartRowList();
            }
        };

        WebMarkupContainer wmc = new WebMarkupContainer("wmc");
        add(wmc.setOutputMarkupId(true));

        ListView lv = new ListView<Cart.CartRow>("lvCartRow", ldm) {
            @Override
            protected void populateItem(ListItem<Cart.CartRow> item) {
                item.add(new Label("productName", item.getModelObject().getProduct().getName()));
                item.add(new NumericLabel("productqte", item.getModelObject().getQuantity()));
                item.add(new NumericLabel("productunitprice", item.getModelObject().getProduct().getPrice().doubleValue() + " €"));
                item.add(new NumericLabel("rowprice", item.getModelObject().getSum() + " €"));
                //Action link
                item.add(new AjaxLink("rmLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        BeerTenderSession.get().getCart().removeProduct(
                                item.getModelObject().getProduct()
                        );
                        ldm.detach();
                        target.add(wmc);
                        send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));
                    }
                });
                item.add(new AjaxLink("plusLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        BeerTenderSession.get().getCart().addProduct(
                                item.getModelObject().getProduct(),
                                1
                        );
                        ldm.detach();
                        target.add(wmc);
                        send(getApplication(), Broadcast.BREADTH, new RefreshEvent(target));
                    }
                });
                item.add(new AjaxLink("minusLink") {
                    @Override
                    public void onClick(AjaxRequestTarget target) {
                        BeerTenderSession.get().getCart().addProduct(
                                item.getModelObject().getProduct(),
                                -1
                        );
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
