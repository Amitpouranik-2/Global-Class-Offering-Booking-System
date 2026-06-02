package com.project.Offering_Booking_System.dto.response;

import com.project.Offering_Booking_System.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class AuthResponse {

    private String token;
    private String userName;
    private Role role;

}
