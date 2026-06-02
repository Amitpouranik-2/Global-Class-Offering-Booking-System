package com.project.Offering_Booking_System.config;

import com.project.Offering_Booking_System.entity.UserPrincipal;
import com.project.Offering_Booking_System.exceptions.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {


    private static UserPrincipal getPrincipal()  {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if( authentication == null || !(( authentication.getPrincipal()) instanceof  UserPrincipal))
        {
            throw new AccessDeniedException("user is not authenticated" , 401);
        }
        return ( UserPrincipal) authentication.getPrincipal();
     }


     public static Long getCurrentUserId()
     {
         return getPrincipal().getUserId();
     }


}
