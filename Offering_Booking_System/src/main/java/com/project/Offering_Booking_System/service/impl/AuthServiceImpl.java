package com.project.Offering_Booking_System.service.impl;

import com.project.Offering_Booking_System.config.JwtTokenProvider;
import com.project.Offering_Booking_System.dto.request.AuthRequest;
import com.project.Offering_Booking_System.dto.request.RegisterRequest;
import com.project.Offering_Booking_System.dto.response.AuthResponse;
import com.project.Offering_Booking_System.entity.UserEntity;
import com.project.Offering_Booking_System.entity.UserPrincipal;
import com.project.Offering_Booking_System.repository.UserRepository;
import com.project.Offering_Booking_System.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl  implements AuthService  {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse registerUser(RegisterRequest request) {

        if(userRepository.existsByEmail(
                request.getEmail())) {

            throw new RuntimeException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setTimezone(request.getTimezone());
        userRepository.save(user);

        UserPrincipal principal = (UserPrincipal)  userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenProvider.generateToken(principal);

        return new AuthResponse(token , user.getName() , user.getRole());

    }

    @Override
    public AuthResponse loginUser(AuthRequest request) {



        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));
            UserPrincipal principal =
                    (UserPrincipal) auth.getPrincipal();



        String token =
                jwtTokenProvider.generateToken(principal);

        return AuthResponse.builder()
                .token(token)
                .userName(principal.getUsername())
                .role(principal.getRole())
                .build();

        } catch (Exception ex) {
            log.error("Authentication failed", ex);
            throw ex;
        }
    }





}
