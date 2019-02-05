package com.rectus29.beertender.web.panel.hc.hcpiePanel;

import java.util.HashMap;

/*-----------------------------------------------------*/
/*                     Rectus29                        */
/*                                                     */
/*                   Date: 30/01/2019                     */
/*                 All right reserved                  */
/*-----------------------------------------------------*/
public class PieDataObject {

	private String name;

	private HashMap<String, Number> data = new HashMap<>();

	public PieDataObject(String name) {
		this.name = name;
	}

	public PieDataObject(String name, HashMap<String, Number> data) {
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public PieDataObject setName(String name) {
		this.name = name;
		return this;
	}

	public HashMap<String, Number> getData() {
		return data;
	}

	public PieDataObject addData(String serieLabel, Number serieValue) {
		data.put(serieLabel, serieValue);
		return this;
	}

	public PieDataObject setData(HashMap<String, Number> data) {
		this.data = data;
		return this;
	}
}
