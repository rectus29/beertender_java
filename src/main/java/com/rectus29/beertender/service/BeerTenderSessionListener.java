package com.rectus29.beertender.service;

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

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeerTenderSessionListener implements SessionListener {

    private static final Logger log = LogManager.getLogger(BeerTenderSessionListener.class);

    protected IserviceSession serviceSession;

    @Autowired
    public void setServiceSession(IserviceSession serviceSession) {
        this.serviceSession = serviceSession;
    }


    public void onStart(Session session) {
    }

    public void onStop(Session session) {
        serviceSession.onStop(session);
    }

    public void onExpiration(Session session) {
        serviceSession.onExpiration(session);
    }
}
