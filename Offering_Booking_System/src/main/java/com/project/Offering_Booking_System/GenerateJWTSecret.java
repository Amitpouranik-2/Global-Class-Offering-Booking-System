package com.project.Offering_Booking_System;

import java.security.SecureRandom;
import java.util.Base64;

public class GenerateJWTSecret {

    public static void main( String[] args)
    {

        byte[] key = new byte[64];
        new SecureRandom().nextBytes(key);

        String secret = Base64.getEncoder().encodeToString(key);
        System.out.println("JWT Secret : " + secret);


        byte[] sessionKey  = new byte[32];
        new SecureRandom().nextBytes(sessionKey);

        StringBuilder sb = new StringBuilder();
        for( byte b : sessionKey)
        {
            sb.append(String.format("%02X" , b));

        }

        System.out.println(sb);
    }


}
