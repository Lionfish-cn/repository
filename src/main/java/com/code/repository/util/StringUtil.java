package com.code.repository.util;

public class StringUtil {

    public static Boolean isNull(String str) {
        return str == null || str.length() == 0 || str.toLowerCase().equals("undefined");
    }

    public static Boolean isNotNull(String str) {
        return !isNull(str);
    }
}
