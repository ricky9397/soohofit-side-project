package com.project.soohofit.common.minio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class FileEntity {

    @Id
    @Column
    private String id;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    @Column(name = "FILE_SIZE")
    private long size;

    @Column(name = "FILE_EXT")
    private String fileExt;

}
