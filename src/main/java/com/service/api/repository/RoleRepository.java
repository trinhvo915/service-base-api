package com.service.api.repository;

import com.service.api.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Repository
public interface RoleRepository  extends JpaRepository<Role, UUID>{
    Set<Role> findByNameIn(Collection<String> names);

    Set<Role> findByIdIn(Collection<UUID> roleIds);
}
