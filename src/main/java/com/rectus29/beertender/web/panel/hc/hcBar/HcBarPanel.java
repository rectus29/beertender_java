package com.rectus29.beertender.web.panel.hc.hcBar;

/**
 * Created by RTAI on 03/07/2014.
 */

import com.rectuscorp.evetool.web.Config;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.*;

public abstract class HcBarPanel extends Panel {

    private static final Logger log = LogManager.getLogger(HcBarPanel.class);

    public static String height = "200px";
    public static String width = "400px";
    private Double max = 0d;
    private Double min = 0d;
    private String legendPosition = "e";
    private String step = "1 month";
    private String legendPlacement = "outside";
    private int lineWidth = 2;
    private boolean legend = true;
    private int legendMargin = 30;
    private int angle = 30;
    private Label chart;
    private Label infoLabel;
    private List<Date> innerRefTable = new ArrayList<Date>();
    private ArrayList<String> innerLabelList = new ArrayList<String>();

    public HcBarPanel(String id) {
        super(id);
        init();
    }

    public HcBarPanel(String id, String width, String height) {
        super(id);
        this.height = height;
        this.width = width;
        init();
    }

    public HcBarPanel(String id, String width, String height, boolean legend) {
        super(id);
        this.height = height;
        this.width = width;
        this.legend = legend;
        init();
    }

    private void init() {
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
                "       resetZoom: 'Retour',                                                                                                                \n" +
                "       resetZoomTitle: 'Reset zoom level 1:1',                                                                                             \n" +
                "       thousandsSep: ' '                                                                                                                   \n" +
                "   }                                                                                                                                       \n" +
                "});                                                                                                                                        \n" +
                "       chart" + chart.getMarkupId() + " =new Highcharts.Chart({                                                                            \n" +
                "	        chart: {                                                                                                                        \n" +
                "		        renderTo: '" + chart.getMarkupId() + "',                                                                                    \n" +
                "               defaultSeriesType: 'column',                                                                                                \n" +
                "	    	    spacingRight: 20,                                                                                                           \n" +
                "               //animation: false                                                                                                          \n" +
                "	        }," +
                "           credits: {                                                                                                                      \n" +
                "               enabled: false                                                                                                              \n" +
                "           },                                                                                                                              \n" +
                "           colors: [" + getColor() + "],                                                                               \n" +
                "           title: {                                                                                                                        \n" +
                "               text: null                                                                                                                  \n" +
                "           },                                                                                                                              \n" +
                "           legend:{                                                                                                                        \n" +
                "               enabled: " + legend + "                                                                                                     \n" +
                "           },                                                                                                                              \n" +
                "            yAxis: {                                                                                                                       \n" +
                "               title:null,                                                                                                                 \n" +
                "                                                                                                                                           \n" +
                "           },                                                                                                                              \n" +
                "           tooltip: {                                                                                                                      \n" +
                "               formatter: function() {                                                                                                     \n" +
                "                   return '<b>'+ this.series.name +'</b>: '+ this.y +'';                                                                          \n" +
                "               }                                                                                                                           \n" +
                "           },                                                                                                                              \n" +
                "           plotOptions: {                                                                                                                  \n" +
                "               column: {                                                                                                                   \n" +
                "                   dataLabels: {                                                                                                           \n" +
                "                       enabled: true,                                                                                                      \n" +
                "                       color: '#666',                                                                                                      \n" +
                "                       //y: -10,                                                                                                           \n" +
                "                       formatter: function() {                                                                                             \n" +
                "                               return Math.round(this.y*100)/100;                                                                          \n" +
                "                       },                                                                                                                  \n" +
                "                       style: {                                                                                                            \n" +
                "                           font: 'normal 13px Verdana, sans-serif'                                                                         \n" +
                "                       }                                                                                                                   \n" +
                "                   }                                                                                                                       \n" +
                "               }                                                                                                                           \n" +
                "           },                                                                                                                              \n" +
                "           series: [" + getData() + "],                                                                                                    \n" +
                "           xAxis: {                                                                                                                        \n" +
                "                gridLineWidth: 1,                                                                                                          \n" +
                "               //type:'datetime',                                                                                                          \n" +
                "               categories: [" + buildTicks() + "]                                                                                            \n" +
                "           },                                                                                                                              \n" +
                "                                                                                                                                           \n" +
                "           exporting: {                                            \n" +
                "               enabled: false                                                      \n" +
                "           }                                               \n" +
                "       });";
        return graph;

    }
    public abstract String getData();

    public String buildData(List<Date> series, List<BarDataObject> in) {
        //enoie des donnée a la classe pour builticks

        this.innerRefTable = series;
        //variables de sortie
        String out = "";
        //construction des donnée plus des labels
        for (BarDataObject tempObject : in) {

            out += "{name:'" + tempObject.getLabel() + "',data:[";
            this.innerLabelList.add(tempObject.getLabel());
            for (Double tempData : tempObject.getData()) {
                out += tempData + ",";
            }
            //nettoyage derniere virgule
            out = out.substring(0, out.length() - 1) + "]},";
        }
        //nettoyage derniere virgule
        out = out.substring(0, out.length() - 1);

        //build des ticks
        buildTicks();

        return out;
    }

    public String buildData(Map<String, Double> in) {
        Set entries = in.entrySet();
        Iterator it = entries.iterator();
        String out = "";
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            out += "{name:'" + entry.getKey().toString() + "',data:[";
            this.innerLabelList.add(entry.getKey().toString());

            out += entry.getValue() + "]},";
            //if (tempData < min) min = tempData;
            //if (Math.abs(tempData) > max) max = MathUtil.round(Math.abs(tempData), 1);

            //nettoyage derniere virgule
            //out = out.substring(0, out.length() - 1) + "]},";
        }

        //nettoyage derniere virgule
        if (!out.isEmpty())
            out = out.substring(0, out.length() - 1);

        //build des ticks

        buildTicks();

        return out;
    }

    public String buildTicks() {
        String out = "' '";
      /* for (String temp : innerRefTable) {
           out += "'" + temp + "',";
       }
       //nettoyage derniere virgule
       out = out.substring(0, out.length() - 1);*/
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

    public class BarDataObject {

        private String label;
        private ArrayList<Double> data;

        public BarDataObject(ArrayList<Double> data, String label) {
            this.data = data;
            this.label = label;
        }
        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public ArrayList<Double> getData() {
            return data;
        }

        public void setData(ArrayList<Double> data) {
            this.data = data;
        }

    }
}
