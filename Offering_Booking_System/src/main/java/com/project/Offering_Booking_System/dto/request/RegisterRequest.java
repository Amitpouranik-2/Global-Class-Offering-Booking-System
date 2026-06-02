package com.project.Offering_Booking_System.dto.request;

import com.project.Offering_Booking_System.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequest {

    private String name;

    private String email;

    private String password;

    private Role role;

    private String timezone;


}
