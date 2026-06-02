package com.project.Offering_Booking_System.controller;

import com.project.Offering_Booking_System.dto.request.AuthRequest;
import com.project.Offering_Booking_System.dto.request.RegisterRequest;
import com.project.Offering_Booking_System.dto.response.AuthResponse;
import com.project.Offering_Booking_System.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {


    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> login(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok( authService.registerUser(request));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @RequestBody AuthRequest request) {

        return ResponseEntity.ok( authService.loginUser(request));
    }


}
