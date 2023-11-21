package com.project.soohofit.common.minio;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {

    public static final String BUCKET_NAME = "spring-file";

    @Value("${minio.url}")
    private String url;

    @Value("${minio.port}")
    private int port;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient client = MinioClient.builder()
                .endpoint(url, port, false)
                .credentials(accessKey, secretKey)
                .build();
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(BUCKET_NAME).build())) {
            client.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(BUCKET_NAME)
                            .build()
            );
        }
        return client;
    }
}
