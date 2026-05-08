package com.example.demo1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="payments_status_mapping")
public class Payment_Status_Mapping {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int payment_id;
    private int status_id;
    private boolean lock_version;
    private boolean created_At;
    private boolean created_By;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPayment_id() {
        return payment_id;
    }
    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }
    public int getStatus_id() {
        return status_id;
    }
    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }
    public boolean isLock_version() {
        return lock_version;
    }
    public void setLock_version(boolean lock_version) {
        this.lock_version = lock_version;
    }
    public boolean isCreated_At() {
        return created_At;
    }
    public void setCreated_At(boolean created_At) {
        this.created_At = created_At;
    }
    public boolean isCreated_By() {
        return created_By;
    }
    public void setCreated_By(boolean created_By) {
        this.created_By = created_By;
    }
    public Payment_Status_Mapping(int id, int payment_id, int status_id, boolean lock_version, boolean created_At,
            boolean created_By) {
        this.id = id;
        this.payment_id = payment_id;
        this.status_id = status_id;
        this.lock_version = lock_version;
        this.created_At = created_At;
        this.created_By = created_By;
    }
    
    
}
