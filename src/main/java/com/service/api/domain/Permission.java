package com.service.api.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_delete='false'")
@SQLDelete(sql = "UPDATE permission SET is_delete = TRUE WHERE id=?")
@Table(name = "permission")
public class Permission  extends AbstractAuditingEntity<UUID> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "char(36)")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name", unique = true, length = 255, nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "display", length = 255, nullable = false)
    private String display;

    @NotNull
    @Size(max = 255)
    @Column(name = "action", length = 255, nullable = false)
    private String action;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Permission)) {
            return false;
        }
        return Objects.equals(name, ((Permission) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
