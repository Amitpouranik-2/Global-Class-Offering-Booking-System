package com.project.Offering_Booking_System.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferingResponse {


    private Long id;

    private String name;

    private String description;

    private Long courseId;

    private String courseName;

    private Long maxCapacity;

    private String timezone;

    private List<SessionResponse> sessions;


}
