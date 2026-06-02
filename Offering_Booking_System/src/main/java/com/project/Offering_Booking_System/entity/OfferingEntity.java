package com.project.Offering_Booking_System.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table( schema = "booking_system" , name = "offering")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfferingEntity {


    @Id
    @SequenceGenerator( name = "booking_system.offering_id_sequence" , schema = "booking_system" , sequenceName = "booking_system.offering_id_sequence" , allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "booking_system.offering_id_sequence")
    private Long id;

    private String name;

    private String description;

    private Long maxCapacity;

    private String timezone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @OneToMany(
            mappedBy = "offering",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SessionEntity> sessions =
            new ArrayList<>();

}
