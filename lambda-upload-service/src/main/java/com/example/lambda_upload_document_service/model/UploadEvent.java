package com.example.lambda_upload_document_service.model;
import java.time.LocalDateTime;


public class UploadEvent {

    private String bucket;
    private String key;
    private long fileSize;
    private long user_id;
    private LocalDateTime uploadTime;
    public UploadEvent(String bucket, String key, long user_id, long fileSize, LocalDateTime uploadTime) {
        this.bucket = bucket;
        this.key = key;
        this.user_id = user_id;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
    }
    public UploadEvent() {
    }
    public String getBucket() {
        return bucket;
    }
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public long getFileSize() {
        return fileSize;
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    public long getUser_id() {
        return user_id;
    }
    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }
    
    



}




