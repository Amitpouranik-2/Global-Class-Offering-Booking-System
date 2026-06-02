package com.project.Offering_Booking_System.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Builder

public class CreateSessionsRequest {

    private List<SessionRequest> sessions;


}
