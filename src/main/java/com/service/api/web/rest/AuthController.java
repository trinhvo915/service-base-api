package com.service.api.web.rest;

import com.service.api.framework.constants.Constants;
import com.service.api.framework.constants.MessageConstants;
import com.service.api.framework.handler.model.ResponseData;
import com.service.api.framework.support.ResponseSupport;
import com.service.api.model.request.user.LoginResDTO;
import com.service.api.model.response.user.UserLoginResDTO;
import com.service.api.model.response.user.UserProfileResDTO;
import com.service.api.security.JwtTokenProvider;
import com.service.api.security.UserPrincipal;
import com.service.api.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseSupport responseSupport;
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtTokenProvider jwtTokenProvider,
                          UserService userService, ResponseSupport responseSupport){
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.responseSupport = responseSupport;
    }
    @PostMapping("/sign-in")
    public ResponseEntity<ResponseData> signIn(
            @Valid @RequestBody LoginResDTO loginRes
    ) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRes.getUsername(),
                loginRes.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserProfileResDTO userInfo = userService.getUserProfile(userPrincipal.getId());

        String jwt = jwtTokenProvider.createToken(authentication, loginRes.isRememberMe());
        UserLoginResDTO result = UserLoginResDTO.builder()
                .userInfo(userInfo)
                .accessToken(jwt)
                .tokenType(Constants.BEARER_TOKEN)
                .build();

        return responseSupport.success(ResponseData.builder()
                .isSuccess(true)
                .httpStatus(HttpStatus.OK.value())
                .message(MessageConstants.LOGOUT_SUCCESS)
                .data(result).build());
    }
}
