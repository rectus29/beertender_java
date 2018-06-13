package com.rectus29.beertender.tools;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Rectus for
 * Date: 5 nov. 2010
 * Time: 09:37:27
 */
public class DateUtil extends DateUtils {


    /**
     * renvoie nb jour entre 2 dates
     *
     * @param d1 date superieur
     * @param d2 date inferieur
     * @return int nb jour
     */
    public static int dayBetween(Date d1, Date d2) {


        DateTime start = new DateTime(d1.getTime());
        DateTime end = new DateTime(d2.getTime());

        Days days = Days.daysBetween(start, end);
        return Math.abs(days.getDays());
    }

    /**
     * Ajouter x jour
     *
     * @param date
     * @param amount
     * @return
     */
    public static java.sql.Date addDay(java.sql.Date date, int amount) {

        Date tempDate = new Date(date.getTime());
        tempDate = DateUtils.addDays(tempDate, amount);
        return new java.sql.Date(tempDate.getTime());

        /*DateTimeFormatter parser = ISODateTimeFormat.date();
        DateTime datetime = new DateTime(date.getTime());
        datetime.plusDays(amount);
        return new java.sql.Date(datetime.toDate().getTime());
          */
        //return new java.sql.Date(date.getTime() + (DateUtil.MILLIS_PER_DAY * amount));

    }

    /**
     * test du mm jour pour date SQL
     *
     * @param date0
     * @param date1
     * @return
     */
    public static boolean isSameDay(java.sql.Date date0, java.sql.Date date1) {
        Date date01 = new Date(date0.getTime());
        Date date11 = new Date(date1.getTime());
        return DateUtils.isSameDay(date01, date11);
    }

