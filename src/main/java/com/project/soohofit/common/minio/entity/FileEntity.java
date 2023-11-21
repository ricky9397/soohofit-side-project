package com.project.soohofit.common.minio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
