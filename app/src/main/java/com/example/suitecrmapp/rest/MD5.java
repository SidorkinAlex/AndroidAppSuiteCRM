package com.example.suitecrmapp.rest;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.math.*;

public class MD5 {
    public static String hash(String string)  {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(StandardCharsets.UTF_8.encode(string));
        return String.format("%032x", new BigInteger(1, md5.digest()));
    }
}