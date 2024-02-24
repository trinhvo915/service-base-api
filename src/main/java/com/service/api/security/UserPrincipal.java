package com.service.api.security;

import com.service.api.domain.User;
import com.service.api.framework.exception.UserNotActivatedException;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPrincipal implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String username;

    private String password;

    private String email;

    private String mobile;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user) {

        if (!user.isActivated()) {
            throw new UserNotActivatedException("User was not activated");
        }

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(AuthoritiesConstants.ROLE_PREFIX +role.getName()))
                .collect(Collectors.toSet());

        user.getRoles().forEach(role -> {
            if(CollectionUtils.isNotEmpty(role.getPermissions())){
                role.getPermissions().forEach(p -> {
                    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ROLE_PREFIX +p.getName()));
                });
            }
        });

        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
