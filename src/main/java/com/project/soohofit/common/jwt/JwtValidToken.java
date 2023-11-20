package com.project.soohofit.common.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtValidToken {

    private boolean success; // 토큰 상태체크.
    private String username;
}
