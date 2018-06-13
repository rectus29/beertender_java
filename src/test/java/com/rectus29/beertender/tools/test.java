package com.rectus29.beertender.tools;

import com.rectus29.beertender.api.EveCRESTApi;
import com.rectuscorp.evetool.api.EveCRESTApi;
import com.rectuscorp.evetool.entities.core.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.json.JSONObject;
import org.junit.Test;

import java.lang.Character;
import java.util.*;

public class test {
	private static final Logger log = LogManager.getLogger(test.class);

	public static void main(String[] args) {
		HttpClient client = new HttpClient();
		try {
			//GetMethod get = new GetMethod( CrestObject.API_URL + "market/groups/");
			GetMethod get = new GetMethod(EveCRESTApi.API_URL + "regions/");
			client.executeMethod(get);
			String resp = get.getResponseBodyAsString();
			JSONObject jsonObj = new JSONObject(resp);
			ArrayList<JSONObject> jsonObjects = new ArrayList<JSONObject>();
			for (int i = 0; i < jsonObj.getJSONArray("items").length(); i++) {
				JSONObject temp = (JSONObject) jsonObj.getJSONArray("items").get(i);
				if (!temp.has("parentGroup")) {
					jsonObjects.add(temp);
				}
			}
			Collections.sort(jsonObjects, new Comparator<JSONObject>() {
				public int compare(JSONObject o1, JSONObject o2) {
					try {
						return ((String) o1.get("name")).compareTo(((String) o2.get("name")));
					} catch (Exception e) {
						return 0;
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test() {
		String[] dico = new String[] {
				"pdyjrkaylryr",       //4
				"zqdrhpviqslik"      //7

		};
		String to = "rkacypviuburk";

		List<String> chars = Arrays.asList(to.split(""));
		String bestWord = null;
		int bestWeight = 99999;
		for (String word : dico) {
			int weight = 0;
			char[] wordChars = word.toCharArray();

			for(char wordChar:wordChars){
				if(!chars.contains(new String(wordChar+""))){
					weight++;
				}
			}

			int sizeDiff = Math.abs(chars.size() - wordChars.length);

					weight += sizeDiff;
			if (weight < bestWeight) {
				bestWord = word;
				bestWeight = weight;
			}
		}
		System.out.println(bestWord);
	}

}


