package com.service.api.web.rest;

import com.service.api.framework.constants.MessageConstants;
import com.service.api.framework.handler.model.ResponseData;
import com.service.api.framework.support.ResponseSupport;
import com.service.api.model.request.user.UserReqDTO;
import com.service.api.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {
    private final ResponseSupport responseSupport;
    private final UserService userService;


    public UserController(ResponseSupport responseSupport, UserService userService) {
        this.responseSupport = responseSupport;
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseData> createNewUser(
            @Valid @RequestBody UserReqDTO userRes
    ) {

        userService.createNewUser(userRes);
        return responseSupport.success(ResponseData.builder()
                .isSuccess(true)
                .httpStatus(HttpStatus.CREATED.value())
                .message(MessageConstants.REGISTER_USER_SUCCESS)
                .build());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseData> updateUser(
            @PathVariable("id") UUID userId,
            @Valid @RequestBody UserReqDTO userRes
    ) {

        userService.updateUser(userId, userRes);

        return responseSupport.success(ResponseData.builder()
                .isSuccess(true)
                .httpStatus(HttpStatus.OK.value())
                .message(MessageConstants.UPDATE_USER_SUCCESS)
                .build());
    }
}
