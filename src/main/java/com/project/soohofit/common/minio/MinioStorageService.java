package com.project.soohofit.common.minio;

import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class MinioStorageService {

    private final MinioClient minioClient;
    long size = 5242880L; // bytes (5mb)

    public void fileSave(MultipartFile file, UUID uuid) throws Exception {

        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(MinioConfiguration.BUCKET_NAME)
                        .object(uuid.toString())
                        .stream(file.getInputStream(), file.getSize(), size)
                        .build()
        );

    }

    public boolean isObjectExist(String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder()
                    .bucket(MinioConfiguration.BUCKET_NAME)
                    .object(objectName)
                    .build()
            );
            return true;
        } catch (ErrorResponseException e) {
            log.info(e.getMessage());
            return false;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public GetObjectResponse downloadFile(String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(MinioConfiguration.BUCKET_NAME)
                        .object(objectName)
                        .build()
        );
    }

}
