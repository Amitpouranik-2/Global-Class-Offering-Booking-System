package com.project.Offering_Booking_System.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CourseResponse {


    private Long id;
    private String name;
    private String description;


}
