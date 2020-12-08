package com.electric.business.util;

import java.util.Collection;

public class ArrayUtil {

    public static Boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty() || collection.size() <= 0;
    }



}
