package com.project.Offering_Booking_System.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionRequest {

    private Long offeringId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;


}