    /**
     * test du mm mois pour date SQL
     *
     * @param date0
     * @param date1
     * @return
     */
    public static boolean isSameMonth(Date date0, Date date1) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date0);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date1);

        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
    }


    /**
     * Ajouter x mois
     *
     * @param date
     * @param amount
     * @return
     */
    public static java.sql.Date addMonthsql(java.sql.Date date, int amount) {
        Date temp = new Date(date.getTime());
        java.sql.Date out = new java.sql.Date(DateUtils.addMonths(temp, amount).getTime());
        return out;
    }

    /**
     * va a la fin du mois de la date en cour
     *
     * @param date
     * @return
     */
    public static Date goToEndOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int d = cal.getActualMaximum(cal.DAY_OF_MONTH);
        date = DateUtil.setDays(date, cal.getActualMaximum(cal.DAY_OF_MONTH));
        return date;
    }

    /**
     * va au debut du mois de la date en cour
     *
     * @param date
     * @return
     */
    public static Date goToBeginOfMonth(Date date) {
        return DateUtil.setDays(date, 1);
    }

    /**
     * va a la fin de la semaine de la date en cour
     *
     * @param date
     * @return
     */
    public static Date goToEndOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return DateUtil.addDays(date, (7 - cal.get(cal.DAY_OF_WEEK)));
    }

    /**
     * va au debut de la semaine de la date en cour
     *
     * @param date
     * @return
     */
    public static Date goToBeginOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return DateUtil.addDays(date, -(cal.get(cal.DAY_OF_WEEK)));
    }

    /**
     * va au debut de l'anne de la date fournie
     *
     * @param date
     * @return
     */
    public static Date goToBeginOfYear(Date date) {
        date = DateUtil.setMonths(date, 0);
        date = DateUtil.setDays(date, 1);
        return date;
    }

    /**
     * Donne le nombre de mois en tre 2 date
     *
     * @param datedeb
     * @param datefin
     * @return
     */
    public static int monthBetween(Date datedeb, Date datefin) {

        DateTime start = new DateTime(datedeb.getTime());
        DateTime end = new DateTime(datefin.getTime());

        Months months = Months.monthsBetween(start, end);
        return months.getMonths();
    }

    /**
     * Donne le nombre de'annee en tre 2 date
     *
     * @param datedeb
     * @param datefin
     * @return
     */
    public static int yearBetween(Date datedeb, Date datefin) {
        DateTime start = new DateTime(datedeb.getTime());
        DateTime end = new DateTime(datefin.getTime());

        Years years = Years.yearsBetween(start, end);
        return years.getYears();
    }

    /**
     * Donne le nombre de'annee en tre 2 date en double
     *
     * @param datedeb
     * @param datefin
     * @return
     */
    public static Double yearBetweenDouble(Date datedeb, Date datefin) {
        return dayBetween(datedeb, datefin) / 365d;
    }

    /**
     * met une date a l'anne courante
     *
     * @param date
     * @return
     */
    public static Date setCurrentYear(Date date) {
        Calendar cal = Calendar.getInstance();
        int n = cal.get(cal.YEAR);
        return DateUtil.setYears(date, n);
    }

    /**
     * MAJ la date de cloture du user de façon a travailler sur la periode de 12 mois
     *
     * @param date
     * @return
     */
    public static Date updateClotureDate(Date date) {
        //MAJ de la date pour retour
        Date dateRef = DateUtil.setCurrentYear(date);
        //preparation calendar pour le mois
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateRef);
        int m = cal.get(cal.MONTH);
        //preparation calendar de reference TODAY
        Calendar cal2 = Calendar.getInstance();
        int m2 = cal2.get(cal.MONTH);
        //si le mm mois on recule de 1 ans
        if (m >= m2) {
            if(m == m2 && cal.get(cal.DAY_OF_MONTH) <= cal2.get(cal2.DAY_OF_MONTH)){
                return dateRef;
            }else
            return DateUtil.addYears(dateRef, -1);
        }
        return dateRef;
    }


    /**
     * renvoie true si le mm mois annee non gerer
     *
     * @param d1
     * @param d2
     * @return
     */
   /* public static Boolean isSameMonth(Date d1, Date d2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        int m = cal.get(cal.MONTH);
        cal.setTime(d2);
        int m2 = cal.get(cal.MONTH);
        if (m == m2) return true;
        return false;
    }*/

    /**
     * renvoie true si le mm annee mois non gerer
     *
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean isSameYear(Date d1, Date d2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        int m = cal.get(cal.YEAR);
        cal.setTime(d2);
        int m2 = cal.get(cal.YEAR);
        if (m == m2) return true;
        return false;
    }


    /**
     * cree une date au donnee fournie
     * ATTENTION PAS DE VERIF
     *
     * @param day
     * @param month
     * @param year
     * @return
     */
    public static Date getDate(int day, int month, int year) {
        Date temp = new Date();
        temp = DateUtil.setDays(temp, day);
        temp = DateUtil.setMonths(temp, month - 1);
        temp = DateUtil.setYears(temp, year);
        return temp;
    }

    /**
     * return l'annee de la date donne
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(cal.YEAR);
    }

    /**
     * return l'anne de la date donne
     *
     * @param date1 reference
     * @param date1 date à mettre à jour
     * @return
     */
    public static Date setSameYear(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        cal2.set(cal.get(cal.YEAR),cal2.get(cal2.MONTH),cal2.get(cal2.DAY_OF_MONTH));
        return cal2.getTime();
    }

    /**
     * Donne le nombre de semaine en tre 2 date
     *
     * @param datedeb
     * @param datefin
     * @return
     */
    public static int weekBetween(Date datedeb, Date datefin) {
        DateTime start = new DateTime(datedeb.getTime());
        DateTime end = new DateTime(datefin.getTime());

        Weeks weeks = Weeks.weeksBetween(start, end);
        return weeks.getWeeks();
    }

    /**
     * retourne la derniere date ouvrée
     *
     * @param date
     * @return
     */
    public static Date goToPreviousWorkingDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayWeek = cal.get(cal.DAY_OF_WEEK);
        if (dayWeek == 1) {
            date = addDays(date, -2);
        }
        if (dayWeek == 7) {
            date = addDays(date, -1);
        }
        return date;

    }
}
