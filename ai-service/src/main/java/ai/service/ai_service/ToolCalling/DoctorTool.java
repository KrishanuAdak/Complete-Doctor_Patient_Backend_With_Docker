package ai.service.ai_service.ToolCalling;

import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

@Component
public class DoctorTool {
    @Tool(name = "available_doctors_today",
     value = "Get a list of doctors available today when user ask for specific location or city")
    public String availableDoctorsToday(@P ("location") String location){
        System.out.println("DoctorTool called with location: " + location);
        return "Dr. Smith, Dr. Johnson, Dr. Lee available in " + location;

    }

}
