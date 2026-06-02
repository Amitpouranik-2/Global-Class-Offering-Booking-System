package com.project.Offering_Booking_System.service;

import com.project.Offering_Booking_System.dto.request.OfferingRequest;
import com.project.Offering_Booking_System.dto.response.OfferingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OfferingService {


    OfferingResponse createOffering(
            OfferingRequest request);

    OfferingResponse updateOffering(
            Long id,
            OfferingRequest request);

    OfferingResponse getOfferingById(
            Long id);

    Page<OfferingResponse> getTeacherOfferings(
            Pageable pageable);

    List<OfferingResponse> getUpcomingOfferings();

    void deleteOffering(Long id);

}
