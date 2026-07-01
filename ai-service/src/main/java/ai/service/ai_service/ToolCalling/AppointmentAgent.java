package ai.service.ai_service.ToolCalling;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface AppointmentAgent {

   @SystemMessage("""
         You are a helpful medical assistant for an appointment booking system.
         You have access to multiple tools and you MUST use the correct tool: -
         To get DOCTOR LIST → use available_doctors_today tool.
          To get APPOINTMENT COUNT → use get_appointment_counts_for_today tool.
          IMPORTANT: If user asks about appointment count or number of appointments, you MUST call get_appointment_counts_for_today tool,
          if user asks about schedules of any doctor from specific city -> use get_schedules_for_a_specific_doctor_for_the_week this tool.
           Never answer appointment counts from your own knowledge.""")

   public String chatAndReply(@UserMessage String message);

}
