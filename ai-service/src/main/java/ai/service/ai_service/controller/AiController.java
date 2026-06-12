package ai.service.ai_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.service.ai_service.ToolCalling.AppointmentAgent;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = "http://localhost:4200") // ← add this line
public class AiController {

    @Autowired
    private AppointmentAgent agent;

    @GetMapping("/chat")
    public String chat(@RequestParam String query) {

        return agent.chatAndReply(query);
    }

}
