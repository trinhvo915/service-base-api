package com.service.api.service.user;

import com.service.api.domain.User;
import com.service.api.framework.exception.NotFoundException;
import com.service.api.model.response.user.UserProfileResDTO;
import com.service.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public UserProfileResDTO getUserProfile(UUID userId){
        User user = userRepository.findByIdAndActivatedTrueAndIsDeleteFalse(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id " + userId));

        return UserProfileResDTO.fromUser(user);
    }
}
