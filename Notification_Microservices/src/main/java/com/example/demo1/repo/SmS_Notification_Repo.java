package com.example.demo1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Notification_Sms;
@Repository
public interface SmS_Notification_Repo extends JpaRepository<Notification_Sms,Integer>{

}
