package com.project.soohofit.common.minio;

import com.project.soohofit.common.minio.entity.FileEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileUtil implements FileUploadService {

    private final MinioStorageService minioStorageService;

    @Override
    public UUID fileSave(MultipartFile file) {
        try {
            UUID fileId = UUID.randomUUID();
            FileEntity fileEntity = FileEntity.builder()
                    .id(fileId.toString())
                    .size(file.getSize())
                    .contentType(file.getContentType())
                    .build();
                    // db 저장 로직

            minioStorageService.fileSave(file, fileId); // minio save
            return fileId;
        } catch (Exception e) {
            log.info("exception occurred when trying to save the file", e);
            throw new RuntimeException();
        }
    }

    @Override
    public InputStream downloadFile(String objectName) {
        try {
            boolean existFile = minioStorageService.isObjectExist(objectName);

            if (!existFile) {
                throw new FileNotFoundException("file does not exist: " + objectName);
            }

            int bytesRead;
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = minioStorageService.downloadFile(objectName);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (FileNotFoundException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("an error occurred while downloading the file", e);
            throw new RuntimeException(e);
        }
    }
}
