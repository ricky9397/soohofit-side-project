package com.project.soohofit.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USER_OAUTH")
public class UserOauth {

    @Id
    @Column(name = "USER_ID", length = 50)
    private String userId;

    @Column(name = "PROVIDER", length = 100)
    private String provider;

    @Column(name = "PROVIDER_ID", length = 1000)
    private String providerId;


}
