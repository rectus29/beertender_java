package com.rectus29.beertender.web.panel.hc.piePanel;

import com.rectus29.beertender.service.IserviceUser;
import com.rectuscorp.evetool.service.IserviceUser;
import com.rectuscorp.evetool.web.Config;
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
public abstract class HcPiePanel extends Panel {

	private static final Logger log = LogManager.getLogger(HcPiePanel.class);

	@SpringBean(name = "serviceUser")
	private IserviceUser serviceUser;

	private String height = "300px";

	private String width = "400px";

	private String legendPosition = "so";

	private String step = "1 month";

	private String legendPlacement = "outside";

	private int lineWidth = 2;

	private int ticksNumber = 6;

	private boolean legend = true;

	private int legendMargin = 30;

	private int angle = 0;

	private Date xStart;

	private Date xStop;

	private Label chart;

	private ArrayList<PlotDataObject> innerDataTable = new ArrayList<PlotDataObject>();

	public HcPiePanel(String id) {
		super(id);
	}

	public HcPiePanel(String id, String width, String height) {
		super(id);
		this.height = height;
		this.width = width;
	}

	public HcPiePanel(String id, String width, String height, boolean legend) {
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

	@Override public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(OnDomReadyHeaderItem.forScript(getJS()));
	}

	private String getJS() {
		String graph = "" +
				"Highcharts.setOptions({                                                                                                                    \n" +
				"   lang: {                                                                                                                                 \n" +
				"       loading: 'Chargement...',                                                                                                           \n" +
				"       months: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin','Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],   \n" +
				"       shortMonths: ['Jan', 'Fev', 'Mar', 'Avr', 'Mai', 'Jui', 'Juil', 'Aou', 'Sep', 'Oct', 'Nov', 'Dec'],                                 \n" +
				"       weekdays: ['Dimanche', 'Lundi', 'Mardi', 'Mercredi', 'Jeudi', 'Vendredi', 'Samedi'],                                                \n" +
				"       decimalPoint: ',',                                                                                                                  \n" +
				"       resetZoom: 'retour',                                                                                                                \n" +
				"       resetZoomTitle: 'Reset zoom level 1:1',                                                                                             \n" +
				"       thousandsSep: ' '                                                                                                                   \n" +
				"   }                                                                                                                                       \n" +
				"});                                                                                                                                        \n" +
				"       var " + chart.getMarkupId() + " =new Highcharts.Chart({                                                                                  \n" +
				"	        chart: {                                                                                                                        \n" +
				"		        renderTo: '" + chart.getMarkupId() + "',                                                                                    \n" +
				"               defaultSeriesType: 'pie',                                                                                                   \n" +
				"	    	    spacingRight: 20                                                                                                            \n" +
				"               //animation: false                                                                                                          \n" +
				"	        },                                                                                                                              \n" +
				"           colors: [" + getColor() + "],                                                                                                   \n" +
				"           title: {                                                                                                                        \n" +
				"               text: null                                                                                                                  \n" +
				"           },                                                                                                                              \n" +
				"           legend:{                                                                                                                        \n" +
				"               enabled: " + legend + "                                                                                                     \n" +
				"           },                                                                                                                              \n" +
				"           tooltip: {                                                                                                                      \n" +
				"               formatter: function() {                                                                                                     \n" +
				"                   return '<b>'+ this.point.name +'</b>: '+ this.point.y+' ('+ Math.round(this.percentage*100)/100 +'%)';                 \n" +
				"               }                                                                                                                           \n" +
				"           },                                                                                                                              \n" +
				"           plotOptions: {                                                                                                                  \n" +
				"               pie: {                                                                                                                      \n" +
				"                   allowPointSelect: true,                                                                                                 \n" +
				"                   cursor: 'pointer',                                                                                                      \n" +
				"                   dataLabels: {                                                                                                           \n" +
				"                       enabled: false                                                                                                      \n" +
				"                   },                                                                                                                      \n" +
				"                   showInLegend: true                                                                                                      \n" +
				"               }                                                                                                                           \n" +
				"           },                                                                                                                              \n" +
				"           series: [" + getData() + "],                                                                                                    \n" +
				"           navigation: {                                                                                                                   \n" +
				"               menuItemStyle: {                                                                                                            \n" +
				"                   fontSize: '10px'                                                                                                        \n" +
				"               }                                                                                                                           \n" +
				"           },                                                                                                                              \n" +
				"           exporting: {                                                                                                                    \n" +
				"               enabled: false                                                                                                              \n" +
				"           }                                                                                                                               \n" +
				"       });";
		return graph;

	}

	public abstract String getData();

	public String buildData(Map<String, Double> data) {
		String out = "{data:[";
		Set entries = data.entrySet();
		Iterator it = entries.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String entryString = (String) entry.getKey();

			out += "['" + entryString.replaceAll("'", "\\\\'") + "'," + entry.getValue() + "],\n";
		}
		out += "]},";

		//nettoyage derniere virgule
		out = out.substring(0, out.length() - 1);
		return out;
	}

	public String buildDataWithColor(HashMap<String, HashMap<String, Object>> data) {
		TreeMap<String, HashMap<String, Object>> dato = new TreeMap<String, HashMap<String, Object>>(data);
		String out = "";
		//out += "{name:'" + tempObject.getLabel() + "',data:[";
		out += "{data:[";
		Set<Map.Entry<String, HashMap<String, Object>>> entries = dato.entrySet();
		Iterator<Map.Entry<String, HashMap<String, Object>>> it = entries.iterator();
		while (it.hasNext()) {
			Map.Entry<String, HashMap<String, Object>> entry = it.next();
			String entryString = (String) entry.getKey();
			out += "{name: '" + entryString.replaceAll("'", "\\\\'") + "', color: '" + entry.getValue().get("color") + "',y: " + entry.getValue().get("value") + "},\n";
		}
		out += "]},";

		//nettoyage derniere virgule
		out = out.substring(0, out.length() - 1);
		return out;
	}

	//construction des labels de puis le table de ref
	public String buildSeries() {
		String out = "[";
		for (PlotDataObject temp : this.innerDataTable) {
			//out += "{label:'" + temp.getLabel() + "'},";
			out += "'" + temp.getLabel() + "',";
		}
		//nettoyage derniere virgule
		out = out.substring(0, out.length() - 1) + "]";
		return out;
	}

	public String angleXAxis() {
		if (angle != 0) {
			return ", angle:-" + angle;
		} else {
			return "";
		}
	}

	public String getColor() {
		return Config.get().getDefaultColor();
	}

	public class PlotDataObject implements Serializable {

		private String label;

		private HashMap<Date, Double> data;

		public PlotDataObject(HashMap<Date, Double> data, String label) {
			this.data = data;
			this.label = label;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public HashMap<Date, Double> getData() {
			return data;
		}

		public void setData(HashMap<Date, Double> data) {
			this.data = data;
		}
	}

}