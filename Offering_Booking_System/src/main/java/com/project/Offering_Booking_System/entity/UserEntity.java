package com.project.Offering_Booking_System.entity;


import com.project.Offering_Booking_System.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( schema = "booking_system" , name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {


    @Id
    @SequenceGenerator( name = "booking_system.user_id_sequence" , schema = "booking_system" , sequenceName = "booking_system.user_id_sequence" , allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "booking_system.user_id_sequence")
    private Long id;

    private String name;


    @Column(unique = true)
    private String email;


    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    private String timezone;

}

