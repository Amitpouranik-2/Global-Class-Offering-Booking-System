package com.project.Offering_Booking_System.service;

import com.project.Offering_Booking_System.dto.request.BookingRequest;
import com.project.Offering_Booking_System.dto.response.BookingResponse;
import com.project.Offering_Booking_System.dto.response.OfferingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {

    BookingResponse bookOffering(Long offeringId);

    Page<BookingResponse> getMyBookings(Pageable pageable);

    BookingResponse getBookingById(Long bookingId);

    Page<OfferingResponse> upcomingOfferings( Pageable pageable);

    void cancelBooking(Long bookingId);


}
