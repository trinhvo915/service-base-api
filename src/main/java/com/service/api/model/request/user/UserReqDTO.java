package com.service.api.model.request.user;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReqDTO {

    @NotNull(message = "Username is required")
    @Size(min = 1, max = 50)
    private String username;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(min = 8, max = 50)
    private String mobile;

    @NotNull(message = "Password is required")
    @Size(min = 6, max = 30)
    private String password;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    private List<UUID> roleIds;
}
