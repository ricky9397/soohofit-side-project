package com.project.soohofit.user.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserLogin {
    private String userId;
    private String userPwd;
}
