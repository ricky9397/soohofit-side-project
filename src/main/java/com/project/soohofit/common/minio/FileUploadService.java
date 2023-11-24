package com.project.soohofit.common.minio;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public interface FileUploadService {

    UUID fileSave(MultipartFile file);

    InputStream downloadFile(String objectName);
}
