package com.project.Offering_Booking_System.repository;

import com.project.Offering_Booking_System.entity.UserEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserEntity  , Long> {

     boolean existsByEmail( String email);

     Optional<UserEntity> findByEmail(String email);

     @Lock(LockModeType.PESSIMISTIC_WRITE)
     @Query("""
       select u
       from UserEntity u
       where u.id = :id
       """)
     Optional<UserEntity> findByIdForUpdate(
             @Param("id") Long id);


}
