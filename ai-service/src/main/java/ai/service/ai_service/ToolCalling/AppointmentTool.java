package ai.service.ai_service.ToolCalling;

import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

@Component
public class AppointmentTool {

    @Tool(name = "get_appointment_booked_for_today",
      value = "Call this tool ONLY when user asks about COUNT or NUMBER or TOTAL of appointments booked today.")
    public int getAppointmentBookedForToday(@P ("location") String location) {
        System.out.println("AppointmentTools called with location: " + location);
        return 6;
    }
    @Tool(name="booking_appointment_with_doctor" 
    ,value="this tool will be invoked when user asks book appointment with any doctor otherwise do not invoke it. but ask for yes or no when user asks for book appointment with any doctor if yes then only invoke it.")

    public String bookAppointmentWithDoctor(@P ("doctorname") String doctor_name){
      return "Your appointment booked successfully with"+ doctor_name;

    }
    
}
   