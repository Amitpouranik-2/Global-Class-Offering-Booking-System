package com.project.Offering_Booking_System.service;

import com.project.Offering_Booking_System.dto.request.CourseRequest;
import com.project.Offering_Booking_System.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CourseService {

    public CourseResponse createCourse(CourseRequest request);

    public CourseResponse updateCourse( CourseRequest request , Long id );

    public Page<CourseResponse> fetchCourseByUserId(Long userId , Pageable pageable);

    public CourseResponse getById( Long id );

    public void delete( Long id );


}
