package com.example.demo1.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
 import org.springframework.http.MediaType;
 import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fast2sms", url = "https://www.fast2sms.com")
public interface Fast2SmsClient {
	@PostMapping(value = "/dev/bulkV2", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String sendSms(
        @RequestHeader("authorization") String apiKey,
        @RequestParam("sender_id") String senderId,
        @RequestParam("message") String message,
        @RequestParam("language") String language,
        @RequestParam("route") String route,
        @RequestParam("numbers") String numbers
    );

}
