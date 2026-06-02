package com.project.Offering_Booking_System.exceptions;

public class AccessDeniedException  extends  CustomException{


    public AccessDeniedException(String message, int status) {
        super(message, status);
    }
}
