package com.example.demo1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.dto.DoctorScheduleResponse;
import com.example.demo1.repo.DoctorRepo;
import com.example.demo1.service.DoctorScheduleService;

@RestController
@RequestMapping(value = "/doctor/schedule")
public class DoctorSchedulesController {

    private final DoctorScheduleService scheduleService;
    //private final RequestContextDetails requestContextDetails;
    private final DoctorRepo doctorRepo;
    @Value("${secret_key}")
    private String secert_key;

    public DoctorSchedulesController(DoctorScheduleService scheduleService, DoctorRepo doctorRepo){
            //RequestContextDetails requestContextDetails) {
        this.scheduleService = scheduleService;
        this.doctorRepo = doctorRepo;
      //  this.requestContextDetails = requestContextDetails;

    }

    // @PostMapping("/v1/saveOrUpdate")
    // public ResponseEntity<?> saveDoctorSchedules(
    //         @RequestBody DoctorScheduleRequest requestData) {
    //     try {
    //         String authUserId = requestContextDetails.getUserId();
    //         String role = requestContextDetails.getRole();
    //         // String secretKey = requestContextDetails.getSecretKey();
    //         long converted_authUserId = Long.parseLong(authUserId);
    //         System.out.println("ID --> " + converted_authUserId);

    //         Optional<Doctor> doctorDetails = this.doctorRepo.findByAuthUserId(converted_authUserId);
    //         if (role.equalsIgnoreCase("doctor") && !doctorDetails.isEmpty()) {
    //             DoctorScheduleResponse response = this.scheduleService.saveOrUpdateSchedule((long) converted_authUserId,
    //                     requestData);
    //             return ResponseEntity.status(HttpStatus.OK).body(response);
    //         }
    //         return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No Permission to do this action");
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    //     }

    // }

    @GetMapping("/fetch")
    public ResponseEntity<?> getAllSchedulesByDoctors(@RequestHeader("X-User-Role") String role,@RequestParam (required=false) Long id,Authentication authentication) {
        // String authUserId = requestContextDetails.getUserId();
        // String role = requestContextDetails.getRole();
        String authUserId=(String) authentication.getPrincipal();
        long converted_authUserId=Long.parseLong(authUserId);
        //int converted_authUserId = Integer.parseInt(authUserId);
      //  String role=RequestHeader.
        // uthentication.getAuthorities().stream()
        //         .findFirst()
        //         .map(GrantedAuthority::getAuthority)
        //         .orElse(null);

        System.out.println("ID called from Token  " + authUserId);
        

         try {

            if (role.equalsIgnoreCase("patient")) {
                List<DoctorScheduleResponse> listOfSchedules = this.scheduleService
                        .getSchedules(id);
                System.out.println("Patient called with id : "+id+ "role "+role);
                return ResponseEntity.status(HttpStatus.OK).body(listOfSchedules);

            }
           else if(role.equalsIgnoreCase("doctor"))
            {
                System.out.println(role+" Called ");
            List<DoctorScheduleResponse> listOfSchedules = this.scheduleService
            .getSchedules(converted_authUserId);
            return ResponseEntity.status(HttpStatus.OK).body(listOfSchedules);
            }

          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Schedules found");
        } 
        catch (Exception e) 
        {
            return ResponseEntity.internalServerError().body("No found....");
        }
    

    }

}
