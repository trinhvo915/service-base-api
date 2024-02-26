package com.service.api.model.request.user.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    @NotNull(message = "Username is required")
    @Size(min = 1, max = 50)
    private String username;

    @NotNull(message = "Password is required")
    @Size(min = 6, max = 30)
    private String password;
}
