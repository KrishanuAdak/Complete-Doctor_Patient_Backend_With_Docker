package ai.service.ai_service.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ai.service.ai_service.ToolCalling.AppointmentAgent;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AppointmentAgent agent;

    public AiController(AppointmentAgent agent) {
        this.agent = agent;
    }

    @GetMapping(value="/chat",produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestParam String query) {
        return agent.chatAndReply(query);
    }

}
