package com.project.soohofit.user.domain;

import com.project.soohofit.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "TB_USER")
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "USER_ID", length = 50)
    private String userId;

    @Column(name = "USER_PWD", length = 200)
    private String userPwd;

    @Column(name = "USER_NM", length = 100)
    private String userNm;

    @Column(name = "BIRTH", length = 8)
    private String birth;

    @Column(name = "GENDER", length = 1)
    private String gender;

    @Column(name = "EMAIL", length = 100)
    private String email;

    @ColumnDefault(value = "'Y'")
    @Column(name = "EMIAL_YN", length = 1)
    private String emailYn;

    @Column(name = "MOBILE", length = 30)
    private String mobile;

    @ColumnDefault(value = "'Y'")
    @Column(name = "MOBILE_YN", length = 1)
    private String mobileYn;

    @Column(name = "ZIP_CD", length = 10)
    private String zipCd;

    @Column(name = "ADDR1", length = 150)
    private String addr1;

    @Column(name = "ADDR2", length = 150)
    private String addr2;

    @ColumnDefault(value = "'Y'")
    @Column(name = "USER_STATUS", length = 1)
    private String userStatus;

    @CreatedDate
    @Column(name = "LEAVE_DT")
    private LocalDateTime leaveDt;

    @Column(name = "ROLE", length = 20)
    private String role;


}
