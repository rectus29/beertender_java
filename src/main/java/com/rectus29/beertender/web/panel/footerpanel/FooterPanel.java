package com.rectus29.beertender.web.panel.footerpanel;

import com.rectuscorp.evetool.web.Config;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.Date;

public class FooterPanel extends Panel {
	
    public FooterPanel(String id){
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        add(new Label("yearLabel", Config.get().dateFormat("yyyy",new Date())));
    }
}
