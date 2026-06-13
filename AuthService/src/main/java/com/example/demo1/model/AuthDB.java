package com.example.demo1.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType ;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
@Entity
public class AuthDB {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Email(message="Please enter valid email")
	@Column(nullable=false,unique=true)
	private String email;
	@Size(min=8)
	private String password;
	private String role;
<<<<<<< HEAD
	private String username;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getId() {
=======
	public long getId() {
>>>>>>> test
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
<<<<<<< HEAD

	public AuthDB(int id, String email, String password, String role, String username) {
=======
	
	public AuthDB(long id, String email, String password, String role) {
>>>>>>> test
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
		this.username = username;
	}
		
	
	public AuthDB() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
