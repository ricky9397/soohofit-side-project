package com.project.soohofit.common.config.security.repository;

import com.project.soohofit.user.domain.UserOauth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOauth2Repository extends JpaRepository<UserOauth, String> {
}
