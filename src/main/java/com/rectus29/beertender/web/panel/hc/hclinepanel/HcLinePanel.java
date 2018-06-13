package com.rectus29.beertender.web.panel.hc.hclinepanel;


import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.Config;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.internal.HtmlHeaderContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Rectus
 * Date: 09/02/11
 * Time: 16:39
 */
public abstract class HcLinePanel extends Panel {

    private static final Logger log = LogManager.getLogger(HcLinePanel.class);

    @SpringBean(name = "serviceUser")
    private IserviceUser serviceUser;

    public static String height = "300px";
    public static String width = "400px";
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

    public HcLinePanel(String id) {
        super(id);
        init();
    }

    public HcLinePanel(String id, String width, String height) {
        super(id);
        this.height = height;
        this.width = width;
        init();
    }

    public HcLinePanel(String id, String width, String height, boolean legend) {
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
        WebSession.get().getClientInfo().getUserAgent();
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
                "       var chart" + chart.getMarkupId() + " =new Highcharts.Chart({                                                                            \n" +
                "	        chart: {                                                                                                                        \n" +
                "		        renderTo: '" + chart.getMarkupId() + "',                                                                                    \n";
        if (!WebSession.get().getClientInfo().getUserAgent().toLowerCase().contains("ipad") && !WebSession.get().getClientInfo().getUserAgent().toLowerCase().contains("iphone"))
            graph += "zoomType: 'xy', \n";
        graph += "               defaultSeriesType: 'line',                                                                                                  \n" +
                "	    	    spacingRight: 20,                                                                                                          \n" +
                "               //animation: false                                                                                                          \n" +
                "	        },                                                                                                                              \n" +
                "           colors: [" + getColor() + "],                                                                               \n" +
                "           title: {                                                                                                                        \n" +
                "               text: null                                                                                                                  \n" +
                "           },                                                                                                                              \n" +
                "           legend:{                                                                                                                        \n" +
                "               enabled: " + legend + "                                                                                                     \n" +
                "           },                                                                                                                              \n" +
                "           xAxis: {                                                                                                                        \n" +
                "	            type: 'datetime',                                                                                                           \n" +
                "	            maxZoom: 24 * 3600000, // dayly                                                                                             \n" +
                "               gridLineWidth: 1,                                                                                                           \n" +
                "	            title: {                                                                                                                    \n" +
                "			        text: null                                                                                                              \n" +
                "		        },                                                                                                                          \n" +
                "               dateTimeLabelFormats: {                                                                                                     \n" +
                "                   day: '%e/%b/%y'                                                                                                         \n" +
                "               }                                                                                                                           \n" +
                "               //labels: {                                                                                                                 \n" +
                "                 //  rotation: 30                                                                                                          \n" +
                "               //}                                                                                                                         \n" +
                "	        },                                                                                                                              \n" +
                "           yAxis: {                                                                                                                        \n" +
                "               title: {                                                                                                                    \n" +
                "                   text: null                                                                                                              \n" +
                "               },                                                                                                                          \n" +
                "               plotLines: [{                                                                                                               \n" +
                "                   value: 0,                                                                                                               \n" +
                "                   width: 2,                                                                                                               \n" +
                "                   color: '#8f8f8f'                                                                                                        \n" +
                "               }],                                                                                                                         \n" +
                "               min: null,                                                                                                                  \n" +
                "               startOnTick: false                                                                                                          \n" +
                "           },                                                                                                                              \n" +
                "           tooltip: {                                                                                                                      \n" +
                "               formatter: function() {                                                                                                     \n" +
                "                   var date = new Date(this.x);                                                                                            \n" +
                "                   var s = '<b>'+ Highcharts.dateFormat('%e %B %Y', this.x)  +'</b>';                                                      \n" +
                "                   $.each(this.points, function(i, point) {                                                                                \n" +
                "                       s += '<br/><span style=\"fill:'+point.series.color+';\">'+ point.series.name +': </span>'+ Math.round(point.y*100)/100\n" +
                "                   });                                                                                                                     \n" +
                "                   return s;                                                                                                               \n" +
                "               },                                                                                                                          \n" +
                "               shared: true                                                                                                                \n" +
                "           },                                                                                                                              \n" +
                "           plotOptions: {                                                                                                                  \n" +
                "               line :{                                                                                                                     \n" +
                "                   marker:{                                                                                                                \n" +
                "                       enabled:false,                                                                                                      \n" +
                "                       states: {                                                                                                            \n" +
                "                           hover: {                            \n" +
                "                               enabled: true,                  \n" +
                "                               symbol: 'circle',               \n" +
                "                               radius: 5,                          \n" +
                "                               lineWidth: 1                        \n" +
                "                           }                                   \n" +
                "                       }                           \n" +
                "                   }                                       \n" +
                "               }                                       \n" +
                "           },                                      \n" +
                "           series: [" + getData() + "],                \n" +
                "           navigation: {                               \n" +
                "               menuItemStyle: {                \n" +
                "                   fontSize: '10px'            \n" +
                "               }                           \n" +
                "           }                                              \n" +
                "       });";
        return graph;

    }

    public abstract String getData();

    public String buildData(ArrayList<PlotDataObject> dataList) {
        this.innerDataTable = dataList;
        //variables de sortie
        String out = "";
        //construction des données plus des labels
        for (PlotDataObject tempObject : this.innerDataTable) {
            out += "{name:'" + tempObject.getLabel() + "',data:[";
            Set entries = tempObject.getData().entrySet();
            Iterator it = entries.iterator();
            boolean first = true;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (first) {
                    xStart = (Date) entry.getKey();
                    first = false;
                }
                String[] date = new java.sql.Date(((Date) entry.getKey()).getTime()).toString().split(" ")[0].split("-");
                int month = Integer.valueOf(date[1]);
                out += "[Date.UTC(" + date[0] + "," + (month - 1) + "," + Integer.valueOf(date[2]) + ")," + entry.getValue() + "],\n";
            }
            out += "]},";
        }
        //nettoyage derniere virgule
        out = (out.length() > 0) ? out.substring(0, out.length() - 1) : out;
        return out;
    }

    //construction des labels depuis le table de ref
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


}