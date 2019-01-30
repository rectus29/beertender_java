package com.rectus29.beertender.web.panel.hc.piePanel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rectus29.beertender.web.panel.hc.HcGraphPanel;

import java.util.Map;

/**
 * User: Rectus
 * Date: 09/02/11
 * Time: 16:39
 */
public abstract class HcPiePanel extends HcGraphPanel {


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
	protected String getJS() {
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
				"           series: [" + buildData() + "],                                                                                                    \n" +
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

	public abstract PieDataObject getData();

	private String buildData() {
		//outputDataObject
		JsonObject out = new JsonObject();
		//retreive data
		PieDataObject data = getData();
		if (data != null) {
			out.addProperty("name", data.getName());
			out.addProperty("colorByPoint", true);
			JsonArray dataArray = new JsonArray();
			for (Map.Entry<String, Number> temp : data.getData().entrySet()) {
				JsonObject dataPoint = new JsonObject();
				dataPoint.addProperty("name", temp.getKey());
				dataPoint.addProperty("y", temp.getValue());
				dataArray.add(dataPoint);
			}
			out.add("data", dataArray);
		}
		return out.toString();
	}
}