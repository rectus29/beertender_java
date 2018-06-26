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

import com.rectus29.beertender.entities.Cart;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

public class BeerTenderSession extends WebSession {

    private Cart cart = new Cart();

    public BeerTenderSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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
