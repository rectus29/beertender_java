package com.rectus29.beertender.web.panel.serverloadpanel;

import com.sun.management.OperatingSystemMXBean;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import java.io.File;
import java.lang.management.ManagementFactory;

/*-----------------------------------------------------*/
/*                     Adelya                          */
/*                                                     */
/*                Date: 19/11/2018 14:35               */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class ServerLoadpanel extends Panel {

	private boolean autoRefresh = false;

	public ServerLoadpanel(String id) {
		super(id);
	}

	public ServerLoadpanel(String id, boolean autoRefresh) {
		super(id);
		this.autoRefresh = autoRefresh;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		add(new Label("cpuLoad", operatingSystemMXBean.getSystemLoadAverage()));
		add(new Label("memoryUsage", byteToGigaByte(operatingSystemMXBean.getFreePhysicalMemorySize())+ "Go / " + byteToGigaByte(operatingSystemMXBean.getTotalPhysicalMemorySize()) + " Go"));
		add(new Label("fileSystem", byteToGigaByte(new File("/").getTotalSpace())  +"Go / " + byteToGigaByte(new File("/").getUsableSpace()) + "Go"));
	}

	private Long byteToGigaByte(long byteValue){
		return byteValue / (1024 * 1024 * 1024);
	}
}
