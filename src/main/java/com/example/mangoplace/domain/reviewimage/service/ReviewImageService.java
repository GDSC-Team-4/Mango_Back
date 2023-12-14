package com.example.mangoplace.domain.reviewimage.service;

import com.example.mangoplace.domain.reviewimage.dto.request.UploadImageRequest;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ReviewImageService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final Storage storage;


    public void uploadImage(UploadImageRequest uploadImageRequest) throws IOException {
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = uploadImageRequest.getImage().getContentType(); // 파일의 형식 ex) JPG

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                uploadImageRequest.getImage().getInputStream()
        );

    }
}
