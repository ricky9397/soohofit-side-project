package com.project.soohofit.common.minio;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioStorageService {

    private final MinioClient minioClient;
    long size = 5242880L; // bytes (5mb)

    public void save(MultipartFile file, UUID uuid) throws Exception {

        minioClient.putObject(
                PutObjectArgs
                        .builder()
                        .bucket(MinioConfiguration.BUCKET_NAME)
                        .object(uuid.toString())
                        .stream(file.getInputStream(), file.getSize(), size)
                        .build()
        );
    }

    public InputStream getInputStream(UUID uuid, long offset, long length) throws Exception {
        return minioClient.getObject(
                GetObjectArgs
                        .builder()
                        .bucket(MinioConfiguration.BUCKET_NAME)
                        .offset(offset)
                        .length(length)
                        .object(uuid.toString())
                        .build());
    }

}
