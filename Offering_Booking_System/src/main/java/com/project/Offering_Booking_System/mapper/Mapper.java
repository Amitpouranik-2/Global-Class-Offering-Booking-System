package com.project.Offering_Booking_System.mapper;

import com.project.Offering_Booking_System.dto.response.OfferingResponse;
import com.project.Offering_Booking_System.dto.response.SessionResponse;
import com.project.Offering_Booking_System.entity.OfferingEntity;
import com.project.Offering_Booking_System.entity.SessionEntity;
import com.project.Offering_Booking_System.util.TimeZoneUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Mapper {


    public SessionResponse mapToSessionResponse(SessionEntity entity , String timeZone)
    {
        SessionResponse response = new SessionResponse();
        response.setId(entity.getId());
        response.setOfferingId(entity.getOffering().getId());
        response.setStartTime(TimeZoneUtil.fromUtc(entity.getStartTime(), timeZone));
        response.setEndTime(TimeZoneUtil.fromUtc(entity.getEndTime(), timeZone));
        return response;
    }



    public OfferingResponse mapToOfferingResponse(
            OfferingEntity entity) {
        OfferingResponse response = new OfferingResponse();
        log.info(" entity : {}" , entity);
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setDescription(entity.getDescription());
        response.setTimezone(entity.getTimezone());
        response.setMaxCapacity(entity.getMaxCapacity());
        response.setCourseId(entity.getCourse().getId());
        response.setCourseName(entity.getCourse().getName());
        List<SessionResponse> responseList =  entity.getSessions().stream().map(
                entities -> mapToSessionResponse( entities , entity.getCourse().getUser().getTimezone())
        ).toList();
        response.setSessions(responseList);
        return response;
    }

}
