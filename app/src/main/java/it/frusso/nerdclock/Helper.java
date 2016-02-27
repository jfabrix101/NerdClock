package it.frusso.nerdclock;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GD06548 on 25/02/2016.
 */
public class Helper {

    private static SimpleDateFormat row2SDF = new SimpleDateFormat("yyyy MM dd - HH:mm");

    public static String getFirstRow(ClockType type) {
        long now = System.currentTimeMillis();
        StringBuilder row = new StringBuilder();
        return "" + now;
    }

    public static String getSecondRow() {
        long now = System.currentTimeMillis();
        return row2SDF.format(new Date());
    }

    public static String getAsHex(int year, int month, int day, int hour, int minutes) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(year));
        sb.append(" " + Integer.toHexString(month));
        sb.append(" " + Integer.toHexString(day));
        sb.append(" " + Integer.toHexString(hour));
        sb.append(" " + Integer.toHexString(minutes));
        return sb.toString();
    }

    public static String getAsBinary(int year, int month, int day, int hour, int minutes) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toBinaryString(year));
        sb.append(" " + Integer.toBinaryString(month));
        sb.append(" " + Integer.toBinaryString(day));
        sb.append(" " + Integer.toBinaryString(hour));
        sb.append(" " + Integer.toBinaryString(minutes));
        return sb.toString();
    }


    public static String getAsMorse(int year, int month, int day, int hour, int minutes) {
        String[] alpha = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
                "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
                "9", "0", " ", "\n" };
        String[] dottie = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.",
                "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
                "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
                "-.--", "--..", ".----", "..---", "...--", "....-", ".....",
                "-....", "--...", "---..", "----.", "-----", "|", "\n" };

        Map<String, String> map = new HashMap<String, String>();


        for (int i=0; i<alpha.length; i++) {
            map.put(alpha[i], dottie[i]);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(year  + " " + month + " " + day + "\n" + hour + " " + minutes);
        String x = sb.toString();

        String morse = "";
        char[] translates = x.toCharArray();
        for (int j = 0; j < translates.length; j++)  {
            String a = x.substring(j, j+1);
            morse += map.get("" + a);
        }
        return morse;
    }


    public static void log(String msg) {
        Log.d("NerdClock", msg);
    }

}
