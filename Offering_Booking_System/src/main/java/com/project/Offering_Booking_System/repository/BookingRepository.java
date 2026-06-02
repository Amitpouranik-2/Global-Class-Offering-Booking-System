package com.project.Offering_Booking_System.repository;

import com.project.Offering_Booking_System.entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingRepository  extends JpaRepository<BookingEntity, Long > {


    Page<BookingEntity> findByUserId(Long id , Pageable pageable);

    Optional<BookingEntity> findByIdAndUserId(Long id , Long userId  );

    @Query("""
       select count(bookedSession) > 0
       from BookingEntity b
       join b.offering bookedOffering
       join bookedOffering.sessions bookedSession
       where b.user.id = :userId
       and bookedSession.startTime < :newEnd
       and bookedSession.endTime > :newStart
       """)
    boolean existsTimeConflict(
            Long userId,
            LocalDateTime newStart,
            LocalDateTime newEnd);



}
