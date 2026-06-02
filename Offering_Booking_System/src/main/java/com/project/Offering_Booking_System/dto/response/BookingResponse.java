package com.project.Offering_Booking_System.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long bookingId;

    private Long offeringId;

    private String offeringName;

    private Long courseId;

    private String courseName;

    private LocalDateTime bookedAt;

    private List<SessionResponse> sessions;


}
