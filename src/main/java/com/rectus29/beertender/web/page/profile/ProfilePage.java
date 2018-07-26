package com.rectus29.beertender.web.page.profile;

import com.rectus29.beertender.web.page.base.BeerTenderPage;
import com.rectus29.beertender.web.page.profile.account.AccountPanel;
import org.apache.wicket.markup.html.panel.Panel;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 28/02/2016                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class ProfilePage extends BeerTenderPage {

    public static String PANEL = "panel";
    public static String APIKEY = "apikey";
    public static String CHARACTER = "character";
    public static String ACCOUNT = "account";
    private Panel panel;

    public ProfilePage() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();


        add(panel = new AccountPanel("panel"));

    }

    @Override
    public String getTitleContribution() {
        return "Account management";
    }

}
