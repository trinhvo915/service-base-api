package com.service.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.api.framework.constants.Constants;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends AbstractAuditingEntity<UUID> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "id", length = 36)
    private UUID id;

    @NotNull(message = "Username is required")
    @Size(min = 1, max = 50)
    @Column(name = "username", length = 50, unique = true, nullable = false)
    private String username;

    @Email
    @Size(min = 5, max = 254)
    @Column(name = "email", length = 254)
    private String email;

    @Size(min = 8, max = 50)
    @Column(name = "mobile", length = 50)
    private String mobile;

    @JsonIgnore
    @NotNull(message = "Password is required")
    @Size(min = 6, max = 30)
    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;

    @Builder.Default
    @NotNull
    @Column(name = "activated", nullable = false)
    private boolean activated = false;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Builder.Default
    @Column(name = "reset_date")
    private Instant resetDate = null;

    @Column(name = "url_avatar", columnDefinition = "LONGTEXT")
    private String urlAvatar;

    @Builder.Default
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") }
    )
    @BatchSize(size = 20)
    private Set<Role> roles = new HashSet<>();
}
