package com.code.repository.util;


import java.util.UUID;

public class IDGenerator {
    public static String generateID() {
        String hex = Long.toHexString(System.currentTimeMillis());
        hex += UUID.randomUUID();
        hex = hex.replaceAll("-","");
        return hex.substring(0,32);
    }
}
