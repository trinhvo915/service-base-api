package com.service.api.repository;

import com.service.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsernameAndActivatedTrueAndIsDeleteFalse(String username);

    Optional<User> findByEmailAndActivatedTrueAndIsDeleteFalse(String email);

    Optional<User> findByMobileAndActivatedTrueAndIsDeleteFalse(String mobile);

    Optional<User> findByIdAndActivatedTrueAndIsDeleteFalse(UUID id);
}
