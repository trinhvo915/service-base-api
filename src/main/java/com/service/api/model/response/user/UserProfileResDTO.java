package com.service.api.model.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.service.api.domain.Role;
import com.service.api.domain.User;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResDTO {

    private String firstName;

    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<String> authorities;

    public static UserProfileResDTO fromUser(User user) {
        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        user.getRoles().forEach(role -> {
            if(CollectionUtils.isNotEmpty(role.getPermissions())){
                role.getPermissions().forEach(p -> {
                    roles.add(p.getName());
                });
            }
        });

        return UserProfileResDTO
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .authorities(roles)
                .build();
    }
}
