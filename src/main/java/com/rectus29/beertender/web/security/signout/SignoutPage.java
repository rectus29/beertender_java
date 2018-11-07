package com.rectus29.beertender.web.security.signout;

import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.page.base.BasePage;
import com.rectus29.beertender.web.security.signin.SigninPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;


/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 	   */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class SignoutPage extends BasePage {

	@SuppressWarnings("unchecked")
	public SignoutPage() {
		this.setStatelessHint(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		try {
			BeerTenderSession.get().invalidate();
			SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
			//SecurityUtils.getSubject().logout();
			Session session = SecurityUtils.getSubject().getSession(false);
			if (session != null) {
				session.stop();
			}
		} catch (Exception ex) {
			// Ignore all errors, as we're trying to silently
			// log the user out.
		}
		setResponsePage(SigninPage.class);
	}

	@Override
	public String getTitleContribution() {
		return "logOut";
	}
}
