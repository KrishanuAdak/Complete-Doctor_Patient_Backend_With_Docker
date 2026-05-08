package com.example.demo1.userdetails;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo1.model.Admin;
import com.example.demo1.repo.AdminRepo;
//@Service
public class UserDetailsImpl {
//implements UserDetailsService{
//	@Autowired
//	private AdminRepo repo;
//  
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		
//		Admin admin=null;
//		admin=this.repo.findAdminByEmail(username);
//		if(admin==null) {
//			throw new RuntimeException("Admin Email Not Found");
//		}
//		
//		return User.builder().username(admin.getEmail()).password(admin.getPassword()).roles(admin.getRole()).build();
//	}

}
