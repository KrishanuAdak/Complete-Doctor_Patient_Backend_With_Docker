package com.example.demo1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="payments_status")
public class Payments_Status {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String status_name;
    private boolean lock_version;
    private boolean is_Accessed;
    public Payments_Status() {
    }
    public Payments_Status(int id, String status_name, boolean is_Accessed, boolean lock_version) {
        this.id = id;
        this.status_name = status_name;
        this.is_Accessed = is_Accessed;
        this.lock_version = lock_version;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStatus_name() {
        return status_name;
    }
    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
    public boolean isLock_version() {
        return lock_version;
    }
    public void setLock_version(boolean lock_version) {
        this.lock_version = lock_version;
    }
    public boolean isIs_Accessed() {
        return is_Accessed;
    }
    public void setIs_Accessed(boolean is_Accessed) {
        this.is_Accessed = is_Accessed;
    }
    
}
