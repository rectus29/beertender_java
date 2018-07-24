package com.rectus29.beertender.web.panel.cartmodalpanel;

import com.rectus29.beertender.web.page.billspage.BillsPage;
import com.rectus29.beertender.web.panel.cart.OrderPanel;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 28/06/2018 11:26               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class CartModalPanel extends Panel {

    public CartModalPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new OrderPanel("panel"));
        add(new BookmarkablePageLink("seeBills", BillsPage.class) );
    }

}
