package com.rectus29.beertender.tools;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.wicket.ajax.json.JSONObject;
import org.junit.Test;

import java.util.*;

public class test {
	private static final Logger log = LogManager.getLogger(test.class);

	public static void main(String[] args) {

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


