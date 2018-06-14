package com.rectus29.beertender.web.security;

import com.rectus29.beertender.session.BeerTenderSession;
import com.rectus29.beertender.web.page.base.ProtectedPage;
import com.rectus29.beertender.web.security.signin.SigninPage;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;

/*-----------------------------------------------------*/
/* User: Rectus for          Date: 21/12/12 11:22 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public final class BeerTenderAuthorizationStrategy implements IAuthorizationStrategy, IUnauthorizedComponentInstantiationListener {

    private static final Logger log = LogManager.getLogger(BeerTenderAuthorizationStrategy.class);


    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }

    public boolean isInstantiationAuthorized(Class componentClass) {
		return !ProtectedPage.class.isAssignableFrom(componentClass) || BeerTenderSession.get().isAuthenticated();

	}

	public boolean isResourceAuthorized(IResource iResource, PageParameters pageParameters) {
		return true;
	}

	public void onUnauthorizedInstantiation(Component component) {
        throw new RestartResponseAtInterceptPageException(
                SigninPage.class);
    }
}
