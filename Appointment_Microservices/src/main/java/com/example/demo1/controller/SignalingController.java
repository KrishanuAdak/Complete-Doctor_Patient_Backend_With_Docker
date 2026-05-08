package com.example.demo1.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SignalingController {
    

    @MessageMapping("/offer")
    @SendTo("/topic/offer")
    public String offer() {
        return "offer";
    }
    

    @MessageMapping("/answer")
    @SendTo("/topic/answer")
    public String answer() {
        return "answer";
    }

    @MessageMapping("/candidate")
    @SendTo("/topic/candidate")
    public String candidate() {
        return "candidate";
    }

}
