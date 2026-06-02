package com.project.Offering_Booking_System.controller;

import com.project.Offering_Booking_System.config.SecurityUtil;
import com.project.Offering_Booking_System.dto.request.CourseRequest;
import com.project.Offering_Booking_System.dto.response.CourseResponse;
import com.project.Offering_Booking_System.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/teacher/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(
            @RequestBody CourseRequest request) {

        Authentication auth =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        System.out.println(auth);
        System.out.println(auth.getPrincipal());
        System.out.println(auth.getAuthorities());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@PathVariable Long id, @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(request, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CourseResponse>> getCourses(@PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {
        Long userId = SecurityUtil.getCurrentUserId();
        return ResponseEntity.ok(courseService.fetchCourseByUserId(userId,pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent()
                .build();
    }
}
