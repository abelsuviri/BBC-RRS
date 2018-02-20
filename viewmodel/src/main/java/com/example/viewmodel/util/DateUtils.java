package com.example.viewmodel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Abel Suviri
 */

public class DateUtils {
    public static String parseDate(String date) {
        try {
            SimpleDateFormat currentFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZZ", Locale.ENGLISH);
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.ENGLISH);
            Date currentDate = currentFormat.parse(date);
            return sdf.format(currentDate);
        } catch (ParseException ignore) {
            return date;
        }
    }
}
