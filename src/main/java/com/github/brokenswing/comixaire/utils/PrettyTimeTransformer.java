package com.github.brokenswing.comixaire.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrettyTimeTransformer
{

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy");

    private PrettyTimeTransformer()
    {
    }

    public static String prettyDuration(int input)
    {
        StringBuilder sb = new StringBuilder();

        int hours = input / 3600;
        int minutes = (input % 3600) / 60;
        int seconds = input % 60;

        if (hours > 0)
        {
            sb.append(hours).append("h ");
        }
        if (minutes > 0 || hours > 0)
        {
            sb.append(minutes).append("min ");
        }
        sb.append(seconds).append("s");

        return sb.toString();
    }

    public static String prettyDate(Date date)
    {
        return DATE_FORMAT.format(date);
    }

}
