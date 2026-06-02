package com.project.Offering_Booking_System.repository;

import com.project.Offering_Booking_System.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository  extends JpaRepository<CourseEntity , Long> {


    Page<CourseEntity> findByUserId(Long userId , Pageable pageable);

}
