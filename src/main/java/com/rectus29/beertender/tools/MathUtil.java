package com.rectus29.beertender.tools;

import org.apache.logging.log4j.Logger; import org.apache.logging.log4j.LogManager;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Rectus
 * Date: 25/02/11
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */


public class MathUtil {

    private static final Logger log = LogManager.getLogger(MathUtil.class);

    /**
     * renvoie une moyenne ponderee
     *
     * @param v1    borne basse
     * @param v2    borne haute
     * @param inter interval de travail
     * @param pos   position recherche
     * @return
     */
    public static double moyPonder(double v1, double v2, int inter, int pos) {
        return v1 + (v2 - v1) / inter * pos;
    }

    /**
     * calcul de perf
     *
     * @param v1       borne basse
     * @param v2       borne haute
     * @param dividend tab de dividend
     * @return
     */
    public static Double perfEngine(double v1, double v2, List<Double> dividend) {
        Double divi = 0d;
        for (Double tempDivi : dividend) {
            divi += tempDivi;
        }
        return (v2 - v1 + divi) / v1;
    }

    /**
     * Function pour arrondir return en double
     *
     * @param value
     * @param decimalPlaces
     * @return
     */
    public static double round(double value, int decimalPlaces) {
        if (decimalPlaces < 0) {
            return value;
        }
        double augmentation = Math.pow(10, decimalPlaces);
        return Math.round(value * augmentation) / augmentation;
    }

    /**
     * Function pour arrondir return en double
     *
     * @param value
     * @return
     */
    public static double round(double value) {
        double augmentation = Math.pow(10, 2);
        return Math.round(value * augmentation) / augmentation;
    }


    /**
     * calcul de la base 100 de x hashMap
     *
     * @param el
     * @return
     */
    public static List<HashMap> base100ConvertorHashMap(List<HashMap> el) {
        for (HashMap<Date, Double> Lfh : el) {
            Double ref = null;
            Set entries = Lfh.entrySet();
            Iterator it = entries.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (ref == null) ref = (Double) entry.getValue();
                entry.setValue(((Double) (entry.getValue()) * 100) / ref);
            }
        }
        return el;
    }

    public static HashMap base100Convertor(HashMap el) {
        Double ref = null;
        Set entries = el.entrySet();
        Iterator it = entries.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (ref == null) ref = (Double) entry.getValue();
            entry.setValue(((Double) (entry.getValue()) * 100) / ref);
        }
        return el;
    }


}
