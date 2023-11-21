package com.project.soohofit.common.minio;

import com.project.soohofit.common.minio.entity.FileEntity;
import com.project.soohofit.main.controller.MainController;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileUtil implements FileUploadService {

    private Logger logger = LogManager.getLogger(this);

    private final MinioStorageService minioStorageService;

    @Override
    /**
     *  todo : db 저장 로직 추가
     */
    public UUID save(MultipartFile file) {
        try {
            UUID fileId = UUID.randomUUID();
            FileEntity fileEntity = FileEntity.builder()
                    .id(fileId.toString())
                    .size(file.getSize())
                    .contentType(file.getContentType())
                    .build();
                    // db 저장 로직

            minioStorageService.save(file, fileId); // minio save
            return fileId;
        } catch (Exception e) {
            logger.info("Exception occurred when trying to save the file", e);
            throw new RuntimeException();
        }
    }
}
