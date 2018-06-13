package com.rectus29.beertender.web.panel.hc.spiderPanel;

import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.Config;
import com.rectus29.beertender.web.panel.hc.GraphPanel;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.io.Serializable;
import java.util.*;

/**
 * User: Rectus
 * Date: 09/02/11
 * Time: 16:39
 */
public abstract class SpiderPanel extends GraphPanel {

	private static final Logger log = LogManager.getLogger(SpiderPanel.class);

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	private String height = "100%";

	private String width = "100%";

	private String legendAlign = "center";

	private String legendVerticalAlign = "bottom";

	private Set<String> ticksSet = new TreeSet<String>();

	private boolean legend = true;

	private Label chart;

	public SpiderPanel(String id) {
		super(id);
	}

	public SpiderPanel(String id, String width, String height) {
		super(id);
		this.height = height;
		this.width = width;
	}

	public SpiderPanel(String id, String width, String height, boolean legend) {
		super(id);
		this.height = height;
		this.width = width;
		this.legend = legend;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		chart = new Label("chart1");
		chart.add(new AttributeModifier("style", "width:" + (width) + "; margin:auto; height:" + (height) + ";"));
		chart.setOutputMarkupId(true);
		add(chart);
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(OnDomReadyHeaderItem.forScript(getJS()));
	}

	@Override
	protected String getJS() {
		String graph = "" +
				"       chart" + chart.getMarkupId() + " =new Highcharts.Chart({                                                                            \n" +
				"	        chart: {                                                                                                                        \n" +
				"		        renderTo: '" + chart.getMarkupId() + "',                                                                                    \n" +
				"               polar: true,                                                                                                                \n" +
				"	    	    type: 'line'                                                                                                                \n" +
				"	        },                                                                                                                              \n" +
				"           colors: [" + getColor() + "],                                                                               \n" +
				"           title: {                                                                                                                        \n" +
				"               text: null                                                                                                                  \n" +
				"           },                                                                                                                              \n" +
				"           legend:{                                                                                                                        \n" +
				"               enabled: " + legend + ",                                                                                                    \n" +
				"               align: '" + legendAlign + "',                                                                                               \n" +
				"               verticalAlign: '" + legendVerticalAlign + "',                                                                               \n" +
				"           },                                                                                                                              \n" +
				"           tooltip: {                                                                                                                      \n" +
				"               shared: true                                                                                                                \n" +
				"           },                                                                                                                              \n" +
				"           series: [" + getData() + "],                                                                                                    \n" +
				"           xAxis: {                                                                                                                        \n" +
				"            categories: " + buildTicks() + ",                                                                                            \n" +
				"            tickmarkPlacement: 'on',                                                                                                       \n" +
				"            lineWidth: 0                                                                                                                   \n" +
				"           },                                                                                                                              \n" +
				"           yAxis: {                                                                                                                        \n" +
				"               gridLineInterpolation: 'polygon',                                                                                           \n" +
				"               lineWidth: 0,                                                                                                               \n" +
				"               min: 0                                                                                                                      \n" +
				"           },                                                                                                                              \n" +
				"           pane: {                                                                                                                         \n" +
				"            size: '80%'                                                                                                                    \n" +
				"           },                                                                                                                              \n" +
				"           exporting: {                                                                                                                    \n" +
				"               enabled: false\n" +
				"           }                                                                                                                               \n" +
				"       });";
		return graph;

	}

	/**
	 * builData : build javascript Array String with the HM provide in parameter
	 *
	 * @param data HashMap<String, List<SpiderDataObject>>
	 * @return
	 */
	public String buildData(HashMap<String, List<SpiderDataObject>> data) {
		String out = "";
		Set entries = data.entrySet();
		Iterator it = entries.iterator();
		while (it.hasNext()) {
			Map.Entry<String, List<SpiderDataObject>> entry = (Map.Entry<String, List<SpiderDataObject>>) it.next();
			out += "{ name : '" + entry.getKey() + "',";
			out += "data :[";
			for (SpiderDataObject el : entry.getValue()) {
				out += "['" + el.getLabel() + "'," + ((el.getData() < 0) ? 0 : el.getData()) + "],";
				ticksSet.add(el.getLabel());
			}
			//cleanup last coma on series
			out = out.substring(0, out.length() - 1);
			out += "],";
			out += "pointPlacement: 'on' },";
		}
		//cleanup last coma of the string and close the series array
		out = out.substring(0, out.length() - 1);
		return out;
	}

	//build of ticks javascript array
	public String buildTicks() {
		String out = "[";
		for (String temp : this.ticksSet) {
			out += "'" + temp + "',";
		}
		//cleanup last coma of the string and close the ticks array
		out = out.substring(0, out.length() - 1) + "]";
		return out;
	}

	public static class SpiderDataObject implements Serializable {

		private String label;

		private Float data;

		public SpiderDataObject(Float data, String label) {
			this.data = data;
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public Float getData() {
			return data;
		}

		public void setData(Float data) {
			this.data = data;
		}

	}

}