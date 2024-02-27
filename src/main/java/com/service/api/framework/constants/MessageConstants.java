package com.service.api.framework.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessageConstants {
    public static final String LOGOUT_SUCCESS = "Logout is successfully.";
    public static final String LOGIN_SUCCESS = "Login is successfully.";
    public static final String REGISTER_USER_SUCCESS = "Register user is successfully.";

    public static final String UPDATE_USER_SUCCESS = "Update user is successfully.";
    public static final String USER_NOT_LOGIN = "The user is not logged in.";
    public static final String NOT_FOUND_USER = "Account is not existing.";
}
