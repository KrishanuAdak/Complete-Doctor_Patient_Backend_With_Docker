package com.example.demo1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.dto.DoctorScheduleRequest;
import com.example.demo1.dto.DoctorScheduleResponse;
import com.example.demo1.model.Doctor;
import com.example.demo1.service.DoctorScheduleService;
import com.example.demo1.service.DoctorService;

@RestController
@RequestMapping(value = "/doctor/schedule")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorSchedulesController {

    private final DoctorScheduleService scheduleService;
    private final DoctorService doctorService;

    public DoctorSchedulesController(DoctorScheduleService scheduleService,DoctorService doctorService){
        this.scheduleService = scheduleService;
        this.doctorService=doctorService;

    }
   // @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/v1/saveOrUpdate")
    public ResponseEntity<?> saveDoctorSchedules(
           @RequestHeader("X-User-Id") String doctorId,
           @RequestHeader("X-User-Role") String role,
           @RequestBody DoctorScheduleRequest requestData
        ) {
        try {
            long converted_authUserId = Long.parseLong(doctorId);
            System.out.println("ID --> " + converted_authUserId);

            Optional<Doctor> doctorDetails = this.doctorService.getDoctorByUserId(converted_authUserId);
            if (!doctorDetails.isEmpty()) {
                DoctorScheduleResponse response = this.scheduleService.saveOrUpdateSchedule((long) converted_authUserId,
                        requestData);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No Permission to do this action");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/fetch")
    public ResponseEntity<?> getAllSchedulesByDoctors(@RequestHeader("X-User-Role") String role,@RequestParam (required=false) Long id,Authentication authentication) {
        String authUserId=(String) authentication.getPrincipal();
        long converted_authUserId=Long.parseLong(authUserId);
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
