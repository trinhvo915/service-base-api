package com.service.api.model.request.user.signup;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpMobileReqDTO extends SignUpReqDTO {

    @NotNull(message = "Mobile is required")
    @Size(min = 8, max = 50)
    private String mobile;
}
