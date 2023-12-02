package com.project.soohofit.common.config.security.service;

import com.project.soohofit.common.config.security.domain.UserDetail;
import com.project.soohofit.common.config.security.repository.UserSecurityRepository;
import com.project.soohofit.user.domain.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserSecurityRepository userSecurityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userSecurityRepository.findByUserId(username).orElseThrow(() -> new IllegalArgumentException(username + " 사용자가 존재하지 않습니다"));
        return new UserDetail(userEntity);
    }

}
