package com.project.Offering_Booking_System.controller;

import com.project.Offering_Booking_System.dto.response.BookingResponse;
import com.project.Offering_Booking_System.dto.response.OfferingResponse;
import com.project.Offering_Booking_System.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/parent/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @PostMapping("/{offeringId}")
    public ResponseEntity<BookingResponse> createBooking( @PathVariable Long offeringId )
    {
        return ResponseEntity.ok( bookingService.bookOffering(offeringId));
    }


    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getMyBookings(Pageable pageable) {
        return ResponseEntity.ok(bookingService.getMyBookings(pageable));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }


    @GetMapping("/upcoming-offering")
    public ResponseEntity<Page<OfferingResponse>> getUpcomingOffering( Pageable pageable)
    {
        return ResponseEntity.ok(bookingService.upcomingOfferings( pageable));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}