package com.example.demo1.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="doctor_registration_docs",indexes={
    @Index(name="idx_doctor_id", columnList="doctor_id"),
    @Index(name="idx_status", columnList="status")
})
public class DoctorRegistrationDocuments {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private long doctor_id;
    @Column(name="s3_key",nullable=false)
    private String s3Key;
    @Column(name="file_name",nullable=false)
    private String fileName;
    private long verifiedBy;
    private LocalDate verifiedAt;
    private String rejectionReason;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private UploadStatus status=UploadStatus.PENDING;
    private LocalDate uploadedAt;
    private LocalDate updatedAt;

    @PrePersist
    protected void onCreate() {
        uploadedAt = LocalDate.now();
        // updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(long doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getS3Key() {
        return s3Key;
    }

    public void setS3Key(String s3Key) {
        this.s3Key = s3Key;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

   

    public long getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(long verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public LocalDate getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDate verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public UploadStatus getStatus() {
        return status;
    }

    public void setStatus(UploadStatus status) {
        this.status = status;
    }

    public LocalDate getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDate uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DoctorRegistrationDocuments(long id, long doctor_id, String s3Key, String fileName, long verifiedBy, LocalDate verifiedAt, String rejectionReason, UploadStatus status,
            LocalDate uploadedAt, LocalDate updatedAt) {
        this.id = id;
        this.doctor_id = doctor_id;
        this.s3Key = s3Key;
        this.fileName = fileName;
        this.verifiedBy = verifiedBy;
        this.verifiedAt = verifiedAt;
        this.rejectionReason = rejectionReason;
        this.status = status;
        this.uploadedAt = uploadedAt;
        this.updatedAt = updatedAt;
    }

    public DoctorRegistrationDocuments() {
    }
    

}
