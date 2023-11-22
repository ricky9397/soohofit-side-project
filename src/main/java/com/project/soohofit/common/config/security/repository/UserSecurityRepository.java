package com.project.soohofit.common.config.security.repository;

import com.project.soohofit.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSecurityRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);



}
