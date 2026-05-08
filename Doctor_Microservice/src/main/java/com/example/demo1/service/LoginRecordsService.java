package com.example.demo1.service;


import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo1.model.LoginRecordsDoctor;
import com.example.demo1.repo.Doctor_Login_Records;


@Service
public class LoginRecordsService {
	
	@Autowired
	private Doctor_Login_Records repo;
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoginRecordsService.class);
	
	public void saveOrUpdateLoginDetails(int doctor_id) {
		LoginRecordsDoctor d=this.repo.getDoctorId(doctor_id);
		LoginRecordsDoctor d2=new LoginRecordsDoctor();
		log.info("Exist or not");
		Date date=new Date();
		if(d==null ||  d.getLoginDate()!=date) {
			d2.setDoctor_id(doctor_id);
			d2.setLoginTime(LocalDateTime.now());
			d2.setLock_version(false);
			d2.setLogoutTime(null);
			d2.setLoginDate(new Date());
			this.repo.save(d2);
			
		}
		else {
			d.setDoctor_id(d.getDoctor_id());
			d.setLock_version(false);
			d.setLoginTime(d.getLoginTime());
			d.setLogoutTime(LocalDateTime.now());
			d.setLoginDate(d.getLoginDate());
			this.repo.save(d);
		}
//
//
//		
//		if(d!=null) {
//			d.setLogoutTime(LocalDateTime.now());
////			d.setLoginTime(d.getLoginTime());
//			d.setLock_version(false);
//			
//		}
//		else {
//			d.setLoginTime(LocalDateTime.now());
//			d.setLock_version(false);
//			d.setLogoutTime(null);
//		}
	
	}

}
