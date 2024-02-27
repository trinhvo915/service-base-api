package com.service.api.service.user;

import com.service.api.domain.Role;
import com.service.api.domain.User;
import com.service.api.framework.constants.EmailConstants;
import com.service.api.framework.constants.MessageConstants;
import com.service.api.framework.constants.RoleConstant;
import com.service.api.framework.exception.NotFoundException;
import com.service.api.model.request.user.UserReqDTO;
import com.service.api.model.request.user.signup.SignUpEmailReqDTO;
import com.service.api.model.request.user.signup.SignUpMobileReqDTO;
import com.service.api.model.response.user.UserProfileResDTO;
import com.service.api.repository.RoleRepository;
import com.service.api.repository.UserRepository;
import com.service.api.service.email.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, MailService mailService,
                       RoleRepository roleRepository){
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.roleRepository = roleRepository;
    }

    public UserProfileResDTO getUserProfile(UUID userId){
        User user = userRepository.findByIdAndActivatedTrueAndIsDeleteFalse(userId)
                .orElseThrow(() -> new NotFoundException("Not found user with id " + userId));

        return UserProfileResDTO.fromUser(user);
    }

    public User signUpEmail(SignUpEmailReqDTO signUp){
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

    public void signUpMobile(SignUpMobileReqDTO signUpMobile){
        Set<Role> roles = roleService.getRolesInName(List.of(RoleConstant.USER));
        User user = User.builder()
                .mobile(signUpMobile.getMobile())
                .username(signUpMobile.getUsername())
                .password(passwordEncoder.encode(signUpMobile.getPassword()))
                .activated(false)
                .roles(roles)
                .build();

        userRepository.save(user);

        //TODO: Send email sign up user
        //ThreadUtil.runAsync(() -> sendEmailSignUpUser(userSave));
    }

    private void sendEmailSignUpUser(User user){
        String subject = "Sign Up User";
        Map<String, Object> emailContent = new HashMap<>();

        emailContent.put(EmailConstants.EMAIL, user.getEmail());
        emailContent.put(EmailConstants.FULLNAME, user.getUsername());

        mailService.sendEmail(new String[]{user.getEmail()}
                , new String[0]
                , subject
                , EmailConstants.SIGN_UP_USER_TEMPLATE_MAIL
                , emailContent);
    }


    public void createNewUser(UserReqDTO userRes){

        Set<Role> roles = roleRepository.findByIdIn(userRes.getRoleIds());

        User user = User.builder()
                .firstName(userRes.getFirstName())
                .lastName(userRes.getLastName())
                .username(userRes.getUsername())
                .email(userRes.getEmail())
                .mobile(userRes.getMobile())
                .password(passwordEncoder.encode(userRes.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);

        //TODO: Send email sign up user
        //ThreadUtil.runAsync(() -> sendEmailSignUpUser(userSave));
    }


    public void updateUser(UUID userId, UserReqDTO userRes){
        User user = getUserById(userId);

        user.setFirstName(userRes.getFirstName());
        user.setLastName(userRes.getLastName());
        user.setUsername(userRes.getUsername());
        user.setEmail(userRes.getEmail());
        user.setMobile(userRes.getMobile());

        Set<Role> roles = roleRepository.findByIdIn(userRes.getRoleIds());
        user.setRoles(roles);

        userRepository.save(user);
    }

    public User getUserById(UUID userId){
        return userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException(MessageConstants.NOT_FOUND_USER));
    }
}
