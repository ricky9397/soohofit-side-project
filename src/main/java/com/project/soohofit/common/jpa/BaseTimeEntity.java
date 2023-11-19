package com.project.soohofit.common.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @Column(name = "REG_ID", length = 30)
    private String regId;

    @CreatedDate
    @Column(name = "REG_DT")
    public LocalDateTime regDt;

    @Column(name = "UPD_ID", length = 30)
    private String updId;

    @CreatedDate
    @Column(name = "UPD_DT")
    public LocalDateTime updDt;

}
