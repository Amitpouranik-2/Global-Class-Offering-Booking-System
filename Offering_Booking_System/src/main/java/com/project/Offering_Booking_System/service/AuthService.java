package com.project.Offering_Booking_System.service;

import com.project.Offering_Booking_System.dto.request.AuthRequest;
import com.project.Offering_Booking_System.dto.request.RegisterRequest;
import com.project.Offering_Booking_System.dto.response.AuthResponse;

public interface AuthService {


    public AuthResponse registerUser(RegisterRequest request);

    public AuthResponse loginUser(AuthRequest request);


}
