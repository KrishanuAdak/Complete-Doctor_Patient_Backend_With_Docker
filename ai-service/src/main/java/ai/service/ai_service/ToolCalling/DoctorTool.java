package ai.service.ai_service.ToolCalling;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ai.service.ai_service.dto.DoctorScheduleResponse;
import ai.service.ai_service.openfeign.DoctorFeign;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

@Component
public class DoctorTool {
    private final DoctorFeign doctorFeign;
    private final ObjectMapper objectMapper; // add this

    public DoctorTool(DoctorFeign doctorFeign, ObjectMapper objectMapper) {
        this.doctorFeign = doctorFeign;
        this.objectMapper = objectMapper;
    }

    @Tool(name = "available_doctors_today", value = "Get a list of doctors available today when user ask for specific location or city but these doctors are only available in mecheda not for other city.don't call this tool for doctor's schedule.")
    public String availableDoctorsToday(@P("location") String location) {
        System.out.println("Available tool called");
        System.out.println("DoctorTool called with location: " + location);
        return "Dr. Smith, Dr. Johnson, Dr. Lee available in " + location;
    }

    @Tool(name = "get_schedules_for_a_specific_doctor_for_the_week", value = "Fetches the weekly schedule of a specific doctor by doctor_name and city. "
            +
            "Call this whenever user asks about a doctor's schedule, availability, timings, or slots. " +
            "If doctor_name or city is missing, ask the user for them." +
            "Important: 1.Do not take courtesy or previous word before doctor name for search doctor name and city only take doctor name and city." +
            "2.If the result of this tool is empty or any exception occured return like doctor's record not found"
       // "3.Take  role from this tool first which we are getting from X-User-Role variable , start giving response with respect to  role which are we are getting from X-User-Role. always start with greeting and user role"
    )
    public String getAllSchedulesForSpecificDoctorByCity(@P("city") String city, @P("doctor_name") String doctor_name,
            @RequestHeader("X-User-Role") String role)
            throws JsonProcessingException {
                if(role.equalsIgnoreCase("patient")){
                    System.out.println("Patienttttttt-------");
                    return "You are not authorized to get the result";
                }
        System.out.println("schedule tool called "+role);
        long detailsId = this.doctorFeign.getDoctorDetailsByNameAndCity(doctor_name, city);
        if (detailsId == 0) {
            return "No Such Doctor Found.";

        }
        List<DoctorScheduleResponse> lists = this.doctorFeign.getAllSchedules(detailsId);
        if (lists.isEmpty()) {
            return "No such doctor records found!";
        }
        return objectMapper.writeValueAsString(lists);

    }

}
