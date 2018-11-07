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

import com.rectus29.beertender.service.IserviceTimeFrame;
import org.apache.shiro.SecurityUtils;
import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;

public class BeerTenderSession extends WebSession {

	@SpringBean(name = "serviceTimeFrame")
	private IserviceTimeFrame serviceTimeFrame;
	private BeerTenderFilter tenderFilter = new BeerTenderFilter();
	private boolean isOrderEnable;


	public BeerTenderSession(Request request) {
		super(request);
		Injector.get().inject(this);
		isOrderEnable = serviceTimeFrame.getCurrentTimeFrame() != null;
	}

	public static BeerTenderSession get() {
		return (BeerTenderSession) Session.get();
	}

	public boolean isAuthenticated() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	public BeerTenderFilter getBeerTenderFilter() {
		if (this.tenderFilter == null) {
			this.tenderFilter = new BeerTenderFilter();
		}
		return this.tenderFilter;
	}

	public boolean isOrderEnable(){
		return this.isOrderEnable;
	}
}
