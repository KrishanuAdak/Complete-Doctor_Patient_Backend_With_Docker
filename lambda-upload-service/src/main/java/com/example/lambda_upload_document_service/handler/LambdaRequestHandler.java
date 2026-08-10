package com.example.lambda_upload_document_service.handler;



import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.example.lambda_upload_document_service.model.UploadEvent;
import com.example.lambda_upload_document_service.repo.DocumentUploadRepository;

public class LambdaRequestHandler implements RequestHandler<S3Event, String> {
    private static final DocumentUploadRepository repository = new DocumentUploadRepository();

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("Received S3 event: " + s3Event);
        List<String> failedList = new ArrayList<>();
        for (S3EventNotification.S3EventNotificationRecord record : s3Event.getRecords()) {
            UploadEvent uploadEvent = parseEvent(record);
            logger.log("Processing file: " + uploadEvent.getKey() + " from bucket: " + uploadEvent.getBucket());
            try {
                repository.saveOrUpdateUpload(uploadEvent);
                logger.log("Successfully processed file: " + uploadEvent.getKey());
            } catch (Exception e) {
                logger.log("Failed to process file: " + uploadEvent.getKey() + " due to: " + e.getMessage());
                failedList.add(uploadEvent.getKey());
            }
        }
        if (!failedList.isEmpty()) {
            throw new RuntimeException("Failed to process the s3 key " + s3Event.getRecords().get(0).getS3().getObject().getKey() + " due to: " + String.join(", ", failedList));
        }
        return "Successfully processed all files.";
    }

    private UploadEvent parseEvent(S3EventNotification.S3EventNotificationRecord record) {
        String bucketName = record.getS3().getBucket().getName();
        String rawKey = record.getS3().getObject().getKey();
        String key = URLDecoder.decode(rawKey, StandardCharsets.UTF_8);
        long fileSize = record.getS3().getObject().getSizeAsLong();
        long doctorId = extractDoctorId(key);
        LocalDateTime uploadTime = LocalDateTime.now();
        return new UploadEvent(bucketName, key, doctorId, fileSize, uploadTime);
    }

    private long extractDoctorId(String objectKey) {
        try {
            String[] parts = objectKey.split("/");
            return Long.parseLong(parts[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return -1;
        }
    }

}



