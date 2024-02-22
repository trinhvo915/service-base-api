package com.service.api.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
public class Role extends AbstractAuditingEntity<UUID> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @NotNull(message = "Role name is required")
    @Size(max = 50)
    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id", referencedColumnName = "id") }
    )
    @BatchSize(size = 20)
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return Objects.equals(name, ((Role) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
