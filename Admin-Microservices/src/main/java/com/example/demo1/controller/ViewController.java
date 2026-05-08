package com.example.demo1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.OpenFiegn.FeignApi;
import com.example.demo1.model.Admin;
import com.example.demo1.model.Doctor;
import com.example.demo1.repo.AdminRepo;
import com.example.demo1.service.AdminService;
import com.example.demo1.userdetails.UserDetailsImpl;
import com.example.demo1.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class ViewController {
//	
//	@Autowired
//	private JwtUtil jwtUtil;
	
	@Autowired
	private FeignApi openFeign;
	
	
//	@Autowired
//	private AuthenticationManager manager;
	
//	@Autowired
//	private UserDetailsImpl userDetails;
	
	@Autowired
	private AdminService service;
	
	@GetMapping("/home")
	public String home(@RequestHeader("X-User-Id") String userid,
	        @RequestHeader("X-Role") String role)
	{
		if(role.equalsIgnoreCase("admin"))
		{
			
		
		return "Admin Panel";
		}
		return "No permission";
	}
	
//	@PostMapping("/login")
//	public ResponseEntity<?> loginAdmin(@RequestBody Admin admin) {
//		Authentication auth=manager.authenticate(new UsernamePasswordAuthenticationToken(admin.getEmail(),admin.getPassword()));
//		UserDetails userDe=userDetails.loadUserByUsername(admin.getEmail());
//	   String jwt=jwtUtil.generateToken(userDe.getUsername());	
//		return ResponseEntity.ok(jwt);
//		
//	}
//	@PostMapping("/register")
//	public ResponseEntity<?> createAdmin(@RequestBody Admin admin){
//		Admin ad=this.service.saveAdmin(admin);
//		if(ad!=null) {
//			return ResponseEntity.status(HttpStatus.CREATED).body(ad);
//		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error Occured");
//	}
	
	@GetMapping("doctors/list")
	public ResponseEntity<?> ListOfDoctors(@RequestHeader("X-User-Id") String userid,
	        @RequestHeader("X-Role") String role){

	   if(role.equalsIgnoreCase("admin"))
	   {
		
		   List<Doctor> list=this.openFeign.getAllDoctors(userid,role);
		   System.out.println(list.size());
		if(list.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No doctors registered!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	
	   
	   return ResponseEntity.status(HttpStatus.NO_CONTENT).body("You don't have permission to view the list");
	   
		
	}
	
	

}
