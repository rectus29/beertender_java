package com.rectus29.beertender.web.security.maintenancepage;

import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.web.page.base.BasePage;
import com.rectus29.beertender.web.page.base.BeerTenderBasePage;
import com.rectus29.beertender.web.page.base.BeerTenderPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 27/12/14 22:19                */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class MaintenancePage extends BasePage {

    @SpringBean(name = "serviceConfig")
    private IserviceConfig serviceConfig;

    public MaintenancePage() {
        super();
    }
}
