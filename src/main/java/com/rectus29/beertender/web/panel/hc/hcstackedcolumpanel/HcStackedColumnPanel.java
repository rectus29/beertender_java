package com.rectus29.beertender.web.panel.hc.hcstackedcolumpanel;

/*-----------------------------------------------------*/
/* User: Rectus_29      Date: 17/02/2015 15:42 */
/*                                                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/

import com.rectus29.beertender.web.BeerTenderConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.*;

public abstract class HcStackedColumnPanel extends Panel {

    private static final Logger log = LogManager.getLogger(HcStackedColumnPanel.class);
    private Label chart;
    private ArrayList<String> tickList = new ArrayList<String>();
    private List<BarStackedDataObject> data = new ArrayList<BarStackedDataObject>();
    public static String height = "300px";
    public static String width = "400px";

    public HcStackedColumnPanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        chart = new Label("chart");
        chart.add(new AttributeModifier("style", "width:" + getWidth() + "; margin:auto; height:" + getHeight() + ";"));
        chart.setOutputMarkupId(true);
        add(chart);
    }


	@Override public void renderHead(IHeaderResponse response) {
		super.renderHead(response);
		response.render(OnDomReadyHeaderItem.forScript(getJS()));
	}

    private String getJS() {
        String graph = "" +
                "   $('#" + chart.getMarkupId() + "').highcharts({                                                                                          \n" +
                "	        chart: {                                                                                                                        \n" +
                "               type: 'column'                                                                                                              \n" +
                "	        },                                                                                                                              \n" +
                "           credits: {                                                                                                                      \n" +
                "               enabled: false                                                                                                              \n" +
                "           },                                                                                                                              \n" +
                "           colors: [" + getColor() + "],                                                                               \n" +
                "           title: {                                                                                                                        \n" +
                "               text: null                                                                                                                  \n" +
                "           },                                                                                                                              \n" +
                "           legend:{                                                                                                                        \n" +
                "               enabled: " + legendIsVisble() + "                                                                                           \n" +
                "           },                                                                                                                              \n" +
                "            yAxis: {                                                                                                                       \n" +
                "               title:null                                                                                                                  \n" +
                "           },                                                                                                                              \n" +
                "           tooltip: {                                                                                                                      \n" +
                "               formatter: function() {                                                                                                     \n" +
                "                   return '<b>' + this.x + '</b><br/>' +                                                                                   \n" +
                "                    this.series.name + ': ' + this.y + '<br/>' +                                                                           \n" +
                "                    'Total: ' + this.point.stackTotal;                                                                                     \n" +
                "               }                                                                                                                           \n" +
                "           },                                                                                                                              \n" +
                "           plotOptions: {                                                                                                                  \n" +
                "               column: {                                                                                                                   \n" +
                "                   stacking: 'normal',                                                                                                     \n" +
                "                   dataLabels: {                                                                                                           \n" +
                "                       enabled: true,                                                                                                      \n" +
                "                       color: '#666',                                                                                                      \n" +
                "                       formatter: function() {                                                                                             \n" +
                "                               return Math.round(this.y*100)/100;                                                                          \n" +
                "                       },                                                                                                                  \n" +
                "                       style: {                                                                                                            \n" +
                "                           font: 'normal 13px Verdana, sans-serif'                                                                         \n" +
                "                       }                                                                                                                   \n" +
                "                   }                                                                                                                       \n" +
                "               }                                                                                                                           \n" +
                "           },                                                                                                                              \n" +
                "           series: [" + buildData() + "],                                                                                                  \n" +
                "           xAxis: {                                                                                                                        \n" +
                "               categories: [" + getTicks() + "]                                                                                            \n" +
                "           },                                                                                                                              \n" +
                "           exporting: {                                                                                                                    \n" +
                "               enabled: false                                                                                                              \n" +
                "           }                                                                                                                               \n" +
                "       });";
        return graph;

    }

    protected Boolean legendIsVisble() {
        return true;
    }

    public abstract List<BarStackedDataObject> getData();

    public String buildData() {
        String out = "";
        HashMap<String, List<Float>> data = new HashMap<String, List<Float>>();
        for (BarStackedDataObject temp : getData()) {
            tickList.add(temp.ticks);
            for (Map.Entry<String, List<Float>> tempEntry : temp.data.entrySet()) {
                if(data.get(tempEntry.getKey()) == null)
                    data.put(tempEntry.getKey(), new ArrayList<Float>());
                for (Float tempValue : tempEntry.getValue())
                    data.get(tempEntry.getKey()).add(tempValue);
            }
        }
        for (String temp : data.keySet()) {
            out += "{name:'" + temp + "',data:[";
            for (Float tempFloat : data.get(temp)) {
                out += tempFloat.toString() + ",";
            }
            out = out.substring(0, out.length() - 1);
            out += "]},";
        }
        if (!out.isEmpty())
            out = out.substring(0, out.length() - 1);
        return out;
    }

    private String getTicks() {
        String out = "";
        for (String temp : tickList) {
            out += "'" + ((temp != null) ? temp : "") + "',";
        }
        out = out.substring(0, out.length() - 1);
        return out;
    }

    public class BarStackedDataObject {
        public String ticks;
        public LinkedHashMap<String, List<Float>> data;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

	public String getColor() {
		return BeerTenderConfig.get().getDefaultColor();
	}

}
