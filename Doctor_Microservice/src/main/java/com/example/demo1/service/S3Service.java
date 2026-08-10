package com.example.demo1.service;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo1.model.DoctorRegistrationDocuments;
import com.example.demo1.repo.DoctorRegistrationDocsRepo;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Service
@Slf4j
public class S3Service {
    private final S3Presigner s3Presigner;
    private final String bucket;
    private final DoctorRegistrationDocsRepo doctorRegistrationDocsRepo;

    public S3Service(S3Presigner s3Presigner, @Value ("${aws.s3.bucket}") String bucket, DoctorRegistrationDocsRepo doctorRegistrationDocsRepo) {
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
        this.doctorRegistrationDocsRepo = doctorRegistrationDocsRepo;
    }
    //This is for generating a pre-signed URL for uploading an object to S3. The URL will be valid for 5 minutes.
    public String generatePresignedUrlForUpload(String key,String contentType){
        PutObjectRequest objectRequest=PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        return s3Presigner.presignPutObject(r -> r.signatureDuration(Duration.ofMinutes(5))
                .putObjectRequest(objectRequest)).url().toString();
    }

    //This is for generating a pre-signed URL for downloading an object from S3. The URL will be valid for 5 minutes.
    public String generatePreSignedDownloadUrl(String key){
        GetObjectRequest getObjectRequest=GetObjectRequest.builder()
        .key(key)
        .bucket(bucket)
        .build();
        GetObjectPresignRequest getObjectPresignRequest=GetObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(5))
        .getObjectRequest(getObjectRequest)
        .build();

        return s3Presigner.presignGetObject(getObjectPresignRequest).url().toString();
    }

    public void saveFileToDB(DoctorRegistrationDocuments docs){
        if(docs.getDoctor_id()!=0 && docs.getS3Key()!=null && !docs.getS3Key().isEmpty()){
            // Save the document to the database
            // You can use a repository to save the document
            DoctorRegistrationDocuments doc=new DoctorRegistrationDocuments();
            doc.setDoctor_id(docs.getDoctor_id());
            doc.setS3Key(docs.getS3Key());
            doc.setFileName(docs.getFileName());
            log.info("Saving document to database: {}", doc);
            doctorRegistrationDocsRepo.save(doc);

        }

    }

}

