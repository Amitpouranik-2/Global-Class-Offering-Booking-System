package com.project.Offering_Booking_System.service.impl;

import com.project.Offering_Booking_System.config.SecurityUtil;
import com.project.Offering_Booking_System.dto.request.OfferingRequest;
import com.project.Offering_Booking_System.dto.response.OfferingResponse;
import com.project.Offering_Booking_System.dto.response.SessionResponse;
import com.project.Offering_Booking_System.entity.CourseEntity;
import com.project.Offering_Booking_System.entity.OfferingEntity;
import com.project.Offering_Booking_System.exceptions.CustomException;
import com.project.Offering_Booking_System.mapper.Mapper;
import com.project.Offering_Booking_System.repository.CourseRepository;
import com.project.Offering_Booking_System.repository.OfferingRepository;
import com.project.Offering_Booking_System.service.OfferingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OfferingServiceImpl  implements OfferingService  {

    private final CourseRepository courseRepository;
    private final OfferingRepository offeringRepository;
    private final Mapper mapper;

    @Override
    @Transactional
    public OfferingResponse createOffering(
            OfferingRequest request) {

        Long userId = SecurityUtil.getCurrentUserId();

        CourseEntity course = courseRepository.findById(request.getCourseId()).orElseThrow(() -> new CustomException("Course not found", 404));

        if (!course.getUser().getId().equals(userId)) {
            throw new CustomException(
                    "You are not authorized to create offering for this course",
                    403);
        }

        OfferingEntity entity = new OfferingEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setTimezone(request.getTimezone());
        entity.setMaxCapacity(request.getMaxCapacity());
        entity.setCourse(course);
        OfferingEntity saved = offeringRepository.save(entity);

        return mapper.mapToOfferingResponse(saved);
    }

    @Override
    @Transactional
    public OfferingResponse updateOffering(
            Long id,
            OfferingRequest request) {

        Long userId = SecurityUtil.getCurrentUserId();

        OfferingEntity offering = offeringRepository.findById(id).orElseThrow(() -> new CustomException("Offering not found", 404));

        if (!offering.getCourse().getUser().getId().equals(userId)) {
            throw new CustomException("Unauthorized", 403);
        }
        offering.setName(request.getName());
        offering.setDescription(request.getDescription());
        offering.setTimezone(request.getTimezone());
        offering.setMaxCapacity(request.getMaxCapacity());
        OfferingEntity updated = offeringRepository.save(offering);

        return mapper.mapToOfferingResponse(updated);
    }

    @Override
    @Transactional
    public OfferingResponse getOfferingById(Long id) {

        OfferingEntity offering = offeringRepository.findById(id).orElseThrow(() -> new CustomException("Offering not found", 404));
        return mapper.mapToOfferingResponse(offering);
    }

    @Override
    @Transactional
    public Page<OfferingResponse> getTeacherOfferings(
            Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserId();
        return offeringRepository.findByCourseUserId(userId, pageable).map(mapper::mapToOfferingResponse);
    }

    @Override
    @Transactional
    public List<OfferingResponse> getUpcomingOfferings() {

        Long userId = SecurityUtil.getCurrentUserId();
        return offeringRepository.findUpcomingOfferings( userId).stream().map(mapper::mapToOfferingResponse).toList();

    }

    @Override
    @Transactional
    public void deleteOffering(Long id) {

        Long userId = SecurityUtil.getCurrentUserId();
        OfferingEntity offering = offeringRepository.findById(id).orElseThrow(() -> new CustomException("Offering not found", 404));
        if (!offering.getCourse().getUser().getId().equals(userId)) {
            throw new CustomException("Unauthorized", 403);
        }
        offeringRepository.delete(offering);
    }



}
