package com.project.Offering_Booking_System.service;

import com.project.Offering_Booking_System.dto.request.CreateSessionsRequest;
import com.project.Offering_Booking_System.dto.request.SessionRequest;
import com.project.Offering_Booking_System.dto.response.SessionResponse;

import java.util.List;

public interface SessionService {

    List<SessionResponse> createSessions(
            Long offeringId,
            CreateSessionsRequest request);

    List<SessionResponse> getSessionsByOffering(
            Long offeringId);

    SessionResponse updateSession(
            Long sessionId,
            SessionRequest request);

    List<SessionResponse> getUpcomingSessions();

    void deleteSession(Long sessionId);

}
