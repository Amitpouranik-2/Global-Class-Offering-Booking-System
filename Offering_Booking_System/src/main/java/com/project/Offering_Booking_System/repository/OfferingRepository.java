package com.project.Offering_Booking_System.repository;

import com.project.Offering_Booking_System.entity.OfferingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OfferingRepository extends JpaRepository<OfferingEntity , Long> {

    Page<OfferingEntity> findByCourseUserId(Long userId, Pageable pageable);

    @Query("""
       select distinct o
       from OfferingEntity o
       join o.course c
       join o.sessions s
       where c.user.id = :userId
       """)
    List<OfferingEntity> findUpcomingOfferings(
            @Param("userId") Long userId);

    @Query("""
       select distinct o
       from OfferingEntity o
       join o.sessions s
       where s.startTime > :now
       """)
    Page<OfferingEntity> findAvailableOfferings(
            @Param("now") LocalDateTime now,
            Pageable pageable);




}
