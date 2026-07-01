package com.example.demo1.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.service.SmsService;
@RestController
public class SmsController {
	 private final SmsService smsService;

	public SmsController(SmsService smsService) {
		this.smsService = smsService;
	}

	    @PostMapping("/send")
	    public String sendSms(@RequestParam String number, @RequestParam String message) {
	        return smsService.sendSms(number, message);
	    }

}
