package com.rectus29.beertender.session;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 13/06/2018 16:28               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class BeerTenderSession extends WebSession {

      public BeerTenderSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    public boolean logout() {
        SecurityUtils.getSubject().logout();
        return true;
    }

    public boolean isAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public static BeerTenderSession get() {
        return (BeerTenderSession) Session.get();
    }
}
