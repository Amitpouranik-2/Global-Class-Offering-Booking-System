package com.project.Offering_Booking_System.service.impl;

import com.project.Offering_Booking_System.config.SecurityUtil;
import com.project.Offering_Booking_System.dto.request.CreateSessionsRequest;
import com.project.Offering_Booking_System.dto.request.SessionRequest;
import com.project.Offering_Booking_System.dto.response.SessionResponse;
import com.project.Offering_Booking_System.entity.OfferingEntity;
import com.project.Offering_Booking_System.entity.SessionEntity;
import com.project.Offering_Booking_System.entity.UserEntity;
import com.project.Offering_Booking_System.exceptions.CustomException;
import com.project.Offering_Booking_System.mapper.Mapper;
import com.project.Offering_Booking_System.repository.OfferingRepository;
import com.project.Offering_Booking_System.repository.SessionRepository;
import com.project.Offering_Booking_System.repository.UserRepository;
import com.project.Offering_Booking_System.service.SessionService;
import com.project.Offering_Booking_System.util.TimeZoneUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class SessionServiceImpl  implements SessionService {

        private final SessionRepository sessionRepository;
        private final OfferingRepository offeringRepository;
        private final UserRepository userRepository;
        private final Mapper mapper;

    @Override
    @Transactional
    public List<SessionResponse> createSessions(Long offeringId, CreateSessionsRequest request) {

        Long userId = SecurityUtil.getCurrentUserId();
        OfferingEntity offering = offeringRepository.findById(offeringId).orElseThrow(() ->
                new CustomException("Offering not found", 404));

        if (!offering.getCourse().getUser().getId().equals(userId)) {
            throw new CustomException("Unauthorized", 403);
        }


        UserEntity teacher = offering.getCourse().getUser();

        List<SessionEntity> sessions = new ArrayList<>();

        for (SessionRequest dto : request.getSessions()) {

            if (dto.getStartTime().isAfter(dto.getEndTime())) {
                throw new CustomException("Start time must be before end time", 400);
            }

            LocalDateTime startUtc = TimeZoneUtil.toUtc(dto.getStartTime(), teacher.getTimezone());
            LocalDateTime endUtc = TimeZoneUtil.toUtc(dto.getEndTime(), teacher.getTimezone());

            boolean exists = sessionRepository.existsByOfferingIdAndStartTimeAndEndTime(offeringId, startUtc, endUtc);

            if (exists) {
                throw new CustomException("Session already exists", 400);
            }
            SessionEntity entity = new SessionEntity();
            entity.setOffering(offering);
            entity.setStartTime(startUtc);
            entity.setEndTime(endUtc);
            sessions.add(entity);
        }

        List<SessionEntity> saved = sessionRepository.saveAll(sessions);
        return saved.stream()
                .map( sessionEntity -> mapper.mapToSessionResponse( sessionEntity , teacher.getTimezone()) )
                .toList();
    }
        @Override
        @Transactional
        public List<SessionResponse> getSessionsByOffering(Long offeringId) {
            Long userId = SecurityUtil.getCurrentUserId();
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(()-> new CustomException(" User Id not Found" , 404));
            return sessionRepository.findByOfferingId(offeringId)
                    .stream().map( sessionEntity -> mapper.mapToSessionResponse( sessionEntity , user.getTimezone())).toList();
        }

        @Override
        @Transactional
        public SessionResponse updateSession(Long sessionId, SessionRequest request) {

            Long userId = SecurityUtil.getCurrentUserId();
            SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(() -> new CustomException("Session not found", 404));
            OfferingEntity offering = offeringRepository.findById(request.getOfferingId()).orElseThrow(() ->
                    new CustomException("Offering not found", 404));

            UserEntity teacher = offering.getCourse().getUser();

            if (!session.getOffering().getCourse().getUser().getId().equals(userId)) {
                throw new CustomException("Unauthorized", 403);
            }

            LocalDateTime startUtc = TimeZoneUtil.toUtc(request.getStartTime(), teacher.getTimezone());
            LocalDateTime endUtc = TimeZoneUtil.toUtc(request.getEndTime(), teacher.getTimezone());
            boolean exists = sessionRepository.existsByOfferingIdAndStartTimeAndEndTime(request.getOfferingId(), startUtc, endUtc);
            if (exists) {
                throw new CustomException("Session already exists", 400);
            }
            session.setStartTime(startUtc);
            session.setEndTime(endUtc);
            SessionEntity updated = sessionRepository.save(session);
            return mapper.mapToSessionResponse(updated , teacher.getTimezone());
        }

    @Override
    @Transactional( readOnly = true)
    public List<SessionResponse> getUpcomingSessions() {

        Long userId = SecurityUtil.getCurrentUserId();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomException(" User Id not Found" , 404));
        return sessionRepository.findByOfferingCourseUserIdAndStartTimeAfter( userId , LocalDateTime.now()).stream()
                .map( sessionEntity -> mapper.mapToSessionResponse( sessionEntity , user.getTimezone() )).toList();
    }

    @Override
        @Transactional
        public void deleteSession(Long sessionId) {

            Long userId = SecurityUtil.getCurrentUserId();

            SessionEntity session = sessionRepository.findById(sessionId)
                            .orElseThrow(() -> new CustomException("Session not found", 404));

            if (!session.getOffering().getCourse().getUser().getId().equals(userId)) {
                throw new CustomException("Unauthorized", 403);
            }

            sessionRepository.delete(session);
        }


    }

