package com.project.Offering_Booking_System.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SessionResponse {

    private Long id;

    private Long offeringId;
    private LocalDateTime startTime;

    private LocalDateTime endTime;


}
