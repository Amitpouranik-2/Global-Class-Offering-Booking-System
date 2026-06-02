package com.project.Offering_Booking_System.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler( CustomException.class)
    public ResponseEntity<?> handleCustomException( CustomException ex)
    {
        Map<String , Object > body = Map.of(
                "timestamp" , Instant.now().toString() ,
                "status" , ex.getStatus(),
                "error" , ex.getMessage(),
                "details" , ex.getMessage()
        );

        return ResponseEntity.status(ex.getStatus()).body(body);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnexpected( Exception ex)
    {
        Map<String , Object > body = Map.of(
                "timestamp" , Instant.now().toString() ,
                "status" , 500,
                "error" , "Internal Server Error",
                "details" , ex.getMessage()
        );

     return ResponseEntity.status(500).body(body);
    }

}
