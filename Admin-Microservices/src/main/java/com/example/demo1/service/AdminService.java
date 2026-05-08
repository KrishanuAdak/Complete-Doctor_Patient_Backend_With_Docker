package com.example.demo1.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Admin;
import com.example.demo1.repo.AdminRepo;

@Service
public class AdminService {
	@Autowired
	private AdminRepo repo;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	public Admin saveAdmin(Admin admin) {
		Random rand=new Random();
		int id=rand.nextInt(999)+1;
		Admin a=admin;
		a.setId(id);
		a.setEmail(admin.getEmail());
//		a.setPassword(passwordEncoder.encode(admin.getPassword()));
		a.setRole("ADMIN");
		Admin x=this.repo.save(a);
		return x;		
	}

}
