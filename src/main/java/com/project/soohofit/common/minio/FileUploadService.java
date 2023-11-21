package com.project.soohofit.common.minio;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface FileUploadService {

    UUID save(MultipartFile file);
}
