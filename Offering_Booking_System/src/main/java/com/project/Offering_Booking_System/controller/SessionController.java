package com.project.Offering_Booking_System.controller;


import com.project.Offering_Booking_System.dto.request.CreateSessionsRequest;
import com.project.Offering_Booking_System.dto.request.SessionRequest;
import com.project.Offering_Booking_System.dto.response.SessionResponse;
import com.project.Offering_Booking_System.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/offerings/{offeringId}/sessions")
    public ResponseEntity<List<SessionResponse>> createSessions(@PathVariable Long offeringId, @RequestBody CreateSessionsRequest request) {
        return ResponseEntity.ok(sessionService.createSessions(offeringId, request));
    }

    @GetMapping("/offerings/{offeringId}/sessions")
    public ResponseEntity<
            List<SessionResponse>> getSessions(@PathVariable Long offeringId) {

        return ResponseEntity.ok(sessionService.getSessionsByOffering(offeringId));
    }

    @PutMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionResponse> updateSession(@PathVariable Long sessionId, @RequestBody SessionRequest request) {
        return ResponseEntity.ok(sessionService.updateSession(sessionId, request));
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sessions/upcoming")
    public ResponseEntity<List<SessionResponse>> getUpcomingSessions()
    {
        return ResponseEntity.ok( sessionService.getUpcomingSessions());
    }

}
