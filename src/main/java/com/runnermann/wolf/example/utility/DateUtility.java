package com.runnermann.wolf.example.utility;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DateUtility {

    public static int[] parseDate(String date) {
        String[] d = date.split("-");
        return Arrays.stream(d).mapToInt(i -> Integer.parseInt(i)).toArray();
    }

    public static String isoDate(int yr, int mo, int day) {
        return yr + "-" + mo + "-" + day;
    }

    public static String toISODate(long nanos) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(nanos);
        return sdf.format(d);
    }

  /*  public static void main(String[] args) {
        int[] testDate = new int[3];
        int year = 2021;
        int mo = 5;
        int day = 15;
        testDate[0] = mo;
        testDate[1] = day;
        testDate[2] = year;
        String date = isoDate(year, mo, day);
        boolean bool = true;

        int[] dateInt = parseDate(date);
        for (int i = 0; i < dateInt.length; i++) {
            System.out.println(dateInt[i] + " = " + testDate[i]);
        }
    }
    */
}
