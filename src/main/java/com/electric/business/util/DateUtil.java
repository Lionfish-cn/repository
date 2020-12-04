package com.electric.business.util;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        if (StringUtil.isNotNull(d)) {
            return sdf.parse(d);
        }
        return null;
    }
}
