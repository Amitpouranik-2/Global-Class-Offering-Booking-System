package com.project.Offering_Booking_System.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferingRequest {

    private String name;

    private String description;

    private Long courseId;

    private Long maxCapacity;

    private String timezone;




}
