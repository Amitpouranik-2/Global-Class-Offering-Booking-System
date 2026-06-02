package com.project.Offering_Booking_System.service.impl;

import com.project.Offering_Booking_System.config.SecurityUtil;
import com.project.Offering_Booking_System.dto.response.BookingResponse;
import com.project.Offering_Booking_System.dto.response.OfferingResponse;
import com.project.Offering_Booking_System.dto.response.SessionResponse;
import com.project.Offering_Booking_System.entity.BookingEntity;
import com.project.Offering_Booking_System.entity.OfferingEntity;
import com.project.Offering_Booking_System.entity.SessionEntity;
import com.project.Offering_Booking_System.entity.UserEntity;
import com.project.Offering_Booking_System.exceptions.CustomException;
import com.project.Offering_Booking_System.mapper.Mapper;
import com.project.Offering_Booking_System.repository.BookingRepository;
import com.project.Offering_Booking_System.repository.OfferingRepository;
import com.project.Offering_Booking_System.repository.UserRepository;
import com.project.Offering_Booking_System.service.BookingService;
import com.project.Offering_Booking_System.util.TimeZoneUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl  implements BookingService {

  private final BookingRepository bookingRepository;
  private final OfferingRepository offeringRepository;
  private final UserRepository userRepository;
  private final Mapper mapper;


    @Override
    @Transactional
    public BookingResponse bookOffering(Long offeringId) {

        Long userId = SecurityUtil.getCurrentUserId();
       userRepository.findByIdForUpdate(userId);
        OfferingEntity offering = offeringRepository.findById(offeringId)
                .orElseThrow(()-> new CustomException("Offering Not Found" , 404));

        for (SessionEntity session : offering.getSessions()) {

            boolean conflict =
                    bookingRepository.existsTimeConflict(
                            userId,
                            session.getStartTime(),
                            session.getEndTime());

            if (conflict) {
                throw new CustomException(
                        "Offering conflicts with existing booking",
                        400);
            }
        }

        BookingEntity entity = new BookingEntity();
        entity.setOffering(offering);
        if( userId!= null)
        {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(()-> new CustomException("User Not Found" , 404));
            entity.setUser(user);
        }
        entity.setBookedAt(LocalDateTime.now());
        BookingEntity saved = bookingRepository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    @Transactional( readOnly = true)
    public Page<BookingResponse> getMyBookings(Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserId();
        return bookingRepository.findByUserId( userId, pageable).map(this::mapToResponse);
    }

    @Override
    @Transactional( readOnly = true)
    public BookingResponse getBookingById(Long bookingId) {
        Long userId = SecurityUtil.getCurrentUserId();
        BookingEntity booking = bookingRepository.findByIdAndUserId(bookingId, userId).orElseThrow(() ->
                                new CustomException("Booking not found", 404));
        return mapToResponse(booking);
    }

    @Override
    @Transactional( readOnly = true)
    public Page<OfferingResponse> upcomingOfferings( Pageable pageable) {

        return offeringRepository.findAvailableOfferings( TimeZoneUtil.nowUtc() , pageable ).map(
                mapper::mapToOfferingResponse);
    }

    @Override
    @Transactional
    public void cancelBooking(Long bookingId) {
        Long userId = SecurityUtil.getCurrentUserId();
        BookingEntity booking = bookingRepository.findByIdAndUserId(bookingId, userId)
                        .orElseThrow(() -> new CustomException("Booking not found", 404));
        bookingRepository.delete(booking);
    }



    private BookingResponse mapToResponse(BookingEntity bookingEntity) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(bookingEntity.getId());
        response.setOfferingId(bookingEntity.getOffering().getId());
        response.setOfferingName(bookingEntity.getOffering().getName());
        response.setCourseId(bookingEntity.getOffering().getCourse().getId());
        response.setCourseName(bookingEntity.getOffering().getCourse().getName());
        response.setBookedAt(bookingEntity.getBookedAt());
         List<SessionResponse> responseList =  bookingEntity.getOffering().getSessions().stream().map( entity -> mapper.mapToSessionResponse( entity , bookingEntity.getUser().getTimezone())).toList();
        response.setSessions(responseList);
        return response;
    }




}
