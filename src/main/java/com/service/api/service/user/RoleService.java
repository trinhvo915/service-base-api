package com.service.api.service.user;

import com.service.api.domain.Role;
import com.service.api.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRolesInName(List<String> names){
        return roleRepository.findByNameIn(names);
    }
}
