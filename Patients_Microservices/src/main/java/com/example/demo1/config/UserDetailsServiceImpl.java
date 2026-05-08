package com.example.demo1.config;



import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Patients;
import com.example.demo1.repo.PatientsRepo;

//@Service
public class UserDetailsServiceImpl {
//implements UserDetailsService {
//	@Autowired
//	private PatientsRepo repo;
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Patients p=null;
//		p=this.repo.getByPatientEmail(username).orElseThrow(()-> new RuntimeException("No Patient found "));		
//		return User.builder().username(p.getEmail()).password(p.getPassword()).roles(p.getRole()).build();
//	}

}
