package com.example.demo1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Admin;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer>{
	
	@Query(value="select * from admin_team where email=?1", nativeQuery=true)
	public Admin findAdminByEmail(String email);

}
