package com.service.api.model.request.user.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpEmailReqDTO extends SignUpReqDTO {

    @Email
    @Size(min = 5, max = 254)
    @NotNull(message = "Email is required")
    private String email;
}
