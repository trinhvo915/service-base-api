package com.service.api.repository;

import com.service.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameAndActivatedTrueAndIsDeleteTrue(String username);

    Optional<User> findByEmailAndActivatedTrueAndIsDeleteTrue(String email);

    Optional<User> findByMobileAndActivatedTrueAndIsDeleteTrue(String mobile);
}
