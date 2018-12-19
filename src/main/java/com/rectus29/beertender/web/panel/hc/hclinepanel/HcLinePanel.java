package com.rectus29.beertender.web.panel.hc.hclinepanel;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.rectus29.beertender.service.IserviceUser;
import com.rectus29.beertender.web.Config;
import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
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
    private boolean legend = true;
    private Label chart;
    private List<SeriesDataObject> seriesObjectList = new ArrayList<SeriesDataObject>();

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
                "       var chart" + chart.getMarkupId() + " =new Highcharts.Chart({                                                                            \n" +
                "	        chart: {                                                                                                                        \n" +
                "		        renderTo: '" + chart.getMarkupId() + "',                                                                                    \n" +
        		 "               defaultSeriesType: 'line',                                                                                                  \n" +
                "	    	    spacingRight: 20,                                                                                                          \n" +
                "               //animation: false                                                                                                          \n" +
                "	        },                                                                                                                              \n" +
                "           colors: [" + getColor() + "],                                                                               \n" +
                "           title: {                                                                                                                        \n" +
                "               text: null                                                                                                                  \n" +
                "           },                                                                                                                              \n" +
                "           legend:{                                                                                                                        \n" +
                "               enabled: " + legend + "," +
				"				placement: " + this.legendPlacement + "                                                                                     \n" +
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
                "                       states: {                                                                                                           \n" +
                "                           hover: {                            																			\n" +
                "                               enabled: true,                  																			\n" +
                "                               symbol: 'circle',               																			\n" +
                "                               radius: 5,                     																				\n" +
                "                               lineWidth: 1                    																			\n" +
                "                           }                                   																			\n" +
                "                       }                           																						\n" +
                "                   }                                     																				  	\n" +
                "               }                                   																				    	\n" +
                "           },                                    																				  			\n" +
                "           series: [" + buildData() + "],        																				        	\n" +
                "           navigation: {                        																				       		\n" +
                "               menuItemStyle: {               																				 				\n" +
                "                   fontSize: '10px'           																				 				\n" +
                "               }                         																				  					\n" +
                "           }                                          																				    	\n" +
                "       });";
        return graph;

    }

    public abstract List<SeriesDataObject> getData();

    private String buildData() {
        this.seriesObjectList = getData();
        //variables de sortie
		JsonObject out = new JsonObject();
        //construction des données plus des labels
        for (SeriesDataObject tempObject : this.seriesObjectList) {
            out.addProperty("name", tempObject.getLabel());
            //iter on each plot in series
			JsonArray dataArray = new JsonArray();
			for( Map.Entry<Object, Number> entry : tempObject.getData().entrySet()){
				JsonObject jsonObject = new JsonObject();
				jsonObject.addProperty("x", entry.getKey().toString());
				jsonObject.addProperty("y",entry.getValue());
				dataArray.add(jsonObject);
			}
            out.add("data", dataArray);
        }
        return out.toString();
    }

	public String getColor() {
		return Config.get().getDefaultColor();
	}

}