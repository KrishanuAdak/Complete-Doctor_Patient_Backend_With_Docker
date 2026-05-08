package com.example.demo1.controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo1.service.SmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
@RestController
public class SmsController {
	 @Autowired
	    private SmsService smsService;

	    @PostMapping("/send")
	    public String sendSms(@RequestParam String number, @RequestParam String message) {
	        return smsService.sendSms(number, message);
	    }

}
