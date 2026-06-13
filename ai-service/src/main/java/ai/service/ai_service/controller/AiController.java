package ai.service.ai_service.controller;

<<<<<<< HEAD
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
=======
import org.springframework.http.MediaType;
>>>>>>> test
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.service.ai_service.ToolCalling.AppointmentAgent;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "http://localhost:4200")  // ← add this line
public class AiController {

    private final AppointmentAgent agent;

<<<<<<< HEAD
    @GetMapping("/ask")
    public String askQuestion(@RequestParam String query) throws JsonProcessingException {
        return ragService.searchAndAnswer(query);
=======
    public AiController(AppointmentAgent agent) {
        this.agent = agent;
>>>>>>> test
    }

    @GetMapping(value="/chat",produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestParam String query) {
        return agent.chatAndReply(query);
    }

}
