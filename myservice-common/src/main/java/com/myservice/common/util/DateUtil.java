package com.myservice.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static String format(final Date date) {
        return format(date, "dd/MM/yyyy");
    }

    public static String format(final Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date format(String dateInString) {
        return format(dateInString, "yyyy-MM-dd");
    }

    public static Date format(String dateInString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(dateInString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date formatBR(String dateInString, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        try {
            return formatter.parse(dateInString);

        } catch (ParseException e) {
            return null;
        }
    }

}
