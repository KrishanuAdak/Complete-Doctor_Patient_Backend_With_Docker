package com.example.demo1.controller;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo1.Exception.ResourceNotFoundException;
import com.example.demo1.config.BlacklistToken;
import com.example.demo1.model.Available_Doctor_Now;
import com.example.demo1.model.Doctor;
import com.example.demo1.model.DoctorDetailsToAppointment;
import com.example.demo1.repo.DoctorRepo;
import com.example.demo1.service.Avaible_Now_service;
import com.example.demo1.service.DoctorService;
import com.example.demo1.service.LoginRecordsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;






@RestController
@RequestMapping("/doctor")
// @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")

@CrossOrigin(origins = "http://localhost:4200")
public class BasicController {

	@Autowired
	private DoctorService service;
	
	@Autowired
	private Avaible_Now_service service1;
		
	@Autowired
	private DoctorRepo repo;
	
	@Autowired
	private BlacklistToken token;
	
	@Autowired
	private LoginRecordsService loginRecordsService;
	
	@Autowired
	private DoctorRepo doctorRepo;	

	@GetMapping("/home")
	public ResponseEntity<?> home()
	{
	return ResponseEntity.status(HttpStatus.OK).body("welcome to Doctor portal,God");
		
	}
	@GetMapping("/verified-doctor/counts")
	public int getVerifiedDoctors(){
		return this.service.getAllDoctors();
	}
	
	@PostMapping("/register")
	public Doctor registerDoctor(@RequestBody Doctor d){
		return this.service.saveBasicDetails(d);
		
	}
	
	@PostMapping(value="/register/basic-details",
		    consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
)
	public ResponseEntity<?> saveBasicDetails(@RequestHeader("X-User-Id") String user_id,
	        @RequestHeader("X-Role") String role,@Valid @RequestPart("details") String docx,@RequestPart("file") MultipartFile file,BindingResult bindingResults) throws IOException{
				if(docx!=null && user_id!=null) {
					ObjectMapper mapper=new ObjectMapper();
					Doctor doctor=mapper.readValue(docx, Doctor.class);
					doctor.setDoctor_name(doctor.getDoctor_name());
					doctor.setAuth_user_id(Integer.parseInt(user_id));
					doctor.setRegistrationNumber(doctor.getRegistrationNumber());
				    doctor.setPhone_number(doctor.getPhone_number());
					doctor.setCreation_date(LocalDateTime.now());	
					doctor.setFileName(file.getName());
					Doctor d=doctor;
					Doctor x=this.doctorRepo.save(d);
					return ResponseEntity.status(HttpStatus.CREATED).body(x);
				}
				return ResponseEntity.badRequest().build();
	}


 
	
	@GetMapping("check/{id}") 
	public ResponseEntity<?> findByNameAndPhone(@PathVariable int id){
		DoctorDetailsToAppointment data=this.service.findNameAndPhoneById(id);
		if(data==null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(data);
	}


	
	
	 
	
	@GetMapping("/available-doctors")
	public ResponseEntity<?> getAvailableDoctors()
	{
		List<Integer> li= this.service1.availableDoctors();
		if(li.isEmpty())
		{
			throw new ResourceNotFoundException("OOPS!! NO DOCTORS AVAILABLE NOW!! \n"
					+ "we will let you know once any doctor available.");
		}
		return ResponseEntity.status(HttpStatus.OK).body(li);
	}
	
	
	@PostMapping("/add-available")
	public Available_Doctor_Now create(@RequestBody Available_Doctor_Now d)
	{
		return this.service1.add(d); 
		
	}
//	@GetMapping("/{id}/download")
//	public ResponseEntity<byte[]> download(@PathVariable("id") int id){
//		 System.out.print("Download id: "+id);
////		 int idx=Integer.parseInt(id);
//		Doctor d=this.service.getById(id);
//		if(d.getRegistrationFile()==null || id==0 || id<0) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//		}
//		   String fileType = "application/octet-stream"; // Default binary file type
//		    try {
//		        fileType = Files.probeContentType(Paths.get(d.getFileName()));
//		        System.out.println(d.getFileName());
//		    } catch (Exception e) {      
//		        e.printStackTrace();
//		    }
//		    return ResponseEntity.ok()
//		            .contentType(MediaType.parseMediaType(fileType))
//		            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + d.getFileName() + "\"")
//		            .body(d.getRegistrationFile());		
//	}
//	
//	@GetMapping("/image/{file_name}")
//	public ResponseEntity<Resource> getImage(@PathVariable("file_name")  String filename) {
//	    try {
//	        Path filePath = Paths.get("add/doctor").resolve(filename).normalize();
//	        Resource resource = new UrlResource(filePath.toUri());
//
//	        if (resource.exists()) {
//	            return ResponseEntity.ok()
//	                    .contentType(MediaType.IMAGE_JPEG) // Change type if needed
//	                    .body(resource);
//	        } else {
//	            return ResponseEntity.notFound().build();
//	        }
//	    } catch (MalformedURLException e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	    }
//	}
//	@GetMapping("/{id}/preview")
//	public ResponseEntity<byte[]> preview(@PathVariable("id") int id) {
//	    Doctor d = service.getById(id);
//
//	    if (d == null || d.getRegistrationFile() == null) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//	    }
//
//	    String fileType = "application/octet-stream";
//	    try {
//	        // Guess MIME type from filename
//	        fileType = Files.probeContentType(Paths.get(d.getFileName()));
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//
//	    return ResponseEntity.ok()
//	            .contentType(MediaType.parseMediaType(fileType))
//	            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + d.getFileName() + "\"")
//	            .body(d.getRegistrationFile());
//	}

	


	
	
	

}
