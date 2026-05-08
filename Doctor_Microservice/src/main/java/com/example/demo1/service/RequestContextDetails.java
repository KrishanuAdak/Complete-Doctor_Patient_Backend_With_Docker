package com.example.demo1.service;
import com.krishanu.security.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class RequestContextDetails {
    public String getUserId(){
        return RequestContext.getUserId();
    }
    public String getRole(){
        return RequestContext.getRole();
    }
    public String getUsername(){
        return RequestContext.getUsername();
    }
    public String getSecretKey() {
        return RequestContext.getSecretKey();
    }

}
