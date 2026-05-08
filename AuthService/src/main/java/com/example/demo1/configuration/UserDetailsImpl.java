package com.example.demo1.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo1.model.AuthDB;
import com.example.demo1.repo.AuthRepo;
@Service
public class UserDetailsImpl implements UserDetailsService{
	@Autowired
	private AuthRepo repo;
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
		AuthDB auth=this.repo.findByEmail(username).orElseThrow(()-> new RuntimeException("Not found any user"));
		System.out.println(auth.getRole()+"role from user details impl"+auth.getEmail());
		return User.builder().username(auth.getEmail()) .password(auth.getPassword()).roles(auth.getRole()).build();
		
	}
}
	


