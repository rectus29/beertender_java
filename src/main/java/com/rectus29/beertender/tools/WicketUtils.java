package com.rectus29.beertender.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: rectus_29
 * Date: 8 déc. 2010
 * Time: 15:35:50
 * To change this template use File | Settings | File Templates.
 */
public class WicketUtils {

    public static List<String> getWicketId(String txt){
       List<String> id= new ArrayList<String>();

        Matcher matcher = Pattern.compile("wicket:id=\"(.*?)\"").matcher(txt);
        while (matcher.find())
            id.add(matcher.group(1));

        return id;
    }
    public static List<String> getWicketIdByTag(String txt, String tag){
        List<String> id= new ArrayList<String>();

        Matcher matcher = Pattern.compile("wicket:id=\"" + tag + "_(.*?)\"").matcher(txt);
        while (matcher.find())
            id.add(matcher.group(1));

        return id;
    }
}
