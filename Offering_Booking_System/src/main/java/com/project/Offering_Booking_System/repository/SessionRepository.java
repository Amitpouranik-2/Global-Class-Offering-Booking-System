package com.project.Offering_Booking_System.repository;

import com.project.Offering_Booking_System.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository  extends JpaRepository<SessionEntity , Long> {

    List<SessionEntity> findByOfferingId( Long offeringId);

    List<SessionEntity> findByOfferingCourseUserIdAndStartTimeAfter(Long userId, LocalDateTime now);

    boolean existsByOfferingIdAndStartTimeAndEndTime(
            Long offeringId,
            LocalDateTime startTime,
            LocalDateTime endTime);
}
