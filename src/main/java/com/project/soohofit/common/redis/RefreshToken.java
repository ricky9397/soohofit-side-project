package com.project.soohofit.common.redis;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60*60*24*3)
public class RefreshToken {

    @Id
    @Indexed
    private String userId;
    private String refreshToken;

    public RefreshToken(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;

    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
