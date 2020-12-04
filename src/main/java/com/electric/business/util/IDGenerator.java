package com.electric.business.util;


import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.UUID;

public class IDGenerator {
    public static String generateID() {
        String hex = Long.toHexString(System.currentTimeMillis());
        hex += UUID.randomUUID();
        hex = hex.replaceAll("-","");
        return hex.substring(0,32);
    }
}
