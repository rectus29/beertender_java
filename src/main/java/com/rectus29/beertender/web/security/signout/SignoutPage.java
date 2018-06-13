package com.rectus29.beertender.web.security.signout;

import com.rectus29.beertender.web.page.base.BasePage;
import org.apache.shiro.SecurityUtils;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class SignoutPage extends BasePage {

    @SuppressWarnings("unchecked")
    public SignoutPage(){
        this.setStatelessHint( true );
        SecurityUtils.getSubject().logout();
        setResponsePage(getApplication().getHomePage());

    }


    @Override
    public String getTitleContribution() {
        return "logOut";
    }
}
