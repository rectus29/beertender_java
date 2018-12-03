package com.rectus29.beertender.web.page.test;

import com.rectus29.beertender.entities.ProductDefinition;
import com.rectus29.beertender.service.IserviceCategory;
import com.rectus29.beertender.service.IserviceProduct;
import com.rectus29.beertender.service.IserviceProductDefinition;
import com.rectus29.beertender.web.page.base.BasePage;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*                                                     */
/*                Date: 19/09/2018 17:19               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class TestPage extends BasePage {

    @SpringBean(name = "serviceProduct")
    private IserviceProduct serviceProduct;
    @SpringBean(name = "serviceCategory")
    private IserviceCategory serviceCategory;
    @SpringBean(name = "serviceProductDefinition")
    private IserviceProductDefinition serviceProductDefinition;

    public TestPage() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        ProductDefinition pd = serviceProductDefinition.get(1l);


    }
}
