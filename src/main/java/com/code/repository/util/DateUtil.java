package com.code.repository.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtil {

    public static String convertDateToString(Date d, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (d != null) {
            return sdf.format(d);
        }
        return null;
    }

    public static Date convertStringToDate(String d, String pattern) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (com.headyonder.dmp.util.StringUtil.isNotNull(d)) {
            return sdf.parse(d);
        }
        return null;
    }

    public static Boolean isDate(String d) {
        return Pattern.matches("(\\d{4}(-|年|.)\\d{2}(-|年|.)\\d{2} \\d{2}(:|时|.)\\d{2}(:|分|.))", d);
    }
}
