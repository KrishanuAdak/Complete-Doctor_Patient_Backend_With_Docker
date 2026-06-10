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
    
}
   