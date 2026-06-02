package com.project.Offering_Booking_System.controller;


import com.project.Offering_Booking_System.dto.request.OfferingRequest;
import com.project.Offering_Booking_System.dto.response.OfferingResponse;
import com.project.Offering_Booking_System.service.OfferingService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher/offerings")
@RequiredArgsConstructor
public class OfferingController {

    private final OfferingService offeringService;

    @PostMapping
    public ResponseEntity<OfferingResponse> createOffering(@RequestBody OfferingRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(offeringService.createOffering(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OfferingResponse> updateOffering(@PathVariable Long id, @RequestBody OfferingRequest request) {
        return ResponseEntity.ok(offeringService.updateOffering(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferingResponse> getOfferingById(@PathVariable Long id) {
        return ResponseEntity.ok(offeringService.getOfferingById(id));
    }

    @GetMapping
    public ResponseEntity<Page<OfferingResponse>> getTeacherOfferings(Pageable pageable) {
        return ResponseEntity.ok(offeringService.getTeacherOfferings(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffering(@PathVariable Long id) {
        offeringService.deleteOffering(id);
        return ResponseEntity.noContent()
                .build();
    }


    @GetMapping("/offering/upcoming")
    public ResponseEntity<List<OfferingResponse>> getUpcomingOffering()
    {
       return ResponseEntity.ok(offeringService.getUpcomingOfferings());
    }

}