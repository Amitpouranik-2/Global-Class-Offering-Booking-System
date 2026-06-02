package com.project.Offering_Booking_System.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table( schema = "booking_system" , name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionEntity {

    @Id
    @SequenceGenerator( name = "booking_system.session_id_sequence" , schema = "booking_system" , sequenceName = "booking_system.session_id_sequence" , allocationSize = 1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE , generator = "booking_system.session_id_sequence")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offering_id")
    private OfferingEntity offering;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
