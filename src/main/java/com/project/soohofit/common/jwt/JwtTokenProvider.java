package com.project.soohofit.common.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.util.Date;

public class JwtTokenProvider {

    @Value("${jwt.secret_key}")
    private static String secretKey;

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(secretKey);
    private static final long AUTH_TIME = 60 * 10;
    private static final long REFRESH_TIME = 60*60*24*7; // 7일


    /**
     * AuthToken 토큰발행
     * @param userName
     * @return
     */
    public static String makeAuthToken(String userName) {
        // Hashish 암호방식
        return JWT.create()
                .withSubject(userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + AUTH_TIME))
                .withClaim("exp", Instant.now().getEpochSecond() + AUTH_TIME)
                .sign(ALGORITHM);
    }

    /**
     * RefreshToken 토큰발행
     *
     * @param userName
     * @return
     */
    public static String makeRefreshToken(String userName){
        return JWT.create()
                .withSubject(userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TIME))
                .withClaim("exp", Instant.now().getEpochSecond() + REFRESH_TIME)
                .sign(ALGORITHM);
    }

    public static JwtValidToken jwtValidToken(String token){
        try {
            DecodedJWT verify = JWT.require(ALGORITHM).build().verify(token);
            return JwtValidToken.builder().success(true)
                    .username(verify.getSubject()).build();
        }catch(Exception ex){
            DecodedJWT decode = JWT.decode(token);
            return JwtValidToken.builder().success(false)
                    .username(decode.getSubject()).build();
        }
    }

}
