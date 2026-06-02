package com.project.Offering_Booking_System.service.impl;

import com.project.Offering_Booking_System.config.SecurityUtil;
import com.project.Offering_Booking_System.dto.request.CourseRequest;
import com.project.Offering_Booking_System.dto.response.CourseResponse;
import com.project.Offering_Booking_System.entity.CourseEntity;
import com.project.Offering_Booking_System.entity.UserEntity;
import com.project.Offering_Booking_System.exceptions.CustomException;
import com.project.Offering_Booking_System.repository.CourseRepository;
import com.project.Offering_Booking_System.repository.UserRepository;
import com.project.Offering_Booking_System.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl  implements CourseService {


    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {

        Long userId = SecurityUtil.getCurrentUserId();
        log.info(" user id : {}" , userId);
        CourseEntity entity = new CourseEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());

        if( userId != null)
        {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(()-> new CustomException("User not Found" , 404));

            entity.setUser(user);

        }
        CourseEntity saved  = courseRepository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public CourseResponse updateCourse(CourseRequest request, Long id) {

        Long userId = SecurityUtil.getCurrentUserId();
        CourseEntity entity = courseRepository.findById(id).orElseThrow(() -> new CustomException("Course not found",404));
        if (!entity.getUser().getId().equals(userId)) {
            throw new CustomException("You are not authorized to update this course", 403);
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        CourseEntity updated = courseRepository.save(entity);
        return mapToResponse(updated);
    }

    @Override
    public Page<CourseResponse> fetchCourseByUserId(Long userId, Pageable pageable) {
        return courseRepository
                .findByUserId(userId, pageable)
                .map(this::mapToResponse);
    }


    @Override
    public CourseResponse getById(Long id) {
        Long userId = SecurityUtil.getCurrentUserId();
        CourseEntity entity = courseRepository.findById(id).orElseThrow(() -> new CustomException("Course not found", 404));

        if (!entity.getUser().getId().equals(userId)) {
            throw new CustomException("Access denied", 403);
        }
        return mapToResponse(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Long userId = SecurityUtil.getCurrentUserId();

        CourseEntity entity = courseRepository.findById(id).orElseThrow(() -> new CustomException("Course not found", 404));
        if (!entity.getUser().getId().equals(userId)) {throw new CustomException("Access denied", 403);}
        courseRepository.delete(entity);
    }


    private CourseResponse mapToResponse( CourseEntity entity) {
        CourseResponse response = new CourseResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        return response;
    }




}
