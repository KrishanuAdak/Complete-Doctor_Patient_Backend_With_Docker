package ai.service.ai_service.openfeign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ai.service.ai_service.dto.DoctorScheduleResponse;

@FeignClient(name="doctor-service")
public interface DoctorFeign {
    @GetMapping("/doctor/schedule/fetch")
    public List<DoctorScheduleResponse> getAllSchedules(@RequestParam (required=false) long id);
    @GetMapping("doctor/feign/details")
    public long getDoctorDetailsByNameAndCity(@RequestParam String name,@RequestParam String city);



}
