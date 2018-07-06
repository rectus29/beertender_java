package com.rectus29.beertender.web.page.billspage;

import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.page.base.ProtectedPage;
import com.rectus29.beertender.web.panel.cart.CartPanel;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 28/06/2018 11:49               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class BillsPage extends BeerTenderPage {

    public BillsPage() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new CartPanel("panel"));
    }
}
