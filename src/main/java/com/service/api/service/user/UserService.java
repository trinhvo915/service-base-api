package com.service.api.service.user;

import com.service.api.domain.Role;
import com.service.api.domain.User;
import com.service.api.framework.constants.RoleConstant;
import com.service.api.framework.exception.NotFoundException;
import com.service.api.model.request.user.signup.SignUpEmailDTO;
import com.service.api.model.request.user.signup.SignUpMobileDTO;
import com.service.api.model.response.user.UserProfileResDTO;
import com.service.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserProfileResDTO getUserProfile(UUID userId){
        User user = userRepository.findByIdAndActivatedTrueAndIsDeleteFalse(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id " + userId));

        return UserProfileResDTO.fromUser(user);
    }

    public User signUpEmail(SignUpEmailDTO signUp){
        Set<Role> roles = roleService.getRolesInName(List.of(RoleConstant.USER));
        User user = User.builder()
                .email(signUp.getEmail())
                .username(signUp.getUsername())
                .password(passwordEncoder.encode(signUp.getPassword()))
                .activated(false)
                .roles(roles)
                .build();

        return userRepository.save(user);
    }

    public User signUpMobile(SignUpMobileDTO signUpMobile){
        Set<Role> roles = roleService.getRolesInName(List.of(RoleConstant.USER));
        User user = User.builder()
                .mobile(signUpMobile.getMobile())
                .username(signUpMobile.getUsername())
                .password(passwordEncoder.encode(signUpMobile.getPassword()))
                .activated(false)
                .roles(roles)
                .build();

        return userRepository.save(user);
    }
}
