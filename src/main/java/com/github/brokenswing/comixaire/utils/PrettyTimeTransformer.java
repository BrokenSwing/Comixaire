package com.github.brokenswing.comixaire.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrettyTimeTransformer {

    public PrettyTimeTransformer(){}

    public static String prettyDuration(int input) {
        StringBuilder sb = new StringBuilder();

        int hours = input / 3600;
        int minutes = (input % 3600) / 60;
        int seconds = input % 60;

        if(hours > 0) {
            sb.append(hours + "h ");
        }
        if(minutes > 0 || hours > 0) {
            sb.append(minutes + "min ");
        }
        sb.append(seconds + "s");

        return sb.toString();
    }

    public static String prettyDate(Date date) {
        return new SimpleDateFormat("dd MMM yyyy").format(date);
    }
}
