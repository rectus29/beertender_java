package com.rectus29.beertender.web.panel.serverstatepanel;

import com.rectus29.beertender.entities.core.Config;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.tasks.schedulde.ServerStateTask;
import com.rectus29.beertender.entities.core.Config;
import com.rectus29.beertender.service.IserviceConfig;
import com.rectus29.beertender.tasks.schedulde.ServerStateTask;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/*-----------------------------------------------------*/
/*      _____           _               ___   ___      */
/*     |  __ \         | |             |__ \ / _ \     */
/*     | |__) |___  ___| |_ _   _ ___     ) | (_) |    */
/*     |  _  // _ \/ __| __| | | / __|   / / \__, |    */
/*     | | \ \  __/ (__| |_| |_| \__ \  / /_   / /     */
/*     |_|  \_\___|\___|\__|\__,_|___/ |____| /_/      */
/*                                                     */
/*                Date: 08/07/2016 14:59               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

public class ServerStatePanel extends Panel {


	@SpringBean(name = "serviceConfig")
	private IserviceConfig serviceConfig;

	public ServerStatePanel(String id) {
		super(id);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		String serverOpenString = "-";
		String playersString = "-";

		Config serverOpen  = serviceConfig.getByKey(ServerStateTask.SERVEROPEN);
		if(serverOpen!= null)
			serverOpenString = serverOpen.getValue();
		Config players  = serviceConfig.getByKey(ServerStateTask.ONLINEPLAYERS);
		if(players!= null)
			playersString = players.getValue();
		add(new Label("tqState", serverOpenString));
		add(new Label("tqPlayers", playersString));
	}
}
