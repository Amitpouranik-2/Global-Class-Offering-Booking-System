package com.project.Offering_Booking_System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table( schema = "booking_system" , name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseEntity {


    @Id
    @SequenceGenerator( name = "booking_system.course_id_sequence" , schema = "booking_system" , sequenceName = "booking_system.course_id_sequence" , allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "booking_system.course_id_sequence")
    private Long id;

    private String name;

    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;



}
