package com.service.api.security;

import com.service.api.domain.User;
import com.service.api.framework.constants.MessageConstants;
import com.service.api.framework.exception.NotFoundException;
import com.service.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String usernameOrEmailOrMobile) throws UsernameNotFoundException {
        log.info("Authenticating {}", usernameOrEmailOrMobile);

        User user = getUserLogin(usernameOrEmailOrMobile.toLowerCase())
                .orElseThrow(() -> new NotFoundException(MessageConstants.NOT_FOUND_USER));
        return UserPrincipal.create(user);
    }

    private Optional<User> getUserLogin(String login){
        Optional<User> usernameUser = userRepository.findByUsernameAndActivatedTrueAndIsDeleteFalse(login);
        if(usernameUser.isPresent()){
            return usernameUser;
        }

        Optional<User> emailUser = userRepository.findByEmailAndActivatedTrueAndIsDeleteFalse(login);
        if(emailUser.isPresent()){
            return emailUser;
        }

        return userRepository.findByMobileAndActivatedTrueAndIsDeleteFalse(login);
    }
}
