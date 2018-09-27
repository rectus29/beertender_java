package com.rectus29.beertender.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 15 avr. 2010
 * Time: 17:04:01
 * To change this template use File | Settings | File Templates.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {


	private static final Logger log = LogManager.getLogger(StringUtils.class);

	public static String getTagRegExp(String tag) {
		return "<" + tag + "[^>]*>(.*?)</\\1>";
	}

	public static String getTagContent(String txt, String tag) {
		String result = "";
		int posMin = txt.indexOf("<" + tag + ">");
		int posMax = txt.indexOf("</" + tag + ">");

		if ((posMin == -1) || (posMax == -1))
			return txt;
		return txt.substring(posMin + tag.length() + 2, posMax);
	}

	/**
	 * ajoute/met à jour les ancres des TITRE_1
	 *
	 * @param text
	 * @return
	 */
	public static String addTitleAnchor(String text) {
		StringBuilder sb = new StringBuilder(text);

		int pos = 0;
		int nb = 0;
		while ((pos = sb.indexOf("<TITRE1>", +pos)) != -1) {
			sb.insert(pos + 7, " id=\"" + nb + "\"");
			nb++;
			pos++;
			//log.debug(nb);
		}

		return sb.toString();
	}

	/**
	 * Encode les entités HTML dans la string
	 *
	 * @param text
	 * @return text
	 */
	public static String encodeHtmlEntities(String text) {
        /*text = text.replaceAll("&", "&amp;");
        text = text.replaceAll(">", "&gt;");
        text = text.replaceAll("<", "&lt;");
        text = text.replaceAll("'", "&acute;");
        text = text.replaceAll("`", "&acute;");
        text = text.replaceAll("\"", "&quot;");  */
		return text;
	}

	/**
	 * Encode les entités HTML dans la string
	 *
	 * @param text
	 * @return text
	 */
	public static String decodeHtmlEntities(String text) {
       /* text = text.replaceAll("&gt;", ">");
        text = text.replaceAll("&lt;", "<");
        text = text.replaceAll("&acute;", "'");
        text = text.replaceAll("&quot;", "\"");
        text = text.replaceAll("&amp;", "&");*/
		return text;
	}

	/**
	 * Renvoie le nombre d'occurrences du pattern spécifié dans la chaine de caractères spécifiée
	 *
	 * @param text  chaine de caractères initiale
	 * @param regex expression régulière dont le nombre d'occurrences doit etre compté
	 * @return le nombre d'occurrences du pattern spécifié dans la chaine de caractères spécifiée
	 */
	public static int regexOccur(String text, String regex) {
		Matcher matcher = Pattern.compile(regex).matcher(text);
		int occur = 0;
		while (matcher.find()) {
			occur++;
		}
		return occur;
	}

	public static String[] splitStringEvery(String s, int interval) {
		int arrayLength = (int) Math.ceil(((s.length() / (double) interval)));
		String[] result = new String[arrayLength];

		int j = 0;
		int lastIndex = result.length - 1;
		for (int i = 0; i < lastIndex; i++) {
			result[i] = s.substring(j, j + interval);
			j += interval;
		} //Add the last bit
		result[lastIndex] = s.substring(j);

		return result;
	}

	/**
	 * compte le nombre d'occurence dans un texte
	 *
	 * @param text   le texte général
	 * @param search le teste cherché
	 * @return le nombre cherché
	 */
	public int countIndexOf(String text, String search) {
		int count = 0;
		for (int fromIndex = 0; fromIndex > -1; count++)
			fromIndex = text.indexOf(search, fromIndex + ((count > 0) ? 1 : 0));
		return count - 1;
	}


}
