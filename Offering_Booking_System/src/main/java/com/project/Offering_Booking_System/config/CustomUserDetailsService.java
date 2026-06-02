package com.project.Offering_Booking_System.config;

import com.project.Offering_Booking_System.entity.UserEntity;
import com.project.Offering_Booking_System.entity.UserPrincipal;
import com.project.Offering_Booking_System.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

   private final UserRepository userRepository;


    @Override
    @Transactional( readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByEmail(email).orElseThrow( ()->
        {
            return new UsernameNotFoundException("User Not Found ");
        });

        return new UserPrincipal(user);

    }
}
